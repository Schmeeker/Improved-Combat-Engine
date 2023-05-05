package code;

import party.iroiro.luajava.value.LuaValue;
import party.iroiro.luajava.lua51.*;
import party.iroiro.luajava.*;
import party.iroiro.luajava.Lua.LuaError;

public class ScriptManager {
	
	public static Lua L;
	
	public static void close() {
		L.close();
	}
	
	public static void init() {
		L = new Lua51();
	}
	
	public static LuaError run(Script script) {
		if(!script.isValid() || script.getContents() == null)
			return null;
		
		LuaError error = L.run(script.getContents());
		System.out.println("Lua: " + error);
		return error;
	}
	
	public static LuaError runRaw(String code) {
		LuaError error = L.run(code);
		System.out.println("Lua: " + error);
		return error;
	}
	
	public static LuaValue[] executeRaw(String code) {
		if(code == null)
			return null;
			
		return L.execute(code);
	}
	
	// Only really used for testing
	public static LuaValue[] trigger(Script script, String event) {
		String function = script.getEvents().get(event);
		
		if(function == null)
			return null;
			
		return L.execute(function);
	}
	
	// push a variable to Lua and name it.
	// MUST BE USED WHENEVER A LUA SCRIPT REFERENCES/CHANGES AN ENGINE FIELD
	public static void push(String name, Object object) {
		if(object != null && name != null) {
			L.push(object,Lua.Conversion.FULL);
			L.setGlobal(name);
		}
	}
	
	public static void push(Object object, String name) {
		if(object != null && name != null) {
			L.push(object,Lua.Conversion.FULL);
			L.setGlobal(name);
		}
	}
	
	public static LuaValue[] get(String name) {
		return L.execute("return" + name);
	}
	
	//Stolen code here
	// Not really sure if I want to keep it or whatever,.
	
//	public static boolean load(String key) {
//		boolean success = ScriptManager.register(key, key);
//		if(!success) {
//			UI.errorMessage(String.format("Loading Of %s Script Failed!", key));
//		}
//		return success;
//	}
//	
//	public static boolean register(String key, String fileName) {
//		Script script = new Script(fileName);
//		if(script.isValid()) {
//			if(ScriptManager.scripts.containsKey(key))
//				ScriptManager.scripts.remove(key);
//			
//			ScriptManager.scripts.put(key, script);
//			
//			return true;
//		} else
//			UI.errorMessage(String.format("%s Script %s Not Found!", key, fileName));
//		return false;
//	}
//	
//	public static boolean reload(String key) {
//		if(ScriptManager.scripts.containsKey(key))
//			return register(key, ScriptManager.scripts.get(key).getSource());
//		return false;
//	}
//	
//	public static Script lookup(String key) {
//		if(ScriptManager.scripts.containsKey(key))
//			return ScriptManager.scripts.get(key);
//		else {
//			if(!ScriptManager.load(key))
//				return null;
//			
//			return ScriptManager.scripts.get(key);
//		}
//	}
//	
//	public static LuaValue[] execute(String key, Object... objects) {
//		
//		Script script = ScriptManager.lookup(key);
//		
//		if(script != null) {
//			if(!script.isValid() || script.getContents() == null)
//				return null;
//			return L.execute(script.getContents());
//		} else
//			return null;
//			
//	}

}
