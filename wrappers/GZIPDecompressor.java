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

import java.util.zip.Inflater;

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class GZIPDecompressor {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook inflator;
	public GZIPDecompressor(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("GZIPDecompressor");
			inflator = currentHook.getFieldHook("getInflater");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		inflator=null;
	}
	public Inflater getInflater(){
		if(inflator!=null)
			inflator = currentHook.getFieldHook("getInflater");		
		if(inflator!=null){
			Object data = inflator.get(currentObject);
			if(data!=null)
				return (Inflater)data;
		}
		return null;		
	}
}
