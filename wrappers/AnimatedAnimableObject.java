package org.dynamac.bot.api.wrappers;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;

public class AnimatedAnimableObject extends Animable{
	public Object currentObject;
	public ClassHook currentHook;
	public AnimatedAnimableObject(Object o) {
		super(o);
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("AnimatedAnimableObject");
	}
	public AnimatedObject getAnimatedObject(){
		FieldHook fh = currentHook.getFieldHook("getAnimatedObject");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null){
				return new AnimatedObject(data, this.getMaxX(), this.getMinY());
			}
		}
		return null;
	}
}
