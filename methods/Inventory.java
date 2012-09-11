package org.dynamac.bot.api.methods;

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.Interface;
import org.dynamac.bot.api.wrappers.InterfaceChild;

public class Inventory {
	public static int getCount(){
		return getItemIDs().length;
	}
	public static boolean isInventoryFull(){
		return getCount()>27;
	}
	public static int getItemStackSize(int itemID){
		Interface inventory = Client.getInterfaceCache()[679];
		if(inventory!=null){
			InterfaceChild inventoryItems = inventory.getChildren()[0];
			if(inventoryItems!=null){
				for(InterfaceChild slot : inventoryItems.getChildren()){
					if(slot!=null){
						if(slot.getComponentID()==itemID){
							return slot.getComponentStackSize();
						}
					}
				}
			}
		}
		return -1;
	}
	public static int[] getItemIDs(){
		ArrayList<Integer> itemIDs = new ArrayList<Integer>();
		Interface inventory = Client.getInterfaceCache()[679];
		if(inventory!=null){
			InterfaceChild inventoryItems = inventory.getChildren()[0];
			if(inventoryItems!=null){
				for(InterfaceChild slot : inventoryItems.getChildren()){
					if(slot!=null){
						if(slot.getComponentID()!=-1){
							itemIDs.add(slot.getComponentID());
						}
					}
				}
			}
		}
		int[] ret = new int[itemIDs.size()];
		for(int i=0;i<ret.length;++i)
			ret[i]=itemIDs.get(i);
		return ret;
	}
}
