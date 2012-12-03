/******************************************************
* Created by Marneus901                                *
* � 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.methods;

import java.util.HashMap;

import org.dynamac.bot.randoms.AppletRefresh;
import org.dynamac.bot.randoms.LoginHandler;
import org.dynamac.bot.script.ScriptDef;

public class Randoms {
	private static HashMap<String, ScriptDef> runningRandoms = new HashMap<String, ScriptDef>();
	public static void loadAllRandoms(){
		for(ScriptDef random : runningRandoms.values())
			random.stopScript();
		runningRandoms.clear();
		runningRandoms.put("AppletRefresh", new AppletRefresh());
		runningRandoms.put("LoginHandler", new LoginHandler());
	}
	public static void pauseAllRandoms(){
		for(ScriptDef random : runningRandoms.values()){
			if(!random.isPaused)
				random.pause();
			else
				System.out.println(random.getClass().getName()+" is already paused...");
		}
	}
	public static void pauseRandom(String name){
		ScriptDef random = runningRandoms.get(name);
		if(random!=null){
			if(!random.isPaused)
				random.pause();
			else
				System.out.println(name+" is already paused...");
		}
		else
			System.out.println("No such random is running...");
	}
	public static void startAllRandoms(){
		for(ScriptDef random : runningRandoms.values())
			random.start();
	}
	public static void startRandom(String name){
		ScriptDef random = runningRandoms.get(name);
		if(random!=null){
			if(random.isPaused)
				random.unpause();
			else if(!random.isAlive())
				random.start();
			else
				System.out.println(name+" is already running...");
		}
		else
			System.out.println("No such random is running...");
	}
	public static void stopRandom(String name){
		ScriptDef random = runningRandoms.get(name);
		if(random!=null){
			random.stopScript();
			runningRandoms.remove(name);
		}
		else
			System.out.println("No such random is running...");
	}
	public static void unpauseAllRandoms(){
		for(ScriptDef random : runningRandoms.values()){
			if(random.isPaused)
				random.unpause();
			else
				System.out.println(random.getClass().getName()+" is already unpaused...");
		}
	}
	public static void unpauseRandom(String name){
		ScriptDef random = runningRandoms.get(name);
		if(random!=null){
			if(random.isPaused)
				random.unpause();
			else
				System.out.println(name+" is already unpaused...");
		}
		else
			System.out.println("No such random is running...");
	}
}
