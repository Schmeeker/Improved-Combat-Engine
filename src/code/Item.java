package code;

import party.iroiro.luajava.value.LuaValue;

public class Item extends Script {
	
	private transient String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Item(String path) {
		super(path);
		if(path != null)
			this.name = path.substring(0, path.indexOf("."));
		else
			this.name = "Nothing";
	}
	
	
//	public LuaValue[] event(String key, Character user, Character target) {
//		if(this.hasEvent(key)) {
//			String function = this.getEvents().get(key);
//			this.pushVars();
//			ScriptManager.push(user, "user");
//			ScriptManager.push("target", target);
//			LuaValue[] value = ScriptManager.executeRaw(function);
//			this.update();
//			return value;
//		} else
//			return null;
//	}
//	
//	public Object eventObj(String key, Character user, Character target) {
//		if(this.hasEvent(key)) {
//			String function = this.getEvents().get(key);
//			this.pushVars();
//			ScriptManager.push(user, "user");
//			ScriptManager.push("target", target);
//			
//			Object returnObject;
//			try
//			{	returnObject = ScriptManager.executeRaw(function)[0].toJavaObject();}
//			catch (ArrayIndexOutOfBoundsException e)
//			{	returnObject = ScriptManager.executeRaw(function);}
//			
//			this.update();
//			
//			return returnObject;
//		} else
//			return null;
//	}
	
//	// Stop stupid people from not passing all the arguments.
//	@Override
//	@Deprecated
//	public LuaValue[] event(String key) {
//		new Exception("Incorrect usage of Item.event(). Requires user, target.").printStackTrace();
//		return null;
//	}
//	
//	@Override
//	@Deprecated
//	public Object eventObj(String key) {
//		new Exception("Incorrect usage of Item.eventObj(). Requires user, target.").printStackTrace();
//		return null;
//	}
	
	
}
