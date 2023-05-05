package code;

public class Weapon extends Item {
	
	private int bonus;
	private int difficulty;
	
	private boolean finesse = false;
	
	private String type;
	
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public boolean isFinesse() {
		return finesse;
	}
	public void setFinesse(boolean finesse) {
		this.finesse = finesse;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Weapon(String path) {
		super(path);
		this.bonus = 0;
		this.difficulty = 0;
		this.type = null;
		this.finesse = false;
		
//		this.setName("Their Fists");
	}
	
}
