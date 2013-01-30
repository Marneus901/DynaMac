/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's main script definition.                    *
* To be used in order to interact or read/write data   *
* to the client that is being hooked.                  *
********************************************************/
package org.dynamac.script;

import java.util.Random;

import org.dynamac.enviroment.Data;
import org.dynamac.gui.ClientGUI;

public abstract class ScriptDef extends Thread{
	public abstract void run();
	public boolean isPaused=false;
	public static int random(int min, int max){
		return new Random().nextInt(max-min)+min;
	}
	public static void sleep(long delay){
		try {
			Thread.sleep(delay);
		} catch (Exception e) {
		}
	}
	@SuppressWarnings("deprecation")
	public void stopScript(){
		isPaused=false;
		if(this.equals(Data.CURRENT_SCRIPT)){
			ClientGUI.startScript.setLabel("Start Script");
    		Data.CURRENT_SCRIPT=null;
    		Runtime.getRuntime().gc();
		}
		this.stop();
	}
	@SuppressWarnings("deprecation")
	public void pause(){
		isPaused=true;
		this.suspend();
	}
	@SuppressWarnings("deprecation")
	public void unpause(){
		isPaused=false;
		this.resume();
	}
}
