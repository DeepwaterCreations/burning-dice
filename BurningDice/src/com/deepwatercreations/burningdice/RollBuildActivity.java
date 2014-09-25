package com.deepwatercreations.burningdice;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v4.app.NavUtils;

@SuppressLint("NewApi")
/**
 * This activity allows the user to specify the details of a roll.
 * @author Dylan Craine
 *
 */
public class RollBuildActivity extends Activity implements OnItemSelectedListener {
	
	public final static String EXTRA_ROLLRESULTS = "com.deepwatercreations.burningdice.ROLLRESULTS";

	Roll roll;
	int shade;
	int boonDice = 0;
	boolean spentDeeds = false;
	boolean openEnded = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roll_build);
		// Show the Up button in the action bar.
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
//        	getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Typeface headerfont = Typeface.createFromAsset(getAssets(), "HamletOrNot.ttf");
		Typeface regularfont = Typeface.createFromAsset(getAssets(), "TheanoOldStyle-Regular.ttf");
						
		roll = new Roll();
		
		//Todo: Use Styles, however they work. styles.xml, possibly just CSS?
		//I also want all the button and stuff to get fonts, and I want this title to be in BWG Red.
		//Use Liberation Serif bold+italics for the app's front page title - it matches the Burning Wheel
		//book cover title.
		((TextView)findViewById(R.id.buildroll_title)).setTypeface(headerfont);
		
		Spinner shadespinner = (Spinner) findViewById(R.id.build_shade_spinner);
		ArrayAdapter<CharSequence> shadeadapter = ArrayAdapter.createFromResource(this,
		        R.array.dieshades_array, android.R.layout.simple_spinner_item);
		shadeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		shadespinner.setAdapter(shadeadapter);
		shadespinner.setOnItemSelectedListener(this);
		
		Spinner boonspinner = (Spinner) findViewById(R.id.build_boon_spinner);
		ArrayAdapter<CharSequence> boonadapter = ArrayAdapter.createFromResource(this,
		        R.array.boon_array, android.R.layout.simple_spinner_item);
		boonadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		boonspinner.setAdapter(boonadapter);
		boonspinner.setOnItemSelectedListener(this);
				
		EditText disadvField = (EditText)findViewById(R.id.build_disadvantage_input);
		disadvField.setVisibility(View.GONE);//Only show this for Beginner's Luck.
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_roll_build, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	 * Adds the field data to the roll object, then makes the roll happen.
	 * Should eventually switch to a view where the user can roll the dice with a touch gesture. 
	 * 
	 * @param view
	 */
	public void makeRoll(View view){
		try{
			EditText expField = (EditText)findViewById(R.id.build_exponent_input);
			if(expField.length() > 0){ 
				int exponent = Integer.parseInt(expField.getText().toString());
				roll.setExponent(exponent);
			} else {
				//Perhaps the character has exp 0 resources and is using cash dice.
				roll.setExponent(0);
			}
			
			EditText obField = (EditText)findViewById(R.id.build_obstacle_input);
			if(obField.length() > 0){
				int obstacle = Integer.parseInt(obField.getText().toString());
				roll.setObstacle(obstacle);
			} else {
				roll.setObstacle(1); //Just like a quick roll.
			}
			
			if(roll.getBeginnersLuck()){
				EditText disadvField = (EditText)findViewById(R.id.build_disadvantage_input);
				if(disadvField.length() > 0){
					int disadvantage = Integer.parseInt(disadvField.getText().toString());
					roll.setDisadvantage(disadvantage);
				} else {
					roll.setDisadvantage(0);
				}				
			}
			
			EditText addlField = (EditText)findViewById(R.id.build_addldice_input);
			if(addlField.length() > 0){
				int addl = Integer.parseInt(addlField.getText().toString());
				roll.addDice(addl);
			} else {
				roll.addDice(0);
			}
		}
		catch(NumberFormatException integertantrum){
			Context context = getApplicationContext();
			CharSequence text = "Number Format Exception";
			int duration = Toast.LENGTH_SHORT;

			Toast.makeText(context, text, duration).show();			
		}
		
		//Make sure they have at least one die between exponent and advantage.
		if(roll.getTotalDice() < 1){
			Context context = getApplicationContext();
			CharSequence text = "You need at least one die to roll!";
			int duration = Toast.LENGTH_SHORT;

			Toast.makeText(context, text, duration).show();			
		} else {	//If they do, finish setting up	the roll and make it happen.	
			if(spentDeeds)
				roll.spendDeeds();
			
			roll.spendPersona(boonDice);
			
			roll.setShade(shade);
					
			roll.setOpenEnded(openEnded);
			
			Intent intent = new Intent(this, RollDisplayActivity.class); 		
			intent.putExtra(EXTRA_ROLLRESULTS, roll);
			roll.doRoll();
			roll = new Roll(); //Reset the roll object in case we come back to this activity.
			startActivity(intent);
		}
	}


	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
			if(parent == findViewById(R.id.build_shade_spinner)){
				shade = 4 - pos; //Bit of a hack here - we're ignoring the data in the array entirely,
				//and only reading the position in the array of the item that was selected.
				//As it happens, shades are encoded in terms of integers. As long as
				//the spinner goes Black-Gray-White, as it should, we can subtract the position
				//from 4 to get the correct number for that shade. Kind of wacky, but cleaner
				//IMO than translating the letters into the proper numbers.
			}
			else if(parent == findViewById(R.id.build_boon_spinner)){
				boonDice = pos;				
			}
	}
	
	public void selectOpenEnded(View view){
		openEnded = ((CheckBox)view).isChecked();
	}
	
	public void selectDeedsSpent(View view){
		spentDeeds = ((CheckBox)view).isChecked();		
	}

	public void toggleBeginnersLuck(View view){
		EditText expField = (EditText)findViewById(R.id.build_exponent_input);
		EditText obField = (EditText)findViewById(R.id.build_obstacle_input);
		EditText disadvField = (EditText)findViewById(R.id.build_disadvantage_input);
		
		if(((ToggleButton) view).isChecked()){
			roll.setBeginnersLuck(true);
			//Also change the hint text on the text fields and enable the Disadvantage field.
			expField.setHint(R.string.expfield_bl_hint);
			obField.setHint(R.string.obfield_bl_hint);
			disadvField.setVisibility(View.VISIBLE);
		}		
		else{
			roll.setBeginnersLuck(false);
			expField.setHint(R.string.expfield_hint);
			obField.setHint(R.string.obfield_hint);
			disadvField.setVisibility(View.GONE);
			roll.setDisadvantage(0);
		}
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
