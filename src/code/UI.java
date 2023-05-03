package code;

public class UI {
	
	public static void displayParty() {
		//TODO stuff
	}
	
	// Hmm... I might want the various specific methods to return strings to return with the message one.
	
	public static void message(String text) {
		System.out.println(text);
	}
	
	public static void endMessage(String text) {
		System.out.println(text);
		breakln2();
	}
	
	public static void breakln() {
		for(int i = 1; i <= 30; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	public static void breakln2() {
		for(int i = 1; i <= 30; i++) {
			System.out.print("=");
		}
		System.out.println();
	}
	
	public static void attacking(Character user, Weapon used) {
		if(used != null)
			message(String.format("%s Is Attacking With %s...", user.getName(), used.getName()));
		else
			message(String.format("%s Is Attacking With %s...", user.getName(), "Their Bare Hands"));
	}
	
	public static void damage(Character user, int amount, String damageSource) {
		if(damageSource != null)
			message(new String(user.getName() + " Took " + amount + " Damage From " + damageSource + "!"));
	}
	
	public static void sourcelessDamage(Character user, int amount) {
		message(new String(user.getName() + " Took " + amount + " Damage!"));
	}
	
	public static void lucky(Character user) {
		message(String.format("%s Is Lucky!", user.getName()));
	}
	
	public static void crit() {
		message(String.format("Critical Hit!"));
	}
	
	public static void dodge(Character user) {
		message(String.format("%s Dodged The Attack!", user.getName()));
	}
	
	public static void armourBreak(Character user) {
		message(String.format("%s's %s Armour Broke!", user.getName(), user.getArmour().getName()));
	}
	
	public static void damageNotification(Character attacker, Character defender, int damage) {
		if(damage > 0)
			endMessage(String.format("%s Attacks %s For %d Damage!", attacker.getName(), defender.getName(), damage));
		else
			endMessage(String.format("%s Missed!", attacker.getName()));
	}
	
	public static void flavour(Character spotlight, String text) {
		message(String.format("%s is %s!", spotlight.getName(), text));
	}
	
	public static void speak(Character speaker, String words) {
		message(String.format("[%s] \"%s\"", speaker.getName(), words));
	}
	
	public static void brief(Character spotlight) {
		String result = ">\n";
		
		result += String.format("Name: %s%n", spotlight.getName());
		
		result += String.format("HP: %d/%d%n", spotlight.getHP(), spotlight.getMaxHP());
		
		if(spotlight.getPrimary() != null)
			result += String.format("Primary: %s%n", spotlight.getPrimary().getName());
		else
			result += String.format("Primary: %s%n", "Nothing");
		
		System.out.println(result);
	}
	
	public static void bio(Character spotlight) {
		String result = ">\n";
		result += Creator.build.toJson(spotlight);
		System.out.println(result);
	}
	
	public static void fileNotFound(String path) {
		errorMessage(String.format("File: %s Does Not Exist! It Must Be Unique.%n", path));
	}
	
	public static void errorMessage(String text) {
		breakln2();
		endMessage(String.format("ERROR: %s%n", text));
	}
	
}