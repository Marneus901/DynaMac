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


public class HardReference extends Reference{
	public Object currentObject;
	public ClassHook currentHook;
	public HardReference(Object o){
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("HardReference");
	}
	public Object getHardReference(){
		FieldHook fh = currentHook.getFieldHook("getReference");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return data;
		}
		return null;
	}
}
