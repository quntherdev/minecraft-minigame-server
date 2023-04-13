package fr.qunther.raw_basics.moderation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.qunther.raw_basics.Main;
import fr.qunther.raw_basics.bdd.PlayerBDD;

public class Banning implements CommandExecutor{
	private static String errorRetrievingPlayerTEXT = ChatColor.RED+""+ChatColor.UNDERLINE+"Format:"+ChatColor.RESET+ChatColor.GOLD+" Joueur pas connecté ou pas dans la BDD.";
	private static String banCmdTEXT = 	ChatColor.RED+""+ChatColor.UNDERLINE+"Format:"+ChatColor.RESET+ChatColor.GOLD+" /rban <joueur> <id_raison> <duree_heures>";
	private static String noPermTEXT = Main.serverMsgPrefix+ChatColor.RED+" Vous n'avez pas la permission.";
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {

		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			if(arg1.getName().equalsIgnoreCase("rban")) {
				if(PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
					
					int id_staff_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1);
					
					if(id_staff_rank==3 || id_staff_rank==4 || id_staff_rank==5) {
						
						if(arg3.length==0) {
							p.sendMessage(ChatColor.YELLOW+"Raison id <1> :"+ChatColor.BLUE+" Spam dans le but de nuire au serveur / publicité d'autre serveur");
							p.sendMessage(ChatColor.YELLOW+"Raison id <2> :"+ChatColor.BLUE+" Tricherie");
							p.sendMessage(ChatColor.YELLOW+"Raison id <3> :"+ChatColor.BLUE+" Menaces / Manipulation psychologique / Attaques");
							p.sendMessage(ChatColor.YELLOW+"Raison id <4> :"+ChatColor.BLUE+" Pseudonyme/Skin non toléré");
							p.sendMessage(ChatColor.YELLOW+"Raison id <5> :"+ChatColor.BLUE+" Propos graves");
							p.sendMessage(ChatColor.YELLOW+"Raison id <6> :"+ChatColor.BLUE+" Raison non spécifiée");
							p.sendMessage(banCmdTEXT);
						}else {
							
							try {
								@SuppressWarnings("unused")
								Player to_ban = Bukkit.getPlayer(arg3[0]);
							}catch(Exception e) {
								p.sendMessage(errorRetrievingPlayerTEXT);
								return false;
							}
							
							try {
								@SuppressWarnings("unused")
								int raison_id = Integer.valueOf(arg3[1]);
								@SuppressWarnings("unused")
								int heures_ban = Integer.valueOf(arg3[2]);
							}catch(Exception e) {
								p.sendMessage(banCmdTEXT);
								return false;
							}
							
							
							Player to_ban = Bukkit.getPlayer(arg3[0]);
							int raison_id = Integer.valueOf(arg3[1]);
							int heures_ban = Integer.valueOf(arg3[2]);
							
							try {
								PlayerBDD.isPlayerInBDD(to_ban.getUniqueId().toString());
							}catch(Exception e) {
								p.sendMessage(Main.serverMsgPrefix+" Ce joueur n'existe pas, vérifiez le pseudonyme.");
								return false;
							}
							
							if(PlayerBDD.isPlayerInBDD(to_ban.getUniqueId().toString())){
								
								if(PlayerBDD.getPlayerInfos(to_ban.getUniqueId().toString()).get(1) != null) {
									if((int) PlayerBDD.getPlayerInfos(to_ban.getUniqueId().toString()).get(1)==5) {
										p.sendMessage(Main.serverMsgPrefix+" Tu ne peux pas bannir un administrateur");
										return false;
									}
								}
								
								if(PlayerBDD.isPlayerTemporaryBanned(to_ban.getUniqueId().toString())
										|| PlayerBDD.isPlayerPermanentBanned(to_ban.getUniqueId().toString())) {
									
									p.sendMessage(Main.serverMsgPrefix+ChatColor.RED+" Joueur déjà banni");
									return false;
								}else {
									PlayerBDD.banPlayer(to_ban.getUniqueId().toString(), p.getUniqueId().toString(), raison_id, heures_ban);
									p.sendMessage(Main.serverMsgPrefix+" "+ChatColor.GRAY+to_ban.getDisplayName()+ChatColor.GREEN+" banni pour "+heures_ban+" heures !");
									if(heures_ban== -1) {
										to_ban.kickPlayer(ChatColor.LIGHT_PURPLE+"Vous venez d'être banni de RAW.\n\n"
												+ ChatColor.RED+"Raison : "+ChatColor.YELLOW+(String)PlayerBDD.getBanReasonTitleFromIdReason(raison_id).get(0)+"\n"
												+ ChatColor.RED+"Banni par : "+ChatColor.YELLOW+p.getDisplayName()+"\n"
												+ ChatColor.RED+"Temps avant débanissement : "+ChatColor.YELLOW+"Jamais"+"\n\n"
												+ ChatColor.RED+ChatColor.BOLD+"Si vous jugez ce banissement injustifié, veuillez vous rendre sur le serveur Discord pour solliciter une aide.\n"
												+ "Discord : "+ChatColor.RESET+ChatColor.GOLD+"discord.gg/");	
										}else {
											to_ban.kickPlayer(ChatColor.LIGHT_PURPLE+"Vous venez d'être banni de RAW.\n\n"
													+ ChatColor.RED+"Raison : "+ChatColor.YELLOW+(String)PlayerBDD.getBanReasonTitleFromIdReason(raison_id).get(0)+"\n"
													+ ChatColor.RED+"Banni par : "+ChatColor.YELLOW+p.getDisplayName()+"\n"
													+ ChatColor.RED+"Temps avant débanissement : "+ChatColor.YELLOW+heures_ban+" heures\n\n"
													+ ChatColor.RED+ChatColor.BOLD+"Si vous jugez ce banissement injustifié, veuillez vous rendre sur le serveur Discord pour solliciter une aide.\n"
													+ "Discord : "+ChatColor.RESET+ChatColor.GOLD+"discord.gg/");	
										}

								}
							}else {
								p.sendMessage(Main.serverMsgPrefix+" Ce joueur n'existe pas, vérifiez le pseudonyme.");
								return false;
							}
						}
					}else {
						p.sendMessage(noPermTEXT);
					}
				}else {
					p.sendMessage(noPermTEXT);
				}

			}
		}else {
			// ENVOYE DEPUIS LA CONSOLE
			
			if(arg1.getName().equalsIgnoreCase("rban")) {
				Player to_ban = Bukkit.getPlayer(arg3[0]);
				int raison_id = Integer.valueOf(arg3[1]);
				int heures_ban = Integer.valueOf(arg3[2]);
				
				try {
					PlayerBDD.isPlayerInBDD(to_ban.getUniqueId().toString());
				}catch(Exception e) {
					System.out.println(Main.serverMsgPrefix+" Ce joueur n'existe pas, vérifiez le pseudonyme.");
					return false;
				}
				
				if(PlayerBDD.isPlayerInBDD(to_ban.getUniqueId().toString())){
					
					if(PlayerBDD.getPlayerInfos(to_ban.getUniqueId().toString()).get(1)==null) {
						
					}else if((int) PlayerBDD.getPlayerInfos(to_ban.getUniqueId().toString()).get(1)==5) {
						System.out.println(Main.serverMsgPrefix+" Tu ne peux pas bannir un administrateur");
						return false;
					}
					
					if(PlayerBDD.isPlayerTemporaryBanned(to_ban.getUniqueId().toString())
							|| PlayerBDD.isPlayerPermanentBanned(to_ban.getUniqueId().toString())) {
						
						System.out.println(Main.serverMsgPrefix+ChatColor.RED+" Joueur déjà banni");
						return false;
					}else {
						PlayerBDD.banPlayer(to_ban.getUniqueId().toString(), "0-0-0-0", raison_id, heures_ban);
						System.out.println(Main.serverMsgPrefix+" "+ChatColor.GRAY+to_ban.getDisplayName()+ChatColor.GREEN+" banni pour "+heures_ban+" heures !");
						if(heures_ban== -1) {
							to_ban.kickPlayer(ChatColor.LIGHT_PURPLE+"Vous venez d'être banni de RAW.\n\n"
									+ ChatColor.RED+"Raison : "+ChatColor.YELLOW+(String)PlayerBDD.getBanReasonTitleFromIdReason(raison_id).get(0)+"\n"
									+ ChatColor.RED+"Banni par : "+ChatColor.YELLOW+"AntiCheat / Console"+"\n"
									+ ChatColor.RED+"Temps avant débanissement : "+ChatColor.YELLOW+"Jamais"+"\n\n"
									+ ChatColor.RED+ChatColor.BOLD+"Si vous jugez ce banissement injustifié, veuillez vous rendre sur le serveur Discord pour solliciter une aide.\n"
									+ "Discord : "+ChatColor.RESET+ChatColor.GOLD+"discord.gg/");	
							}else {
								to_ban.kickPlayer(ChatColor.LIGHT_PURPLE+"Vous venez d'être banni de RAW.\n\n"
										+ ChatColor.RED+"Raison : "+ChatColor.YELLOW+(String)PlayerBDD.getBanReasonTitleFromIdReason(raison_id).get(0)+"\n"
										+ ChatColor.RED+"Banni par : "+ChatColor.YELLOW+"AntiCheat / Console"+"\n"
										+ ChatColor.RED+"Temps avant débanissement : "+ChatColor.YELLOW+heures_ban+" heures\n\n"
										+ ChatColor.RED+ChatColor.BOLD+"Si vous jugez ce banissement injustifié, veuillez vous rendre sur le serveur Discord pour solliciter une aide.\n"
										+ "Discord : "+ChatColor.RESET+ChatColor.GOLD+"discord.gg/");	
							}

					}
				}else {
					System.out.println(Main.serverMsgPrefix+" Ce joueur n'existe pas, vérifiez le pseudonyme.");
					return false;
				}
			}
			
		}
		return false;
	}
}
