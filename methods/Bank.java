package org.dynamac.bot.api.methods;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import org.dynamac.bot.api.wrappers.AnimableObject;
import org.dynamac.bot.api.wrappers.Interface;
import org.dynamac.bot.api.wrappers.InterfaceChild;
import org.dynamac.bot.api.wrappers.InterfaceItem;

public class Bank {
	public static int BANK_INTERFACE_ID = 762;
	public static int BANK_SLOTS_INTERFACE_ID = 95;
	public static int BANK_DEPOSIT_INVENTORY_BUTTON_ID = 34;
	public static int BANK_DEPOSIT_EQUIPMENT_BUTTON_ID = 36;
	public static int BANK_DEPOSIT_BEAST_BUTTON_ID = 38;
	public static int BANK_SCROLLBAR = 116;
	public static final int[] BANKER_IDS = new int[]{
		44, 45, 166, 494, 495, 496, 497, 498, 499, 553, 909, 953, 958, 1036, 1360, 1702, 2163, 2164, 2354, 2355,
		2568, 2569, 2570, 2718, 2759, 3046, 3198, 3199, 3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 4519, 4907,
		5257, 5258, 5259, 5260, 5488, 5776, 5777, 5901, 6200, 6362, 7049, 7050, 7605, 8948, 9710, 13932, 14923, 14924,
		14925, 15194
	};
	public static final int[] BANK_BOOTH_IDS = new int[]{
		782, 2012, 2015, 2019, 2213, 3045, 5276, 6084, 10517, 11338, 11758, 12759, 12798, 12799, 12800, 12801, 14369, 14370,
		16700, 19230, 20325, 20326, 20327, 20328, 22819, 24914, 25808, 26972, 29085, 34205, 34752, 35647,
		35648, 36262, 36786, 37474, 49018, 49019, 52397, 52589
	};
	public static final int[] BANK_CHEST_IDS = new int[]{
		2693, 4483, 8981, 12308, 14382, 20607, 21301, 27663, 42192, 57437, 62691
	};
	
	/**
	 * Deposit whole inventory using the Deposit Inventory Button
	 * @return true if successful, false otherwise
	 */
	public static boolean depositInventory(){
		if(isOpen()){
			int invCount = Inventory.getCount();
			if(invCount>0){
				InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_INVENTORY_BUTTON_ID];
				button.click();
				for(int i=0;i<20;++i){
					if(invCount>Inventory.getCount())
						return true;
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
					}
				}
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Deposit item with the given name
	 * @param name name of the item
	 * @param amount amount of the item
	 * @return true if successful, false otherwise
	 */
	public static boolean depositItem(String name, int amount){
		InterfaceItem item = Inventory.getItemByName(name);
		if(item!=null)
			return depositItem(item.getID(), amount);
		return false;
	}
	
	/**
	 * Deposit a item with a given id
	 * @param id id of the item
	 * @param amount amount of the item
	 * @return true if successful, false otherwise
	 */
	public static boolean depositItem(int id, int amount){
		InterfaceItem item = Inventory.getItemByID(id);
		if(item!=null){
			String action = "";
			if(Inventory.getCount(id)>1 || item.getStackSize()>1){
				if(amount==0)
					action="Deposit-All";
				else if(amount==5 || amount==10)
					action="Deposit-"+amount;
				else{
					Point p = item.getInterfaceChild().getRandomPoint();
					Mouse.moveMouse(p);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					Mouse.clickMouse(Mouse.RIGHT_BUTTON);
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
					}
					if(!Menu.isOpen())
						return false;
					if(Menu.contains("Deposit-"+amount))
						return Menu.click("Deposit-"+amount);
					else{
						if(!Menu.click("Deposit-X"))
							return false;
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
					}
					Keyboard.sendKeys(""+amount);
					Keyboard.sendKey((char) KeyEvent.VK_ENTER);
					return true;
				}
			}
			else
				action="Deposit";
			return item.doAction(action);
		}
		return false;
	}
	
	/**
	 * Deposit all the items except the given ones
	 * @param items items ids
	 * @return true if successful, false otherwise
	 */
	public static boolean depositAllExcept(int... items) {
		if (isOpen()) {
			boolean deposit = true;
			int invCount = Inventory.getCount();
			outer: for (int i = 0; i < 28; i++) {
				InterfaceItem itemToDisplay=Inventory.getItemAt(i);
				if(itemToDisplay==null)return false;
				InterfaceChild item = itemToDisplay.getInterfaceChild();
				if (item != null && item.getComponentID() != -1) {
					for (int id : items) {
						if (item.getComponentID() == id) {
							continue outer;
						}
					}
					for (int tries = 0; tries < 5; tries++) {
						depositItem(item.getComponentID(), 0);
						for(int k=0;k<20;++k){
							try {
								Thread.sleep(new Random().nextInt(100)+50);
							} catch (Exception e) {
							}
							int cInvCount = Inventory.getCount();
							if (cInvCount < invCount) {
								invCount = cInvCount;
								continue outer;
							}
						}
					}
					deposit = false;
				}
			}
			return deposit;
		}
		return false;
	}
	
