package code;

import party.iroiro.luajava.value.LuaValue;
import party.iroiro.luajava.lua51.*;
import party.iroiro.luajava.*;
import party.iroiro.luajava.Lua.LuaError;

import java.util.HashMap;

public class ScriptManager {
	
	private static HashMap<String, Script> scripts;
	
	public static Lua L;
	
	public ScriptManager() {
		ScriptManager.scripts = new HashMap<String, Script>();
	}
	
	public static void close() {
		L.close();
	}
	
	public static void init() {
		ScriptManager.scripts = new HashMap<String, Script>();
		L = new Lua51();
	}
	
	public static LuaValue[] execute(Script script) {
		if(!script.isValid() || script.getContents() == null)
			return null;
		
		return L.execute(script.getContents());
	}
	
	public static LuaValue[] executeRaw(String code) {
		if(code == null)
			return null;
			
		return L.execute(code);
	}
	
	public static LuaValue[] execute(FuncScript origin, String key) {
		String function = origin.getFunctions().get(key);
		
		if(function == null)
			return null;
			
		return L.execute(function);
	}
	
	public static LuaError test(String code) {
		LuaError error = L.run(code);
		System.out.println(error);
		return error;
	}
	
	//Stolen code here
	// Not really sure if I want to keep it or whatever,.
	
	public static boolean load(String key) {
		boolean success = ScriptManager.register(key, key);
		if(!success) {
			UI.errorMessage(String.format("Loading Of %s Script Failed!", key));
		}
		return success;
	}
	
	public static boolean register(String key, String fileName) {
		Script script = new Script(fileName);
		if(script.isValid()) {
			if(ScriptManager.scripts.containsKey(key))
				ScriptManager.scripts.remove(key);
			
			ScriptManager.scripts.put(key, script);
			
			return true;
		} else
			UI.errorMessage(String.format("%s Script %s Not Found!", key, fileName));
		return false;
	}
	
	public static boolean reload(String key) {
		if(ScriptManager.scripts.containsKey(key))
			return register(key, ScriptManager.scripts.get(key).getSource());
		return false;
	}
	
	public static Script lookup(String key) {
		if(ScriptManager.scripts.containsKey(key))
			return ScriptManager.scripts.get(key);
		else {
			if(!ScriptManager.load(key))
				return null;
			
			return ScriptManager.scripts.get(key);
		}
	}
	
	public static LuaValue[] execute(String key, Object... objects) {
		
		Script script = ScriptManager.lookup(key);
		
		if(script != null) {
			if(!script.isValid() || script.getContents() == null)
				return null;
			return L.execute(script.getContents());
		} else
			return null;
			
	}
	
	public static void push(Object object, String name) {
		if(object != null && name != null) {
			L.push(object,Lua.Conversion.FULL);
			L.setGlobal(name);
		}
	}

}
