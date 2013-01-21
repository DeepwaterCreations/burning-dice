package com.deepwatercreations.burningdice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Holds data for a single roll. When the doRoll method is invoked, generates numbers and calculates successes,
 * overall success/failure, and difficulty level of roll. Also has options for spending Artha post-roll and
 * re-rolling dice due to open ended rolls. 
 * 
 * @author DeepwaterCreations
 *
 */
public class Roll implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Random dicebag;
	
	private int exponent;
	private int extraDice = 0;
	private int arthaDice = 0;
	
	private int obstacle;
	private int diceShade = 4; //Gray is 3, White is 2.
	private ArrayList<Integer> results;
	private int successes = 0;
	
	private boolean openEnded = false;
	private boolean rollMade = false; 
	
	private int fateSpent = 0;
	private int personaSpent = 0;
	private int deedsSpent = 0;
	
	public Roll(int exp){
		dicebag = new Random();	
		results = new ArrayList<Integer>();
		
		exponent = exp;
		
		//Bare minimum.
		obstacle = 1;
	}
	
	public Roll(){
		this(1);
	}
	
	public void setExponent(int exp){
		if(exp > 0)
			exponent = exp;
	}	
	
	public void setObstacle(int ob){
		if(ob > 0)
			obstacle = ob;
	}
	
	public void setShade(int shade){
		if(shade >= 2 && shade <= 4)
			diceShade = shade;
	}
	
	public void makeOpenEnded(){
		openEnded = true;
	}
	
	public void makeNotOpenEnded(){
		openEnded = false;
	}
	
	public void addDice(int extras){
		extraDice += extras;
	}
	
	public void subtractDice(int superfluousExtras){
		if(extraDice - superfluousExtras >= 0)
			extraDice -= superfluousExtras;
	}
			
	public void doRoll(){
		if(!rollMade){ //TODO: Consider changing this. Might be nicer to reroll and make sure that
			//old results are removed.
			for(int i = 0; i < getTotalDice(); i++){
				int rollnum = dicebag.nextInt(6) + 1;
				results.add(rollnum);
				if(rollnum >= diceShade)
					successes++;
				if(openEnded && rollnum == 6)
					extraDice++; //Fate hasn't been spent yet, so this is Steel or something.
			}
			rollMade = true;
		}
	}
	
	public ArrayList<Integer> getResults(){
		return results;		
	}
	
	public boolean getSuccess(){
		return successes >= obstacle; 
	}
	
	public int getNumSuccesses(){
		return successes;
	}
	
	public int getTotalDice(){
		return exponent + extraDice + arthaDice;
	}
	
	/**
	 * "Luck - A player may spend a fate point to make the dice of a single roll open-ended... If
	 * the roll is already open ended...then the player may reroll a single traitor (which is
	 * not open-ended). Luck is purchased after the dice have been rolled." p. 66 BWG   
	 */
	public void spendFate(){
		if(fateSpent > 0)
			System.out.println("ERROR: Can't spend more than one fate on a roll!");
		else if(!rollMade)
			System.out.println("ERROR: Can't activate Luck until roll has been made!");
		else{
			if(!openEnded){
				openEnded = true;
				rollFateOpenEnded();
			}
			else
				//Reroll a traitor - TODO: Make this not open ended!
				for(int i = 0; i < results.size(); i++){
					if(results.get(i) < diceShade){
						reroll(i);
						break;
					}
				}
			fateSpent ++;
		} 
	}
	
	/**
	 * "Boon - A player may choose to spend up to three persona points on a skill or stat test. Each
	 * point spent grants a bonus die (+1D) to roll on the test. A Boom must be announced before the
	 * dice are cast." p. 66 BWG 
	 * 
	 * @param points the number of persona points to spend. Total for a given roll must be between 0-3.
	 */
	public void spendPersona(int points){
		if(personaSpent + points > 3)
			System.out.println("ERROR: Can't spend more than three persona on Boons");
		else if(rollMade)
			System.out.println("ERROR: Can't activate Boons after roll has been made!");
		else{
			arthaDice += points;
			personaSpent += points;
		}
	}
	
	/**
	 *  "Divine Inspiration - One point of deeds artha may be spent to double the exponent of a single stat, skill or attribute test. 
	 *  ...when spending a deeds point on a test that gains additional dice from FoRKs, helping, stances
	 *  or other means, only double the base skill/stat and then add in the extra dice. Divine Inspiration must be announced
	 *  before the dice are cast." p. 67 BWG   
	 *  
	 * "Saving Grace - A deeds point may be spent to reroll all dice that failed to come up successes on any given test.
	 * This may be used for equipment dice like armor, shields and guns. Obviously, Saving Grace is
	 * announced after the dice have been rolled." p. 67 BWG 
	 */
	public void spendDeeds(){
		if(!rollMade){
			//Divine Inspiration
			arthaDice += exponent;	
		}
		else{
			//Saving Grace
			for(int i = 0; i < results.size(); i++){
				if(results.get(i) < diceShade)
					reroll(i);
			}
		}
		deedsSpent++; 
	}
	
	private void rollFateOpenEnded(){
		for(int i = 0; i < results.size(); i++){
			if(results.get(i) == 6){
				rollArthaDie();
			}
		}
	}
	
	private int rollArthaDie(){
		int rollnum = dicebag.nextInt(6) + 1;
		results.add(rollnum);
		if(rollnum >= diceShade)
			successes++;
		arthaDice++;
		return rollnum; 
	}
	
	private void reroll(int index){
		int rollnum = dicebag.nextInt(6) + 1;
		results.set(index, rollnum);
		if(rollnum >= diceShade)
			successes++; //WARNING: Assumes we aren't re-rolling successes! Which we shouldn't be doing anyway.
	}

}
