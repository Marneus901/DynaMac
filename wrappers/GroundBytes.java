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

public class GroundBytes {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook bytes;
	public GroundBytes(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("GroundBytes");
			bytes = currentHook.getFieldHook("getBytes");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		bytes=null;
	}
	public byte[][][] getBytes(){
		if(bytes==null)
			bytes = currentHook.getFieldHook("getBytes");
		if(bytes!=null){
			Object data = bytes.get(currentObject);
			if(data!=null)
				return (byte[][][])data;
		}
		return new byte[][][]{};		
	}
}
