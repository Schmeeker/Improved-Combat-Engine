package code;

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
	
}
