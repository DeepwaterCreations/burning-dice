package com.deepwatercreations.burningdice;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        // Add the up button to the action bar.
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
        
        //TODO: Get these values from the graphics, rather than hard-coding them.
        int dieWidth = 256;
        int dieMargin = 8;
        int maxRowDice = 5;
        int numRows = (int)Math.ceil((float)results.getTotalDice()/maxRowDice);
        Bitmap resultsBitmap = Bitmap.createBitmap((dieWidth + (2*dieMargin)) * Math.min(results.getTotalDice(), maxRowDice), 
        											(dieWidth + (2*dieMargin)) * numRows, 
        											Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(resultsBitmap);
        
        int xPos;
        int yPos;
        //TODO: Think this over and see if there isn't a more elegant way of doing it.
        //Finds the number of dice short of a full row for the last row and multiplies by half a die width.
        int lastRowNumDice = results.getResults().size() % maxRowDice;
        int finalRowOffset = (lastRowNumDice == 0) ? 0 : (maxRowDice - lastRowNumDice) * ((dieWidth + (2*dieMargin))/2);
        //Draws the die images to the canvas. 
        for(int i = 0; i < results.getResults().size(); i++){
        	xPos = ((i % maxRowDice) * (dieWidth + (2*dieMargin))) + dieMargin;
        	if(i >= (numRows - 1) * maxRowDice && numRows > 1) //Trying to center the first row leads to issues.
        		xPos += finalRowOffset; //This is to make the final row centered
        	yPos = (dieWidth + (2*dieMargin)) * (i/maxRowDice);
        	canvas.drawBitmap(bmpGenerator.getDieGraphic(results.getResults().get(i)), xPos, yPos, paint);
        }

        diceView.setImageBitmap(resultsBitmap);
             
        //Difficulty of test earned
        TextView difficultyText = (TextView)findViewById(R.id.difficulty_field);
        String difficulty = "Test Earned: ";
        switch(results.getDifficulty()){
        	case PLAYER_CHOICE:
        		difficulty += "Routine or Difficult";
        		break;
        	case ROUTINE:
        		if(!results.getBeginnersLuck())
        			difficulty += "Routine";
        		else
        			difficulty += "Learn Skill";
        		break;
    		case DIFFICULT:
    			if(!results.getBeginnersLuck())
    				difficulty += "Difficult";
    			else
    				difficulty += "Difficult for Root Stat";
    			break;
    		case CHALLENGING:
    			if(!results.getBeginnersLuck())
    				difficulty += "Challenging";
    			else
    				difficulty += "Challenging for Root Stat";
    			break;
        } 
        difficultyText.setText(difficulty);
    
        //Number of Successes
        TextView numSuccessText = (TextView)findViewById(R.id.num_success_field);
        String numsuccess = String.valueOf(results.getNumSuccesses());
        numsuccess += "\nSuccesses";
        numSuccessText.setText(numsuccess);
        
        //Obstacle
        TextView obText = (TextView)findViewById(R.id.ob_field);
        String ob;
        if(!results.getBeginnersLuck())
        	ob = String.valueOf(results.getObstacle());
        else
        	ob = String.valueOf((results.getObstacle() * 2) + results.getDisadvantage());        	
        ob += "\nObstacle";
        obText.setText(ob);
        
        //Margin
        TextView marginText = (TextView)findViewById(R.id.margin_field);
        String margin = String.valueOf(results.getMargin());
        margin += "\nMargin";
        marginText.setText(margin);
        		      
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
