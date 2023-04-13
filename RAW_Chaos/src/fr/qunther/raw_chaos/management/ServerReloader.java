package fr.qunther.raw_chaos.management;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.qunther.raw_chaos.Chaos;
import fr.qunther.raw_chaos.Plateform;

public class ServerReloader{

	public static void launchNewGame(Chaos main) {
		AlterFileState.setBUSY(main);
		killAllEntities();
		eraseMap();
		reloadChaosPlugin();
		kickBuggedPlayers();
	}
	
	public static void eraseMap() {
		for(int i=1; i<100; i++) {
			for(Block b : Plateform.getPlat_1()) {
				Location loc = new Location(Bukkit.getWorld(Chaos.world_name),b.getX(),b.getY()+i,b.getZ());
				
				Bukkit.getWorld(Chaos.world_name).getBlockAt(loc).setType(Material.AIR);
			}
		}
	}
	
	public static void killAllEntities() {
		try {
			for(Entity e : Bukkit.getWorld(Chaos.world_name).getEntities()) {
				if(!(e instanceof Player)) {
					e.remove();
				}
			}
		}catch(Exception e) {
			
		}
	}
	public static void reloadChaosPlugin() {
		Bukkit.reload();
	}
	
	public static void kickBuggedPlayers() {
		try {
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.kickPlayer(null);
			}
		}catch(Exception e) {
			
		}
	}
	
}
