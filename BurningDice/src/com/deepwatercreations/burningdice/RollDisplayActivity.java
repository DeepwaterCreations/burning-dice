package com.deepwatercreations.burningdice;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

@SuppressLint("NewApi")
public class RollDisplayActivity extends Activity {

	private Roll results;
	private DieBitmapGenerator bmpGenerator;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_display);
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
        	getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        results = (Roll) intent.getSerializableExtra(MainActivity.EXTRA_ROLLRESULTS);
        bmpGenerator = new DieBitmapGenerator(getResources(), R.drawable.dice_test);
        
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
        
        //Dice Display
        ImageView diceView = (ImageView)findViewById(R.id.die_results_images);
        diceView.setImageBitmap(bmpGenerator.getDieGraphic(results.getResults().get(0)));
        //diceView.setImageResource(R.drawable.dice_test);
        //Bitmap dieBm = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888);
        //Bitmap dieBm = BitmapFactory.decodeResource(R.drawable.dice_test, );
        //diceView.setImageBitmap(dieBm);
        //dieBm.eraseColor(Color.MAGENTA);
        
//        //Number of dice rolled
//        TextView numDiceText = (TextView)findViewById(R.id.num_dice_field);
//        String numdice = String.valueOf(results.getTotalDice()); 
//        numDiceText.setText(numdice);
//        
//        //The raw numbers that were rolled
//        TextView dieResultsText = (TextView)findViewById(R.id.die_results_field);
//        String dieresults = "";
//        for(int i : results.getResults()){
//        	dieresults += (String.valueOf(i) + " ");
//        } 
//        dieResultsText.setText(dieresults);
        
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
        
        //Disable Artha buttons if Artha has been spent.
        //TODO: Also disable if these results aren't the most recent in history.  
        Button deedsButton = (Button)findViewById(R.id.deeds_button);
        deedsButton.setEnabled(results.isDeedsAvailable());
        Button fateButton = (Button)findViewById(R.id.fate_button);
        fateButton.setEnabled(results.isFateAvailable());
        
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
