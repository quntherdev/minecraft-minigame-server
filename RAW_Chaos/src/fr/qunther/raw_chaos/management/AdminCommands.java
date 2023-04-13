package fr.qunther.raw_chaos.management;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.qunther.raw_chaos.PlayerBDD;

public class AdminCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("force_erase")) {
				if(PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
					int id_staff_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1);
					
					if(id_staff_rank==5 || id_staff_rank==4) {
						ServerReloader.eraseMap();
						ServerReloader.killAllEntities();
					}
				}
				
			}
		}
		return false;
	}

}
