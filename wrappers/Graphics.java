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

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class Graphics {
	public Object currentObject;
	public ClassHook currentHook;
	public Graphics(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("Graphics");
	}
	public GraphicsDevice getGraphicsDevice(){
		FieldHook fh = currentHook.getFieldHook("getGraphicsDevice");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (GraphicsDevice)data;
		}
		return null;		
	}
	public DisplayMode getDisplayMode(){
		FieldHook fh = currentHook.getFieldHook("getDisplayMode");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (DisplayMode)data;
		}
		return null;		
	}
}
