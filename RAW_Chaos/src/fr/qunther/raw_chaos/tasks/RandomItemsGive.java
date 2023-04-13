package fr.qunther.raw_chaos.tasks;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_chaos.Chaos;
import fr.qunther.raw_chaos.management.State;

public class RandomItemsGive extends BukkitRunnable{
	private ArrayList<ItemStack> items_list;
	private Chaos main;
	private int timer= 60;
	
	public RandomItemsGive(Chaos main) {
		this.main = main;
		this.items_list = new ArrayList<>();
		fillItemsList();
	}

	public void fillItemsList() {
		ItemStack string = new ItemStack(Material.STRING,3);
		items_list.add(string);
		
		ItemStack flint = new ItemStack(Material.FLINT_AND_STEEL);
		flint.setDurability((short) ((short) 64-8));
		items_list.add(flint);

		ItemStack golden_apple = new ItemStack(Material.GOLDEN_APPLE, 2);
		items_list.add(golden_apple);
		
		@SuppressWarnings("deprecation")
		ItemStack witch = new ItemStack(Material.MONSTER_EGG,1,EntityType.WITCH.getTypeId());
		items_list.add(witch);
		
		ItemStack fishing_rod = new ItemStack(Material.FISHING_ROD);
		fishing_rod.setDurability((short) ((short)64-8));
		items_list.add(fishing_rod);
		
		/*ItemStack iron = new ItemStack(Material.IRON_INGOT,2);
		items_list.add(iron);*/
		
		ItemStack snowballs = new ItemStack(Material.SNOW_BALL,2);
		items_list.add(snowballs);
		
		ItemStack jump2 = new ItemStack(Material.POTION, 1, (short) 16427);
		items_list.add(jump2);

		ItemStack anti_fire = new ItemStack(Material.POTION, 1, (short) 16419);
		items_list.add(anti_fire);
		
		ItemStack heal = new ItemStack(Material.POTION, 1, (short) 16421);
		items_list.add(heal);
		
		ItemStack heal2 = new ItemStack(Material.POTION, 1, (short) 16421);
		items_list.add(heal2);
		
		ItemStack bucket = new ItemStack(Material.BUCKET, 1);
		items_list.add(bucket);
	}
	
	@Override
	public void run() {
		if(main.isState(State.FINISHING)) {
			cancel();
			return;
		}
		
		if(main.isState(State.PVP) || timer==0) {
			cancel();
			return;
		}
		
		if(timer%20==0) {
			for(Entity e : Bukkit.getWorld(Chaos.world_name).getEntities()) {
				if(e instanceof Player) {
					Player p = (Player)e;
					Random random = new Random();
					int r = random.nextInt(items_list.size());
					
					p.getInventory().addItem(items_list.get(r));
				}
			}	
		}
		timer--;
	}
}
