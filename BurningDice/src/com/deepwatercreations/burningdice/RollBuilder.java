package com.deepwatercreations.burningdice;

import java.util.ArrayList;

public class RollBuilder {

	private int numDice;
	private int obstacle;
	private int exponent;
	private int diceShade = 4; //Gray is 3, White is 2.
	private boolean openEnded = false;

	private int arthadice = 0;  
	private int personaSpent = 0;
	private int deedsSpent = 0;
	
	public RollBuilder(int _exponent, int _obstacle){
		numDice = _exponent;
		exponent = _exponent;
		obstacle = _obstacle;
	}
	
	public RollBuilder(int _exponent){
		this(_exponent, 1);
	}
	
	public RollResults buildRoll(){
		return new RollResults(numDice, obstacle, diceShade, openEnded, arthadice, personaSpent, deedsSpent);		
	}
	
	public RollBuilder shade(int _shade){
		diceShade = _shade;
		return this;
	}
	
	public RollBuilder openEnd(){
		openEnded = true;
		return this;
	}
	
	public RollBuilder spendPersona(int _persona){
		if(_persona + personaSpent <= 3){
			personaSpent += _persona;
			arthadice += _persona;
		}
		else
			System.out.println("ERROR: You can't buy that many dice with Persona!");
		return this;			
	}
	
	public RollBuilder spendDeeds(){
		if(deedsSpent == 0){
			deedsSpent = 1;
			arthadice += exponent;
		}
		else
			System.out.println("ERROR: You can't spend more than one Deeds before a roll!");
		return this;
	}
	
	public RollBuilder addDice(int n){
		numDice += n;
		return this;
	}
	
}
