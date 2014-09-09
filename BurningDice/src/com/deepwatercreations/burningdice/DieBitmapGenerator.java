package com.deepwatercreations.burningdice;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DieBitmapGenerator {
	private Bitmap sourceBitmap;
	private int diewidth;
	private int dieheight;
	private int numdiceX = 6;
	
	public DieBitmapGenerator(Resources res, int bitmapId) {
			sourceBitmap = BitmapFactory.decodeResource(res, bitmapId); //TODO: Needs error handling
			diewidth = sourceBitmap.getWidth() / numdiceX;
			dieheight = sourceBitmap.getHeight();
	}
	
	public Bitmap getDieGraphic(int dieValue){
		return Bitmap.createBitmap(sourceBitmap, (dieValue - 1) * diewidth, 0, diewidth, dieheight)	;	
	}
	
	public int getWidth(){
		return diewidth;
	}
	public int getHeight(){
		return dieheight;
	}
	
}
