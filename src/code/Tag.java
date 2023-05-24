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
	
	public LuaValue[] action(String key, Character user, Character target) {
		if(this.hasAction(key)) {
			String function = this.actions.get(key);
			this.pushVars();
			ScriptManager.push(user, "user");
			ScriptManager.push("target", target);
			ScriptManager.push("inflictor", this.inflictor);
			
			LuaValue[] value = ScriptManager.executeRaw(function);
			this.update();
			return value;
		} else
			return null;
	}

}
