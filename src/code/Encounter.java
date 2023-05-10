package code;

import java.util.Scanner;

import party.iroiro.luajava.Lua;

public class Encounter {
	
	// TODO add a file with constants
	
//	public static int attackPrimary(Character attacker, Character defender) {
//		
//		ScriptManager.push(attacker, "attacker");
//		ScriptManager.push(defender, "defender");
//		
//		int attack = attacker.rollAttack(true);
//		
//		int damage;
//			damage = defender.rollDefend(attack, attacker.getPrimary().getType());
//		
//		UI.damageNotification(attacker, defender, damage);
//		
////		defender.getArmour().getAttributes().execute("onAbsorb");
//		
//		return damage;
//	}
	
	public static void main(String[] args) {
		ScriptManager.init();
		
		if(ScriptManager.run(new Script("init.lua")) != Lua.LuaError.OK) {
			UI.errorMessage("Unable to initialize Lua/Java libraries!\n Check your init.lua!");
			System.exit(-2);
		}
		
		Character player1 = new Character("player1.char");
		Character player2 = new Character("player2.chr");
		
		UI.bio(player1);
		UI.bio(player2);
		
		UI.brief(player1);
		UI.brief(player2);
		
		player1.save();
		player2.save();
		
		// Scripting
		
		Script thornsMod = new Script("thorns.lua");
		Script otherMod = new Script("mod.lua");
		Script poisionMod = new Script("poision.lua");
		
		Item revolver = new Item("Revolver.lua");

		Item blood = new Item("The_Bloodbath.lua");
		
		player1.equipPrimary(revolver);
		
		UI.scriptInfo(player1.getPrimary());
		
		player1.attack(player2, true);
		player1.attack(player2, true);
		player1.attack(player2, true);
		player1.attack(player2, true);
		player1.attack(player2, true);
		player1.attack(player2, true);
		player1.attack(player2, true);
		
		player1.getPrimary().event("attack");
		
		System.out.println();
		
		UI.brief(player2);
		
		UI.breakln2();
		UI.breakln2();
		
		ScriptManager.trigger(thornsMod, "hello");
		
		player1.addTag(otherMod, "other");
		player1.addTag(poisionMod, "poision");
		
		ScriptManager.push(player1, "main");
		player1.runTagEvent("onStart");
		
		ScriptManager.close();
	}
}
