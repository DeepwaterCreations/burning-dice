package com.deepwatercreations.burningdice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class RollResults implements Serializable {

	private static final long serialVersionUID = 1L;

	private Random dicebag;
	
	private int numDice;
	private int arthaDice = 0;
	
	private int obstacle;
	private int diceShade = 4; //Gray is 3, White is 2.
	private ArrayList<Integer> results;
	private int successes = 0;
	
	private boolean openEnded = false;
	
	private int fateSpent = 0;
	private int personaSpent = 0;
	private int deedsSpent = 0;
		
	public RollResults(int _numdice, int _obstacle, int _shade, boolean _openended, int _arthadice, int persona, int deeds){
		numDice = _numdice;
		obstacle = _obstacle;
		diceShade = _shade;
		openEnded = _openended;
		
		arthaDice = _arthadice;
		personaSpent = persona;
		deedsSpent = deeds;
		
		dicebag = new Random(); 
		results = new ArrayList<Integer>(_numdice + arthaDice);
		
		
		for(int i = 0; i < numDice + arthaDice; i++){
			int rollnum = dicebag.nextInt(6) + 1;
			results.add(rollnum);
			if(rollnum >= diceShade)
				successes++;
			if(openEnded && rollnum == 6)
				numDice++; //Fate hasn't been spent yet, so this is Steel or something.
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
		return numDice + arthaDice;
	}
	
	//TODO: Make this somehow work.
	/*
	public Difficulty getDifficulty(){
		
	}
	*/
	
	public void spendFate(){
		if(fateSpent > 0)
			System.out.println("ERROR: Can't spend more than one fate on a roll!");
		else{
			if(!openEnded){
				openEnded = true;
				rollFateOpenEnded();
			}
			else
				rerollATraitor();
			fateSpent = 1;
		} 
	}
	
	public void spendDeeds(){
		if(deedsSpent > 2)
			System.out.println("ERROR: Can't spend more than two deeds on a roll!");
		else{
			for(int i = 0; i < results.size(); i++){
				if(results.get(i) < diceShade)
					reroll(i);
			}
		}
	}
	
	private void rollFateOpenEnded(){
		for(int i = 0; i < results.size(); i++){
			if(results.get(i) == 6){
				rollADie();
				arthaDice++; 
			}
		}
	}
	
	private int rollADie(){
		int rollnum = dicebag.nextInt(6) + 1;
		results.add(rollnum);
		if(rollnum >= diceShade)
			successes++;
		return rollnum; 
	}
	
	private void rerollATraitor(){
		for(int i = 0; i < results.size(); i++){
			if(results.get(i) < diceShade){
				reroll(i);
				break;
			}
		}
	}
	
	private void reroll(int index){
		int rollnum = dicebag.nextInt(6) + 1;
		results.set(index, rollnum);
		if(rollnum >= diceShade)
			successes++;
	}
		
	
}
