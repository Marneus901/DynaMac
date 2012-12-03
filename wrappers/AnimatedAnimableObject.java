package org.dynamac.bot.api.wrappers;

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class AnimatedAnimableObject extends Animable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook animatedObject;
	public AnimatedAnimableObject(Object o) {
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("AnimatedAnimableObject");
			animatedObject = currentHook.getFieldHook("getAnimatedObject");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		animatedObject=null;
	}
	public AnimatedObject getAnimatedObject(){
		if(animatedObject==null)
			animatedObject = currentHook.getFieldHook("getAnimatedObject");
		if(animatedObject!=null){
			Object data = animatedObject.get(currentObject);
			if(data!=null){
				return new AnimatedObject(data, this.getMinX(), this.getMinY());
			}
		}
		return null;
	}
}
