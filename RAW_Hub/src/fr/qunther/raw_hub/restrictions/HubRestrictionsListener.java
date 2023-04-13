package fr.qunther.raw_hub.restrictions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.qunther.raw_hub.Main;


public class HubRestrictionsListener implements Listener{
	private static String cantDoItHere = Main.getServerMsgPrefix()+ChatColor.RED+" Vous ne pouvez pas faire cela ici !";
	
	@EventHandler
	public void onPlayerBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if(!p.isOp()) {
			e.setCancelled(true);
			p.sendMessage(cantDoItHere);
		}else{
			Block b = e.getBlock();
			p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Bloc §ncassé,"+ChatColor.RESET+ChatColor.RED+ChatColor.BOLD+" infos:\n"+
					ChatColor.GREEN + b.getType() + " ; " + b.getLocation().getBlockX() + " ; " + b.getLocation().getBlockY() + " ; " + b.getLocation().getBlockZ());
		}
	}
	
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		Player p = e.getPlayer();

		if(!p.isOp()) {
			e.setCancelled(true);
			p.sendMessage(cantDoItHere);
		}else {
			Block b = e.getBlock();
			p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Bloc §nplacé, "+ChatColor.RESET+ChatColor.RED+ChatColor.BOLD+"infos:\n"+
					ChatColor.GREEN + b.getType() + " ; " + b.getLocation().getBlockX() + " ; " + b.getLocation().getBlockY() + " ; " + b.getLocation().getBlockZ());
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		try {
			if(e.getAction()==Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType()==Material.CHEST || e.getClickedBlock().getType()==Material.ENDER_CHEST) {
					//Cancel trunk openning
					e.setCancelled(true);
					p.sendMessage(cantDoItHere);
				}else
					if(e.getClickedBlock().getType()==Material.ITEM_FRAME) {
					//Cancel item_frame embedded items rotation by right clicking
					e.setCancelled(true);
					p.sendMessage(cantDoItHere);			
				}
			}else if(e.getAction()==Action.LEFT_CLICK_BLOCK) {
				//Cancel item_frame breaking
				if(e.getClickedBlock().getType()==Material.ITEM_FRAME) {
					e.setCancelled(true);
					p.sendMessage(cantDoItHere);			
				}
			}	
		}catch (Exception e3){
			
		}
		
	}
	
	/*
	 * 
	 * A AJOUTER LE MSG AVERT ADMINS
	 */
	 @EventHandler
	    public void FrameEntity(EntityDamageByEntityEvent e) {
	        if (e.getEntity() instanceof ItemFrame) {
	            if (e.getDamager() instanceof Player) {
	                if (!e.getDamager().isOp()) {
	                    e.setCancelled(true);
	                    e.getDamager().sendMessage(cantDoItHere);
	                }
	            }
	            if (e.getDamager() instanceof Projectile) {
	                if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
	                    if (!((Player)((Projectile)e.getDamager()).getShooter()).isOp()) {
	                    	((Player)((Projectile)e.getDamager()).getShooter()).sendMessage(cantDoItHere);
	                        e.getDamager().remove();
	                        e.setCancelled(true);
	                    }
	                }
	            }
	        }
	 }
	
    @EventHandler
    public void FrameRotate(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
            if (!e.getPlayer().isOp()) {
                e.setCancelled(true);
            }
        }
    }
    
	@EventHandler
	public void onLoseDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
}
