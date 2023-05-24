package code;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Character {
	
	private String source;
	
	private String name;
	
	private int HP;
	private int maxHP;
	
	private transient boolean act;
	
	private int strength;
	private int agility;
	private int fortitude;
	private int wit;
	private int luck;
	
	private Item primary;
	private Item secondary;
	
	private Item armour;
	
	private Script traits;
	
	private transient HashMap<String, Tag> tags = new HashMap<String, Tag>();
	
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
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
	public boolean canAct() {
		return act;
	}
	public void cantAct() {
		this.act = false;
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
		return new File("characters\\" + this.source);
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
	public Item getPrimary() {
		if(this.primary != null)
			return this.primary;
		else {
			return new Item(null);
		}
			
	}
	public Item loadPrimary() {
		return this.primary;
	}
	public void equipPrimary(Item primary) {
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
	public Item getArmour() {
		if(this.armour != null)
			return this.armour;
		else {
			return new Item(null);
		}
	}
	public Item loadArmour() {
		return armour;
	}
	public void equipArmour(Item armour) {
		this.armour = armour;
	}
	
	public Character(String path) {
		
		this.source = path;
		
		if(!load()) {
			this.source = null;
			
			this.name = "Unnamed Character";
			
			this.HP = 0;
			this.maxHP = 0;
			
			this.strength = 0;
			this.agility = 0;
			this.fortitude = 0;
			this.wit = 0;
			this.luck = 0;
		}
		
	}
	
	public boolean load() {
		Character model = Creator.loadFromPath(this.source);
		
		if(model == null)
			return false;
		
//			this.source = model.getSourcePath();
		
		this.name = model.getName();
		
		this.HP = model.getHP();
		this.maxHP = model.getMaxHP();
		
		this.strength = model.getStrength();
		this.agility = model.getAgility();
		this.fortitude = model.getFortitude();
		this.wit = model.getWit();
		this.luck = model.getLuck();
		
		
		if(model.loadPrimary() != null && model.loadPrimary().parse()) {
			this.primary = model.getPrimary();
		} else
			this.primary = model.loadPrimary();
		
		if(model.loadSecondary() != null && model.loadSecondary().parse()) {
			this.secondary = model.getSecondary();
		} else
			this.secondary = model.loadSecondary();
		
		if(model.loadArmour() != null && model.loadArmour().parse()) {
			this.armour = model.getArmour();
		} else
			this.armour = model.loadArmour();
		
		return true;
		
	}
	
	public void save() {
		Creator.save(this);
	}
	
	public void saveAs(String path) {
		Creator.saveAs(this, path);
	}
	
	public void damage(int amount, String source) {
		this.HP -= amount;
		UI.damage(this, amount, source);
	}
	
	public void sourcelessDamage(int amount) {
		this.HP -= amount;
	}
	
	public void heal(int amount) {
		this.HP -= amount;
	}
	
	public Script getTraits() {
		return traits;
	}
	public void setTraits(Script traits) {
		this.traits = traits;
	}
	
	public Object getVar(String key) {
		return this.traits.getVar(key);
	}
	
	public boolean hasAction(String key) {
		return (this.traits.hasAction(key));
	}
	
	public Object action(String key, Character target) {
		if(!traits.hasAction(key)) {
			UI.message(String.format("%s cannot %s!", this.name, key));
			return null;
		}
		
		return traits.action(key, this, target);
	}
	
	public Object action(String key) {
		if(!traits.hasAction(key)) {
			UI.message(String.format("%s cannot %s!", this.name, key));
			return null;
		}
		
		return traits.action(key, this, null);
	}
	
	public Object itemAction(String key, Character target, boolean primary) {
		Item used = (primary) ? this.primary : this.secondary;
		return used.action(key, this, target);
	}
	
	public HashMap<String, Tag> getTags() {
		return tags;
	}
	public void setTags(HashMap<String, Tag> tags) {
		this.tags = tags;
	}
	public void addTag(String source, Character inflictor) {
		this.tags.put(source.substring(0, source.indexOf(".lua")), new Tag(source, inflictor));
	}
	public void removeTag(String key) {
		this.tags.remove(key);
	}
	public boolean hasTag(String key) {
		return (this.tags.get(key) != null);
	}
	public Tag getTag(String key) {
		return this.tags.get(key);
	}
	public void tagAction(String key, String event) {
		if(this.hasTag(key))
			this.getTag(key).action(event, this, this);
	}
	
	public void runTagActions(String key) {
		for(String tag: this.tags.keySet()) {
			tagAction(tag, key);
		}
	}
	
	public boolean beginTurn() {
		this.act = true;
		
		this.traits.action("*beginTurn", this, null);
		
		this.runTagActions("*beginTurn");
		
		return this.act;
		
	}
	
}

//	public int rollAttack(boolean primary) {
//		
//		Weapon used;
//		
//		if(this.secondary instanceof Weapon && !primary)
//			used = (Weapon) this.secondary;
//		else
//			used = this.getPrimary();
//		
//		UI.attacking(this, used);
//		
//		ScriptManager.push(used, "weapon");
//		ScriptManager.push(this, "user");
//		used.event("onAttack");
//		
//		int damage = 0;
//		
//		if(this.wit >= used.getDifficulty())
//			damage += used.getBonus();
//		else
//			damage -= used.getBonus();
//		
//		if(Util.random(0, 20) + this.luck >= 15) {
//			UI.crit();
//			damage += used.getBonus();
//			
//			if(used.isFinesse())
//				damage += this.agility;
//			else
//				damage += this.strength;
//			
//		}
//		
//		if(used.isFinesse())
//			damage += this.agility;
//		else
//			damage += this.strength;
//		
//		if(damage < 0)
//			damage = 0;
//		
//		return damage;
//	}
	
//	public int rollDefend(int damage, String type) {
//		//TODO add damage typing and vulnerability/resistance
//		// TODO make this good.
//		
//		int hurt = damage;
//		
//		//Dodge roll
//		int roll = Util.random(0, 5) + this.agility + (this.luck) / 2;
//		double percent = (roll / 20);
//		
//		if(roll + this.luck >= damage - damage * percent) {
//			if(roll < damage - damage * percent)
//				UI.lucky(this);
//			UI.dodge(this);
//			hurt = 0;
//		} else {
//			//resistance
//			if(this.armour == null)
//				hurt -= this.fortitude;
//			else {
//				hurt -= this.armour.absorb(damage);
//				if(this.armour.getHealth() == 0) {
//					UI.armourBreak(this);
//					this.armour = null;
//				}
//			}
//		}
//		
//		if(hurt < 0)
//			hurt = 0;
//		
//		this.HP -= hurt;
//		
//		if(this.HP < 0)
//			this.HP = 0;
//		
//		return hurt;
//	}