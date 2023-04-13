package fr.qunther.raw_basics.moderation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.qunther.raw_basics.Main;
import fr.qunther.raw_basics.bdd.PlayerBDD;

public class Muting implements CommandExecutor {
	private static String muteCmdTEXT = ChatColor.RED+""+ChatColor.UNDERLINE+"Format:"+ChatColor.RESET+ChatColor.GOLD+" /rmute <joueur> <id_raison> <duree_heures>";
	private static String noPermTEXT = Main.serverMsgPrefix+ChatColor.RED+" Vous n'avez pas la permission.";
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {

		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			if(arg1.getName().equalsIgnoreCase("rmute")) {
				if(PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
					
					int id_staff_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1);
					
					if(id_staff_rank==3 || id_staff_rank==4 || id_staff_rank==5) {
						
						if(arg3.length==0) {
							p.sendMessage(ChatColor.YELLOW+"Raison id <1> :"+ChatColor.BLUE+" Spam");
							p.sendMessage(ChatColor.YELLOW+"Raison id <2> :"+ChatColor.BLUE+" Menaces");
							p.sendMessage(ChatColor.YELLOW+"Raison id <3> :"+ChatColor.BLUE+" Propos non tolérés");
							p.sendMessage(ChatColor.YELLOW+"Raison id <4> :"+ChatColor.BLUE+" Raison non spécifiée");
							p.sendMessage(muteCmdTEXT);
						}else {
							
							try {
								@SuppressWarnings("unused")
								Player to_mute = Bukkit.getPlayer(arg3[0]);
								@SuppressWarnings("unused")
								int raison_id = Integer.valueOf(arg3[1]);
								@SuppressWarnings("unused")
								int heures_mute = Integer.valueOf(arg3[2]);
							}catch(Exception e) {
								p.sendMessage(muteCmdTEXT);
								return true;
							}
							
							Player to_mute = Bukkit.getPlayer(arg3[0]);
							int raison_id = Integer.valueOf(arg3[1]);
							int heures_mute = Integer.valueOf(arg3[2]);
							
							if(PlayerBDD.isPlayerInBDD(to_mute.getUniqueId().toString())){
								
								if(PlayerBDD.getPlayerInfos(to_mute.getUniqueId().toString()).get(1) != null) {
									if((int) PlayerBDD.getPlayerInfos(to_mute.getUniqueId().toString()).get(1)==5) {
										p.sendMessage(Main.serverMsgPrefix+" Tu ne peux pas mute un administrateur");
									}
								}
								
								if(PlayerBDD.isPlayerTemporaryMuted(to_mute.getUniqueId().toString())
										|| PlayerBDD.isPlayerPermanentMuted(to_mute.getUniqueId().toString())) {
									
									p.sendMessage(Main.serverMsgPrefix+ChatColor.RED+" Joueur déjà mute");
								}else {
									PlayerBDD.mutePlayer(to_mute.getUniqueId().toString(), raison_id, heures_mute, p.getUniqueId().toString());
									p.sendMessage(Main.serverMsgPrefix+" "+ChatColor.GRAY+to_mute.getDisplayName()+ChatColor.GREEN+" mute pour "+heures_mute+" heures !");
									
									to_mute.sendMessage(ChatColor.DARK_PURPLE+"Vous venez d'être réduit au silence. Revoyez votre comportement.");
								}
							}else {
								p.sendMessage(Main.serverMsgPrefix+" Ce joueur n'existe pas, vérifiez le pseudonyme.");
							}
						}
					}else {
						p.sendMessage(noPermTEXT);
					}
				}else {
					p.sendMessage(noPermTEXT);
				}

			}
		}
		return false;
	}

}
