/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's settings.                                  *
********************************************************/
package org.dynamac.settings;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Settings {
	public static String getLastLoaderDirectory(){
		if(readRegistry("LastLoaderLocation").equals("null")){
			writeToRegistry("LastLoaderLocation", getDefaultDirectory());
		}
		return readRegistry("LastLoaderLocation");
	}
	public static String getLastScriptDirectory(){
		if(readRegistry("LastScriptLocation").equals("null")){
			writeToRegistry("LastScriptLocation", getDefaultDirectory());
		}
		return readRegistry("LastScriptLocation");
	}
	public static String getDefaultDirectory(){
		try {
			return new File(".").getCanonicalPath();
		} catch (IOException e) {
			return System.getProperty("user.dir");
		}
	}
	public static void writeToRegistry(String key, String value){
		Preferences userPref = Preferences.userRoot();
		userPref.put(key, value);
	}
	public static String readRegistry(String key){
		Preferences userPref = Preferences.userRoot();
		String s = userPref.get(key, "null");
		return s;
	}
}
