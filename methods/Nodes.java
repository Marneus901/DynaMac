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
				Node n=node.getNext();
				if (n.getID() == id) {
					return n;
				}
				n=node.getPrevious();
				if (n.getID() == id) {
					return n;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
