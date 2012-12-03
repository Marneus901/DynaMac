/******************************************************
* Created by Marneus901                                *
* � 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.wrappers;

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;

public class DetailInfoNode extends Node{
	public Object currentObject;
	public static ClassHook currentHook;
	public DetailInfoNode(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("DetailInfoNode");
	}
	public static void resetHooks(){
		currentHook=null;
	}
}
