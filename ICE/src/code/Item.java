package code;

import java.io.IOException;

public class Item {
	
	private String source;
	
	private String name;
	
	private int weight;
	
	private FuncScript attributes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSourcePath() {
		return source;
	}
	public void setSourcePath(String path) {
		this.source = path;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public Item(String path) {
		this.source = path;
		this.name = "Unnamed Item";
		this.weight = 0;
	}
	
	public void reload() throws IOException {
		Item model = Creator.loadItem(this);
		
		this.name = model.getName();
		
		this.source = model.getSourcePath();
		
		this.weight = model.getWeight();
	}
	
	public void save() throws IOException {
		Creator.saveItem(this);
	}
	
	public FuncScript getAttributes() {
		return attributes;
	}
	public void setAttributes(FuncScript attributes) {
		this.attributes = attributes;
	}
	
}
