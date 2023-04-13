package fr.qunther.raw_hub.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommands implements CommandExecutor{
	private final String hub_world="world";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("spawn")) {
				if(p.getWorld().getName().equalsIgnoreCase(hub_world)) {
					Location spawn = new Location(Bukkit.getWorld(hub_world), 0.5, 90, 0.5);
					spawn.setYaw(180.0f);
					spawn.setPitch(-5.0f);
					
					p.teleport(spawn);
				}
			}			
		}

		return false;
	}
}
