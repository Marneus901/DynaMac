package org.dynamac.bot.api.wrappers;

import org.dynamac.bot.api.methods.Client;
import org.dynamac.bot.api.methods.Nodes;
import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.ClassHook.FieldHook;

public class Item {
	public Object currentObject;
	public ClassHook currentHook;
	public Item(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("Item");
	}
	public int getID(){
		FieldHook fh = currentHook.getFieldHook("getID");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data * fh.getMultiplier();
		}
		return -1;
	}
	public int getStackSize(){
		FieldHook fh = currentHook.getFieldHook("getStackSize");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			if(data!=null)
				return (Integer)data * fh.getMultiplier();
		}
		return -1;
	}
	public ItemDef getItemDef(){
		try{
			Node ref = Nodes.lookup(Client.getItemDefLoader().getCache().getTable(), getID());
			if(ref==null)
				return null;
			if (ref.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("SoftReference").getClassName())) {
				SoftReference sr = new SoftReference(ref.currentObject);
				Object def = sr.getSoftReference().get();
				return new ItemDef(def);
			}
			else if (ref.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("HardReference").getClassName())) {
				HardReference hr = new HardReference(ref.currentObject);
				Object def = hr.getHardReference();
				return new ItemDef(def);
			}
		}
		catch(Exception e){}
		return null;
	}
}
