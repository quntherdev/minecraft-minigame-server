package fr.qunther.raw_chaos.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_chaos.Chaos;
import fr.qunther.raw_chaos.management.State;
import net.md_5.bungee.api.ChatColor;


public class DamageListener implements Listener{
	private Chaos main;
	public static HashMap<Player, Integer> kills = new HashMap<>();
	
	public DamageListener(Chaos main) {
		this.main = main;
	}

	@EventHandler(priority=EventPriority.HIGH)
	public void onDamage(EntityDamageEvent e) {
		if(main.isState(State.WAITING) || main.isState(State.FARMING) ||main.isState(State.FINISHING)) {
			
			if(main.isState(State.FARMING)) {
				if(e.getEntity() instanceof LivingEntity && e.getEntity() instanceof Player) {
					
					if(e.getCause() == DamageCause.WITHER) {
						e.setCancelled(false);
					}else {
						e.setCancelled(true);
						return;
					}
				}else {
					e.setCancelled(false);
					return;
				}
			}else {
				e.setCancelled(true);
				return;
			}
			
		}else if(main.isState(State.PVP)) {
			if(e.getEntity() instanceof LivingEntity && e.getEntity() instanceof Player) {
				
				if(e.getCause().equals(DamageCause.FALL)) {
					e.setCancelled(true);
				}

			}
		}
	}
	
	
	@EventHandler
	public void onDie(PlayerDeathEvent e) {
		
		Player p = e.getEntity();

		if((Player)e.getEntity().getKiller() instanceof Player) {
			
			Player p_killer = (Player)e.getEntity().getKiller();
			
				if(main.getPlayers().contains(p)) {

					DropInventory(p);
					
					e.setDeathMessage(ChatColor.RED+p.getDisplayName()+ChatColor.YELLOW+" a succombé de la main de "
							+ChatColor.GREEN+p_killer.getDisplayName()+ChatColor.YELLOW+" ! Il reste "+ChatColor.GREEN+
							(main.getPlayers().size()-1)+ChatColor.YELLOW+" joueur(s).");
					
					if(!kills.containsKey(p_killer)) {
						kills.put(p_killer, 1);
					}else {
						kills.put(p_killer, kills.get(p_killer)+1);
					}
					
					
					main.instantdeath(p);
					
				      new BukkitRunnable() {
				            int timer = 1;
				            @Override
				            public void run() {
				                if(timer==0) {
				                	p.setGameMode(GameMode.SPECTATOR);
									main.eliminatePlayer(p);
				                    cancel();
				                }
				                timer--;
				              }
				            }.runTaskTimer(main, 0, 1/20);
					
					
					return;
				}
				
				
		}else if(e.getEntity().getLastDamageCause() instanceof Arrow) {
			
			if(((Arrow) e.getEntity().getLastDamageCause()).getShooter() instanceof Player){
				Player p_killer = (Player) ((Arrow)e.getEntity().getLastDamageCause());					

					if(main.getPlayers().contains(p)) {
						DropInventory(p);

						e.setDeathMessage(ChatColor.RED+p.getDisplayName()+ChatColor.YELLOW+" a été la victime de l'as du tir qui n'est autre que "
								+ChatColor.GREEN+p_killer.getDisplayName()+ChatColor.YELLOW+" ! Il reste "+ChatColor.GREEN+
								(main.getPlayers().size()-1)+ChatColor.YELLOW+" joueur(s).");
						
						if(!kills.containsKey(p_killer)) {
							kills.put(p_killer, 1);
						}else {
							kills.put(p_killer, kills.get(p_killer)+1);
						}
						
						main.instantdeath(p);
						
					      new BukkitRunnable() {
					            int timer = 1;
					            @Override
					            public void run() {
					                if(timer==0) {
					                	p.setGameMode(GameMode.SPECTATOR);
										main.eliminatePlayer(p);
					                    cancel();
					                }
					                timer--;
					              }
					            }.runTaskTimer(main, 0, 1/20);
					}	
			}
		}else if(p.getLastDamageCause().getCause().equals(DamageCause.WITHER)){
			
			if(main.getPlayers().contains(p)) {
					DropInventory(p);

					e.setDeathMessage(ChatColor.YELLOW+"Le nuage toxique a eu raison de "+
							ChatColor.RED+p.getDisplayName()+ChatColor.YELLOW+" ! Il reste "+ChatColor.GREEN+
							(main.getPlayers().size()-1)+ChatColor.YELLOW+" joueur(s).");
					
					
					main.eliminatePlayer(p);
			}	
		}else {
				if(main.getPlayers().contains(p)) {
					DropInventory(p);

					e.setDeathMessage(ChatColor.RED+p.getDisplayName()+ChatColor.YELLOW+" est mort."
											+" Il reste "+ChatColor.GREEN+(main.getPlayers().size()-1)+ChatColor.YELLOW+" joueur(s).");
					
					main.instantdeath(p);
					
				      new BukkitRunnable() {
				            int timer = 1;
				            @Override
				            public void run() {
				                if(timer==0) {
				                	p.setGameMode(GameMode.SPECTATOR);
									main.eliminatePlayer(p);
				                    cancel();
				                }
				                timer--;
				              }
				            }.runTaskTimer(main, 0, 1/10);
				}
			
		}
	}
	
	public void DropInventory(Player p) {
		Location pLoc = p.getLocation();
		
		try {
			for(ItemStack i : p.getInventory().getContents()) {
				Bukkit.getWorld(Chaos.world_name).dropItemNaturally(pLoc, i);
			}
		}catch(Exception e) {
			
		}
		p.getInventory().clear();
	}
}
