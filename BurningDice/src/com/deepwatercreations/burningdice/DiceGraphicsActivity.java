package com.deepwatercreations.burningdice;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

/**
 * This activity creates a graphical representation of the dice and allows the player to interact with them.
 * @author Dylan Craine
 */
public class DiceGraphicsActivity extends Activity{

	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dice_graphics);
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
//        	getActionBar().setDisplayHomeAsUpEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(128, 256, Config.RGB_565);
     
	 }

	
}
