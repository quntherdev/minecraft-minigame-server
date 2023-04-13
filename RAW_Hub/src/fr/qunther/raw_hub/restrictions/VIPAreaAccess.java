package fr.qunther.raw_hub.restrictions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_hub.Main;
import net.md_5.bungee.api.ChatColor;

public class VIPAreaAccess extends BukkitRunnable{
	private Main plugin;
	
	public VIPAreaAccess(Main plugin){
		this.plugin = plugin;
	}

	@Override
	public void run(){
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getLocation().getZ() <= -192) {
				
				int id_shop_rank = (int) bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2);

				
				if(bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) == null) {
					
					if(id_shop_rank<=1) {
						p.sendMessage(Main.getServerMsgPrefix()+ChatColor.RED+" L'île remplie de jeux est réservée aux VIPs ! Faites "+ChatColor.GOLD+"/boutique");
						p.teleport(new Location(Bukkit.getWorld(Main.world),0.5,103,-185.5 ,180f,-2.4f));					
					}
				}else {
					int id_staff_rank = (int) bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1);
					
					if(id_staff_rank == -1) {
						p.sendMessage(Main.getServerMsgPrefix()+ChatColor.RED+" L'île remplie de jeux est réservée aux VIPs ! Faites "+ChatColor.GOLD+"/boutique");
						p.teleport(new Location(Bukkit.getWorld(Main.world),0.5,103,-185.5 ,180f,-2.4f));	
					}
				}
			}
		}
	}
}
