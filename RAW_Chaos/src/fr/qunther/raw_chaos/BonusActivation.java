package fr.qunther.raw_chaos;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_chaos.management.State;
import net.minecraft.server.v1_8_R3.EntityArrow;

public class BonusActivation extends BukkitRunnable implements Listener{
	
	boolean acid_bonus_cooldown=false;
	int acid_cooldown_timer=10;

	Chaos main;
	
	public BonusActivation(Chaos main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
			
		try {
			if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK
					|| e.getAction()==Action.LEFT_CLICK_AIR || e.getAction()==Action.LEFT_CLICK_BLOCK) {
				if(main.isState(State.WAITING) || main.isState(State.FINISHING)) {
					e.setCancelled(true);
				}

				if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Bonus.protect1TEXT)) {
					try {
						ItemStack helmet = new ItemStack(p.getInventory().getHelmet().getType());
						helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1);
						p.getInventory().setHelmet(helmet);
					}catch(Exception e1) {
						
					}
					
					try {
						ItemStack chest = p.getInventory().getChestplate();
						chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						p.getInventory().setChestplate(chest);
						p.updateInventory();
					}catch(Exception e1) {
					
					}
					
					try {
						ItemStack pant = new ItemStack(p.getInventory().getLeggings().getType());
						pant.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						p.getInventory().setLeggings(pant);
						p.updateInventory();
					}catch(Exception e1) {
				
					}

					try {
						ItemStack boots = new ItemStack(p.getInventory().getBoots().getType());
						boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						p.getInventory().setLeggings(boots);
						p.updateInventory();
					}catch(Exception e1) {
				
					}
					p.getInventory().remove(e.getItem());
					
					
				}else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(Bonus.tueur_acideTEXT)) {
					
					if(!acid_bonus_cooldown) {
						acid_bonus_cooldown=true;

						new BukkitRunnable() {

							@Override
							public void run() {
								if(acid_cooldown_timer==0) {
									acid_bonus_cooldown=false;
									acid_cooldown_timer=10;
									cancel();
									return;
								}
								acid_cooldown_timer--;
							}
							
						}.runTaskTimer(main, 0, 20);
						
						for(Entity entity : Bukkit.getWorld(Chaos.world_name).getEntities()) {
							if(entity instanceof Player) {
								Player victim = ((Player)entity).getPlayer();
								if(victim != p) {
									victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*3,4), true);
								}
							}
						}
						
						p.getInventory().remove(e.getItem());
						
					}else {
						p.sendMessage(ChatColor.RED+"Cooldown... Vous pourrez activer ce bonus dans "+ChatColor.GOLD+acid_cooldown_timer+ChatColor.RED+" seconde(s).");
					}
					
				}
			}		
		} catch(Exception e4) {
			
		}
	}
	
	@EventHandler
	public void onBowShoot(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow)e.getEntity();
			if(arrow.getShooter() instanceof Player) {
				Player p = (Player)arrow.getShooter();

				 if(p.getItemInHand().getType()==Material.BOW) {
					 ItemStack bow = p.getItemInHand();
					
					if((int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2)==2
						||(int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2)==3) { //rank shop id 3

						if(bow.getItemMeta().getDisplayName().equalsIgnoreCase(Bonus.flame_bowTEXT)){

							removeBlockArrowLandedIn(arrow);
						}else if(PlayerBDD.playerBoughtBonus(p.getUniqueId().toString(), 5)) {
							if(bow.getItemMeta().getDisplayName().equalsIgnoreCase(Bonus.explosive_bowTEXT)){
								explodeBlocks(arrow);
							}
						}
						
					}else if(PlayerBDD.playerBoughtBonus(p.getUniqueId().toString(), 5)) {
						if(bow.getItemMeta().getDisplayName().equalsIgnoreCase(Bonus.explosive_bowTEXT)){
							explodeBlocks(arrow);
						}
					}
				 }
			}		
					 
		}
	}
	
	public void removeBlockArrowLandedIn(Arrow arrow)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            public void run() {
                try {
                
                    EntityArrow entityArrow = ((CraftArrow) arrow).getHandle();

                    Field fieldX = EntityArrow.class
                            .getDeclaredField("d");
                    Field fieldY = EntityArrow.class
                            .getDeclaredField("e");
                    Field fieldZ = EntityArrow.class
                            .getDeclaredField("f");

                    fieldX.setAccessible(true);
                    fieldY.setAccessible(true);
                    fieldZ.setAccessible(true);

                    int x = fieldX.getInt(entityArrow);
                    int y = fieldY.getInt(entityArrow);
                    int z = fieldZ.getInt(entityArrow);

                    Block block = arrow.getWorld().getBlockAt(x, y, z);
                    if(block.getType()!=Material.BARRIER) {
                        block.setType(Material.AIR);
        				arrow.remove();                    	
                    }

                    
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                } catch (SecurityException e1) {
                    e1.printStackTrace();
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
	
	public void explodeBlocks(Arrow arrow) {		
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            public void run() {
                try {
                
                    EntityArrow entityArrow = ((CraftArrow) arrow).getHandle();

                    Field fieldX = EntityArrow.class
                            .getDeclaredField("d");
                    Field fieldY = EntityArrow.class
                            .getDeclaredField("e");
                    Field fieldZ = EntityArrow.class
                            .getDeclaredField("f");

                    fieldX.setAccessible(true);
                    fieldY.setAccessible(true);
                    fieldZ.setAccessible(true);

                    int x = fieldX.getInt(entityArrow);
                    int y = fieldY.getInt(entityArrow);
                    int z = fieldZ.getInt(entityArrow);

                    Block block = arrow.getWorld().getBlockAt(x, y, z);

                    Bukkit.getWorld(Chaos.world_name).createExplosion(block.getLocation(), 4f);
                    arrow.remove();

                    
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                } catch (SecurityException e1) {
                    e1.printStackTrace();
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });
	}

	
	// INUTILE MAIS NE PAS SUPPRIMER
	
	@Override
	public void run() {}

}
