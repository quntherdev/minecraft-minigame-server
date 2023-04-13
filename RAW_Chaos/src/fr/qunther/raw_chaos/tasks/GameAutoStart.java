package fr.qunther.raw_chaos.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.qunther.raw_chaos.Bonus;
import fr.qunther.raw_chaos.Chaos;
import fr.qunther.raw_chaos.ChaosScoreboard;
import fr.qunther.raw_chaos.PVPPhase;
import fr.qunther.raw_chaos.PlateformDrop;
import fr.qunther.raw_chaos.PlayerBDD;
import fr.qunther.raw_chaos.management.AlterFileState;
import fr.qunther.raw_chaos.management.State;

public class GameAutoStart extends BukkitRunnable{
	public static boolean count_running=false;
	private int timer;
	private Chaos main;
	
	public GameAutoStart(Chaos main, int timer) {
		this.main=main;
		this.timer=timer;
	}
	
	@Override
	public void run() {
		/*
		 * 
		 *  chaque seconde pdt phase waiting, vérifier qu'il y a assez joueurs pour lancer le timer d'attente/la partie
		 */
		if(!checkAtLeast2Players()) {
			Bukkit.broadcastMessage(Chaos.serverMsgPrefix+ChatColor.YELLOW+" Pas assez de monde pour démarrer. Attendons l'arrivée de joueurs, puis lançons la partie !");
			
			for(Player p : main.getPlayers()) {
				p.setLevel(0);
			}
			
			count_running=false;
			cancel();
			return;
		}
		
		for(Player p : main.getPlayers()) {
			p.setLevel(timer);
		}
		
		
		/*
		 * si timer=0, phase farm démarre
		 * 
		 */
		if(timer==0) {
			/*
			 * 
			 *  EDIT SERVER STATE
			 */
			AlterFileState.setBUSY(main);
			
			main.hologram.removeHologram();
			main.setState(State.FARMING);
			
			Bukkit.broadcastMessage(ChatColor.GOLD+"La partie commence, vous disposez de "+ChatColor.RED+
					"5 minutes"+ChatColor.GOLD+" pour exploiter la cage !");		
				
			for(Player p : main.getPlayers()) {
				p.getInventory().clear();
				p.closeInventory();
				p.setGameMode(GameMode.SURVIVAL);
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
				
				p.getInventory().addItem(new ItemStack(Material.STONE_AXE));
				p.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE));
				p.getInventory().addItem(new ItemStack(Material.IRON_INGOT,2));

				
				/*
				 *  on donne le bonus du joueur octroyé grâce à son grade
				 * 
				 */
				int shop_rank = (int)PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2);
				
				switch(shop_rank) {
				case 2:
					//BONUS DU VIP ET SURHOMME, bonus id 3
					ItemStack flame_bow = new ItemStack(Material.BOW);
					flame_bow.addEnchantment(Enchantment.ARROW_FIRE,1);
					ItemMeta flame_bowM = flame_bow.getItemMeta();
					flame_bowM.setDisplayName(Bonus.flame_bowTEXT);
					flame_bow.setItemMeta(flame_bowM);
					
					ItemStack flame_bow_arrows= new ItemStack(Material.ARROW, 8);
					
					p.getInventory().addItem(flame_bow);
					p.getInventory().addItem(flame_bow_arrows);
					break;
				case 3:
					// LE BONUS DU SURHOMME, bonus id 3
					ItemStack flame_bow2 = new ItemStack(Material.BOW);
					flame_bow2.addEnchantment(Enchantment.ARROW_FIRE,1);
					ItemMeta flame_bowM2 = flame_bow2.getItemMeta();
					flame_bowM2.setDisplayName(Bonus.flame_bowTEXT);
					flame_bow2.setItemMeta(flame_bowM2);
					
					ItemStack flame_bow_arrows2= new ItemStack(Material.ARROW, 8);
					
					p.getInventory().addItem(flame_bow2);
					p.getInventory().addItem(flame_bow_arrows2);
					
					//LE BONUS DU SURHOMME, bonus id 4
					ItemStack acide = new ItemStack(Material.EMERALD);
					ItemMeta acideM = acide.getItemMeta();
					acideM.setDisplayName(Bonus.tueur_acideTEXT);
					acide.setItemMeta(acideM);
					
					p.getInventory().addItem(acide);
					
				}
				
				
				/*
				 * on donne le bonus du joueur s'il en a choisit un
				 * 
				 */
				if(PlayerBDD.hasPlayerTempoBonus(p.getUniqueId().toString())){
					int bonus_id = (int)PlayerBDD.getPlayerTempoBonus(p.getUniqueId().toString()).get(0);
					
					switch(bonus_id) {
					case 1:
						p.getInventory().addItem(new ItemStack(Material.IRON_AXE));
						p.getInventory().addItem(new ItemStack(Material.DIAMOND_HELMET));
						p.getInventory().addItem(new ItemStack(Material.DIAMOND_BOOTS));
						
						break;
					case 2:
						ItemStack protect1 = new ItemStack(Material.EMERALD);
						ItemMeta protect1M = protect1.getItemMeta();
						protect1M.setDisplayName(Bonus.protect1TEXT);
						protect1.setItemMeta(protect1M);
						
						p.getInventory().addItem(protect1);
						
						break;
					case 5:
						ItemStack explosive_bow = new ItemStack(Material.BOW);
						explosive_bow.addEnchantment(Enchantment.ARROW_FIRE,1);
						ItemMeta explosive_bowM = explosive_bow.getItemMeta();
						explosive_bowM.setDisplayName(Bonus.explosive_bowTEXT);
						explosive_bow.setItemMeta(explosive_bowM);
						
						ItemStack explosive_bow_arrows= new ItemStack(Material.ARROW, 5);
						
						p.getInventory().addItem(explosive_bow);
						p.getInventory().addItem(explosive_bow_arrows);
						break;
					}
					
				}
				
