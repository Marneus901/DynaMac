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


public class Info {
	public Object currentObject;
	public ClassHook currentHook;
	public Info(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("Info");
	}
	public GroundInfo getGroundInfo(){
		FieldHook fh = currentHook.getFieldHook("getGroundInfo");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new GroundInfo(fh.getData(currentObject));
		}
		return null;		
	}
	public BaseInfo getBaseInfo(){
		FieldHook fh = currentHook.getFieldHook("getBaseInfo");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new BaseInfo(fh.getData(currentObject));
		}
		return null;		
	}
	public GroundBytes getGroundBytes(){
		FieldHook fh = currentHook.getFieldHook("getGroundBytes");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new GroundBytes(fh.getData(currentObject));
		}
		return null;		
	}
	public GroundData getGroundData(){
		FieldHook fh = currentHook.getFieldHook("getGroundData");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new GroundData(fh.getData(currentObject));
		}
		return null;		
	}
	public ObjectDefLoader getObjectDefLoaders(){
		FieldHook fh = currentHook.getFieldHook("getObjectDefLoaders");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new ObjectDefLoader(fh.getData(currentObject));
		}
		return null;		
	}
}
