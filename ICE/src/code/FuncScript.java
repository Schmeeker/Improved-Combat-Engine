package code;

import java.util.HashMap;

import party.iroiro.luajava.value.LuaValue;

public class FuncScript extends Script {
	
	private HashMap<String, String> functions;
	private HashMap<String, Object> vars;

	public FuncScript(String source) {
		super(source);
		this.functions = new HashMap<String, String>();
		this.vars = new HashMap<String, Object>();
		this.valid = this.parse();
	}
	
	public boolean parse() {
		if(load(this.getSource())) {
			int end = 0;
			
			while(this.getContents().substring(end).contains("function ")) {
				
				int nameStart = this.getContents().indexOf("function ", end) + 9;
				int nameEnd = this.getContents().indexOf("()", nameStart);
				int start = nameEnd + 2;
				end  = this.getContents().indexOf("Fend", start);
				String subContents = this.getContents().substring(start, end).stripIndent();
				String subName = this.getContents().substring(nameStart, nameEnd);
				this.functions.put(subName, subContents);
				
			}
			return true;
		} else
			return false;
	}
	
	public LuaValue[] event(String key) {
		String function = this.functions.get(key);
		
		if(function == null)
			return null;
			
		return ScriptManager.executeRaw(function);
	}

	public HashMap<String, String> getFunctions() {
		return functions;
	}
}
