package org.dynamac.bot.api.methods;

import java.awt.event.KeyEvent;

public class Camera {
	public static int getX(){
		return Client.getCameraX();
	}
	public static int getY(){
		return Client.getCameraY();
	}
	public static int getZ(){
		return Client.getCameraZ();
	}
	public static int getPitch(){
		return Client.getCameraPitch();
	}
	public static int getYaw(){
		return Client.getCameraYaw();
	}
	public static void setPitch(boolean up){
		int curr = getPitch();
		Keyboard.pressKey(up?(char)KeyEvent.VK_UP:(char)KeyEvent.VK_DOWN);
		for(int i=0;i<20;++i){
			if(getPitch()>curr){
				i=0;
				curr=getPitch();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		Keyboard.releaseKey(up?(char)KeyEvent.VK_UP:(char)KeyEvent.VK_DOWN);
	}
	public static void setPitch(int alt){
		int curr = getPitch();
		Keyboard.pressKey(alt>curr?(char)KeyEvent.VK_UP:(char)KeyEvent.VK_DOWN);
		for(int i=0;i<20;++i){
			if(getPitch()>curr){
				i=0;
				curr=getPitch();
			}
			if(getPitch()>=alt)
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		Keyboard.releaseKey(alt>curr?(char)KeyEvent.VK_UP:(char)KeyEvent.VK_DOWN);
	}
}
