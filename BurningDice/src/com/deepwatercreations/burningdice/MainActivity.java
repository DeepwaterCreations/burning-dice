package com.deepwatercreations.burningdice;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {
	
	 public final static String EXTRA_ROLLRESULTS = "com.deepwatercreations.burningdice.ROLLRESULTS";
	
	DBAdapter db = new DBAdapter(this);
	Random dicebag = new Random();
	EditText diceNumTxt;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonRoll = (Button)findViewById(R.id.roll);
       // buttonRoll.setOnClickListener(mAddListener);
    }

//    private OnClickListener mAddListener = new OnClickListener(){
//    	public void onClick(View v){
//    		RollResults results;
//    		
//    		switch(v.getId()){
//    		
//    		case R.id.qroll1:
//    			results = new RollBuilder(1).buildRoll();
//    			break;
//    		case R.id.qroll2:
//    			results = new RollBuilder(2).buildRoll();
//    			break;
//    		case R.id.qroll3:
//        		results = new RollBuilder(3).buildRoll();
//        		break;
//    		case R.id.qroll4:
//    			results = new RollBuilder(4).buildRoll();
//    			break;
//    		case R.id.qroll5:
//    			results = new RollBuilder(5).buildRoll();
//    			break;
//    		case R.id.qroll6:
//    			results = new RollBuilder(6).buildRoll();
//    			break;
//    		case R.id.qroll7:
//    			results = new RollBuilder(7).buildRoll();
//    			break;
//    		case R.id.qroll8:
//    			results = new RollBuilder(8).buildRoll();
//    			break;
//    		case R.id.qroll9:
//    			results = new RollBuilder(9).buildRoll();
//    			break;
//    		case R.id.qroll10:
//    			results = new RollBuilder(10).buildRoll();
//    			break;
//        	
//    		
////    		case R.id.roll:
////    			db.open();
////    			long id = 0;
////        		try{
////        			diceNumTxt = (EditText)findViewById(R.id.diceNum);
////        			int diceNum = Integer.parseInt(diceNumTxt.getText().toString());
////        			
////        			results = new RollBuilder(diceNum).buildRoll();
////        			
//////        			db.logRoll(results);
//////        			id = db.getEntriesCount();
//////        			
//////        			Context context = getApplicationContext();
//////        			CharSequence text = "You got " + results.getNumSuccesses() + " successes on " + results.getTotalDice() + " dice!";
//////        			int duration = Toast.LENGTH_LONG;
//////        			Toast toast = Toast.makeText(context, text, duration);
//////        			toast.show();
////        			
////        			
////        			
////        			diceNumTxt.setText("");
////        		}
////        		catch(Exception fit){
////        			Context context = getApplicationContext();
////        			CharSequence text = fit.toString() + "ID= " + id;
////        			int duration = Toast.LENGTH_LONG;
////        			Toast toast = Toast.makeText(context, text, duration);
////        			toast.show();
////        		}
////        		db.close();
////        		break;
//    		}
//    		
//    	}
//    };
    
    public void makeRoll(View view){
    	Intent intent = new Intent(this, RollDisplayActivity.class); 
    	diceNumTxt = (EditText)findViewById(R.id.diceNum);
		int diceNum = Integer.parseInt(diceNumTxt.getText().toString());
		RollResults results = new RollBuilder(diceNum).buildRoll();
		
		intent.putExtra(EXTRA_ROLLRESULTS, results);
		startActivity(intent);
    }
     
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
