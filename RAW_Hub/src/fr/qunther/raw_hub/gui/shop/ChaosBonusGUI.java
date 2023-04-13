package fr.qunther.raw_hub.gui.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.qunther.raw_hub.Main;
import fr.qunther.raw_hub.gui.Confirmation;

public class ChaosBonusGUI implements Listener {	
	private static final String titlesTypography = ChatColor.GOLD+""+ChatColor.BOLD;
	private static final String backTEXT = ChatColor.RED+"Retour";	
	
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
	
	
	public static void openBonusInventory(Player p) {
		Inventory kitMenu = Bukkit.createInventory(p, 9, menuTypography);

		ItemStack back_text = new ItemStack(Material.SPRUCE_DOOR_ITEM, 1);
		ItemMeta back_textM = back_text.getItemMeta();
		back_textM.setDisplayName(backTEXT);
		back_text.setItemMeta(back_textM);
		
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
		iron_helmet_lore.add(ChatColor.GOLD+""+bdd.PlayerBDD.getGameBonusInfos(1).get(3)+" Gemmes");
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
		enchant_lore.add(ChatColor.GOLD+""+bdd.PlayerBDD.getGameBonusInfos(2).get(3)+" Gemmes");
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
		tnt_lore.add(ChatColor.GOLD+""+bdd.PlayerBDD.getGameBonusInfos(5).get(1)+" RAWCoins");
		tntM.setLore(tnt_lore);
		tnt.setItemMeta(tntM);

		kitMenu.setItem(8, back_text);
		kitMenu.setItem(0, iron_helmet);
		kitMenu.setItem(1, enchant);
		kitMenu.setItem(2, flint);
		kitMenu.setItem(3, fermented);
		kitMenu.setItem(4, tnt);

		p.closeInventory();
		p.openInventory(kitMenu);

	}
	
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(e.getView().getTitle().equalsIgnoreCase(menuTypography)){
			try {				
				if(e.getCurrentItem().getItemMeta().getDisplayName()==backTEXT) {
					p.closeInventory();
					ShopMenu.openShopMenu(p);
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==gainTempsTEXT) {
					bdd.PlayerBDD.insertPlayerPanier(p.getUniqueId().toString(), "chaos_bonus",1);
					Confirmation.buyConfirmationGUI(p);
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==protectionUltraTEXT) {
					bdd.PlayerBDD.insertPlayerPanier(p.getUniqueId().toString(), "chaos_bonus",2);
					Confirmation.buyConfirmationGUI(p);

				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==archerExplosifTEXT) {
					bdd.PlayerBDD.insertPlayerPanier(p.getUniqueId().toString(), "chaos_bonus",5);
					Confirmation.buyConfirmationGUI(p);

				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==ghostRiderTEXT) {
					p.closeInventory();
					p.sendMessage(Main.getServerMsgPrefix()+ChatColor.BLUE+" Ce bonus très apprécié des joueurs est offert aux gradés VIP et SURHOMME... "+
							ChatColor.GOLD+"/boutique"+ChatColor.BLUE+" pour l'avoir vous aussi ! :)");
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==fermentedSpiderTEXT) {
					p.closeInventory();
					p.sendMessage(Main.getServerMsgPrefix()+ChatColor.BLUE+" Ce bonus très apprécié des joueurs est offert aux gradés SURHOMME... "+
									ChatColor.GOLD+"/boutique"+ChatColor.BLUE+" pour l'avoir vous aussi ! :)");
				}
				
				
			} catch (Exception e2) {

			}
		}
	}
	
	
}
