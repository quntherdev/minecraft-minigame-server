package fr.qunther.raw_hub.hub_games.jumppad;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import fr.qunther.raw_hub.Main;

public class BoostPad implements Listener{

    @EventHandler
    public void PlayerAndar(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = event.getTo().getBlock().getRelative(BlockFace.DOWN);
        if (block.getType() == Material.SLIME_BLOCK) {
        	if(Bukkit.getWorld(Main.world).getBlockAt(new Location(Bukkit.getWorld(Main.world), block.getX(),block.getY()-1,block.getZ())).getType() == Material.REDSTONE_BLOCK){
                player.setVelocity(player.getLocation().getDirection().multiply(60));
                player.setVelocity(new Vector(player.getVelocity().getX(), 1, player.getVelocity().getZ()));	
        	}
        }
    }
	
}
