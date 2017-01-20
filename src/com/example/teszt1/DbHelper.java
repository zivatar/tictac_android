package com.example.teszt1;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "ResultList.db";
	public static final String RESULT_TABLE_NAME = "results";
	public static final String RESULT_COLUMN_DATE = "date";
	public static final String RESULT_COLUMN_WIN = "win";
	public static final String RESULT_COLUMN_FAIL = "fail";
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
			"create table results " +
			"(date string primary key, win integer, fail integer)"
				);
		Log.e("com.example.teszt1", "SQL oncreate");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		db.execSQL("drop table if exists results");
		onCreate(db);
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	public boolean insertResult(int win, int fail) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues val = new ContentValues();
		val.put("date", getDateTime());
		val.put("win", win);
		val.put("fail", fail);
		db.insert("results", null, val);
		return true;
	}
	
	public boolean isDBEmpty() {
		boolean empty = true;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select count(*) from results", null);
		if (cur != null && cur.moveToFirst()) {
		    empty = (cur.getInt (0) == 0);
		}
		cur.close();
		return empty;
	}
	
	public int getWin() {
		String LOG = "com.example.teszt1";
		if ( this.isDBEmpty() ) {
			return 0;
		}
		else {	
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cur = db.rawQuery("select win from results order by date", null);
			cur.moveToFirst();
			int ret = cur.getInt(cur.getColumnIndex(RESULT_COLUMN_WIN));
            cur.close();
            return ret;
		}
	}
	
	
}
