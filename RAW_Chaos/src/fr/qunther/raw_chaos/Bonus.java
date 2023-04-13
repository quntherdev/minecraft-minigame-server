package fr.qunther.raw_chaos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.qunther.raw_chaos.management.ServerMove;
import fr.qunther.raw_chaos.management.State;

public class Bonus implements Listener {
	private static final String titlesTypography = ChatColor.GOLD+""+ChatColor.BOLD;
	private static final String bonusTypography = titlesTypography+"Bonus";
	private static final String bedTypography = titlesTypography+"Quitter";
	
	private static final String gainTempsTEXT = titlesTypography+"Kit gain de temps";
	private static final String protectionUltraTEXT = titlesTypography+"Protection Ultra";
	private static final String ghostRiderTEXT = titlesTypography+"Ghost Rider";
	private static final String fermentedSpiderTEXT = titlesTypography+"Tueur à l'acide";
	private static final String archerExplosifTEXT = titlesTypography+"Archer explosif";

	public static final String protect1TEXT = titlesTypography+"Protection Ultra ";
	public static final String tueur_acideTEXT = titlesTypography+"Tueur à l'acide";

	public static final String flame_bowTEXT = titlesTypography+"Ghost Rider";
	public static final String explosive_bowTEXT = titlesTypography+"Archer explosif";
	
	
	private static final String menuTypography = ChatColor.BLUE+""+ChatColor.BOLD+"Bonus";
	private static final String closeTEXT = ChatColor.RED+"Fermer menu";

	private static final String goToBoutiqueRAWCoinsMessageTEXT = Chaos.serverMsgPrefix+ChatColor.RESET+" Il vous faut des RAWCoins ! "+ChatColor.GOLD+"/boutique"+ChatColor.RESET+".";
	private static final String goToBoutiqueGemmesMessageTEXT = Chaos.serverMsgPrefix+ChatColor.RESET+" Il vous faut des Gemmes ! "+ChatColor.GOLD+"/boutique"+ChatColor.RESET+".";
	private static final String onlyForRanksTEXT = Chaos.serverMsgPrefix+ChatColor.BLUE+" Ce bonus est offert aux gradés ! "+ChatColor.GOLD+"/boutique"+ChatColor.BLUE+".";

	Chaos main;
	
	public Bonus(Chaos main) {
		this.main = main;
	}
	
	public static void setChaosWaitingInventory(Player p) {
		p.getInventory().clear();
		p.updateInventory();

		Inventory pI = p.getInventory();
		
		ItemStack enderEye = new ItemStack(Material.EYE_OF_ENDER,1);
		ItemMeta enderEyeM = enderEye.getItemMeta();
		enderEyeM.setDisplayName(bonusTypography);
		enderEye.setItemMeta(enderEyeM);	
		
		ItemStack bed = new ItemStack(Material.BED,1);
		ItemMeta bedM = bed.getItemMeta();
		bedM.setDisplayName(bedTypography);
		bed.setItemMeta(bedM);
		
		pI.setItem(8, bed);
		pI.setItem(0,enderEye);
		p.updateInventory();
	}
	
