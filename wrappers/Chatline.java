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

public class Chatline {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook message;
	public Chatline(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Chatline");
			message = currentHook.getFieldHook("getMessage");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		message=null;
	}
	public String getMessage(){
		if(message==null)
			message = currentHook.getFieldHook("getMessage");
		if(message!=null){
			Object data = message.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
}
