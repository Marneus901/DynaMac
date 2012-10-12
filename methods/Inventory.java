package org.dynamac.bot.api.methods;

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.Interface;
import org.dynamac.bot.api.wrappers.InterfaceChild;
import org.dynamac.bot.api.wrappers.InterfaceItem;

public class Inventory {
	/**
	 *@author VelvetRevolver
	 *@param ID of the InterfaceItem
	 *@return true if Inventory contains the InterfaceItem, false if not
	 **/
	public static boolean containsItemID(final int itemId) {
		return getItemByID(itemId) != null;
	}   
	/**
	 *@author VelvetRevolver
	 *@param Name of the InterfaceItem 
	 *@return true if Inventory contains the InterfaceItem, false if not
	 **/
	public static boolean containsItemName(String itemName) {
		return getItemByName(itemName) != null;
	}
	public static int getCount(){
		return getItems().length;
	}
	public static int getCount(final int... ids){
		return getItems(true, ids).length;
	}
	public static int getCountExcept(final int... ids) {
		return getItems(false, ids).length;
	}
	public static InterfaceItem getItemAt(int slotIndex){
		Interface inventory = Client.getInterfaceCache()[763];
		if(inventory!=null && inventory.getChildren().length>0){
			InterfaceChild inventoryItems = inventory.getChildren()[0];
			if(inventoryItems!=null){
				return new InterfaceItem(inventoryItems.getChildren()[slotIndex]);
			}
		}
		else{
			inventory = Client.getInterfaceCache()[679];
			if(inventory!=null && inventory.getChildren().length>0){
				InterfaceChild inventoryItems = inventory.getChildren()[0];
				if(inventoryItems!=null){
					return new InterfaceItem(inventoryItems.getChildren()[slotIndex]);
				}
			}
		}
		return null;
	}
	/**
	 *@author VelvetRevolver
	 *@param ID of the InterfaceItem 
	 *@return InterfaceItem in the Inventory
	 **/
	public static InterfaceItem getItemByID(final int itemId) {
		for (InterfaceItem i : Inventory.getItems()) {
			if (i != null) {
				if (i.getID() == itemId) {
					return i;
				}
			}
		}
		return null;
	}
	/**
	 *@author VelvetRevolver
	 *@param Name of the InterfaceItem 
	 *@return InterfaceItem in the Inventory
	 **/
	public static InterfaceItem getItemByName(String itemName) {
		for (InterfaceItem i : Inventory.getItems()) {
			if(i != null && i.getName().length() > 0) {
				if (i.getName().toLowerCase().equals(itemName.toString().toLowerCase())) {
					return i;
				}
			}
		}
		return null;
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
	/**
	 *@author VelvetRevolver, Marneus901
	 *@param Ids of the InterfaceItems you want to count
	 *@param true, if you want to get the InterfaceItems of the selected ids. false, if you want the InterfaceItems EXCEPT the selected ids.
	 *@return amount of InterfaceItems 
	 **/
	public static InterfaceItem[] getItems(boolean selected, int...ids){
		ArrayList<InterfaceItem> items = new ArrayList<InterfaceItem>();
		ArrayList<Integer> itemIDs = new ArrayList<Integer>();
		for(int i=0;i<ids.length;++i)
			itemIDs.add(ids[i]);
		for(InterfaceItem item : getItems()){
			if(selected && itemIDs.contains(item.getID()))
				items.add(item);
			if(!selected && !itemIDs.contains(item.getID()))
				items.add(item);
		}
		return items.toArray(new InterfaceItem[]{});
	}
	/**
	 *@author VelvetRevolver , Marneus901
	 *@param Ids of the InterfaceItems you want to look for  
	 *@return InterfaceItems with the Selected Ids
	 **/
	public static InterfaceItem[] getItemsByIDs(int... ids){
		ArrayList<InterfaceItem> items = new ArrayList<InterfaceItem>();
		for(InterfaceItem item : getItems()){
			for(int i : ids){
				if(i==item.getID()){
					items.add(item);
					break;
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
	public static boolean isFull(){
		return getCount()>27;
	}
}
