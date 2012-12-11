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

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Random;

import org.dynamac.environment.Data;

public class Keyboard {	
	public static int getLocation(final char ch) {
		if (ch >= KeyEvent.VK_SHIFT && ch <= KeyEvent.VK_ALT) {
			return new Random().nextInt((KeyEvent.KEY_LOCATION_RIGHT + 1)-KeyEvent.KEY_LOCATION_LEFT)+KeyEvent.KEY_LOCATION_LEFT;
		}
		return KeyEvent.KEY_LOCATION_STANDARD;
	}
	public static void pressKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = Data.CLIENT_APPLET.getComponent(0);
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, Keyboard.getLocation(s));
		keyboardTarget.dispatchEvent(event); 
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
		keyboardTarget.dispatchEvent(event);
	}
	public static void releaseKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = Data.CLIENT_APPLET.getComponent(0);
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, Keyboard.getLocation(s));
		keyboardTarget.dispatchEvent(event);
	}
	public static void sendKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = Data.CLIENT_APPLET.getComponent(0);
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, Keyboard.getLocation(s));
		keyboardTarget.dispatchEvent(event); 
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
		keyboardTarget.dispatchEvent(event);
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, Keyboard.getLocation(s));
		keyboardTarget.dispatchEvent(event);
	}
	/**
	*@author Inf3cti0us 
     	* Erases all text of the given WidgetChild.
    	* @param textComponent - The component wher the text is found.
    	*/
	   public void eraseAllText(final InterfaceChild textComponent){
        final String text = textComponent.getText();
        for(int i = 0; i <= text.length(); ++i){
            Keyboard.sendKey((char) KeyEvent.VK_BACK_SPACE, Random.nextInt(500, 600));
        }

    }

	public static void sendKeys(String str){
		for(int i=0;i<str.length();++i){
			sendKey(str.charAt(i));
			try{
				Thread.sleep(new Random().nextInt(100));
			}
			catch(Exception e){}
		}
	}
}
