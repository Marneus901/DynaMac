/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's global/static data.                        *
********************************************************/
package org.dynamac.enviroment;

import java.io.File;
import java.util.HashMap;

import org.dynamac.analyzer.gui.AnalyzerMain;
import org.dynamac.gui.ClientGUI;
import org.dynamac.reflection.ClassHook;
import org.dynamac.reflection.FieldHook;
import org.dynamac.script.ScriptDef;

public class Data {
	public static ClassLoader CLIENT_CLASSLOADER=null;
	public static Object CLIENT_INSTANCE=null;
	public static HashMap<String, FieldHook> REFLECTION_STATIC_HOOKS = new HashMap<String, FieldHook>();
	public static HashMap<String, ClassHook> REFLECTION_CLIENT_HOOKS = new HashMap<String, ClassHook>();
	public static ScriptDef CURRENT_SCRIPT;
	public static ClientGUI clientGUI;
	public static File clientFile;
	public static AnalyzerMain analyzerInstance;
}
