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

public class SoftReference extends Reference{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook reference;
	public SoftReference(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("SoftReference");
			reference = currentHook.getFieldHook("getSoftReference");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		reference=null;
	}
	public java.lang.ref.SoftReference<?> getSoftReference(){
		if(reference==null)
			reference = currentHook.getFieldHook("getSoftReference");
		if(reference!=null){
			Object data = reference.get(currentObject);
			if(data!=null)
				return (java.lang.ref.SoftReference<?>)data;
		}
		return null;
	}
	public static boolean isInstance(Object o){
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("SoftReference");
		if(currentHook!=null)
			return o.getClass().getName().equals(currentHook.getClassName());
		return false;
	}
}
