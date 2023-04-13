package fr.qunther.raw_chaos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Plateform{ 
	
	private static World world = Bukkit.getWorld(Chaos.world_name);
	
	private static ArrayList<Block> stone_plateform_1 = new ArrayList<>();
	private static ArrayList<Block> stone_plateform_2 = new ArrayList<>();
	private static ArrayList<Block> stone_plateform_3 = new ArrayList<>();
	private static ArrayList<Block> stone_plateform_4 = new ArrayList<>();

	public static ArrayList<Block> getPlat_1(){
		return stone_plateform_1;
	}
	public static ArrayList<Block> getPlat_2(){
		return stone_plateform_2;
	}
	public static ArrayList<Block> getPlat_3(){
		return stone_plateform_3;
	}
	public static ArrayList<Block> getPlat_4(){
		return stone_plateform_4;
	}
	
	public static World getWorld(){
		return world;
	}
	
	public static void setAll() {
		add_blocks_to_list();
		changeBlocks();
	}
	
	public static void changeBlocks() {
		for(int i=0; i<stone_plateform_1.size(); i++) {
			int random = (int) (Math.random()*100+1);
			
			if(random <= 76) {
				world.getBlockAt(stone_plateform_1.get(i).getX(),
						stone_plateform_1.get(i).getY(),
						stone_plateform_1.get(i).getZ()).setType(Material.STONE);
			}else if(random>76 && random<=87) {
				world.getBlockAt(stone_plateform_1.get(i).getX(),
						stone_plateform_1.get(i).getY(),
						stone_plateform_1.get(i).getZ()).setType(Material.COAL_ORE);
			}else if(random>87 && random<=96) {
				world.getBlockAt(stone_plateform_1.get(i).getX(),
						stone_plateform_1.get(i).getY(),
						stone_plateform_1.get(i).getZ()).setType(Material.IRON_ORE);
			}else if(random>96 && random<=98) {
				world.getBlockAt(stone_plateform_1.get(i).getX(),
						stone_plateform_1.get(i).getY(),
						stone_plateform_1.get(i).getZ()).setType(Material.GOLD_ORE);
			/*}else if(random>98 && random!=100) {
				world.getBlockAt(stone_plateform_1.get(i).getX(),
						stone_plateform_1.get(i).getY(),
						stone_plateform_1.get(i).getZ()).setType(Material.DIAMOND_ORE);*/
			}else{
				world.getBlockAt(stone_plateform_1.get(i).getX(),
						stone_plateform_1.get(i).getY(),
						stone_plateform_1.get(i).getZ()).setType(Material.DIRT);
			}
		}
		
		
		for(int i=0; i<stone_plateform_2.size(); i++) {
			int random = (int) (Math.random()*100+1);
			
			if(random<=99) {
				world.getBlockAt(stone_plateform_2.get(i).getX(),
						stone_plateform_2.get(i).getY(),
						stone_plateform_2.get(i).getZ()).setType(Material.STONE);
			}else{
				world.getBlockAt(stone_plateform_1.get(i).getX(),
						stone_plateform_2.get(i).getY(),
						stone_plateform_2.get(i).getZ()).setType(Material.SLIME_BLOCK);
			}
		}
	
		for(int i=0; i<stone_plateform_3.size(); i++) {
			int random = (int) (Math.random()*100+1);
		
			if(random<=87) {
				world.getBlockAt(stone_plateform_3.get(i).getX(),
						stone_plateform_3.get(i).getY(),
						stone_plateform_3.get(i).getZ()).setType(Material.STONE);
			}else if(random>87 && random<=95){
				world.getBlockAt(stone_plateform_3.get(i).getX(),
						stone_plateform_3.get(i).getY(),
						stone_plateform_3.get(i).getZ()).setType(Material.DIAMOND_ORE);
			}else {
				world.getBlockAt(stone_plateform_3.get(i).getX(),
						stone_plateform_3.get(i).getY(),
						stone_plateform_3.get(i).getZ()).setType(Material.IRON_ORE);	
			}
		}
		
		for(int i=0; i<stone_plateform_4.size(); i++) {
			int random = (int) (Math.random()*100+1);

			if(random<=74) {
				world.getBlockAt(stone_plateform_4.get(i).getX(),
						stone_plateform_4.get(i).getY(),
						stone_plateform_4.get(i).getZ()).setType(Material.STONE);
			}else if(random>74 && random<=85){
				world.getBlockAt(stone_plateform_4.get(i).getX(),
						stone_plateform_4.get(i).getY(),
						stone_plateform_4.get(i).getZ()).setType(Material.IRON_ORE);
			}else if(random>85 && random<=95){
				world.getBlockAt(stone_plateform_4.get(i).getX(),
						stone_plateform_4.get(i).getY(),
						stone_plateform_4.get(i).getZ()).setType(Material.DIAMOND_ORE);	
			}else if(random>95 && random<=97){
				world.getBlockAt(stone_plateform_4.get(i).getX(),
						stone_plateform_4.get(i).getY(),
						stone_plateform_4.get(i).getZ()).setType(Material.GOLD_ORE);	
			}else if(random==98){
				world.getBlockAt(stone_plateform_4.get(i).getX(),
						stone_plateform_4.get(i).getY(),
						stone_plateform_4.get(i).getZ()).setType(Material.SLIME_BLOCK);	
			}else if(random==99){
				world.getBlockAt(stone_plateform_4.get(i).getX(),
						stone_plateform_4.get(i).getY(),
						stone_plateform_4.get(i).getZ()).setType(Material.WATER);	
			}else if(random==100){
				world.getBlockAt(stone_plateform_4.get(i).getX(),
						stone_plateform_4.get(i).getY(),
						stone_plateform_4.get(i).getZ()).setType(Material.LAVA);	
			}
		}
		
	}
	
	public static void setStone() {
		add_blocks_to_list();
		
		for(int i=0; i<stone_plateform_1.size(); i++) {
			world.getBlockAt(stone_plateform_1.get(i).getX(),
					stone_plateform_1.get(i).getY(),
					stone_plateform_1.get(i).getZ()).setType(Material.ANVIL);
			
			world.getBlockAt(stone_plateform_2.get(i).getX(),
					stone_plateform_2.get(i).getY(),
					stone_plateform_2.get(i).getZ()).setType(Material.STONE);

			world.getBlockAt(stone_plateform_3.get(i).getX(),
					stone_plateform_3.get(i).getY(),
					stone_plateform_3.get(i).getZ()).setType(Material.STONE);

			world.getBlockAt(stone_plateform_4.get(i).getX(),
					stone_plateform_4.get(i).getY(),
					stone_plateform_4.get(i).getZ()).setType(Material.STONE);
		}
		
		//set le milieu
		world.getBlockAt(stone_plateform_1.get(0).getX(),
				stone_plateform_1.get(0).getY(),
				stone_plateform_1.get(0).getZ()).setType(Material.DIAMOND_BLOCK);
	}

	public static void add_blocks_to_list() {
		ArrayList<Block> right_up = add_right_up_blocks();
		ArrayList<Block> left_up = add_left_up_blocks(right_up);
		ArrayList<Block> right_down = add_right_down_blocks(right_up);
		ArrayList<Block> left_down = add_left_down_blocks(right_up);
		
		stone_plateform_1.add(world.getBlockAt(0, 50, 0));

		for(int i=1; i<33; i++) {
			stone_plateform_1.add(world.getBlockAt(0, 50, i));
			stone_plateform_1.add(world.getBlockAt(0, 50, -i));
			stone_plateform_1.add(world.getBlockAt(-i, 50, 0));
			stone_plateform_1.add(world.getBlockAt(i, 50, 0));
		}
		
		stone_plateform_1.addAll(right_up);
		stone_plateform_1.addAll(left_up);
		stone_plateform_1.addAll(right_down);
		stone_plateform_1.addAll(left_down);
		
		for(int i=0; i<stone_plateform_1.size(); i++) {
			Block b = world.getBlockAt(stone_plateform_1.get(i).getLocation());
			
			Block bloc_plat_2 = world.getBlockAt(b.getX(), b.getY()-1, b.getZ());
			stone_plateform_2.add(bloc_plat_2);
			
			Block bloc_plat_3 = world.getBlockAt(b.getX(), b.getY()-2, b.getZ());
			stone_plateform_3.add(bloc_plat_3);
			
			Block bloc_plat_4 = world.getBlockAt(b.getX(), b.getY()-3, b.getZ());
			stone_plateform_4.add(bloc_plat_4);
		}

	}

	public static ArrayList<Block> add_right_up_blocks() {
		ArrayList<Block> plateform = new ArrayList<>();
		
		for(int i=1; i<33; i++) { // 6 1er blocs
			plateform.add(world.getBlockAt(-i, 50, 0));
			plateform.add(world.getBlockAt(-i, 50, 1));
			plateform.add(world.getBlockAt(-i, 50, 2));
			plateform.add(world.getBlockAt(-i, 50, 3));
			plateform.add(world.getBlockAt(-i, 50, 4));
			plateform.add(world.getBlockAt(-i, 50, 5));
		}
		
		for(int i=1; i<32; i++) { //2ème pack de blocs
			plateform.add(world.getBlockAt(-i, 50, 6));
			plateform.add(world.getBlockAt(-i, 50, 7));
			plateform.add(world.getBlockAt(-i, 50, 8));
			plateform.add(world.getBlockAt(-i, 50, 9));
		}
		
		for(int i=1; i<31; i++) {
			plateform.add(world.getBlockAt(-i, 50, 10));
			plateform.add(world.getBlockAt(-i, 50, 11));
			plateform.add(world.getBlockAt(-i, 50, 12));
		}
		
		for(int i=1; i<30; i++) {
			plateform.add(world.getBlockAt(-i, 50, 13));
			plateform.add(world.getBlockAt(-i, 50, 14));
		}
		
		for(int i=1; i<29; i++) {
			plateform.add(world.getBlockAt(-i, 50, 15));
			plateform.add(world.getBlockAt(-i, 50, 16));
		}
		
		for(int i=1; i<28; i++) {
			plateform.add(world.getBlockAt(-i, 50, 17));
			plateform.add(world.getBlockAt(-i, 50, 18));
		}
		
		
		for(int i=1; i<27; i++) {
			plateform.add(world.getBlockAt(-i, 50, 19));
		}	
		for(int i=1; i<26; i++) {
			plateform.add(world.getBlockAt(-i, 50, 20));
		}
		for(int i=1; i<25; i++) {
			plateform.add(world.getBlockAt(-i, 50, 21));
		}
		for(int i=1; i<24; i++) {
			plateform.add(world.getBlockAt(-i, 50, 22));
		}
		for(int i=1; i<23; i++) {
			plateform.add(world.getBlockAt(-i, 50, 23));
		}
		for(int i=1; i<22; i++) {
			plateform.add(world.getBlockAt(-i, 50, 24));
		}
		for(int i=1; i<21; i++) {
			plateform.add(world.getBlockAt(-i, 50, 25));
		}
		for(int i=1; i<20; i++) {
			plateform.add(world.getBlockAt(-i, 50, 26));
		}
		for(int i=1; i<19; i++) {
			plateform.add(world.getBlockAt(-i, 50, 27));
		}
		
		// On passe à -2 blocs en X
		for(int i=1; i<17; i++) {
			plateform.add(world.getBlockAt(-i, 50, 28));
		}
		for(int i=1; i<15; i++) {
			plateform.add(world.getBlockAt(-i, 50, 29));
		}
		for(int i=1; i<13; i++) {
			plateform.add(world.getBlockAt(-i, 50, 30));
		}
		
		// On passe à -3 blocs en X
		for(int i=1; i<10; i++) {
			plateform.add(world.getBlockAt(-i, 50, 31));
		}
			
		// -4 blocs
		for(int i=1; i<6; i++) {
			plateform.add(world.getBlockAt(-i, 50, 32));
		}
		
		return plateform;
	}
	
	public static ArrayList<Block> add_left_up_blocks(ArrayList<Block> right_up) {
		ArrayList<Block> left_up = new ArrayList<Block>();
		
		for(int i=0; i<right_up.size(); i++) {
			left_up.add(world.getBlockAt(-right_up.get(i).getX(), right_up.get(i).getY(), right_up.get(i).getZ()));
		}
		return left_up;
	}
	
	
	public static ArrayList<Block> add_right_down_blocks(ArrayList<Block> right_up) {
		ArrayList<Block> right_down = new ArrayList<Block>();
		
		for(int i=0; i<right_up.size(); i++) {
			right_down.add(world.getBlockAt(right_up.get(i).getX(), right_up.get(i).getY(), -right_up.get(i).getZ()));
		}
		return right_down;
	}
	
	public static ArrayList<Block> add_left_down_blocks(ArrayList<Block> right_up) {
		ArrayList<Block> left_down = new ArrayList<Block>();
		
		for(int i=0; i<right_up.size(); i++) {
			left_down.add(world.getBlockAt(-right_up.get(i).getX(), right_up.get(i).getY(), -right_up.get(i).getZ()));
		}
		return left_down;
	}
}
