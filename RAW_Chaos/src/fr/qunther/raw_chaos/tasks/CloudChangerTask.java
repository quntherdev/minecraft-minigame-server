	package fr.qunther.raw_chaos.tasks;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_chaos.Chaos;
import fr.qunther.raw_chaos.Plateform;
import fr.qunther.raw_chaos.management.State;
import xyz.xenondevs.particle.ParticleEffect;

public class CloudChangerTask extends BukkitRunnable{
	private Chaos main;
	private ArrayList<Location> cloud;
	private int y_coord;
	private int up_or_down; //up=1, down=2
	private boolean quinze_sec_active=false;

	
	public CloudChangerTask(Chaos plugin, int y_coord_origin, int sup_or_inf) {
		this.main=plugin;
		this.cloud = new ArrayList<>();
		this.y_coord = y_coord_origin;
		this.up_or_down = sup_or_inf;
		
		fillCloudList();
	}
	
	public int getYCoord() {
		return y_coord;
	}
	
	@Override
	public void run() {
		if(main.isState(State.FINISHING)) {
			cancel();
			return;
		}
		
		for(Entity e : Bukkit.getWorld(Chaos.world_name).getEntities()) {
			if(e instanceof Player) {
				Player p=(Player)e;
				
				if(main.getPlayers().contains(p)) {
					if(containPlayer(p)) {
						if(up_or_down==1) {
							p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Redescend, vite ! "+ChatColor.YELLOW+"Tu tu trouves dans le nuage toxique supérieur !");
							p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20,4), true);
						}else if(up_or_down==2){
							p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Remonte, vite ! "+ChatColor.YELLOW+"Tu tu trouves dans le nuage toxique inférieur !");
							p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20,4), true);
						}
					}
				}
			}
		}
		displayCloud();

		if(main.isState(State.PVP)) {
			if(up_or_down==1){//cloud.get(0).getY()<85) {
				if(!quinze_sec_active) {
					quinze_sec_active=true;
					new BukkitRunnable() {
						@Override
						public void run() {
							Bukkit.broadcastMessage(ChatColor.YELLOW+"Les nuages toxiques s'élèvent d'un bloc !");
							
							
							// Pour prévenir toutes les 7 secondes que les nuages vont s'élever
							new BukkitRunnable() {
								@Override
								public void run() {
									Bukkit.broadcastMessage(ChatColor.RED+"5 secondes"+ChatColor.YELLOW+" avant l'élévation des nuages toxiques.");
									cancel();
									return;
								}
							}.runTaskTimer(main, 20*25, 0);
							
							
							oneBlocChange();
							quinze_sec_active=false;
							cancel();
							return;	
						}
						
					}.runTaskTimer(main, 20*15,20*15);	
				}					
			}else if(up_or_down==2){//cloud.get(0).getY()<75) {
				if(!quinze_sec_active) {
					quinze_sec_active=true;
					new BukkitRunnable() {

						@Override
						public void run() {
							oneBlocChange();
							quinze_sec_active=false;
							cancel();
							return;
						}
						
					}.runTaskTimer(main, 20*15,20*15);	
				}
			}
		}else if(main.isState(State.FINISHING)) {
			cancel();
			return;
		}
	}
	
	public void fillCloudList() {
		for(int i=0; i<Plateform.getPlat_1().size(); i++) {
			if((i%2==0) && (i%4==0) && (i%6==0) && (i%8==0)) {
				Location loc = Plateform.getPlat_1().get(i).getLocation();
				loc.setY(y_coord);
				
				cloud.add(loc);	
			}
		}
	}
	
	public void displayCloud() {
		for(int i=0; i<cloud.size();i++) {
			ParticleEffect.VILLAGER_ANGRY.display(cloud.get(i));
		}
	}
	
	
	public void oneBlocChange() {
		y_coord+=1;
		
		for(int i=0; i<cloud.size(); i++) {
			cloud.get(i).setY(y_coord);
		}
	}
	
	
	
	public boolean containPlayer(Player p) {
		if(up_or_down==1) {
			if(p.getLocation().getY() >= cloud.get(0).getY()) {
				return true;
			}				
		}else if(up_or_down==2) {
			if(p.getLocation().getY() <= cloud.get(0).getY()) {
				return true;
			}	
		}
		return false;
	}
	
}
