package code;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Creator {
	
	public static Gson build = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	
	//TODO add anti-cheat for broken stats
	//TODO probably add wizard stuff for generating/saving/loading new characters/weapons/armour
	public static Character loadFromCharacter(Character character) throws IOException {
		FileReader reader = new FileReader(character.getSourceFile());
		Character loaded = build.fromJson(reader, Character.class);
		reader.close();
		return loaded;
	}
	
	public static Character loadFromPath(String path) throws IOException {
		FileReader reader = new FileReader(new File(path));
		Character loaded = build.fromJson(reader, Character.class);
		reader.close();
		return loaded;
	}
	
	public static void save(Character character) throws IOException {
		FileWriter writer = new FileWriter(character.getSourceFile());
		build.toJson(character, writer);
		writer.close();
	}
	
	public static void saveAs(Character character, String path) throws IOException {
		character.setSourcePath(path);
		FileWriter writer = new FileWriter(new File(path));
		build.toJson(character, writer);
		writer.close();
	}
	
	public static Item loadItem(Item item) throws IOException {
		Item loaded;
		
		if(item.getSourcePath() != null) {
			FileReader reader = new FileReader(new File(item.getSourcePath()));
			loaded = build.fromJson(reader, item.getClass());
			reader.close();
		} else
			loaded = item;
		
		return loaded;
	}
	
	public static void saveItem(Item item) throws IOException {
		if(item.getSourcePath() != null) {
			FileWriter writer = new FileWriter(new File(item.getSourcePath()));
			build.toJson(item, writer);
			writer.close();
		}
	}
	
}
