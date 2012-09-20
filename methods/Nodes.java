/******************************************************
 * Created by Marneus901                                *
 * © 2012 MarneusScripts.com                            *
 * **************************************************** *
 * Access to this source is unauthorized without prior  *
 * authorization from its appropriate author(s).        *
 * You are not permitted to release, nor distribute this* 
 * work without appropriate author(s) authorization.    *
 ********************************************************/
package org.dynamac.bot.api.methods;

import org.dynamac.bot.api.wrappers.HashTable;
import org.dynamac.bot.api.wrappers.Node;

public class Nodes {
	public static Node lookup(HashTable nc, long id) {
		try {
			if (nc == null || nc.getBuckets() == null || id < 0) {
				return null;
			}
			for(Node node : nc.getBuckets()){
				for(Node in = node.getNext();in!=null && !in.currentObject.equals(node.currentObject);in=in.getNext()){
					try{
						if(in.getID()==id)
							return in;
					}
					catch(Exception e){}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
