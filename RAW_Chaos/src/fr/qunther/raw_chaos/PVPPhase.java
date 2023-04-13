package fr.qunther.raw_chaos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_chaos.management.State;
import fr.qunther.raw_chaos.tasks.CloudChangerTask;

public class PVPPhase extends BukkitRunnable{
	private Chaos main;
	public int timer=60*3;

	public PVPPhase(Chaos main) {
		this.main = main;
	}

	@Override
	public void run() {
		if(main.isState(State.FINISHING)) {
			cancel();
			return;
		}
	/*	
		if(timer%60==0 && timer != 60*3) {
			Bukkit.broadcastMessage(ChatColor.RED+"Dans "+timer/60+" minute(s) :");
			Bukkit.broadcastMessage(ChatColor.RED+"- "+ChatColor.GOLD+"activation du PVP");
			Bukkit.broadcastMessage(ChatColor.RED+"- "+ChatColor.GOLD+"apparition à vos pieds d'un nuage toxique");
		}*/
		
		if(timer%30==0 && timer!=60*3) {
			if(timer>30) {
				Bukkit.broadcastMessage(ChatColor.RED+"Dans "+timer/60+"min"+timer%60+"s :");
				Bukkit.broadcastMessage(ChatColor.RED+"- "+ChatColor.GOLD+"activation du PVP");
				Bukkit.broadcastMessage(ChatColor.RED+"- "+ChatColor.GOLD+"apparition à vos pieds d'un nuage toxique");
			}else {
				Bukkit.broadcastMessage(ChatColor.RED+"Dans "+timer+" seconde(s) :");
				Bukkit.broadcastMessage(ChatColor.RED+"- "+ChatColor.GOLD+"activation du PVP");
				Bukkit.broadcastMessage(ChatColor.RED+"- "+ChatColor.GOLD+"apparition à vos pieds d'un nuage toxique");
			}
		}
		
		if(timer==10) {
			Bukkit.broadcastMessage(ChatColor.RED+"Dans "+timer+" secondes :");
			Bukkit.broadcastMessage(ChatColor.RED+"- "+ChatColor.GOLD+"activation du PVP");
			Bukkit.broadcastMessage(ChatColor.RED+"- "+ChatColor.GOLD+"apparition à vos pieds d'un nuage toxique, commencez à construire !");
		}
		
		if(timer<10) {
			Bukkit.broadcastMessage(ChatColor.GOLD+""+timer+"s");
		}
		
		if(timer==0) {
			SecurityPlayerLocationVerification securePlat = new SecurityPlayerLocationVerification(main);
			securePlat.runTaskTimer(main, 0, 20);
			
			try {
				for(Entity e : Bukkit.getWorld(Chaos.serverMsgPrefix).getEntities()) {
					if(e instanceof Animals || e instanceof Monster || e.getType()==EntityType.DROPPED_ITEM) {
						e.remove();
					}
				}
			}catch(Exception e) {
				
			}
			
			
			Bukkit.broadcastMessage(ChatColor.RED+"PVP et Nuages actifs. Vous avez 12 minutes pour gagner.");
			main.setState(State.PVP);
			
			CloudChangerTask inf = new CloudChangerTask(main, 51, 2);
			inf.runTaskTimer(main, 0, 15);	

			for(Entity players : Bukkit.getWorld(Chaos.world_name).getEntities()) {
				if(players instanceof Player) {
					int id_shop_rank = (int) PlayerBDD.getPlayerInfos(players.getUniqueId().toString()).get(2);
					
					switch(id_shop_rank) {
					case 2:
						((Player)players).getInventory().addItem(new ItemStack(Material.COBBLESTONE,64*4));
						break;
					case 3:
						((Player)players).getInventory().addItem(new ItemStack(Material.COBBLESTONE,64*6));
						break;
					default:
						((Player)players).getInventory().addItem(new ItemStack(Material.COBBLESTONE,64*3));
						break;
					}
				}
			}
		
			cancel();
			
			
			
			/*
			 * 
			 * phase pvp démarre, gère le temps avant la fin
			 * 
			 */
			new BukkitRunnable() {
				private int time_before_end=60*10;
				
				@Override
				public void run() {
					if(main.isState(State.FINISHING)) {
						cancel();
						return;
					}
					
					if(time_before_end==120) {
						Bukkit.broadcastMessage(ChatColor.YELLOW+"Il ne vous reste plus que"+ChatColor.GOLD+" 2 minutes "+ChatColor.YELLOW+"pour vous battre ! "
								+ "Si aucun joueur ne gagne d'ici-là,"+ChatColor.GOLD+" la partie s'arrête sans aucun vainqueur"+ChatColor.YELLOW+".");
					}else if(time_before_end==60) {
						Bukkit.broadcastMessage(ChatColor.YELLOW+"Il ne vous reste plus que"+ChatColor.GOLD+" 1 minute "+ChatColor.YELLOW+"pour vous battre ! "
								+ "Si aucun joueur ne gagne d'ici-là,"+ChatColor.GOLD+" la partie s'arrête sans aucun vainqueur"+ChatColor.YELLOW+".");	
					}else if(time_before_end==10 || (time_before_end<=5 && time_before_end>0)) {
						Bukkit.broadcastMessage(ChatColor.YELLOW+"Fin de la partie dans "+ChatColor.GOLD+time_before_end+" secondes"+ChatColor.YELLOW+".");
					}else if(time_before_end==0) {
						Bukkit.broadcastMessage(ChatColor.RED+"Fin de la partie.");
						cancel();
						main.kickChrono(main);		
						return;
					}
					
					time_before_end--;
				}
				
			}.runTaskTimer(main, 0, 20);
			
			
		}
		timer--;
	}
	
	
}
