package fr.qunther.raw_chaos.management;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import fr.qunther.raw_chaos.Chaos;
import net.md_5.bungee.api.ChatColor;

public class Hologram {

	private ArmorStand line1,line2,line3;
	
	public void spawnHologram() {
		Location hologram_loc_1 = new Location(Bukkit.getWorld(Chaos.world_name), 0.5, 51.5, 5.5);
		line1 = (ArmorStand)Bukkit.getWorld(Chaos.world_name).spawnEntity(hologram_loc_1, EntityType.ARMOR_STAND);
		line1.setVisible(false);
		line1.setCustomNameVisible(true);
		line1.setCustomName(ChatColor.RED+""+ChatColor.BOLD+"Si vous ne voyez pas les particules toxiques en l'air :");
		line1.setGravity(false);
		
		Location hologram_loc_2 = new Location(Bukkit.getWorld(Chaos.world_name), 0.5, 51.2, 5.5);
		line2 = (ArmorStand)Bukkit.getWorld(Chaos.world_name).spawnEntity(hologram_loc_2, EntityType.ARMOR_STAND);
		line2.setVisible(false);
		line2.setCustomNameVisible(true);
		line2.setCustomName(ChatColor.AQUA+"- Assurez-vous d'avoir les particules d'activées.");
		line2.setGravity(false);
		
		
		
		Location hologram_loc_3 = new Location(Bukkit.getWorld(Chaos.world_name), 0.5, 50.9, 5.5);
		line3 = (ArmorStand)Bukkit.getWorld(Chaos.world_name).spawnEntity(hologram_loc_3, EntityType.ARMOR_STAND);
		line3.setVisible(false);
		line3.setCustomNameVisible(true);
		line3.setCustomName(ChatColor.AQUA+"- Assurez-vous d'avoir un texture pack qui ne bloque pas les particules concernées.");
		line3.setGravity(false);
	}
	
	public void removeHologram() {
		line1.remove();
		line2.remove();
		line3.remove();
	}
}
