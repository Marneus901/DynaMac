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

import org.dynamac.bot.api.wrappers.Facade;

public class Settings {
	public static int[] getAll(){
		Facade fac = Client.getFacade();
		if(fac!=null){
			org.dynamac.bot.api.wrappers.Settings sets = fac.getSettings();
			if(sets!=null)
				return sets.getData();
		}
		return new int[]{};
	}
	public static int get(int index){
		int[] all = getAll();
		if(all.length>index)
			return all[index];
		return -1;
	}
	public static int get(int index, int mask){
		return get(index) & mask;
	}
	public static int get(int index, int shift, int mask){
		return (get(index)>>shift)&mask;
	}
}
