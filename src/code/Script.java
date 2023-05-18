package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import party.iroiro.luajava.Lua.LuaError;
import party.iroiro.luajava.value.LuaValue;

public class Script {
	
	private String source;
	
	private transient String contents;
	
	protected transient boolean valid;
	
	private transient HashMap<String, String> actions;
	private transient HashMap<String, Object> vars;
	
	public Script(String source) {
		this.source =  source;
		this.valid = (this.load(source));
		this.actions = new HashMap<String, String>();
		this.vars = new HashMap<String, Object>();
		this.valid = this.parse();
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isValid() {
		return valid;
	}
	public String getContents() {
		return this.contents;
	}
	public HashMap<String, Object> getVars() {
		return vars;
	}
	
	public Object getVar(String varName) {
		return this.vars.get(varName);
	}
	public void update() {
		for(String var: this.vars.keySet()) {
			this.vars.put(var, ScriptManager.executeRaw("return " + var)[0].toJavaObject());
		}
	}
	
	public boolean load(String path) {
		this.source = path;
		
		try {
			Scanner code = new Scanner(new File("scripts\\" + path));
			
			String contents = "";
			
			while(code.hasNextLine())
				contents += code.nextLine();
			
			this.contents = contents;
			
			code.close();
			
			this.valid = true;
			return true;
			
		} catch (FileNotFoundException e) {
			this.valid = false;
			return false;
		}
	}
	
	public boolean reload() {
		return this.load(this.source);
	}
	
	public boolean parse() {
		
		this.valid = (this.load(source));
		this.actions = new HashMap<String, String>();
		this.vars = new HashMap<String, Object>();
		this.actions = new HashMap<String, String>();
		
		if(load(this.getSource())) {
			
			int end = 0;
			
			String keyword = "var ";
			String endword = ";";
			
			while(this.getContents().substring(end).contains(keyword)) {
				
				int nameStart = this.getContents().indexOf(keyword, end) + keyword.length();
				int nameEnd = this.getContents().indexOf(" = ", nameStart);
				
				int start = nameEnd + 3;
				end  = this.getContents().indexOf(endword, start);
				
				String subContents = this.getContents().substring(start, end).stripIndent();
				String subName = this.getContents().substring(nameStart, nameEnd);
				
				this.vars.put(subName, ScriptManager.executeRaw("return " + subContents)[0].toJavaObject());
				
			}
			
			end = 0;
			
			keyword = "event ";
			endword = "!Event";
			
			while(this.getContents().substring(end).contains(keyword)) {
				
				int nameStart = this.getContents().indexOf(keyword, end) + keyword.length();
				int nameEnd = this.getContents().indexOf("()", nameStart);
				int start = nameEnd + 2;
				end  = this.getContents().indexOf(endword, start);
				String subContents = this.getContents().substring(start, end).stripIndent();
				String subName = "*" + this.getContents().substring(nameStart, nameEnd);
				this.actions.put(subName, subContents);
				
			}
			
			end = 0;
			
			keyword = "action ";
			endword = "!Action";
			
			while(this.getContents().substring(end).contains(keyword)) {
				
				int nameStart = this.getContents().indexOf(keyword, end) + keyword.length();
				int nameEnd = this.getContents().indexOf("()", nameStart);
				int start = nameEnd + 2;
				end  = this.getContents().indexOf(endword, start);
				String subContents = this.getContents().substring(start, end).stripIndent();
				String subName = this.getContents().substring(nameStart, nameEnd);
				
				this.actions.put(subName, subContents);
				
			}
			return true;
		} else
			return false;
	}
	
	public boolean rebuild() {
		return this.parse();
	}
	
	public void pushVars() {
		ScriptManager.push(this, "me");
		for(String var: this.vars.keySet()) {
			ScriptManager.push(this.getVar(var), var);
		}
	}
	
	public boolean hasAction(String key) {
		return this.actions.get(key) != null;
	}
	public HashMap<String, String> getActions() {
		return actions;
	}
	public int numActions() {
		int result = 0;
		for(String action: this.actions.keySet()) {
			if(!action.startsWith("*"))
				result ++;
		}
		return result;
	}
	public int numEvents() {
		int result = 0;
		for(String action: this.actions.keySet()) {
			if(action.startsWith("*"))
				result ++;
		}
		return result;
	}
	
	public LuaValue[] action(String key, Character user, Character target) {
		if(this.hasAction(key)) {
			String function = this.actions.get(key);
			this.pushVars();
			ScriptManager.push(user, "user");
			ScriptManager.push("target", target);
			
			LuaValue[] value = ScriptManager.executeRaw(function);
			this.update();
			return value;
		} else
			return null;
	}
	
	public Object actionObj(String key, Character user, Character target) {
		if(this.hasAction(key)) {
			String function = this.actions.get(key);
			this.pushVars();
			
			ScriptManager.push(user, "user");
			ScriptManager.push("target", target);
			
			Object returnObject;
			try
			{	returnObject = ScriptManager.executeRaw(function)[0].toJavaObject();}
			catch (ArrayIndexOutOfBoundsException e)
			{	returnObject = ScriptManager.executeRaw(function);}
			
			this.update();
			
			return returnObject;
		} else
			return null;
	}
	
}
