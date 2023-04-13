package fr.qunther.raw_basics.others;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.qunther.raw_basics.bdd.PlayerBDD;

public class PlayerRankProviding implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!(sender instanceof Player)) {
			if(args.length==2) {
				
				Player to_provide = Bukkit.getPlayer(args[0]);
				
				try {
					to_provide.getUniqueId().toString();
				}catch(Exception e) {
					System.out.println("Joueur non connecté");
				}
				
				if(PlayerBDD.isPlayerInBDD(to_provide.getUniqueId().toString())) {
					PlayerBDD.addOrRemovePlayerRawCoins(to_provide.getUniqueId().toString(), Integer.valueOf(args[1]).intValue());
				}else {
					System.out.println("Joueur pas dans la BDD !");
				}
			}else {
				System.out.println("pas le bon nombre d'arguments");
			}
		}else {
			return false;
		}
		
		return false;
	}

}
