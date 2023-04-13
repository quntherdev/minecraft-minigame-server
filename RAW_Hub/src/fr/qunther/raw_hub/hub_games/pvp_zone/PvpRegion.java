package fr.qunther.raw_hub.hub_games.pvp_zone;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PvpRegion extends BukkitRunnable{	
	public static ArrayList<Player> inPvpArea = new ArrayList<Player>();

	public boolean isPlayerInPvpArea(Player p) {
		if(inPvpArea.contains(p)) {
			return true;
		}
		return false;
	}
	
	
	public void removePlayerPvpArea(Player p) {
		if(isPlayerInPvpArea(p)) {
			inPvpArea.remove(p);
		}
	}
	
	
	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getLocation().getX() >= -46 // Démarre le check si dans la cage	
				&& p.getLocation().getX() <= 46
					
				&& p.getLocation().getY() >= 98
				&& p.getLocation().getY() <= 116
					
				&& p.getLocation().getZ() <= -320
				&& p.getLocation().getZ() >= -397) {
				
					if(!isPlayerInPvpArea(p)) {
						inPvpArea.add(p);
						PvpFightGestion.playerEnterAreaSettings(p);
					}
					
			}else {
				if(isPlayerInPvpArea(p)) {
					inPvpArea.remove(p);
					PvpFightGestion.playerQuitAreaSettings(p);
				}
			}
		}
		
	}
	
}
