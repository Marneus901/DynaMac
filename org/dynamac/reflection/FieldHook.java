/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's field hook for reflection.                 *
********************************************************/
package org.dynamac.reflection;

import java.lang.reflect.Field;

import org.dynamac.enviroment.Data;
import org.objectweb.asm.tree.FieldNode;

public class FieldHook{
	private String refactoredName="";
	private Field reflectedField=null;
	private FieldNode bytecodeField;
	private int intMultiplier=1;
	private long longMultiplier=1;
	public FieldHook(String name, FieldNode fn){
		refactoredName=name;
		bytecodeField=fn;
	}
	/**
	 * Gets the field value.
	 * Static field use only.
	 * @return Field data
	 */
	public Object get(){
		try {
			boolean isAccessible = reflectedField.isAccessible();
			if(!isAccessible)
				reflectedField.setAccessible(true);
			Object data = reflectedField.get(Data.CLIENT_INSTANCE);
			if(!isAccessible)
				reflectedField.setAccessible(false);
			return data;
		} 
		catch (Exception e) {
			//Should not happen.
			return null;
		}
	}
	/**
	 * Gets the field value.
	 * For non-static fields.
	 * @param Parent instance
	 * @return Field data
	 */
	public Object get(Object parent){
		try {
			boolean isAccessible = reflectedField.isAccessible();
			if(!isAccessible)
				reflectedField.setAccessible(true);
			Object data = reflectedField.get(parent);
			if(!isAccessible)
				reflectedField.setAccessible(false);
			return data;
		} 
		catch(Exception e) {
			//Should not happen in wrappers.
			return null;
		}
	}
	public FieldNode getBytecodeField(){
		return bytecodeField;
	}
	public int getIntMultiplier(){
		return intMultiplier;
	}
	public long getLongMultiplier(){
		return longMultiplier;
	}
	public String getName(){
		return bytecodeField.name;
	}
	public Field getReflectedField(){
		return reflectedField;
	}
	public String getRefactoredName(){
		return refactoredName;
	}
	/**
	 * Sets the field value.
	 * Static field use only.
	 * @param Data instance
	 */
	public void set(Object data){
		try{
			Field field = getReflectedField();
			if(field!=null)
				field.set(Data.CLIENT_INSTANCE, data);
		}
		catch(Exception e){
			
		}
	}
	/**
	 * Sets the field value.
	 * For non-static fields.
	 * @param Parent instance
	 * @param Data instance
	 */
	public void set(Object parent, Object data){
		try{
			Field field = getReflectedField();
			if(field!=null)
				field.set(parent, data);
		}
		catch(Exception e){
			
		}
	}
	public void setHook(Field f){
		reflectedField=f;
	}
	public void setMultiplier(int i){
		intMultiplier=i;
	}
	public void setMultiplier(long l){
		longMultiplier=l;
	}
	public void setRefactoredName(String s){
		refactoredName=s;
	}
}
