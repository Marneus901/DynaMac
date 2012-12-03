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

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class Settings {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook data;
	public Settings(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Settings");
			data = currentHook.getFieldHook("getData");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		data=null;
	}
	public int[] getData(){
		if(data==null)
			data = currentHook.getFieldHook("getData");
		if(data!=null){
			Object dat = data.get(currentObject);
			if(dat!=null)
				return (int[])dat;
		}
		return new int[]{};
	}
}
