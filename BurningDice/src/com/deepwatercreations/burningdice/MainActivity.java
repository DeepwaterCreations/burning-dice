package com.deepwatercreations.burningdice;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;

public class MainActivity extends Activity {
	
	 public final static String EXTRA_ROLLRESULTS = "com.deepwatercreations.burningdice.ROLLRESULTS";
	
	//DBAdapter db = new DBAdapter(this);
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Typeface titlefont = Typeface.createFromAsset(getAssets(), "LiberationSerif-BoldItalic.ttf");
        ((TextView)findViewById(R.id.title_label)).setTypeface(titlefont);
        
        Button buttonq1 = (Button) findViewById(R.id.qroll1);
        buttonq1.setOnClickListener(mAddListener);
        Button buttonq2 = (Button) findViewById(R.id.qroll2);
        buttonq2.setOnClickListener(mAddListener);
        Button buttonq3 = (Button) findViewById(R.id.qroll3);
        buttonq3.setOnClickListener(mAddListener);
        Button buttonq4 = (Button) findViewById(R.id.qroll4);
        buttonq4.setOnClickListener(mAddListener);
        Button buttonq5 = (Button) findViewById(R.id.qroll5);
        buttonq5.setOnClickListener(mAddListener);
        Button buttonq6 = (Button) findViewById(R.id.qroll6);
        buttonq6.setOnClickListener(mAddListener);
        Button buttonq7 = (Button) findViewById(R.id.qroll7);
        buttonq7.setOnClickListener(mAddListener);
        Button buttonq8 = (Button) findViewById(R.id.qroll8);
        buttonq8.setOnClickListener(mAddListener);
        Button buttonq9 = (Button) findViewById(R.id.qroll9);
        buttonq9.setOnClickListener(mAddListener);
        Button buttonq10 = (Button) findViewById(R.id.qroll10);
        buttonq10.setOnClickListener(mAddListener);
        
    }

    private OnClickListener mAddListener = new OnClickListener(){
    	public void onClick(View v){
    		Roll roll;
    		
    		switch(v.getId()){
    		
    		//Dang, there has got to be a better way to do this!
    		//These are the quick roll buttons, for if the user just wants to roll n dice without typing in a bunch of bleedin' numbers.
    		case R.id.qroll1:
    			roll = new Roll(1);
    			makeRoll(roll);
    			break;
    		case R.id.qroll2:
    			roll = new Roll(2);
    			makeRoll(roll);
    			break;
    		case R.id.qroll3:
        		roll = new Roll(3);
        		makeRoll(roll);
        		break;
    		case R.id.qroll4:
    			roll = new Roll(4);
    			makeRoll(roll);
    			break;
    		case R.id.qroll5:
    			roll = new Roll(5);
    			makeRoll(roll);
    			break;
    		case R.id.qroll6:
    			roll = new Roll(6);
    			makeRoll(roll);
    			break;
    		case R.id.qroll7:
    			roll = new Roll(7);
    			makeRoll(roll);
    			break;
    		case R.id.qroll8:
    			roll = new Roll(8);
    			makeRoll(roll);
    			break;
    		case R.id.qroll9:
    			roll = new Roll(9);
    			makeRoll(roll);
    			break;
    		case R.id.qroll10:
    			roll = new Roll(10);
    			makeRoll(roll);
    			break;
    		}
    		
    	}
    };
    
    public void makeRoll(Roll roll){
    	roll.doRoll();
    	Intent intent = new Intent(this, RollDisplayActivity.class); 		
		intent.putExtra(EXTRA_ROLLRESULTS, roll);
		startActivity(intent);
    }
     
    public void gotoBuildRoll(View view){
    	Intent intent = new Intent(this, RollBuildActivity.class);
    	startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
