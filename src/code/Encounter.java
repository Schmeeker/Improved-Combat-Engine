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
		
		Character player1 = new Character("player1.chr");
		Character player2 = new Character("Jerome.chr");
		
		UI.bio(player1);
		UI.bio(player2);
		
		UI.brief(player1);
		UI.brief(player2);
		
		player1.save();
		player2.save();
		
		// Scripting
		
		Script thornsMod = new Script("thorns.lua");
		
		Item revolver = new Item("Bug_Zapper.lua");
		
		Script trainee = new Script("Trainee.lua");
		
		trainee.rebuild();
		
//		player1.equipPrimary(revolver);
		
		player1.setTraits(trainee);
		
		UI.scriptInfo(player1.getTraits());
		
		player1.action("study");
		
		player1.action("skilled_attack", player2);
		
		player1.action("skilled_attack", player2);
		
		player1.action("lucky_attack", player2);
		
		
		System.out.println();
		
		UI.brief(player2);
		
		player1.addTag("poision.lua", player2);
		
		UI.scriptInfo((Script) player1.getTag("poision"));
		
		ScriptManager.push(player1, "main");
		player1.beginTurn();
		
		UI.scriptInfo(player2.getTraits());
		
		player2.action("geyser", player1);
		player2.action("geyser", player1);
		player2.action("lightning", player1);
		player2.action("geyser", player1);
		player2.action("leech_seed", player1);
		player2.action("fireball", player1);
		player2.action("repair", null);
		player2.beginTurn();
		player2.beginTurn();
		player2.beginTurn();
		player2.action("lightning", player1);
		player2.action("fireball", player1);
		
		ScriptManager.close();
	}
}
