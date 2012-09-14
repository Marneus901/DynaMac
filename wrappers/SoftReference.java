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


public class SoftReference extends Reference{
	public Object currentObject;
	public ClassHook currentHook;
	public SoftReference(Object o){
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("SoftReference");
	}
	@SuppressWarnings({ "rawtypes" })
	public java.lang.ref.SoftReference getSoftReference(){
		FieldHook fh = currentHook.getFieldHook("getSoftReference");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (java.lang.ref.SoftReference)data;
		}
		return null;
	}
}
