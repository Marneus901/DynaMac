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

public class LookupTable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook identityTable;
	public LookupTable(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("LookupTable");
			identityTable = currentHook.getFieldHook("getIdentityTable");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		identityTable=null;
	}
	public int[] getIdentityTable(){
		if(identityTable==null)
			identityTable = currentHook.getFieldHook("getIdentityTable");
		if(identityTable!=null){
			Object data = identityTable.get(currentObject);
			if(data!=null)
				return ((int[])data);	
		}
		return new int[]{};
	}
}
