package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Script {
	
	private String source;
	
	private String contents;
	
	protected boolean valid;
	
	public Script(String source) {
		this.source = source;
		this.valid = (this.load(source));
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
			Scanner code = new Scanner(new File(path));
			
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

}
