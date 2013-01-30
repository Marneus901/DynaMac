/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's custom log system.                         *
********************************************************/
package org.dynamac.enviroment;

import java.util.ArrayList;

public class Logger {
	private static ArrayList<String> logs = new ArrayList<String>();
	private String parent;
	public Logger(){
		this.parent="[Client]";
	}
	public Logger(String parent){
		this.parent="["+parent+"]";
	}
	public void log(String s){
		//Setup custom output stream
		logs.add(parent+" "+s);
		System.out.println(parent+" "+s);
		if(logs.size()>100)
			logs.remove(0);
		Data.clientGUI.updateLog();
	}
	public static void debug(String s){
		System.out.println("[DEBUG] "+s);
	}
	public static String[] getLogs(){
		return logs.toArray(new String[]{});
	}
}
