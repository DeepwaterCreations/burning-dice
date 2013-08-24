package com.deepwatercreations.burningdice;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

@SuppressLint("NewApi")
public class RollDisplayActivity extends Activity {

	private Roll results;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_display);
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
        	getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        results = (Roll) intent.getSerializableExtra(MainActivity.EXTRA_ROLLRESULTS);
        
        setupText();
        
    }
    
    public void spendFate(View view){
    	results.spendFate();   
    	setupText();
    }
    
    public void spendDeeds(View view){
    	results.spendDeeds();
    	setupText();
    }

    /**
     * Sets the text fields to the appropriate numbers.
     */
    public void setupText(){
        Resources res = getResources();
     
               
        //String that says "Success!" or "Failure!"
        TextView isSuccessText = (TextView)findViewById(R.id.is_success_field);
        String successorfail = "";    
        if(results.getMargin() >= 0){
        	successorfail = "Success!";
        	isSuccessText.setTextColor(res.getColor(R.color.success_green));
        }
        else{
        	successorfail = "Failure!";
        	isSuccessText.setTextColor(res.getColor(R.color.failure_red));
        }
        isSuccessText.setText(successorfail);
        
        //Number of dice rolled
        TextView numDiceText = (TextView)findViewById(R.id.num_dice_field);
        String numdice = String.valueOf(results.getTotalDice()); 
        numDiceText.setText(numdice);
        
        //The raw numbers that were rolled
        TextView dieResultsText = (TextView)findViewById(R.id.die_results_field);
        String dieresults = "";
        for(int i : results.getResults()){
        	dieresults += (String.valueOf(i) + " ");
        } 
        dieResultsText.setText(dieresults);
        
        //Difficulty of test earned
        TextView difficultyText = (TextView)findViewById(R.id.difficulty_field);
        String difficulty = "";
        switch(results.getDifficulty()){
        	case PLAYER_CHOICE:
        		difficulty = "Routine or Difficult";
        		break;
        	case ROUTINE:
        		difficulty = "Routine";
        		break;
    		case DIFFICULT:
    			difficulty = "Difficult";
    			break;
    		case CHALLENGING:
    			difficulty = "Challenging";
    			break;
        } 
        difficultyText.setText(difficulty);
    
        //Number of Successes
        TextView numSuccessText = (TextView)findViewById(R.id.num_success_field);
        String numsuccess = String.valueOf(results.getMargin());
        numSuccessText.setText(numsuccess);
        
        //Obstacle
        TextView obText = (TextView)findViewById(R.id.ob_field);
        String ob = String.valueOf(results.getObstacle());
        obText.setText(ob);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_roll_display, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
