package com.deepwatercreations.burningdice;

import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {

	int id = 0;
	public static final String KEY_ROWID = "_id";
	private static final String TAG = "DBAdapter";
	
	private static final String DATABASE_NAME = "BurningDice";
	private static final String DATABASE_TABLE = "rollHistory";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "CREATE TABLE rollHistory (_id INTEGER PRIMARY KEY autoincrement, " 
			+ "numDice INTEGER NOT NULL, arthaDice INTEGER, obstacle INTEGER, diceShade INTEGER, " +
			"success INTEGER, numSuccesses INTEGER, wasOpenEnded INTEGER, fate INTEGER, persona INTEGER, deeds INTEGER);";
	
	private final Context context;
	
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	

	public DBAdapter(Context ctx){
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);			
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(DATABASE_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
			Log.w(TAG, "Updating database from version " + oldVersion + " to " + newVersion
					 + ", which will destroy all old data.");
			db.execSQL("DROPTABLE IF EXISTS rollHistory");
			onCreate(db);
		}
		
	}
	
	//Opens the database
	public DBAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	//Closes the database
	public void close(){
		DBHelper.close();
	}
	
	//Insert a value into the database
	public long logRoll(RollResults results){
		ContentValues initialValues = new ContentValues();
		//initialValues.put("numDice" /*results.getNumDice()*/); //HEY, LISTEN! Can I store the object as a blob? Should I? 
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public int getEntriesCount(){
		Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM rollHistory", null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
		
}