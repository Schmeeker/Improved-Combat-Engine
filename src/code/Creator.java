package code;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Creator {
	
	public static Gson build = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	
	public static File charDir = new File("characters\\");
	public static File scriptDir = new File("scripts\\");
	
	public static boolean init() {
		if(!charDir.exists()) {
			charDir.mkdir();
			UI.errorMessage("There are no character files! Check: " + charDir.getAbsolutePath());
			return false;
		}
		
		if(!scriptDir.exists()) {
			scriptDir.mkdir();
			UI.errorMessage("There are no script files! Check: " + scriptDir.getAbsolutePath());
			return false;
		}
		
		return true;
		
	}
	
	//TODO add anti-cheat for broken stats
	//TODO probably add wizard stuff for generating/saving/loading new characters/weapons/armour
	public static Character loadFromCharacter(Character character) {
		Character loaded;
		try {
			FileReader reader = new FileReader(character.getSourceFile());
			loaded = build.fromJson(reader, Character.class);
			reader.close();
		} catch (IOException e) {
			UI.fileNotFound(character.getSourcePath());
			loaded = null;
		}
		return loaded;
	}
	
	public static Character loadFromPath(String path) {
		Character loaded;
		try {
			FileReader reader = new FileReader(new File("characters\\" + path));
			loaded = build.fromJson(reader, Character.class);
			reader.close();
		} catch (IOException e) {
			UI.fileNotFound(path);
			loaded = null;
		}
		return loaded;
	}
	
	public static void save(Character character) {
		FileWriter writer;
		try {
			writer = new FileWriter(character.getSourceFile());
			build.toJson(character, writer);
			writer.close();
		} catch (IOException e) {
			UI.fileNotFound(character.getSourcePath());
			try {
				writer = new FileWriter(new File(character.getName() + ".char"));
				build.toJson(character, writer);
				writer.close();
			} catch (IOException e1) {
				UI.errorMessage("Unable to Save!");
			}
		}
	}
	
	public static void saveAs(Character character, String path) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("characters\\" + path));
			build.toJson(character, writer);
			writer.close();
		} catch (IOException e) {
			UI.errorMessage("Unable to Save!");
		}
	}
	
	// From before when I had .json items
//	public static Item loadItem(Item item) {
//		Item loaded;
//		
//		try {
//			if(item.getSource() != null) {
//				FileReader reader = new FileReader(new File(item.getSource()));
//				loaded = build.fromJson(reader, item.getClass());
//				reader.close();
//			} else
//				loaded = item;
//		} catch(IOException e) {
//			UI.fileNotFound(item.getSource());
//			loaded = item;
//		}
//		
//		return loaded;
//	}
//	
//	public static void saveItem(Item item) throws IOException {
//		if(item.getSource() != null) {
//			FileWriter writer = new FileWriter(new File(item.getSource()));
//			build.toJson(item, writer);
//			writer.close();
//		}
//	}
	
}
