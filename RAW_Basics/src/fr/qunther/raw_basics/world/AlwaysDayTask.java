package fr.qunther.raw_basics.world;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AlwaysDayTask extends BukkitRunnable {

	@Override
	public void run() {
		try {
			Bukkit.getWorld("world").setTime(0L);
		}catch(Exception e) {
			
		}
		
		try {
			Bukkit.getWorld("chaos_aquarium").setTime(0L);
		}catch(Exception e) {
			
		}

		
	}
	
}
