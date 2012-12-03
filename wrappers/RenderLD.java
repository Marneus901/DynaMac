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

public class RenderLD extends Render{
	public Object currentObject;
	public static ClassHook currentHook;
	private static float absoluteX=(float) 260.0;
	private static float absoluteY=(float) 171.0;
	private static float xMultiplier=(float) 256.0;
	private static float yMultiplier=(float) 167.0;
	private static FieldHook viewport;
	public RenderLD(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("RenderLD");
			viewport = currentHook.getFieldHook("getViewport");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		viewport=null;
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
		if(viewport==null)
			viewport = currentHook.getFieldHook("getViewport");
		if(viewport!=null){
			Object data = viewport.get(currentObject);
			if(data!=null)
				return new Viewport(data);
		}
		return null;
	}
}