	/**
	 * Deposit equipment by using the Deposit Equiment button
	 * @return true if successful, false otherwise
	 */
	public static boolean depositEquipment(){
		if(isOpen()){
			InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_EQUIPMENT_BUTTON_ID];
			button.click();
			return true;
		}
		return false;
	}
	
	/**
	 * Deposit your familiars using the Deposit Beast button
	 * @return true if succesful, false otherwise
	 */
	public static boolean depositFamiliar(){
		if(isOpen()){
			InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_BEAST_BUTTON_ID];
			button.click();
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if bank interface is open
	 * @return true if it's open, false otherwise
	 */
	public static boolean isOpen(){
		return Client.getInterfaceCache()[BANK_INTERFACE_ID]!=null;
	}
	
	/**
	 * Gets the current tab
	 * @return tab
	 */
	public int getCurrentTab(){
		return ((Client.getSettings().getData()[1248] >>> 24) - 136) / 8;
	}
	
	/**
	 * Get specified item
	 * @return item
	 */
	public static InterfaceItem getItem(int id){
		for(InterfaceItem item : getItems())
			if(item.getID()==id)
				return item;
		return null;
	}
	
	/**
	 * Get all the items
	 * @return array of all the items
	 */
	public static InterfaceItem[] getItems(){
		ArrayList<InterfaceItem> bankItems = new ArrayList<InterfaceItem>();
		Interface bank = Client.getInterfaceCache()[BANK_INTERFACE_ID];
		if(bank!=null){
			InterfaceChild slots = bank.getChildren()[BANK_SLOTS_INTERFACE_ID];
			if(slots!=null){
				for(InterfaceChild ic : slots.getChildren()){
					if(ic.getComponentID()!=-1)
						bankItems.add(new InterfaceItem(ic));
				}
			}
		}
		return bankItems.toArray(new InterfaceItem[]{});
	}
	
	/**
	 * Get the amount of an item
	 * @param id id of the item
	 * @return amount of an item
	 */
	public static int getItemCount(int id){
		InterfaceItem item = getItem(id);
		if(item!=null)
			return item.getStackSize();
		return 0;
	}
	
	/**
	 * Opens the bank interface
	 * @return true if successful, false otherwise
	 */
	public static boolean open(){
		AnimableObject object = Objects.getNearestAnimableObjectByID(BANK_BOOTH_IDS);
		if(object!=null){
			System.out.println("[Bank:open] Bank booth found.");
			if(object.isOnScreen()){
				if(!object.clickTile()){
					System.out.println("[Bank:open] Failed to click bank tile.");
					return false;
				}
				for(int i=0;i<20;++i){
					if(Bank.isOpen()){
						System.out.println("[Bank:open] Bank opened.");
						return true;
					}
					try {
						Thread.sleep(new Random().nextInt(100)+100);
					} catch (Exception e) {
					}
				}
				return true;
			}
			System.out.println("[Bank:open] Bank booth not on screen.");
		}
		object=Objects.getNearestAnimableObjectByID(BANK_CHEST_IDS);
		if(object!=null){
			System.out.println("[Bank:open] Bank chest found.");
			if(object.isOnScreen()){
				if(!object.clickTile()){
					System.out.println("[Bank:open] Failed to click chest tile.");
					return false;
				}
				for(int i=0;i<20;++i){
					if(Bank.isOpen()){
						System.out.println("[Bank:open] Bank opened.");
						return true;
					}
					try {
						Thread.sleep(new Random().nextInt(100)+100);
					} catch (Exception e) {
					}
				}
				return true;
			}
			System.out.println("[Bank:open] Bank chest not on screen.");
		}
		/*NPC npcToDisplay=NPCs.getNearest(BANKER_IDS);
		if(npcToDisplay!=null){
			System.out.println("[Bank:open] Banker found.");
			if(npcToDisplay.isOnScreen()){
				if(!npcToDisplay.clickTile()){//doAction
					System.out.println("[Bank:open] Failed to click banker tile.");
					npcToDisplay=null;
					return false;
				}
				for(int i=0;i<20;++i){
					if(Bank.isOpen()){
						System.out.println("[Bank:open] Bank opened.");
						npcToDisplay=null;
						return true;
					}
					try {
						Thread.sleep(new Random().nextInt(100)+100);
					} catch (Exception e) {
					}
				}
				npcToDisplay=null;
				return true;
			}
			System.out.println("[Bank:open] Banker not on screen.");
			npcToDisplay=null;
		}*/
		return false;
	}
	
	/**
	 * Withdraws the amount of the specified item id
	 * @param id id of the item
	 * @param amount amount to withdraw
	 * @return true if successful, false otherwise
	 */
	public static boolean withdraw(int id, int amount){
		InterfaceItem item = getItem(id);
		if(item!=null){
			if(item.getInterfaceChild().getAbsoluteY()<312 && item.getInterfaceChild().getAbsoluteY()>140){
				if(amount==1){
					item.click();
					return true;
				}
				String action = "";
				if(amount==0)
					action="Withdraw-All";
				else if(amount==5 || amount==10)
					action="Withdraw-"+amount;
				else{
					Point p = item.getInterfaceChild().getRandomPoint();
					Mouse.moveMouse(p);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					Mouse.clickMouse(Mouse.RIGHT_BUTTON);
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
					}
					if(!Menu.isOpen())
						return false;
					if(Menu.contains("Withdraw-"+amount))
						return Menu.click("Withdraw-"+amount);
					else{
						if(!Menu.click("Withdraw-X"))
							return false;
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
					}
					Keyboard.sendKeys(""+amount);
					Keyboard.sendKey((char) KeyEvent.VK_ENTER);
					return true;
				}
				int invCount = Inventory.getCount();
				item.doAction(action);
				for(int i=0;i<20;++i){
					try {
						if(Inventory.getCount()>invCount)
							return true;
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
			}
			else{
				InterfaceChild scrollBar=Interfaces.get(BANK_INTERFACE_ID, BANK_SCROLLBAR);
				if(scrollBar==null)
					return false;
				if(!Interfaces.scrollTo(item.getInterfaceChild(), scrollBar))
					return false;
				return withdraw(id, amount);
			}
		}
		return false;
	}
}
