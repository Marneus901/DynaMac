package org.dynamac.bot.api.wrappers;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;

public class AnimatedFloorObject extends FloorDecoration{
	public Object currentObject;
	public ClassHook currentHook;
	public AnimatedFloorObject(Object o, int x, int y) {
		super(o);
		currentObject = o;
		localX=x;
		localY=y;
		currentHook = Data.indentifiedClasses.get("AnimatedFloorObject");
	}
	private int localX;
	private int localY;
	public AnimatedObject getAnimatedObject(){
		FieldHook fh = currentHook.getFieldHook("getAnimatedObject");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null){
				return new AnimatedObject(data, (short)localX, (short)localY);
			}
		}
		return null;
	}
}