				PlateformDrop.dropTrees();
				PlateformDrop.dropBoneMeals();
			}
			
			count_running=false;
			
			
			/*
			 * seconde vague drop arbres et bone meal
			 * 
			 */
			new BukkitRunnable() {
				private int timer=30;
				@Override
				public void run() {
					if(main.isState(State.FINISHING)) {
						cancel();
						return;
					}
					
					if(timer==15) {
						Bukkit.broadcastMessage(ChatColor.GOLD+"Nouvelle pluie dans "+ChatColor.RED+"15 secondes"+ChatColor.GOLD+" !");
					}
					
					if(timer==0) {
						Bukkit.broadcastMessage(ChatColor.GOLD+"Voici une "+ChatColor.RED+"nouvelle pluie de pousses d'arbres et de poudre"
								+ChatColor.GOLD+", si vous avez été malchanceux à la première !");
						PlateformDrop.dropTrees();
						PlateformDrop.dropBoneMeals();
						cancel();
						return;
					}
					timer--;
				}
				
			}.runTaskTimer(main, 0, 20);
		
			
			
			/*
			 * 
			 * drop animaux toutes les 30 secondes pdt phase farm
			 * 
			 */
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if(!main.isState(State.FARMING)) {
						cancel();
						return;
					}
					PlateformDrop.spawnAnimals();
				}
				
			}.runTaskTimer(main, 0, 20*15);
			
			
			/*
			 * give random items pdt phase farm
			 * 
			 */
			RandomItemsGive randomItemG = new RandomItemsGive(main);
			randomItemG.runTaskTimer(main, 20*10, 20);
			
			
			/*
			 * enregister partie dans bdd quand partie en phase farm
			 * 
			 */
			PlayerBDD.createChaosMatch(main.getPlayers().size());
			Chaos.matchID=(Long) PlayerBDD.getLastChaosMatchID().get(0);
			
			
			/*
			 * on lance le runnable du pvp, qui doit avoir un timer à 5 minutes, retire 1 à chaque seconde
			 * 
			 */
			PVPPhase pvp = new PVPPhase(main);
			pvp.runTaskTimer(main, 0, 20);
			
			for(Player pls : main.getPlayers()) {
				ChaosScoreboard cs = new ChaosScoreboard(main,pvp,pls);
				cs.setScoreBoard();
				
				@SuppressWarnings("unused")
				BukkitTask update_scoreboard = cs.runTaskTimer(main, 0, 20*1);
			}
			
			cancel();
			return;
		}
		
		/*
		 * annonce que la phase farm va commencer
		 * 
		 */
		if(timer==10 || timer<=5) {
			for(Player p : main.getPlayers()) {
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
			}
			if(timer==1) {
				Bukkit.broadcastMessage(ChatColor.GREEN+"Démarrage dans... "+ChatColor.YELLOW+timer+ChatColor.GREEN+" seconde !");	
			}else {
				Bukkit.broadcastMessage(ChatColor.GREEN+"Démarrage dans... "+ChatColor.YELLOW+timer+ChatColor.GREEN+" secondes !");					
			}
		}
		
		timer--;
	}
	
	/*
	 *  vérifier qu'il y a assez de joueurs pour lancer la phase de farm
	 * 
	 */
	public boolean checkAtLeast2Players() {
		if(main.getPlayers().size()>=2) { // CHANGER 1 PAR 2
			return true;
		}
		return false;
	}

}
