package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import party.iroiro.luajava.value.LuaValue;

public class Script {
	
	private String source;
	
	private String contents;
	
	protected boolean valid;
	
	private HashMap<String, String> events;
	private HashMap<String, Object> vars;
	private HashMap<String, String> actions;
	
	public Script(String source) {
		this.source =  source;
		this.valid = (this.load(source));
		this.events = new HashMap<String, String>();
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
				String subName = this.getContents().substring(nameStart, nameEnd);
				this.events.put(subName, subContents);
				
			}
			return true;
		} else
			return false;
	}
	
	public LuaValue[] event(String key) {
		if(this.hasEvent(key)) {
			String function = this.events.get(key);
			this.pushVars();
			LuaValue[] value = ScriptManager.executeRaw(function);
			this.update();
			return value;
		} else
			return null;
	}
	
	public Object eventObj(String key) {
		if(this.hasEvent(key)) {
			String function = this.events.get(key);
			this.pushVars();
			
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

	public void pushVars() {
		ScriptManager.push(this, "me");
		for(String var: this.vars.keySet()) {
			ScriptManager.push(this.getVar(var), var);
		}
	}
	
	public boolean hasEvent(String key) {
		return this.events.get(key) != null;
	}
	public HashMap<String, String> getEvents() {
		return events;
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
}
