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


public class RenderLD extends Render{
	public Object currentObject;
	public ClassHook currentHook;
	public static float absoluteX=(float) 260.0;
	public static float absoluteY=(float) 171.0;
	public static float xMultiplier=(float) 256.0;
	public static float yMultiplier=(float) 167.0;
	public RenderLD(Object o){
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("RenderLD");
	}
	public float getAbsoluteX(){
		return absoluteX;
	}
	public float getAbsoluteY(){
		return absoluteY;
	}
	public float getXMultiplier(){
		return xMultiplier;
	}
	public float getYMultiplier(){
		return yMultiplier;
	}
	public Viewport getViewport(){
		FieldHook fh = currentHook.getFieldHook("getViewport");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new Viewport(data);
		}
		return null;
	}
}
