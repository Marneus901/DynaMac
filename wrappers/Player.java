/******************************************************
* Created by Marneus901                                *
* © 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.wrappers;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.ClassHook.FieldHook;


public class Player extends Character{
	public Object currentObject;
	public ClassHook currentHook;
	public Player(Object o){
		super(o);
		currentObject=o;
		currentHook = Data.indentifiedClasses.get("Player");
	}
	public byte getGender(){
		FieldHook fh = currentHook.getFieldHook("getGender");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Byte)data;
		}
		return -1;
	}
	public int getLevel(){
		FieldHook fh = currentHook.getFieldHook("getLevel");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();	
		}
		return -1;
	}
	public PlayerDef getPlayerDef(){
		FieldHook fh = currentHook.getFieldHook("getPlayerDef");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new PlayerDef(data);
		}
		return null;
	}
	public String getPlayerName(){
		FieldHook fh = currentHook.getFieldHook("getPlayerName");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public int getPrayerIcon(){
		FieldHook fh = currentHook.getFieldHook("getPrayerIcon");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();	
		}
		return -1;
	}
	public int getSkullIcon(){
		FieldHook fh = currentHook.getFieldHook("getSkullIcon");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();	
		}
		return -1;
	}
	public int getTeam(){
		FieldHook fh = currentHook.getFieldHook("getTeam");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return ((Integer)data) * fh.getMultiplier();
		}
		return -1;	
	}
	public String getTitle(){
		FieldHook fh = currentHook.getFieldHook("getTitle");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
}
