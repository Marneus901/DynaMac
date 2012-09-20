package org.dynamac.bot.api.methods;

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
	public static int BANK_DEPOSIT_POUCH_BUTTON_ID = 36;
	public static int BANK_DEPOSIT_EQUIPMENT_BUTTON_ID = 38;
	public static int BANK_DEPOSIT_BEAST_BUTTON_ID = 40;
	public static final int[] BANKER_IDS = new int[]{
		44, 45, 166, 494, 495, 496, 497, 498, 499, 553, 909, 953, 958, 1036, 1360, 1702, 2163, 2164, 2354, 2355,
		2568, 2569, 2570, 2718, 2759, 3046, 3198, 3199, 3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 4519, 4907,
		5257, 5258, 5259, 5260, 5488, 5776, 5777, 5901, 6200, 6362, 7049, 7050, 7605, 8948, 9710, 13932, 14923, 14924,
		14925, 15194
	};
	public static final int[] BANK_BOOTH_IDS = new int[]{
		782, 2213, 3045, 5276, 6084, 10517, 11338, 11758, 12759, 12798, 12799, 12800, 12801, 14369, 14370,
		16700, 19230, 20325, 20326, 20327, 20328, 22819, 24914, 25808, 26972, 29085, 34205, 34752, 35647,
		35648, 36262, 36786, 37474, 49018, 49019, 52397, 52589
	};
	public static final int[] BANK_CHEST_IDS = new int[]{
		2693, 4483, 8981, 12308, 14382, 20607, 21301, 27663, 42192, 57437, 62691
	};
	public static boolean depositInventory(){
		if(isOpen()){
			int invCount = Inventory.getCount();
			if(invCount>0){
				InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_INVENTORY_BUTTON_ID];
				button.click();
				return true;
			}
		}
		return false;
	}
	public static boolean depositItem(String name, int amount){
		InterfaceItem item = Inventory.getItemByName(name);
		if(item!=null)
			return depositItem(item.getID(), amount);
		return false;
	}
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
					//TODO Deposit-X algorithm
					return false;
				}
			}
			else
				action="Deposit";
			return item.doAction(action);
		}
		return false;
	}
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
	public static boolean depositEquipment(){
		if(isOpen()){
			InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_EQUIPMENT_BUTTON_ID];
			button.click();
			return true;
		}
		return false;
	}
	public static boolean depositFamiliar(){
		if(isOpen()){
			InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_BEAST_BUTTON_ID];
			button.click();
			return true;
		}
		return false;
	}
	public static boolean depositPouch(){
		if(isOpen()){
			InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_POUCH_BUTTON_ID];
			button.click();
			return true;
		}
		return false;
	}
	public static boolean isOpen(){
		return Client.getInterfaceCache()[BANK_INTERFACE_ID]!=null;
	}
	public int getCurrentTab(){
		return ((Client.getSettings().getData()[1248] >>> 24) - 136) / 8;
	}
	public static InterfaceItem getItem(int id){
		for(InterfaceItem item : getItems())
			if(item.getID()==id)
				return item;
		return null;
	}
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
	public static int getItemCount(int id){
		InterfaceItem item = getItem(id);
		if(item!=null)
			return item.getStackSize();
		return 0;
	}
	public static boolean open(){
		AnimableObject objectToDisplay=Objects.getNearestAnimableObjectByID(BANK_BOOTH_IDS);
		if(objectToDisplay!=null){
			System.out.println("[Bank:open] Bank booth found.");
			if(objectToDisplay.isOnScreen()){
				if(!objectToDisplay.clickTile()){
					System.out.println("[Bank:open] Failed to click bank tile.");
					objectToDisplay=null;
					return false;
				}
				for(int i=0;i<20;++i){
					if(Bank.isOpen()){
						System.out.println("[Bank:open] Bank opened.");
						objectToDisplay=null;
						return true;
					}
					try {
						Thread.sleep(new Random().nextInt(100)+100);
					} catch (Exception e) {
					}
				}
				objectToDisplay=null;
				return true;
			}
			System.out.println("[Bank:open] Bank booth not on screen.");
			objectToDisplay=null;
		}
		objectToDisplay=Objects.getNearestAnimableObjectByID(BANK_CHEST_IDS);
		if(objectToDisplay!=null){
			System.out.println("[Bank:open] Bank chest found.");
			if(objectToDisplay.isOnScreen()){
				if(!objectToDisplay.clickTile()){
					System.out.println("[Bank:open] Failed to click chest tile.");
					objectToDisplay=null;
					return false;
				}
				for(int i=0;i<20;++i){
					if(Bank.isOpen()){
						System.out.println("[Bank:open] Bank opened.");
						objectToDisplay=null;
						return true;
					}
					try {
						Thread.sleep(new Random().nextInt(100)+100);
					} catch (Exception e) {
					}
				}
				objectToDisplay=null;
				return true;
			}
			System.out.println("[Bank:open] Bank chest not on screen.");
			objectToDisplay=null;
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
	public boolean widthdraw(int id, int amount){
		InterfaceItem item = getItem(id);
		if(item!=null){
			if(item.getInterfaceChild().getAbsoluteY()<312 && item.getInterfaceChild().getAbsoluteY()>140){
				String action = "";
				if(amount==0)
					action="Widthdraw-All";
				else if(amount==1 || amount==5 || amount==10)
					action="Widthdraw-"+amount;
				else{
					//TODO Widthdraw-X algorithm
					return false;
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
				//TODO scrolling
			}
		}
		return false;
	}
}
