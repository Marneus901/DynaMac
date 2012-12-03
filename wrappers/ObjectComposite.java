package org.dynamac.bot.api.wrappers;

import org.dynamac.environment.Data;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;

public class ObjectComposite {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook model;
	public ObjectComposite(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ObjectComposite");
			model = currentHook.getFieldHook("getModel");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		model=null;
	}
	public ModelLD getModel(){
		if(model==null)
			model = currentHook.getFieldHook("getModel");
		if(model!=null){
			Object data = model.get(currentObject);
			if(data!=null)
				return new ModelLD(data);
		}
		return null;
	}
}
