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

import java.util.ArrayList;

import org.dynamac.bot.api.wrappers.Interface;
import org.dynamac.bot.api.wrappers.InterfaceChild;

public class Interfaces {
	public static InterfaceChild getChild(final int id) {
		final int x = id >> 0x10;
		final int y = id & 0xffff;
		return Client.getInterfaceCache()[x].getChildren()[y];
	}
	public static Interface[] getInterfaces(){
		ArrayList<Interface> interfaces = new ArrayList<Interface>();
		int k=0;
		for(Interface i : Client.getInterfaceCache()){
    		if(i!=null && i.getChildren().length>0){
    			interfaces.add(i);
    		}
    		k++;
    	}
		return interfaces.toArray(new Interface[]{});
	}
}
