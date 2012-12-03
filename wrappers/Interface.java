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

import java.lang.reflect.Array;

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class Interface {
	public int index;
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook children;
	public Interface(int i, Object o){
		index=i;
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Interface");
			children = currentHook.getFieldHook("getChildren");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		children=null;
	}
	public InterfaceChild[] getChildren(){
		if(children==null)
			children = currentHook.getFieldHook("getChildren");
		if(children!=null){
			Object array = children.get(currentObject);
			if(array!=null){
				InterfaceChild[] children = new InterfaceChild[Array.getLength(array)];
				for(int i=0;i<children.length;++i){
					Object data = Array.get(array, i);
					if(data!=null)
						children[i] = new InterfaceChild(data, this, i);
				}
				return children;
			}
		}
		return new InterfaceChild[]{};
	}	
}