	@EventHandler
	public void onClickItemInventory(InventoryClickEvent e) {
		if(main.isState(State.WAITING) || main.isState(State.FINISHING)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		if(main.isState(State.WAITING) || main.isState(State.FINISHING)) {
			e.setCancelled(true);
		}
	}
	
	
	
	public static void openBonusInventory(Player p) {
		Inventory kitMenu = Bukkit.createInventory(p, 9, menuTypography);

		ItemStack close_door = new ItemStack(Material.DARK_OAK_DOOR_ITEM, 1);
		ItemMeta close_doorM = close_door.getItemMeta();
		close_doorM.setDisplayName(closeTEXT);
		close_door.setItemMeta(close_doorM);
		
		ItemStack iron_helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
		ItemMeta iron_helmetM = iron_helmet.getItemMeta();
		iron_helmetM.setDisplayName(gainTempsTEXT);
		iron_helmet.setItemMeta(iron_helmetM);
		
		ArrayList<String> iron_helmet_lore = new ArrayList<String>();
		
		iron_helmet_lore.add("");
		iron_helmet_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Description");
		iron_helmet_lore.add(ChatColor.BLUE+"Vous obtenez dès le début de la partie");
		iron_helmet_lore.add(ChatColor.BLUE+"une hache en fer, un casque et des bottes");
		iron_helmet_lore.add(ChatColor.BLUE+"en diamant ainsi que 5 pommes de terre cuites.");
		iron_helmet_lore.add("");
		iron_helmet_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Prix");
		iron_helmet_lore.add(ChatColor.GOLD+""+PlayerBDD.getGameBonusInfos(1).get(3)+" Gemmes");
		iron_helmetM.setLore(iron_helmet_lore);
		iron_helmet.setItemMeta(iron_helmetM);

		
		
		ItemStack enchant = new ItemStack(Material.ENCHANTMENT_TABLE, 1);
		ItemMeta enchantM = enchant.getItemMeta();
		enchantM.setDisplayName(protectionUltraTEXT);
		
		ArrayList<String> enchant_lore = new ArrayList<String>();
		enchant_lore.add("");
		enchant_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Description");
		enchant_lore.add(ChatColor.BLUE+"Vous recevez une émeraude spécial qui,");
		enchant_lore.add(ChatColor.BLUE+"lorsqu'elle est activée, ajoute");
		enchant_lore.add(ChatColor.BLUE+"l'enchantement \"Protection 1\" sur");
		enchant_lore.add(ChatColor.BLUE+"TOUTES les armures présentes dans votre");
		enchant_lore.add(ChatColor.BLUE+"inventaire. Activable une fois par partie.");
		enchant_lore.add(ChatColor.BLUE+"");
		enchant_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Prix");
		enchant_lore.add(ChatColor.GOLD+""+PlayerBDD.getGameBonusInfos(2).get(3)+" Gemmes");
		enchantM.setLore(enchant_lore);
		enchant.setItemMeta(enchantM);
		
		
		
		ItemStack flint = new ItemStack(Material.FLINT_AND_STEEL,1);
		ItemMeta flintM = flint.getItemMeta();
		flintM.setDisplayName(ghostRiderTEXT);
		
		ArrayList<String> flint_lore = new ArrayList<String>();
		flint_lore.add("");
		flint_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Description");
		flint_lore.add(ChatColor.BLUE+"Vous recevez dès la phase de farm");
		flint_lore.add(ChatColor.BLUE+"un arc enflammé ainsi que 8 flèches");
		flint_lore.add(ChatColor.BLUE+"qui détruisent tous les blocs ciblés.");
		//flint_lore.add(ChatColor.BLUE+"Si l'arc tombe dans les mains d'un");
		//flint_lore.add(ChatColor.BLUE+"non gradé, l'arc tombe à une durabilité");
		//flint_lore.add(ChatColor.BLUE+"de 2 tirs.");
		flint_lore.add("");
		flint_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Prix");
		flint_lore.add(ChatColor.GOLD+"Offert aux gradés VIP et SURHOMME - Non");
		flint_lore.add(ChatColor.GOLD+"procurable par RAWCoins ou Gemmes.");
		flintM.setLore(flint_lore);
		flint.setItemMeta(flintM);
		
		
		
		ItemStack fermented = new ItemStack(Material.FERMENTED_SPIDER_EYE,1);
		ItemMeta fermentedM = fermented.getItemMeta();
		fermentedM.setDisplayName(fermentedSpiderTEXT);
		
		ArrayList<String> fermented_lore = new ArrayList<String>();
		fermented_lore.add("");
		fermented_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Description");
		fermented_lore.add(ChatColor.BLUE+"Vous devenez un véritable maître de");
		fermented_lore.add(ChatColor.BLUE+"jeu pendant 5 secondes. Un émeraude ");
		fermented_lore.add(ChatColor.BLUE+"spécial activable une fois par partie vous");
		fermented_lore.add(ChatColor.BLUE+"permet d'injecter en intraveineuse un");
		fermented_lore.add(ChatColor.BLUE+"effet toxique sur TOUS les joueurs de la");
		fermented_lore.add(ChatColor.BLUE+"partie, sauf vous, pendant 4 secondes.");
		fermented_lore.add(ChatColor.BLUE+"Jugé meilleur moyen de l'année pour");
		fermented_lore.add(ChatColor.BLUE+"achever vos adversaires !");
		fermented_lore.add("");
		fermented_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Prix");
		fermented_lore.add(ChatColor.GOLD+"Offert aux gradés SURHOMME - Non");
		fermented_lore.add(ChatColor.GOLD+"procurable par RAWCoins ou Gemmes.");
		fermentedM.setLore(fermented_lore);
		fermented.setItemMeta(fermentedM);	
		
		
		
		ItemStack tnt = new ItemStack(Material.TNT,1);
		ItemMeta tntM = tnt.getItemMeta();
		tntM.setDisplayName(archerExplosifTEXT);
		
		ArrayList<String> tnt_lore = new ArrayList<String>();
		tnt_lore.add("");
		tnt_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Description");
		tnt_lore.add(ChatColor.BLUE+"Ce kit vous offre un arc spécial qui a");
		tnt_lore.add(ChatColor.BLUE+"l'effet explosif d'une TNT sur le bloc visé");
		tnt_lore.add(ChatColor.BLUE+"(et ceux l'entourant) ainsi que 5 flèches");
		tnt_lore.add(ChatColor.BLUE+"associées à cet arc.");
		tnt_lore.add(ChatColor.BLUE+"");
		tnt_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Prix");
		tnt_lore.add(ChatColor.GOLD+""+PlayerBDD.getGameBonusInfos(5).get(1)+" RAWCoins");
		tntM.setLore(tnt_lore);
		tnt.setItemMeta(tntM);

		kitMenu.setItem(8, close_door);
		kitMenu.setItem(0, iron_helmet);
		kitMenu.setItem(1, enchant);
		kitMenu.setItem(2, flint);
		kitMenu.setItem(3, fermented);
		kitMenu.setItem(4, tnt);

		p.closeInventory();
		p.openInventory(kitMenu);

	}
	
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		try {
			if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK
					|| e.getAction()==Action.LEFT_CLICK_AIR || e.getAction()==Action.LEFT_CLICK_BLOCK) {
				if(main.isState(State.WAITING) || main.isState(State.FINISHING)) {
					e.setCancelled(true);
				}
				if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(bonusTypography)){
					openBonusInventory(p);
				}
			}			
		} catch(Exception e4) {
			
		}
		
		try {
			if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK) {
				if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(bedTypography)) {
					p.sendMessage(Chaos.serverMsgPrefix+ChatColor.BLUE+" Vous venez de quitter la salle d'attente.");
					
					ServerMove sm = new ServerMove(main);
					sm.sendPlayerToServer(p,"hub-1");
				}
			}			
		}catch(Exception e5) {

		}

	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(e.getView().getTitle().equalsIgnoreCase(menuTypography)){
			try {
				int id_shop_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2);
				
				if(e.getCurrentItem().getItemMeta().getDisplayName()==closeTEXT) {
					p.closeInventory();
					
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==gainTempsTEXT) {
					if(!PlayerBDD.playerBoughtBonus(p.getUniqueId().toString(), 1)) {
						p.sendMessage(goToBoutiqueGemmesMessageTEXT);
					}else {
						changingBonus(p, 1, id_shop_rank);
					}
					
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==protectionUltraTEXT) {
					if(!PlayerBDD.playerBoughtBonus(p.getUniqueId().toString(), 2)) {
						p.sendMessage(goToBoutiqueGemmesMessageTEXT);
					}else {						
						changingBonus(p, 2, id_shop_rank);
					}
					
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==ghostRiderTEXT) {
					if(id_shop_rank<2 || id_shop_rank>3) {
						p.sendMessage(onlyForRanksTEXT);
					}else {
						p.sendMessage(Chaos.serverMsgPrefix+ChatColor.WHITE+" Ce bonus est déjà activé, en raison de votre grade ! ");
					}
					
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==fermentedSpiderTEXT) {
					if(id_shop_rank!=3) {
						p.sendMessage(onlyForRanksTEXT);
					}else {
						p.sendMessage(Chaos.serverMsgPrefix+ChatColor.WHITE+" Ce bonus est déjà activé, en raison de votre grade ! ");
					}
					
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==archerExplosifTEXT) {
					if(!PlayerBDD.playerBoughtBonus(p.getUniqueId().toString(), 5)) {
						p.sendMessage(goToBoutiqueRAWCoinsMessageTEXT);
					}else {
						changingBonus(p, 5, id_shop_rank);
					}
				}
				
				
			} catch (Exception e2) {

			}
		}
	}

	public static void changingBonus(Player p, int bonus_id, int id_shop_rank) {
		if(PlayerBDD.hasPlayerTempoBonus(p.getUniqueId().toString())) {
			PlayerBDD.removePlayerTempoBonus(p.getUniqueId().toString());
		}
		
		PlayerBDD.addPlayerTempoBonus(p.getUniqueId().toString(), bonus_id);
		int player_bonus_id = (int)PlayerBDD.getPlayerTempoBonus(p.getUniqueId().toString()).get(0);
		
		switch(id_shop_rank) {
		case 2:
			p.sendMessage(ChatColor.GREEN+"Bonus activé : "+ChatColor.RED+""+PlayerBDD.getGameBonusInfos(3).get(4)
							+ChatColor.GREEN+", "+ChatColor.RED+PlayerBDD.getGameBonusInfos(player_bonus_id).get(4));
			break;
		case 3:
			p.sendMessage(ChatColor.GREEN+"Bonus activés : "
					+ChatColor.RED+""+PlayerBDD.getGameBonusInfos(3).get(4)
					+ChatColor.GREEN+", "+ChatColor.RED+PlayerBDD.getGameBonusInfos(4).get(4)
					+ChatColor.GREEN+", "+ChatColor.RED+PlayerBDD.getGameBonusInfos(player_bonus_id).get(4));
			break;
		default:
			p.sendMessage(ChatColor.GREEN+"Bonus activés : "+
							ChatColor.RED+PlayerBDD.getGameBonusInfos(player_bonus_id).get(4));
		}
		p.closeInventory();
	}
	
	
}
