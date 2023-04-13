package fr.qunther.raw_basics.player_interactions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.qunther.raw_basics.Main;
import fr.qunther.raw_basics.bdd.PlayerBDD;

public class PlayerCommands implements CommandExecutor{
	Main main;
	private String hub_world_name="world";
	
	public PlayerCommands(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("discord")) {
				p.sendMessage(Main.serverMsgPrefix+ChatColor.YELLOW+" Lien vers le discord : "+ChatColor.BLUE+"discord.gg/QaMFsrbHKW");
				return true;
				
			}else if(cmd.getName().equalsIgnoreCase("boutique")) {
				p.sendMessage(Main.serverMsgPrefix+ChatColor.YELLOW+" Lien vers la boutique : "+ChatColor.BLUE+"https://raw-mc.fr/#boutique");
				return true;
				
			}else if(cmd.getName().equalsIgnoreCase("site")) {
				p.sendMessage(Main.serverMsgPrefix+ChatColor.YELLOW+" Lien vers le site : "+ChatColor.BLUE+"https://raw-mc.fr");
				return true;
				
			}else if(cmd.getName().equalsIgnoreCase("classement_chaos")) {
				p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"=======================");
				
				
				p.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"CHAOS "+ChatColor.YELLOW+ChatColor.BOLD+"| CLASSEMENT OVERALL");
				
				p.sendMessage("");
				
				try {
					p.sendMessage(ChatColor.GREEN+p.getDisplayName()+ChatColor.YELLOW+" | Victoires: "+ChatColor.GREEN+PlayerBDD.getPlayerChaosClassementInfos(p.getUniqueId().toString()).get(1)+
							ChatColor.YELLOW+" ; Kills: "+ChatColor.GREEN+PlayerBDD.getPlayerChaosClassementInfos(p.getUniqueId().toString()).get(2));
				}catch(Exception e) {
					p.sendMessage(ChatColor.GREEN+p.getDisplayName()+ChatColor.GOLD+"Aucune donnée");
				}
				
				p.sendMessage("");
				
				
				try {
					p.sendMessage(ChatColor.YELLOW+"1er  : "+ChatColor.GREEN+PlayerBDD.getPlayerInfos(PlayerBDD.getChaosClassementTop(1).get(0).toString()).get(3)+ChatColor.YELLOW+" ; Victoires: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(1).get(1)+ChatColor.YELLOW+" ; Kills: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(1).get(2));
				}catch(Exception e) {
					p.sendMessage(ChatColor.YELLOW+"1er : "+ChatColor.GOLD+"Aucune donnée");
				}
				
				
				try {
					p.sendMessage(ChatColor.YELLOW+"2ème : "+ChatColor.GREEN+PlayerBDD.getPlayerInfos(PlayerBDD.getChaosClassementTop(2).get(0).toString()).get(3)+ChatColor.YELLOW+" ; Victoires: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(2).get(1)+ChatColor.YELLOW+" ; Kills: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(2).get(2));
				}catch(Exception e) {
					p.sendMessage(ChatColor.YELLOW+"2ème : "+ChatColor.GOLD+"Aucune donnée");
				}
				
				
				try {
					p.sendMessage(ChatColor.YELLOW+"3ème : "+ChatColor.GREEN+PlayerBDD.getPlayerInfos(PlayerBDD.getChaosClassementTop(3).get(0).toString()).get(3)+ChatColor.YELLOW+" ; Victoires: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(3).get(1)+ChatColor.YELLOW+" ; Kills: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(3).get(2));
				}catch(Exception e) {
					p.sendMessage(ChatColor.YELLOW+"3ème : "+ChatColor.GOLD+"Aucune donnée");
				}
				
				
				try {
					p.sendMessage(ChatColor.YELLOW+"4ème : "+ChatColor.GREEN+PlayerBDD.getPlayerInfos(PlayerBDD.getChaosClassementTop(4).get(0).toString()).get(3)+ChatColor.YELLOW+" ; Victoires: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(4).get(1)+ChatColor.YELLOW+" ; Kills: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(4).get(2));
				}catch(Exception e) {
					p.sendMessage(ChatColor.YELLOW+"4ème : "+ChatColor.GOLD+"Aucune donnée");
				}
				
				
				try {
					p.sendMessage(ChatColor.YELLOW+"5ème : "+ChatColor.GREEN+PlayerBDD.getPlayerInfos(PlayerBDD.getChaosClassementTop(5).get(0).toString()).get(3)+ChatColor.YELLOW+" ; Victoires: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(5).get(1)+ChatColor.YELLOW+" ; Kills: "+
															ChatColor.GREEN+PlayerBDD.getChaosClassementTop(5).get(2));
				}catch(Exception e) {
					p.sendMessage(ChatColor.YELLOW+"5ème : "+ChatColor.GOLD+"Aucune donnée");
				}
				
				p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"=======================");
				return true;
				
			}else if(cmd.getName().equalsIgnoreCase("admin")) {
				if(p.getDisplayName().equalsIgnoreCase("Qunther")) {
					p.setGameMode(GameMode.CREATIVE);
					p.sendMessage(ChatColor.RED+""+ChatColor.UNDERLINE+"ADMIN MODE : "+ChatColor.GREEN+"ACTIVE");
					AdminMode.Qunther_Admin_Mode = true;
				}
			}else if(cmd.getName().equalsIgnoreCase("spawn")) {
				if(p.getWorld().getName().equalsIgnoreCase(hub_world_name)) {
					Location spawn = new Location(Bukkit.getWorld(hub_world_name), 0.5, 90, 0.5);
					spawn.setYaw(180.0f);
					spawn.setPitch(-5.0f);
					
					p.teleport(spawn);
				}
			}
			
		}
		return false;
	}

}
