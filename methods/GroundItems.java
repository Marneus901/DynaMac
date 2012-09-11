package org.dynamac.bot.api.methods;

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.GroundItem;
import org.dynamac.bot.api.wrappers.HashTable;
import org.dynamac.bot.api.wrappers.Item;
import org.dynamac.bot.api.wrappers.Node;
import org.dynamac.bot.api.wrappers.NodeList;
import org.dynamac.bot.api.wrappers.NodeListCache;
import org.dynamac.enviroment.Data;

public class GroundItems {
	public static GroundItem[] getItemsAt(int x, int y){
		try{
			ArrayList<GroundItem> items = new ArrayList<GroundItem>();
			HashTable itemTable = Client.getItemHashTable();
			int index = x | y << 14 | Client.getPlane() << 28;
			NodeListCache cache = new NodeListCache(Nodes.lookup(itemTable, index).currentObject);
			NodeList nodeList = cache.getNodeList();
			Node tail = nodeList.getTail();
			for(Node curr = tail.getPrevious();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getPrevious()){	
				if(curr.currentObject.getClass().getName().equals(Data.indentifiedClasses.get("Item").getClassName())){
					items.add(new GroundItem(x, y, new Item(curr.currentObject)));
				}
			}	
			return items.toArray(new GroundItem[]{});
		}
		catch(Exception e){
		}
		return new GroundItem[]{};
	}
}
