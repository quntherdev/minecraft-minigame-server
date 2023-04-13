package fr.qunther.raw_hub.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import fr.qunther.raw_hub.Main;
import net.md_5.bungee.api.ChatColor;

public class Hologram {

	private ArmorStand line1,line2, line3,line4, line5,line6;
	@SuppressWarnings("unused")
	private Main main;	
	
	public Hologram(Main main) {
		this.main=main;
	}
	
	public void spawnHologram() {
		Location hologram_loc_1 = new Location(Bukkit.getWorld(Main.world), 0.5, 91, -5.5);
		line1 = (ArmorStand)Bukkit.getWorld(Main.world).spawnEntity(hologram_loc_1, EntityType.ARMOR_STAND);
		line1.setVisible(false);
		line1.setCustomNameVisible(true);
		line1.setCustomName(ChatColor.GOLD+"L'équipe RAW"+ChatColor.AQUA+" est attentive des attentes de ses joueurs :");
		line1.setGravity(false);
		
		Location hologram_loc_2 = new Location(Bukkit.getWorld(Main.world), 0.5, 90.7, -5.5);
		line2 = (ArmorStand)Bukkit.getWorld(Main.world).spawnEntity(hologram_loc_2, EntityType.ARMOR_STAND);
		line2.setVisible(false);
		line2.setCustomNameVisible(true);
		line2.setCustomName(ChatColor.AQUA+"donnez vos suggestions sur le discord, nous les évaluerons. "+ChatColor.GOLD+"/discord");
		line2.setGravity(false);
		
		
		
		Location hologram_loc_3 = new Location(Bukkit.getWorld(Main.world), 0.5, 90, -5.5);
		line3 = (ArmorStand)Bukkit.getWorld(Main.world).spawnEntity(hologram_loc_3, EntityType.ARMOR_STAND);
		line3.setVisible(false);
		line3.setCustomNameVisible(true);
		line3.setCustomName(ChatColor.RED+"La boutique certifie que vous nous soutenez, faites y un tour !");
		line3.setGravity(false);
		
		Location hologram_loc_4 = new Location(Bukkit.getWorld(Main.world), 0.5, 89.7, -5.5);
		line4 = (ArmorStand)Bukkit.getWorld(Main.world).spawnEntity(hologram_loc_4, EntityType.ARMOR_STAND);
		line4.setVisible(false);
		line4.setCustomNameVisible(true);
		line4.setCustomName(ChatColor.GOLD+"/boutique"+ChatColor.RED+". Les grades améliorent votre expérience de jeu.");
		line4.setGravity(false);
		
		
		
		Location hologram_loc_5 = new Location(Bukkit.getWorld(Main.world), 0.5, 89, -5.5);
		line5 = (ArmorStand)Bukkit.getWorld(Main.world).spawnEntity(hologram_loc_5, EntityType.ARMOR_STAND);
		line5.setVisible(false);
		line5.setCustomNameVisible(true);
		line5.setCustomName(ChatColor.GOLD+"L'équipe RAW"+ChatColor.AQUA+" vous souhaite un superbe moment sur le serveur,");
		line5.setGravity(false);
		
		Location hologram_loc_6 = new Location(Bukkit.getWorld(Main.world), 0.5, 88.7, -5.5);
		line6 = (ArmorStand)Bukkit.getWorld(Main.world).spawnEntity(hologram_loc_6, EntityType.ARMOR_STAND);
		line6.setVisible(false);
		line6.setCustomNameVisible(true);
		line6.setCustomName(ChatColor.AQUA+"amenez vos amis pour encore plus de plaisir !");
		line6.setGravity(false);		
		
		
	}
	
	public void removeHologram() {
		line1.remove();
		line2.remove();
		line3.remove();
		line4.remove();
		line5.remove();
		line6.remove();
	}
}
