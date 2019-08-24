package com.ebs.baseutility.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {

	private static final String PREFS_NAME = "preferences";

	private static SharedPreference shared = new SharedPreference( );

	private SharedPreference()
	{
	}
	
	public static SharedPreference getInstance() {
		return shared;
	}

	public  void SaveSharedPreference(Context context, final String key, final String value){
		try {
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
			Editor editor = settings.edit();

			editor.putString(key, value);

			editor.apply();
		} catch (Exception e){e.printStackTrace();}
	}

	public String GetSharedPreference(Context context, String key, String defaultValue){
		try {
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
			return settings.getString(key, defaultValue);
		} catch (Exception e){e.printStackTrace();}
		return "";
	}

	public  void SaveSharedPreference(Context context, final String key, final boolean value){
		try {
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
			Editor editor = settings.edit();

			editor.putBoolean(key, value);

			editor.apply();
		} catch (Exception e){e.printStackTrace();}
	}
	public boolean GetSharedPreference(Context context, String key, boolean defaultValue){
		try {
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
			return settings.getBoolean(key, defaultValue);
		} catch (Exception e){e.printStackTrace();}
		return false;
	}
}
