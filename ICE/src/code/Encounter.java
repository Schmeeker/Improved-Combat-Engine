package code;

import java.io.IOException;
import java.util.Scanner;

import party.iroiro.luajava.Lua;
import party.iroiro.luajava.lua51.Lua51;

public class Encounter {
	
	// TODO add a file with constants
	
	public static int attackPrimary(Character attacker, Character defender) {
		
		ScriptManager.push(attacker, "attacker");
		ScriptManager.push(defender, "defender");
		
		int attack = attacker.rollAttack(true);
		
		int damage;
			damage = defender.rollDefend(attack, attacker.getPrimary().getType());
		
		UI.damageNotification(attacker, defender, damage);
		
//		defender.getArmour().getAttributes().execute("onAbsorb");
		
		return damage;
	}
	
	public static void main(String[] args) throws IOException {
		ScriptManager.init();
		ScriptManager.execute(new Script("init.lua"));
		
		Character player1 = new Character("player1.char");
		Character player2 = new Character("player2.char");
		
		player1.load();
		player2.load();
		
		UI.brief(player1);
		
		System.out.println();
		
		UI.brief(player2);
		
		UI.bio(player1);
		
		player1.save();
		player2.save();
		
		// Scripting
		
		FuncScript thornsMod = new FuncScript("thorns.lua");
		FuncScript otherMod = new FuncScript("mod.lua");
		FuncScript poisionMod = new FuncScript("poision.lua");
		
		System.out.println();
		
		UI.brief(player2);
		
		UI.breakln2();
		UI.breakln2();
		
		ScriptManager.execute(thornsMod, "hello");
		
		player1.addTag(otherMod, "other");
		player1.addTag(poisionMod, "poision");
		
		ScriptManager.push(player1, "main");
		System.out.println(player1.hasTag("other"));
		player1.runTagFunctions("onStart");
		
		/*
		
		player2.getArmour().setAttributes(thornsMod);
		player1.getPrimary().setAttributes(thornsMod);
		
		ScriptManager.push(2, "ammo");
		
		attackPrimary(player1, player2);
		attackPrimary(player1, player2);
		attackPrimary(player1, player2);
		
		UI.brief(player1);
		UI.brief(player2);
		
		System.out.println(ScriptManager.execute(thornsMod, "isReload")[1].toJavaObject());
		
		*/
		
		ScriptManager.close();
	}
}
