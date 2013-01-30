/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's class hook for reflection.                 *
********************************************************/
package org.dynamac.reflection;

import java.util.HashMap;

import org.dynamac.enviroment.Data;
import org.objectweb.asm.tree.ClassNode;

public class ClassHook {
	private ClassNode bytecodeClass;
	private Class<?> reflectedClass;
	private String refactoredName="";
	private HashMap<String, FieldHook> fieldHooks = new HashMap<String, FieldHook>();
	public ClassHook(String name, ClassNode cn){
		refactoredName=name;
		bytecodeClass=cn;
	}
	public boolean addFieldHook(FieldHook fh){
		return fieldHooks.put(fh.getRefactoredName(), fh)!=null;
	}
	public FieldHook[] getAllFieldHooks(){
		return fieldHooks.values().toArray(new FieldHook[]{});
	}
	public ClassNode getBytecodeClass(){
		return bytecodeClass;
	}
	public String getClassName(){
		return bytecodeClass.name;
	}
	public FieldHook getFieldHook(String refName){
		return fieldHooks.get(refName);
	}
	public String getRefactoredName(){
		return refactoredName;
	}
	public void loadRuntime(){
		try {
			reflectedClass=Data.CLIENT_CLASSLOADER.loadClass(bytecodeClass.name.replace('/', '.'));
			if(reflectedClass==null){
				System.out.println("ClassLoader failed to load class : "+bytecodeClass.name);
				return;
			}
			System.out.println("Loaded runtime class hook : "+refactoredName);
			System.out.println("Loading "+fieldHooks.size()+" field hooks...");
			HashMap<String, FieldHook> staticFields = new HashMap<String, FieldHook>();
			for(FieldHook fh : fieldHooks.values()){
				for(java.lang.reflect.Field f : reflectedClass.getDeclaredFields()){
					if(f.getName().equals(fh.getName())){
						fh.setHook(f);
						if(fh.getBytecodeField().isStatic()){
							staticFields.put(fh.getRefactoredName(), fh);
							System.out.println("Loaded runtime static hook : "+fh.getRefactoredName());	
						}
						else
							System.out.println("Loaded runtime field hook : "+fh.getRefactoredName());
					}
				}
			}
			for(FieldHook fh : staticFields.values()){
				Data.REFLECTION_STATIC_HOOKS.put(fh.getRefactoredName(), fh);//TODO write classname as well
				fieldHooks.remove(fh.getRefactoredName());
			}
		} catch (Exception e) {
			System.out.println("Failed to load runtime class hook : "+refactoredName);
			e.printStackTrace();
			return;
		}
	}
	public void setBytecodeClass(ClassNode node){
		bytecodeClass = node;
	}
}
