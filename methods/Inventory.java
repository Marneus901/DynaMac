package org.dynamac.bot.api.methods;

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.Interface;
import org.dynamac.bot.api.wrappers.InterfaceChild;
import org.dynamac.bot.api.wrappers.InterfaceItem;

public class Inventory {
	public static int getCount(){
		return getItems().length;
	}
	public static InterfaceItem[] getItems(){
		ArrayList<InterfaceItem> items = new ArrayList<InterfaceItem>();
		Interface inventory = Client.getInterfaceCache()[763];
		if(inventory!=null && inventory.getChildren().length>0){
			InterfaceChild inventoryItems = inventory.getChildren()[0];
			if(inventoryItems!=null){
				for(InterfaceChild slot : inventoryItems.getChildren()){
					if(slot!=null){
						if(slot.getComponentID()!=-1){
							items.add(new InterfaceItem(slot));
						}
					}
				}
			}
		}
		else{
			inventory = Client.getInterfaceCache()[679];
			if(inventory!=null && inventory.getChildren().length>0){
				InterfaceChild inventoryItems = inventory.getChildren()[0];
				if(inventoryItems!=null){
					for(InterfaceChild slot : inventoryItems.getChildren()){
						if(slot!=null){
							if(slot.getComponentID()!=-1){
								items.add(new InterfaceItem(slot));
							}
						}
					}
				}
			}
		}
		return items.toArray(new InterfaceItem[]{});
	}
	public static InterfaceItem getSelectedItem(){
		for(InterfaceItem i : getItems())
			if(i.isSelected())
				return i;
		return null;
	}
	public static boolean isInventoryFull(){
		return getCount()>27;
	}
}
