package com.deepwatercreations.burningdice;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

public class AboutActivity extends Activity{
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_about);
        // Add the up button to the action bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
        	getActionBar().setDisplayHomeAsUpEnabled(true);
	
        Typeface titlefont = Typeface.createFromAsset(getAssets(), "LiberationSerif-BoldItalic.ttf");
        ((TextView)findViewById(R.id.about_header)).setTypeface(titlefont);
	        
	 }
}
