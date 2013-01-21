package com.deepwatercreations.burningdice;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
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
        
//        LinearLayout llayout = new LinearLayout(this);
//        
//        TextView successesview = new TextView(this);
//        successesview.setTextSize(30);
//        CharSequence successestext = "You got " + results.getNumSuccesses() + " successes on " + results.getTotalDice() + " dice!";
//        successesview.setText(successestext);
//        
//        TextView resultsview = new TextView(this);
//        successesview.setTextSize(30);
//        String resultstextS = "Results: ";
//        for(int num : results.getResults()){
//        	resultstextS += num;
//        	resultstextS += " ";
//        }
//        CharSequence resultstext = resultstextS;
//        resultsview.setText(resultstext);
//        
//        llayout.addView(successesview);
//        llayout.addView(resultsview);
//                
//        setContentView(llayout);        
        
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
