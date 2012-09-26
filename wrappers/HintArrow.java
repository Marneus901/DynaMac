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


public class HintArrow {
	public Object currentObject;
	public ClassHook currentHook;
	public HintArrow(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("HintArrow");
	}
	public int getHeadIconIndex(){
		FieldHook fh = currentHook.getFieldHook("getHeadIconIndex");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data;
		}			
		return -1;
	}
	public int getTargetIndex(){
		FieldHook fh = currentHook.getFieldHook("getTargetIndex");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data;
		}			
		return -1;
	}
}
