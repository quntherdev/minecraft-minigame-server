package fr.qunther.raw_chaos.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_chaos.Bonus;
import fr.qunther.raw_chaos.Chaos;
import fr.qunther.raw_chaos.PlayerBDD;
import fr.qunther.raw_chaos.management.AlterFileState;
import fr.qunther.raw_chaos.management.State;
import fr.qunther.raw_chaos.tasks.GameAutoStart;


@SuppressWarnings("deprecation")
public class PlayerListener implements Listener {
	ArrayList<Block> furnace_loc = new ArrayList<>();
	ArrayList<Player> player_furnace = new ArrayList<>();
	private static String notYourFurnace = Chaos.serverMsgPrefix+ChatColor.RED+" Ce n'est pas votre four.";
	
	Chaos main;
	
	public PlayerListener(Chaos main) {
		this.main=main;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		RemoveArmor(p);		
		
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv tp "+p.getDisplayName()+" "+Chaos.world_name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvconfirm");
		
        new BukkitRunnable() {
			@Override
			public void run() {
				if(!p.getWorld().getName().equalsIgnoreCase(Chaos.world_name)) {
			        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv tp "+p.getDisplayName()+" "+Chaos.world_name);
			        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvconfirm");
				}
				cancel();
			}
        	
        }.runTaskTimer(main, 20, 20);
        
		if(PlayerBDD.hasPlayerTempoBonus(p.getUniqueId().toString())) {
			PlayerBDD.removePlayerTempoBonus(p.getUniqueId().toString());
		}
		
		Location spawn = new Location(Bukkit.getWorld(Chaos.world_name), 0.5, 60, 0.5, 0f, -3f);
		
		p.teleport(spawn);
		
		
		p.getInventory().clear();
		p.setFoodLevel(20);
		p.setLevel(0);
		p.setExp(0);
		p.setHealth(20);
		
	    if(main.getPlayers().size()==12){
			p.kickPlayer("Limite de joueur atteinte sur ce serveur");
		}
		
		if(!main.isState(State.WAITING)) {
			e.setJoinMessage(null);
			p.sendMessage(Chaos.serverMsgPrefix+ChatColor.BLUE+" Le jeu a déjà commencé, vous êtes spectateur.");
			p.setGameMode(GameMode.SPECTATOR);
			return;
		}
		
		if(!main.getPlayers().contains(p)) main.getPlayers().add(p);
		
		/*
		 * 
		 *  EDIT SERVER STATE
		 */
		if(Bukkit.getWorld(Chaos.world_name).getPlayers().size()>=12) {
			AlterFileState.setBUSY(main);
		}else {
			AlterFileState.setAVAILABLE(main);
		}
		
	
		p.setGameMode(GameMode.ADVENTURE);
		
		Bonus.setChaosWaitingInventory(p);
		
		e.setJoinMessage(ChatColor.YELLOW+p.getDisplayName()+ChatColor.GREEN+" a rejoint la partie ! "+
		ChatColor.LIGHT_PURPLE+"["+main.getPlayers().size()+"/"+Bukkit.getMaxPlayers()+"]");
		
