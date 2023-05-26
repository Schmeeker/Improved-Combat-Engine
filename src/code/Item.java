package code;

public class Item extends Script {
	
	public String getName() {
		return UI.pretty(this.getSource().substring(0, getSource().indexOf(".")));
	}
	
	public Item(String path) {
		super(path);
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
