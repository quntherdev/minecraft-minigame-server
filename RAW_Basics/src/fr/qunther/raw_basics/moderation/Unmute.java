package fr.qunther.raw_basics.moderation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.qunther.raw_basics.Main;
import fr.qunther.raw_basics.bdd.PlayerBDD;

public class Unmute implements CommandExecutor {

	private static String unmuteCmdTEXT = ChatColor.RED+""+ChatColor.UNDERLINE+"Format:"+ChatColor.RESET+ChatColor.GOLD+" /runmute <joueur>";
	private static String noPermTEXT = Main.serverMsgPrefix+ChatColor.RED+" Vous n'avez pas la permission.";

	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {

		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			if(arg1.getName().equalsIgnoreCase("runmute")) {

				int id_staff_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1);
				
				if(PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {

					if(id_staff_rank==3 || id_staff_rank==4 || id_staff_rank==5) {
						
						try {
							@SuppressWarnings("unused")
							Player to_unmute = Bukkit.getPlayer(arg3[0]);
						}catch(Exception e) {
							p.sendMessage(unmuteCmdTEXT);
							return true;
						}
						
						String to_unmute = arg3[0];
						
						if(PlayerBDD.isPlayerInBDDbyNickName(to_unmute)) {
							String to_unmute_uuid = PlayerBDD.getUUIDfromNickname(to_unmute);
							
							if(PlayerBDD.isPlayerTemporaryMuted(to_unmute_uuid)
								|| PlayerBDD.isPlayerPermanentMuted(to_unmute_uuid)) {
								
								PlayerBDD.unmutePlayer(to_unmute_uuid);
								p.sendMessage(Main.serverMsgPrefix+" "+ChatColor.GRAY+to_unmute+ChatColor.GREEN+" est démuté !");
							}else {
								p.sendMessage(Main.serverMsgPrefix+ChatColor.RED+" Joueur non mute");
							}
						}else {
							p.sendMessage(Main.serverMsgPrefix+" Ce joueur n'existe pas, vérifiez le pseudonyme.");
							return false;
						}
					}else {
						p.sendMessage(noPermTEXT);
						return false;
					}
				}else {
					p.sendMessage(noPermTEXT);
					return false;
				}
			}
		}
		
		return false;
	}

}
