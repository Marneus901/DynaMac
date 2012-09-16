package org.dynamac.bot.api.wrappers;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class ObjectComposite {
	public Object currentObject;
	public ClassHook currentHook;
	public ObjectComposite(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("ObjectComposite");
	}
	public ModelLD getModel(){
		FieldHook fh = currentHook.getFieldHook("getModel");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return new ModelLD(data);
		}
		return null;
	}
}
