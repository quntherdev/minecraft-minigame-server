package fr.qunther.raw_hub.join;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import fr.qunther.raw_hub.Main;
import fr.qunther.raw_hub.gui.GUIManagement;
import fr.qunther.raw_hub.scoreboard.LobbyBoard;


public class JoinLeaveManagement implements Listener{
	Main plugin;
	
	public JoinLeaveManagement(Main main) {
		plugin=main;
	}
	
	@EventHandler
	public void onFoodLeveChange(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			p.setFoodLevel(20);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		JoinLeaveManagement.RemoveArmor(p);

		/*
		 * check is player banned, kick with message if that's the case
		 * 
		 */
		if(bdd.PlayerBDD.isPlayerPermanentBanned(p.getUniqueId().toString())){
			String who_banned = bdd.PlayerBDD.getWhoBanned(p.getUniqueId().toString());
			
			String msg =ChatColor.LIGHT_PURPLE+"Vous êtes banni de RAW.\n\n"
					+ ChatColor.RED+"Raison : "+ChatColor.YELLOW+bdd.PlayerBDD.getPlayerBanReasonTitle(p.getUniqueId().toString())+"\n"
					+ ChatColor.RED+"Banni par : "+ChatColor.YELLOW+who_banned+"\n"
					+ ChatColor.RED+"Temps avant débanissement : "+ChatColor.YELLOW+"Jamais\n\n"
					+ ChatColor.RED+ChatColor.BOLD+"Si vous jugez ce banissement injustifié, veuillez vous rendre sur le serveur Discord pour solliciter une aide.\n"
					+ "Discord : "+ChatColor.RESET+ChatColor.GOLD+"discord.gg/QaMFsrbHKW";
			e.setJoinMessage(null);
			p.kickPlayer(msg);
			return;
		}else if(bdd.PlayerBDD.isPlayerTemporaryBanned(p.getUniqueId().toString())) {
			String who_banned = bdd.PlayerBDD.getWhoBanned(p.getUniqueId().toString());

			String msg = ChatColor.LIGHT_PURPLE+"Vous êtes banni de RAW.\n\n"
					+ ChatColor.RED+"Raison : "+ChatColor.YELLOW+bdd.PlayerBDD.getPlayerBanReasonTitle(p.getUniqueId().toString())+"\n"
					+ ChatColor.RED+"Banni par : "+ChatColor.YELLOW+who_banned+"\n"
					+ ChatColor.RED+"Temps avant débanissement : "+ChatColor.YELLOW+bdd.PlayerBDD.getPlayerBanRemainingHeures(p.getUniqueId().toString())+" heures\n\n"
					+ ChatColor.RED+ChatColor.BOLD+"Si vous jugez ce banissement injustifié, veuillez vous rendre sur le serveur Discord pour solliciter une aide.\n"
					+ "Discord : "+ChatColor.RESET+ChatColor.GOLD+"discord.gg/QaMFsrbHKW";
			e.setJoinMessage(null);
			p.kickPlayer(msg);
			return;
		}	
		
		if(bdd.PlayerBDD.isPlayerInBDD(p.getUniqueId().toString())) {
			
		}

		p.setGameMode(GameMode.ADVENTURE);
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setLevel(0);
		p.setExp(0f);
		Bukkit.getServer().getWorld(p.getWorld().getName()).playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
		
		Location spawn = new Location(Bukkit.getWorld(Main.world), 0.5, 90, 0.5);
		spawn.setYaw(180.0f);
		spawn.setPitch(-5.0f);
		
		p.teleport(spawn);
				
		if(!bdd.PlayerBDD.isPlayerInBDD(p.getUniqueId().toString())) {
			bdd.PlayerBDD.insertPlayerFirstConnectionInfos(p.getUniqueId().toString(), p.getDisplayName(), p.getAddress().toString());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpb user "+p.getDisplayName()+" group add player");
			
			p.sendMessage(Main.getServerMsgPrefix()+ChatColor.DARK_PURPLE+" Bienvenue sur RAW, "+ChatColor.GREEN+p.getDisplayName()+ChatColor.DARK_PURPLE
					+" ! Au besoin, voici une vidéo qui te présente toutes les fonctionnalités"
					+" et jeux du serveur : "+ChatColor.RED+"www.youtube.com");
		}else {
			bdd.PlayerBDD.insertPlayerConnectionInfos(p.getUniqueId().toString(), p.getAddress().toString());
		}
		
		GUIManagement.setHUBInventory(p);
		
		LobbyBoard board = new LobbyBoard(p);
		board.setScoreBoard();
		
		@SuppressWarnings("unused")
		BukkitTask update_scoreboard = board.runTaskTimer(plugin, 0, 20*10);
		
		
		
		if(bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
			int id_staff_rank = (int) bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1);
			String p_staff_rank = (String) bdd.PlayerBDD.getPlayerStaffRank(p.getUniqueId().toString()).get(0);
			

			switch(id_staff_rank) {
			case 1:
				e.setJoinMessage(ChatColor.DARK_GREEN+p_staff_rank+" "+p.getDisplayName()+ChatColor.GOLD+" rejoint le lobby !");									
				break;
			case 2:
				e.setJoinMessage(ChatColor.LIGHT_PURPLE+p_staff_rank+" "+p.getDisplayName()+ChatColor.GOLD+" rejoint le lobby !");									
				break;
			case 3:
				e.setJoinMessage(ChatColor.RED+p_staff_rank+" "+p.getDisplayName()+ChatColor.GOLD+" rejoint le lobby !");									
				break;
			case 4:
				e.setJoinMessage(ChatColor.BLUE+p_staff_rank+" "+p.getDisplayName()+ChatColor.GOLD+" rejoint le lobby !");									
				break;
			case 5:
				e.setJoinMessage(ChatColor.RED+p_staff_rank+" "+p.getDisplayName()+ChatColor.GOLD+" rejoint le lobby !");									
				break;
			}
		}else {
			int id_shop_rank = (int) bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2);
			String p_shop_rank = (String) bdd.PlayerBDD.getPlayerShopRank(p.getUniqueId().toString()).get(0);
			
			switch(id_shop_rank) {
			case 1:
				e.setJoinMessage(ChatColor.GRAY+p_shop_rank+" "+p.getDisplayName()+" rejoint le lobby !");									
				break;
			case 2:
				e.setJoinMessage(ChatColor.BLUE+p_shop_rank+" "+p.getDisplayName()+ChatColor.GOLD+" a rejoint le lobby !");					
				break;
			case 3:
				e.setJoinMessage(ChatColor.GREEN+p_shop_rank+" "+p.getDisplayName()+ChatColor.GOLD+" a rejoint le lobby !");					
				break;				
			case 4:
				e.setJoinMessage(ChatColor.DARK_PURPLE+p_shop_rank+" "+p.getDisplayName()+ChatColor.GOLD+" a rejoint le lobby !");					
				break;	
			case 5:
				e.setJoinMessage(ChatColor.LIGHT_PURPLE+p_shop_rank+" "+p.getDisplayName()+ChatColor.GOLD+" a rejoint le lobby !");					
				break;	
			}
		}

	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
	
    public static void RemoveArmor(Player player) {
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }
	
	
}
