package org.dynamac.bot.api.methods;

public class Settings {
	public static int[] getAll(){
		return Client.getSettings().getData();
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
