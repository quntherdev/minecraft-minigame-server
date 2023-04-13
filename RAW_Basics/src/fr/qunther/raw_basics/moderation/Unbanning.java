package fr.qunther.raw_basics.moderation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.qunther.raw_basics.Main;
import fr.qunther.raw_basics.bdd.PlayerBDD;

public class Unbanning implements CommandExecutor{
	private static String unbanCmdTEXT = ChatColor.RED+""+ChatColor.UNDERLINE+"Format:"+ChatColor.RESET+ChatColor.GOLD+" /runban <joueur>";
	private static String noPermTEXT = Main.serverMsgPrefix+ChatColor.RED+" Vous n'avez pas la permission.";

	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {

		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			if(arg1.getName().equalsIgnoreCase("runban")) {

				int id_staff_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1);

				if(PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {

					if(id_staff_rank==3 || id_staff_rank==4 || id_staff_rank==5) {
						
						try {
							@SuppressWarnings("unused")
							Player to_unban = Bukkit.getPlayer(arg3[0]);
						}catch(Exception e) {
							p.sendMessage(unbanCmdTEXT);
							return true;
						}
						
						String to_unban = arg3[0];
						
						if(PlayerBDD.isPlayerInBDDbyNickName(to_unban)) {
							String to_unban_uuid = PlayerBDD.getUUIDfromNickname(to_unban);
							
							if(PlayerBDD.isPlayerTemporaryBanned(to_unban_uuid)
								|| PlayerBDD.isPlayerPermanentBanned(to_unban_uuid)) {
								
								PlayerBDD.unbanPlayer(to_unban_uuid);
								p.sendMessage(Main.serverMsgPrefix+" "+ChatColor.GRAY+to_unban+ChatColor.GREEN+" est débanni !");
							}else {
								p.sendMessage(Main.serverMsgPrefix+ChatColor.RED+" Joueur non banni");
							}
						}else {
							p.sendMessage(Main.serverMsgPrefix+" Ce joueur n'existe pas, vérifiez le pseudonyme.");
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
