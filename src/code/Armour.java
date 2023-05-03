package code;

public class Armour extends Item {
	
	private int protection;
	private int threshold;
	private int health;
	
	public int getProtection() {
		return protection;
	}
	public void setProtection(int protection) {
		this.protection = protection;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public Armour(String path) {
		super(path);
		this.protection = 0;
		this.threshold = 0;
		this.health = -1;
		
		this.setName("No Armour");
	}
	
	public int absorb(int damage) {
		
		if(this.health == -1)
			return 0;
		
		double absorbed = damage * ( this.protection * 0.01 );
		
		if(absorbed > this.threshold) {
			this.health -= (absorbed - this.threshold);
			this.protection--;
			//TODO should protection go down?
			
			if(this.health < 0)
				this.health = 0;
		}
		
		ScriptManager.push(this, "armour");
		ScriptManager.push(absorbed, "damage");
		this.getAttributes().execute("onAbsorb");
		
		return (int) absorbed;
		
	}
	
}
