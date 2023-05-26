package code;

import party.iroiro.luajava.value.LuaValue;

public class Tag extends Script{
	
	private Character inflictor;
	
	public Tag(String source, Character inflictor) {
		super(source);
		this.inflictor = inflictor;
	}

	public Character getInflictor() {
		return inflictor;
	}
	
	public String getName() {
		return UI.pretty(this.getSource().substring(0, getSource().indexOf(".")));
	}
	
	public LuaValue[] action(String key, Character afflicted, Character inflictor) {
		if(this.hasAction(key)) {
			String function = this.actions.get(key);
			this.pushVars();
			ScriptManager.push(afflicted, "afflicted");
			ScriptManager.push("inflictor", this.inflictor);
			ScriptManager.push("target", afflicted);
			
			LuaValue[] value = ScriptManager.executeRaw(function);
			this.update();
			return value;
		} else
			return null;
	}

}
