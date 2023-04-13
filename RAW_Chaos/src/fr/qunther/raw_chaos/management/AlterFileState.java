package fr.qunther.raw_chaos.management;

import org.bukkit.configuration.file.FileConfiguration;

import fr.qunther.raw_chaos.Chaos;

public class AlterFileState{
	
	public static void setBUSY(Chaos main) {
		FileConfiguration config = main.getConfig();
		if(config.getString("state") != null) {
			config.set("state", "busy");
			main.saveConfig();
		}
	}
	
	public static void setAVAILABLE(Chaos main) {
		FileConfiguration config = main.getConfig();
		if(config.getString("state") != null) {
			config.set("state", "available");
			main.saveConfig();
		}
	}
	
	public static boolean isAvailable(Chaos main) {
		FileConfiguration config = main.getConfig();
		
		if(config.getString("state").equalsIgnoreCase("available")) {
			return true;
		}
		return false;
	}

}

