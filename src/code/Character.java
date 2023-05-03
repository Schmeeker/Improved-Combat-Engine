package code;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Character {
	
	private String source;
	
	private String name;
	
	private int HP;
	private int maxHP;
	
	private int strength;
	private int agility;
	private int fortitude;
	private int wit;
	private int luck;
	
	private Weapon primary;
	private Item secondary;
	
	private Armour armour;
	
	private HashMap<String, Integer> resist = new HashMap<String, Integer>();
	
	private FuncScript actions;
	
	private HashMap<String, FuncScript> tags = new HashMap<String, FuncScript>();
	
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public void damage (int amount) {
		this.HP -= amount;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public void healMaxHP() {
		this.HP = this.maxHP;
	}
	public void healMaxHP(int maxHP) {
		this.maxHP = maxHP;
		this.HP = maxHP;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getAgility() {
		return agility;
	}
	public void setAgility(int agility) {
		this.agility = agility;
	}
	public int getFortitude() {
		return fortitude;
	}
	public void setFortitude(int fortitude) {
		this.fortitude = fortitude;
	}
	public int getWit() {
		return wit;
	}
	public void setWit(int wit) {
		this.wit = wit;
	}
	public int getLuck() {
		return luck;
	}
	public void setLuck(int luck) {
		this.luck = luck;
	}
	public String getSourcePath() {
		return source;
	}
	public void setSourcePath(String path) {
		this.source = path;
	}
	public File getSourceFile() {
		return new File(this.source);
	}
	public void setSourceFile(File source) {
		this.source = source.getPath();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Weapon getPrimary() {
		if(this.primary != null)
			return this.primary;
		else {
			return new Weapon(null);
		}
			
	}
	public Weapon loadPrimary() {
		return this.primary;
	}
	public void equipPrimary(Weapon primary) {
		this.primary = primary;
	}
	public Item getSecondary() {
		if(this.secondary != null)
			return this.secondary;
		else {
			return new Item(null);
		}
	}
	public Item loadSecondary() {
		return this.secondary;
	}
	public void equipSecondary(Item secondary) {
		this.secondary = secondary;
	}
	public Armour getArmour() {
		if(this.armour != null)
			return this.armour;
		else {
			return new Armour(null);
		}
	}
	public Armour loadArmour() {
		return armour;
	}
	public void equipArmour(Armour armour) {
		this.armour = armour;
	}
	public HashMap<String, Integer> getResist() {
		return resist;
	}
	public void setResist(HashMap<String, Integer> resist) {
		this.resist = resist;
	}
	
	public Character(String path) {
		this.source = path;
		
		this.name = "Unnamed Character";
		
		this.HP = 0;
		this.maxHP = 0;
		
		this.strength = 0;
		this.agility = 0;
		this.fortitude = 0;
		this.wit = 0;
		this.luck = 0;
		
	}
	
	public void load() {
		try {
			
			Character model = Creator.loadFromPath(this.source);
			
//			this.source = model.getSourcePath();
			
			this.name = model.getName();
			
			this.HP = model.getHP();
			this.maxHP = model.getMaxHP();
			
			this.strength = model.getStrength();
			this.agility = model.getAgility();
			this.fortitude = model.getFortitude();
			this.wit = model.getWit();
			this.luck = model.getLuck();
			
			
			if(model.loadPrimary() != null) {
				try {
					this.primary = (Weapon) Creator.loadItem(model.getPrimary());
				} catch (IOException e) {
					UI.fileNotFound(model.getPrimary().getSourcePath());
					this.primary = model.loadPrimary();
				}
			} else
				this.primary = model.loadPrimary();
			
			if(model.loadSecondary() != null) {
				try {
					this.secondary = Creator.loadItem(model.getSecondary());
				} catch (IOException e) {
					UI.fileNotFound(model.getSecondary().getSourcePath());
					this.secondary = model.loadSecondary();
				}
			} else
				this.secondary = model.loadSecondary();
			
			if(model.loadArmour() != null) {
				try {
					this.armour = (Armour) Creator.loadItem(model.getArmour());
				} catch (IOException e) {
					UI.fileNotFound(model.getArmour().getSourcePath());
					this.armour = model.loadArmour();
				}
			} else
				this.armour = model.loadArmour();
			
			
			this.resist = model.getResist();
			
			// Check that nothing is null!
			
		} catch (IOException e1) {
			UI.fileNotFound(this.getSourcePath());
		}
		
	}
	
	public void save() throws IOException {
		Creator.save(this);
	}
	
	public void saveAs(String path) throws IOException {
		Creator.saveAs(this, path);
	}
	
	public int rollAttack(boolean primary) {
		
		Weapon used;
		
		if(this.secondary instanceof Weapon && !primary)
			used = (Weapon) this.secondary;
		else
			used = this.getPrimary();
		
		UI.attacking(this, used);
		
		ScriptManager.push(used, "weapon");
		ScriptManager.push(this, "user");
		used.getAttributes().execute("onSwing");
		
		//TODO add unarmed strikes
		if(used == null)
			return -1;
		
		int damage = 0;
		
		if(this.wit >= used.getDifficulty())
			damage += used.getBonus();
		else
			damage -= used.getBonus();
		
		if(Util.random(0, 20) + this.luck >= 15) {
			UI.crit();
			damage += used.getBonus();
			
			if(used.isFinesse())
				damage += this.agility;
			else
				damage += this.strength;
			
		}
		
		if(used.isFinesse())
			damage += this.agility;
		else
			damage += this.strength;
		
		if(damage < 0)
			damage = 0;
		
		return damage;
	}
	
	public int rollDefend(int damage, String type) {
		//TODO add damage typing and vulnerability/resistance
		// TODO make this good.
		
		int hurt = damage;
		
		if(this.resist.containsKey(type))
			hurt -= (hurt * resist.get(type));
		
		//Dodge roll
		int roll = Util.random(0, 5) + this.agility + (this.luck) / 2;
		double percent = (roll / 20);
		
		if(roll + this.luck >= damage - damage * percent) {
			if(roll < damage - damage * percent)
				UI.lucky(this);
			UI.dodge(this);
			hurt = 0;
		} else {
			//resistance
			if(this.armour == null)
				hurt -= this.fortitude;
			else {
				hurt -= this.armour.absorb(damage);
				if(this.armour.getHealth() == 0) {
					UI.armourBreak(this);
					this.armour = null;
				}
			}
		}
		
		if(hurt < 0)
			hurt = 0;
		
		this.HP -= hurt;
		
		if(this.HP < 0)
			this.HP = 0;
		
		return hurt;
	}
	
	public FuncScript getActions() {
		return actions;
	}
	public void setActions(FuncScript actions) {
		this.actions = actions;
	}
	public HashMap<String, FuncScript> getTags() {
		return tags;
	}
	public void setTags(HashMap<String, FuncScript> tags) {
		this.tags = tags;
	}
	
	public void act(String key) {
		if(key != null) {
			this.actions.execute(key);
		}
	}
	
	public void addTag(FuncScript tag, String name) {
		this.tags.put(name, tag);
	}
	public void removeTag(String name) {
		this.tags.remove(name);
	}
	public boolean hasTag(String name) {
		return this.tags.get(name) != null;
	}
	
	private int calculateWeight() {
		int result = 0;
		
		if(this.primary != null)
			result += this.primary.getWeight();
		if(this.secondary != null)
			result += this.primary.getWeight();
		if(this.armour != null)
			result += this.primary.getWeight();
		
		return result;
	}
	
}
