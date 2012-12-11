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
import android.view.View;

public class MainActivity extends Activity {
	
	DBAdapter db = new DBAdapter(this);
	Random dicebag = new Random();
	EditText diceNumTxt;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonRoll = (Button)findViewById(R.id.roll);
        buttonRoll.setOnClickListener(mAddListener);
    }

    private OnClickListener mAddListener = new OnClickListener(){
    	public void onClick(View v){
    		switch(v.getId()){
    		
    		case R.id.roll:
    			db.open();
    			long id = 0;
        		try{
        			diceNumTxt = (EditText)findViewById(R.id.diceNum);
        			int diceNum = Integer.parseInt(diceNumTxt.getText().toString());
        			
        			RollResults results = new RollBuilder(diceNum).buildRoll();
        			
        			db.logRoll(results);
        			id = db.getEntriesCount();
        			
        			Context context = getApplicationContext();
        			CharSequence text = "You got " + results.getNumSuccesses() + " successes on " + results.getTotalDice() + " dice!";
        			int duration = Toast.LENGTH_LONG;
        			Toast toast = Toast.makeText(context, text, duration);
        			toast.show();
        			diceNumTxt.setText("");
        		}
        		catch(Exception fit){
        			Context context = getApplicationContext();
        			CharSequence text = fit.toString() + "ID= " + id;
        			int duration = Toast.LENGTH_LONG;
        			Toast toast = Toast.makeText(context, text, duration);
        			toast.show();
        		}
        		db.close();
        		break;
    		}
    		
    	}
    };
     
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
