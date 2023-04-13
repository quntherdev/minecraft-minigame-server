package fr.qunther.raw_hub.hub_games.pvp_zone;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_hub.Main;
import fr.qunther.raw_hub.gui.GUIManagement;
import fr.qunther.raw_hub.join.JoinLeaveManagement;

public class PvpFightGestion implements Listener{
	Main main;
	PvpRegion pvp_area_check;
	
	public PvpFightGestion(Main main) {
		this.main = main;
		
		pvp_area_check = new PvpRegion();
	}
	
	public void runCheckTimer() {
		pvp_area_check.runTaskTimer(main, 0, 20);
	}
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		pvp_area_check.removePlayerPvpArea(e.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onDie(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
			
			Player v = (Player)e.getEntity();
			Player k = (Player)e.getEntity().getKiller();
			
			if(!(pvp_area_check.isPlayerInPvpArea((Player)e.getEntity()) && pvp_area_check.isPlayerInPvpArea((Player)e.getEntity().getKiller()))) {
				
			}else {
				// Démarre check si en dehors de la zone safe
				
				if(!(v.getLocation().getX() < 8
				   && v.getLocation().getX() > -7
						
				   && v.getLocation().getZ() < -351
				   && v.getLocation().getZ() > -360
				   
				   && k.getLocation().getX() < 8
				   && k.getLocation().getX() > -7
						
				   && k.getLocation().getZ() < -351
				   && k.getLocation().getZ() > -360)) {

						main.instantdeath(v);
						
					      new BukkitRunnable() {
					            int timer = 1;
					            @Override
					            public void run() {
					                if(timer==0) {
										v.getInventory().clear();										
										playerEnterAreaSettings(v);
					                    cancel();
					                }
					                timer--;
					              }
					            }.runTaskTimer(main, 0, 1/20);
						
						e.setDeathMessage(ChatColor.RED+""+ChatColor.BOLD+"[Arène PVP] "+ChatColor.RED+v.getDisplayName()+ChatColor.YELLOW+" a été tué par "+ChatColor.GREEN+k.getDisplayName()+ChatColor.YELLOW+".");
			}
			
		}
	}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onHitPlayer(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			
			Player v = (Player)e.getEntity();
			Player k = (Player)e.getDamager();
			
			if(!(pvp_area_check.isPlayerInPvpArea((Player)e.getEntity())
					&& pvp_area_check.isPlayerInPvpArea((Player)e.getDamager()))) {
				e.setCancelled(true);

			}else {
				// Démarre check si en dehors de la zone safe
				
				if(!(v.getLocation().getX() < 8
				   && v.getLocation().getX() > -7
						
				   && v.getLocation().getZ() < -351
				   && v.getLocation().getZ() > -360
				   
				   && k.getLocation().getX() < 8
				   && k.getLocation().getX() > -7
						
				   && k.getLocation().getZ() < -351
				   && k.getLocation().getZ() > -360)) {
					
					e.setCancelled(false);
				}else {
					e.setCancelled(true);
				}
			}
			
		}
	}
	
	@EventHandler
	public static void clickInventory(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if(!PvpRegion.inPvpArea.contains(p)) {
			e.setCancelled(true);
		}
	}
	

	public static void playerEnterAreaSettings(Player p) {
		p.getInventory().clear();
		p.teleport(new Location(Bukkit.getWorld(Main.world), 0.5, 109,-356, 0.7f,-0.1f));
		
		p.setHealth(20);
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

		p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));

		for(int i=0; i<9*4-1; i++) {
			Potion potion = new Potion(PotionType.INSTANT_HEAL, 2);
			potion.setSplash(true);
			ItemStack potion_item = potion.toItemStack(1);
			
			p.getInventory().addItem(potion_item);
		}
	}
	
	
	public static void playerQuitAreaSettings(Player p) {
		JoinLeaveManagement.RemoveArmor(p);
		
		GUIManagement.setHUBInventory(p);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			if(pvp_area_check.isPlayerInPvpArea((Player)e.getWhoClicked())) {
				e.setCancelled(false);
			}
		}
	}
	
}
