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
import org.dynamac.enviroment.hook.FieldHook;


public class PlayerDef {
	public Object currentObject;
	public ClassHook currentHook;
	public PlayerDef(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("PlayerDef");
	}
	public int[] getEquipment(){
		FieldHook fh = currentHook.getFieldHook("getEquipment");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public int getID(){
		FieldHook fh = currentHook.getFieldHook("getID");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data * fh.getMultiplier();
		}
		return -1;
	}
	public long getModelHash(){
		FieldHook fh = currentHook.getFieldHook("getModelHash");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Long)data * fh.getLongMultiplier();
		}
		return -1;
	}
}
