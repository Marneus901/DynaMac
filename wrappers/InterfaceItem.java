package org.dynamac.bot.api.wrappers;

import org.dynamac.bot.api.methods.Client;
import org.dynamac.bot.api.methods.Nodes;
import org.dynamac.enviroment.Data;

public class InterfaceItem {
	private String itemName;
	private int itemID;
	private int itemStackSize;
	private InterfaceChild itemInterface;
	public InterfaceItem(InterfaceChild ic){
		itemName = ic.getComponentName();
		itemID = ic.getComponentID();
		itemStackSize = ic.getComponentStackSize();
		itemInterface = ic;
	}
	public int getID(){
		return itemID;
	}
	public int getStackSize(){
		return itemStackSize;
	}
	public String getName(){
		return itemName;
	}
	public InterfaceChild getInterfaceChild(){
		return itemInterface;
	}
	public int getIndex(){
		return itemInterface.index;
	}
	public boolean isSelected(){
		return itemInterface.getBorderThickness()==2;
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
