package fr.qunther.raw_chaos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_chaos.management.State;

public class SecurityPlayerLocationVerification extends BukkitRunnable{

	private ArrayList<Block> plat = Plateform.getPlat_1();
	private Chaos main;
	boolean inside;
	Location spawn = new Location(Bukkit.getWorld(Chaos.world_name), 0.5, 52, 0.5, 0f, -3f);	
	
	
	public SecurityPlayerLocationVerification(Chaos main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		if(!main.isState(State.FINISHING)) {
			inside=false;
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(main.getPlayers().contains(p)){
					for(int i=0; i<plat.size(); i++) {
	
						if(p.getLocation().getBlock().getX()==plat.get(i).getLocation().getX()
								&& p.getLocation().getBlock().getZ()==plat.get(i).getLocation().getZ()) {
	
							inside=true;
						}
	
					}
					
					if(!inside) {
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rban "+p.getDisplayName()+" 2 -1");
					}
					
					inside=false;
				}
			}
		}else {
			cancel();
		}
	}

	
}
