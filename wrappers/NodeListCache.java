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

public class NodeListCache extends Node{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook nodeList;
	public NodeListCache(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NodeListCache");
			nodeList = currentHook.getFieldHook("getNodeList");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		nodeList=null;
	}
	public NodeList getNodeList(){
		if(nodeList==null)
			nodeList = currentHook.getFieldHook("getNodeList");
		if(nodeList!=null){
			Object data = nodeList.get(currentObject);
			if(data!=null)
				return new NodeList(data);
		}
		return null;
	}
}
