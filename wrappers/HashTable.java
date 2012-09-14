/******************************************************
* Created by Marneus901                                *
* © 2012 MarneusScripts.com                            *
* **************************************************** *
* Access to this source is unauthorized without prior  *
* authorization from its appropriate author(s).        *
* You are not permitted to release, nor distribute this* 
* work without appropriate author(s) authorization.    *
********************************************************/
package org.dynamac.bot.api.wrappers;

import java.lang.reflect.Array;

import org.dynamac.enviroment.Data;
import org.dynamac.enviroment.hook.ClassHook;
import org.dynamac.enviroment.hook.FieldHook;


public class HashTable {
	public Object currentObject;
	public ClassHook currentHook;
	public HashTable(Object o){
		currentObject = o;
		currentHook = Data.indentifiedClasses.get("HashTable");
	}
	public Node[] getBuckets(){
		FieldHook fh = currentHook.getFieldHook("getBuckets");
		if(fh!=null){
			Object data = fh.getData(currentObject);
			Node[] nodes = new Node[Array.getLength(data)];
			for(int i=0;i<nodes.length;++i)
				nodes[i]=new Node(Array.get(data, i));
			return nodes;
		}			
		return new Node[]{};
	}
}
