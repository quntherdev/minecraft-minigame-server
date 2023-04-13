package fr.qunther.raw_chaos;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class PlateformDrop {
	public static void dropTrees() {
		for(int i=0; i<14; i++) {
			int max = Plateform.getPlat_1().size()-1;
			
			Random random = new Random();
			
			int coords = random.nextInt(max);
			Location block_loc = Plateform.getPlat_1().get(coords).getLocation();
			block_loc.setY(block_loc.getY()+15);
			
			Plateform.getWorld().dropItemNaturally(block_loc, new ItemStack(Material.SAPLING,1));
		}
	}
	
	public static void dropBoneMeals() {
		for(int i=0; i<14; i++) {
			int max = Plateform.getPlat_1().size()-1;
			
			Random random = new Random();
			
			int coords = random.nextInt(max);
			Location block_loc = Plateform.getPlat_1().get(coords).getLocation();
			block_loc.setY(block_loc.getY()+15);
			
			Plateform.getWorld().dropItemNaturally(block_loc, new ItemStack(Material.INK_SACK,5,(byte)15));
		}
	}
	
	public static void spawnAnimals() {
		for(int i=0; i<2; i++) {
			int max = Plateform.getPlat_1().size()-1;
			
			Random random = new Random();
			
			int coords = random.nextInt(max);
			Location block_loc = Plateform.getPlat_1().get(coords).getLocation();
			block_loc.setY(block_loc.getY()+1);
			
			if(i%2==0) {
				Plateform.getWorld().spawnEntity(block_loc, EntityType.PIG);
			}else {
				Plateform.getWorld().spawnEntity(block_loc, EntityType.COW);				
			}
		}
	}
	
	
}
