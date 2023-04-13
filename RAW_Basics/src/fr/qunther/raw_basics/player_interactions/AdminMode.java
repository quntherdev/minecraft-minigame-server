package fr.qunther.raw_basics.player_interactions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AdminMode implements Listener{
	public static boolean Qunther_Admin_Mode = false;
	public static final String Admin_Name = "Qunther";

	public AdminMode() {
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(p.getDisplayName().equalsIgnoreCase(Admin_Name)) {
			Qunther_Admin_Mode=false;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p.getDisplayName().equalsIgnoreCase(Admin_Name)) {
			if(Qunther_Admin_Mode) {
				e.setCancelled(false);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBreak(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(p.getDisplayName().equalsIgnoreCase(Admin_Name)) {
			if(Qunther_Admin_Mode) {
				e.setCancelled(false);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBreak(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			
			if(p.getDisplayName().equalsIgnoreCase(Admin_Name)) {
				if(Qunther_Admin_Mode) {
					e.setCancelled(false);
				}
			}			
		}
	}
	
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player)e.getDamager();
			if(p.getDisplayName().equalsIgnoreCase(Admin_Name)) {
				if(Qunther_Admin_Mode) {
					e.setCancelled(false);
				}
			}
		}
	}
	
	
}