		int id_shop_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2);

		if(id_shop_rank==2) {
			p.sendMessage(ChatColor.GREEN+"Bonus activé : "+ChatColor.RED+""+PlayerBDD.getGameBonusInfos(3).get(4));
		}else if(id_shop_rank==3) {
			p.sendMessage(ChatColor.GREEN+"Bonus activés : "
							+ChatColor.RED+""+PlayerBDD.getGameBonusInfos(3).get(4)
							+ChatColor.GREEN+", "+ChatColor.RED+PlayerBDD.getGameBonusInfos(4).get(4));
		}
		
		
		if(main.isState(State.WAITING) && main.getPlayers().size() >= 2) { // CHANGER 1 PAR 2
			if(!GameAutoStart.count_running)
			{
				GameAutoStart.count_running=true;
				GameAutoStart start = new GameAutoStart(main,20); // TEMPS DEMARRAGE QUAND AU MOINS 2 JOUEURS
				start.runTaskTimer(main, 0, 20);		
			}
		}
	}
	
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		for(PotionEffect effect : p.getActivePotionEffects()) {
			p.removePotionEffect(effect.getType());
		}
		
		if(PlayerBDD.hasPlayerTempoBonus(p.getUniqueId().toString())) {
			PlayerBDD.removePlayerTempoBonus(p.getUniqueId().toString());
		}
		
		if(main.getPlayers().contains(p)) {
			if(!(main.isState(State.WAITING) && !(main.isState(State.FINISHING)))) {
		        for(ItemStack i : p.getInventory().getContents()){
		            if(i != null){
		                p.getWorld().dropItemNaturally(p.getLocation(), i);
		                p.getInventory().remove(i);
		            }
		        }
			}
						
			if(main.isState(State.PVP) || main.isState(State.FARMING)) {
				e.setQuitMessage(null);
				Bukkit.broadcastMessage(ChatColor.RED+p.getDisplayName()+ChatColor.YELLOW+" est mort d'une manière pour le moins.... étrange."
						+" Il reste "+ChatColor.GREEN+(main.getPlayers().size()-1)+ChatColor.YELLOW+" joueur(s).");
				
				main.eliminatePlayer(p);
			}	
		}
		
		if(main.isState(State.WAITING)) {
			main.getPlayers().remove(p);
			
			e.setQuitMessage(ChatColor.YELLOW+p.getDisplayName()+ChatColor.RED+" a quitté la partie. "+
					ChatColor.LIGHT_PURPLE+"["+main.getPlayers().size()+"/"+Bukkit.getMaxPlayers()+"]");
		}
	}
	
	
	@EventHandler
	public void onFoodLeveChange(FoodLevelChangeEvent e) {
		if((main.isState(State.WAITING) || main.isState(State.FINISHING)) && e.getEntity() instanceof Player) {
			e.setFoodLevel(20);
		}
	}
	
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onSpeak(PlayerChatEvent e) {
		if(!main.getPlayers().contains(e.getPlayer())) {
			e.setCancelled(true);
			/*
			for(Player autres : Bukkit.getOnlinePlayers()) {
				if(main.getPlayers().contains(autres)) {
					autres.sendMessage(ChatColor.GRAY+""+ChatColor.ITALIC+"(Spectateur) "+e.getPlayer().getDisplayName()+": "+e.getMessage());					
				}
			}*/
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent e) {
		if(main.isState(State.FARMING) || main.isState(State.PVP)) {

			if(e.getBlock().getType()==Material.FURNACE) {
				if(main.isState(State.PVP)) {
					e.setCancelled(false);
				}else {
					for(int i=0; i<player_furnace.size(); i++) {
						if(e.getBlock().getX()==furnace_loc.get(i).getX()
								&& e.getBlock().getY()==furnace_loc.get(i).getY()
								&& e.getBlock().getZ()==furnace_loc.get(i).getZ()) {
							if(e.getPlayer()==player_furnace.get(i).getPlayer()){
								e.setCancelled(false);
								player_furnace.remove(i);
								furnace_loc.remove(i);
							}else {
								e.setCancelled(true);
								e.getPlayer().sendMessage(notYourFurnace);
							}
						}
					}	
				}
				
			}else if(e.getBlock().getType()==Material.LOG) {
				e.setCancelled(true);
				Location log_loc = e.getBlock().getLocation();
				
				Bukkit.getWorld(Chaos.world_name).getBlockAt(log_loc).setType(Material.AIR);
				e.getPlayer().getInventory().addItem(new ItemStack(Material.WOOD,4));
			}else {
				e.setCancelled(false);
			}
		}else {
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onBlockPlace(BlockPlaceEvent e) {
		if(main.isState(State.FARMING) || main.isState(State.PVP)) {
			e.setCancelled(false);
			if(e.getBlock().getType()==Material.FURNACE) {
				player_furnace.add(e.getPlayer());
				furnace_loc.add(e.getBlock());
			}
		}else {
			e.setCancelled(true);
		}
		
		if(e.getBlock().getType()==Material.TNT) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Chaos.serverMsgPrefix+ChatColor.RED+" TNT désactivées.");
		}
	}

	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		try {
			if(e.getAction()==Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType()==Material.FURNACE) {
					if(main.isState(State.PVP)) {
						e.setCancelled(false);
					}else {
						for(int i=0; i<player_furnace.size(); i++) {
	
							if(e.getClickedBlock().getX()==furnace_loc.get(i).getX()
									&& e.getClickedBlock().getY()==furnace_loc.get(i).getY()
									&& e.getClickedBlock().getZ()==furnace_loc.get(i).getZ()) {
	
								if(e.getPlayer()==player_furnace.get(i).getPlayer()){
									e.setCancelled(false);
								}else {
									e.setCancelled(true);
									e.getPlayer().sendMessage(notYourFurnace);
								}
							}else {
							}
						}
					}
				}
			}
		}catch(Exception e1) {
			
		}
	}
	
    public static void RemoveArmor(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }
	
}
