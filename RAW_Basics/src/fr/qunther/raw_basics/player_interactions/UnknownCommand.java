package fr.qunther.raw_basics.player_interactions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.qunther.raw_basics.Main;


public class UnknownCommand implements Listener{
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		String msg = e.getMessage();
		String[] args = msg.split(" ");
		Player p = e.getPlayer();
		
		if(Bukkit.getServer().getHelpMap().getHelpTopic(args[0]) == null) {
			e.setCancelled(true);
			
			p.sendMessage(Main.serverMsgPrefix+ChatColor.RED+" Commande inconnue.");
		}
	}
}
