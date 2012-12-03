/******************************************************
* Created by Marneus901                                *
* � 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.wrappers;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class Graphics {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook graphicsDevice;
	private static FieldHook displayMode;
	public Graphics(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Graphics");
			graphicsDevice = currentHook.getFieldHook("getGraphicsDevice");
			displayMode = currentHook.getFieldHook("getDisplayMode");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		graphicsDevice=null;
		displayMode=null;
	}
	public GraphicsDevice getGraphicsDevice(){
		if(graphicsDevice==null)
			graphicsDevice = currentHook.getFieldHook("getGraphicsDevice");
		if(graphicsDevice!=null){
			Object data = graphicsDevice.get(currentObject);
			if(data!=null)
				return (GraphicsDevice)data;
		}
		return null;		
	}
	public DisplayMode getDisplayMode(){
		if(displayMode==null)
			displayMode = currentHook.getFieldHook("getDisplayMode");
		if(displayMode!=null){
			Object data = displayMode.get(currentObject);
			if(data!=null)
				return (DisplayMode)data;
		}
		return null;		
	}
}
