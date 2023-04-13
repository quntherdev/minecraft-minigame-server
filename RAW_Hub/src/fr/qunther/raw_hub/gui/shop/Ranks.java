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
import fr.qunther.raw_hub.gui.GUIManagement;

public class Ranks implements Listener{
	private static final String ranksMenuTEXT = GUIManagement.getMenuTypography()+"Grades";
	private static final String backTEXT = ChatColor.RED+"Retour";	
	private static final String unrankTEXT = ChatColor.GRAY+"JOUEUR";
	private static final String vipTEXT = ChatColor.BLUE+""+ChatColor.BOLD+"VIP";
	private static final String surhommeTEXT = ChatColor.RED+""+ChatColor.BOLD+"SURHOMME";

	
	public static void openRankMenu(Player p) {
		Inventory ranksMenu = Bukkit.createInventory(p, 27, ranksMenuTEXT);
		
		ItemStack flower_pot = new ItemStack(Material.FLOWER_POT_ITEM, 1);
		ItemStack ender_pearl = new ItemStack(Material.ENDER_PEARL, 1);
		ItemStack eye_ender = new ItemStack(Material.EYE_OF_ENDER, 1);
		
		ItemMeta flower_potM = flower_pot.getItemMeta();
		ItemMeta ender_pearlM = ender_pearl.getItemMeta();
		ItemMeta eye_enderM = eye_ender.getItemMeta();

		flower_potM.setDisplayName(unrankTEXT);
		ender_pearlM.setDisplayName(vipTEXT);
		eye_enderM.setDisplayName(surhommeTEXT);

		ArrayList<String> joueur_lore = new ArrayList<String>();
		joueur_lore.add(ChatColor.GOLD+""+ChatColor.UNDERLINE+"Informations");
		joueur_lore.add("");
		joueur_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Général");
		joueur_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Préfixe de base et non amélioré: [JOUEUR]");
		
		joueur_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Vous n'êtes pas prioritaire");
		joueur_lore.add(ChatColor.GRAY+"sur l'accès au serveur et aux jeux");
		
		joueur_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Vous n'avez pas accès à la zone VIP,");
		joueur_lore.add(ChatColor.GRAY+"accès au simple Hub uniquement");
		
		joueur_lore.add("");
		joueur_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 En Chaos");
		
		joueur_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Vous ne recevez que 3 stacks");
		joueur_lore.add(ChatColor.GRAY+"de blocs au lieu de 4 ou 6");
		
		joueur_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Vous ne possédez pas les kits");
		joueur_lore.add(ChatColor.GRAY+"seulement ceux de base");
		
		joueur_lore.add("");
		joueur_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Hub");
		joueur_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Annonce de connexion non-honorifique");
		joueur_lore.add("");
		joueur_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Prix: grade de base gratuit");
		flower_potM.setLore(joueur_lore);

		
		
		
		ArrayList<String> vip_lore = new ArrayList<String>();
		vip_lore.add(ChatColor.GOLD+""+ChatColor.UNDERLINE+"Avantages");
		vip_lore.add("");
		vip_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Général");
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Préfixe: "+ChatColor.BLUE+"[VIP]");
		
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Accès prioritaire au serveur");
		vip_lore.add(ChatColor.GRAY+"et aux jeux: "+ChatColor.GREEN+"\u2713");
		
		//vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Message d'annonce dans tout le");
		//vip_lore.add(ChatColor.GRAY+"serveur de l'acquisition du titre: "+ChatColor.GREEN+"\u2713");
		
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Accès à la zone VIP: "+ChatColor.GREEN+"\u2713");
		vip_lore.add("");
		vip_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 En Chaos");
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"4 stacks de blocs au lieu de 3: "+ChatColor.GREEN+"\u2713");
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Reçoit des kits exclusifs: "+ChatColor.GREEN+"\u2713");
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Multiplicateur personnel");
		vip_lore.add(ChatColor.GRAY+"de RAWCoins: "+ChatColor.GOLD+"+65%");
		
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Multiplicateur personnel");
		vip_lore.add(ChatColor.GRAY+"de Gemmes: "+ChatColor.GOLD+"+65%");		
		
		vip_lore.add("");
		vip_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Hub");
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Annonce de connexion");
		vip_lore.add(ChatColor.GRAY+" honorifique: "+ChatColor.GREEN+"\u2713");
		
		vip_lore.add("");
		vip_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Prix : "+ChatColor.RED+"4 500 RAWCoins");
		ender_pearlM.setLore(vip_lore);
		
		
		ArrayList<String> surhomme_lore = new ArrayList<String>();
		surhomme_lore.add(ChatColor.GOLD+""+ChatColor.UNDERLINE+"Avantages");
		surhomme_lore.add("");
		surhomme_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Général");
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Préfixe: "+ChatColor.GREEN+"[SURHOMME]");
		
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Accès prioritaire au serveur et");
		surhomme_lore.add(ChatColor.GRAY+"aux jeux: "+ChatColor.GREEN+"\u2713");
		
		//surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Message d'annonce dans tout le");
		//surhomme_lore.add(ChatColor.GRAY+"serveur de l'acquisition du titre: "+ChatColor.GREEN+"\u2713");
		
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Accès à la zone VIP: "+ChatColor.GREEN+"\u2713");
		surhomme_lore.add("");
		surhomme_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 En Chaos");
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"6 stacks de blocs au lieu de 3: "+ChatColor.GREEN+"\u2713");
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Reçoit des kits exclusifs: "+ChatColor.GREEN+"\u2713");
		
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Multiplicateur personnel");
		surhomme_lore.add(ChatColor.GRAY+"de RAWCoins: "+ChatColor.GOLD+"+200% "+ChatColor.RED+ChatColor.BOLD+"(X3)");
		
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Multiplicateur personnel");
		surhomme_lore.add(ChatColor.GRAY+"de Gemmes: "+ChatColor.GOLD+"+200% "+ChatColor.RED+ChatColor.BOLD+"(X3)");
		
		surhomme_lore.add("");
		surhomme_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Hub");
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Annonce de connexion");
		surhomme_lore.add(ChatColor.GRAY+"honorifique: "+ChatColor.GREEN+"\u2713");
		surhomme_lore.add("");
		surhomme_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Prix : "+ChatColor.RED+"7 500 RAWCoins");
		eye_enderM.setLore(surhomme_lore);
		
		flower_pot.setItemMeta(flower_potM);
		ender_pearl.setItemMeta(ender_pearlM);
		eye_ender.setItemMeta(eye_enderM);
		
		ranksMenu.setItem(12, ender_pearl);
		ranksMenu.setItem(14, eye_ender);
		ranksMenu.setItem(18, flower_pot);
		
		ItemStack back_door = new ItemStack(Material.SPRUCE_DOOR_ITEM, 1);
		ItemMeta back_doorM = back_door.getItemMeta();
		back_doorM.setDisplayName(backTEXT);
		back_door.setItemMeta(back_doorM);
		ranksMenu.setItem(26, back_door);
		
		p.closeInventory();
		p.openInventory(ranksMenu);
	}
	
	@EventHandler
	public void onRanksClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(e.getView().getTitle().equalsIgnoreCase(ranksMenuTEXT)) {			
			try {
				if(e.getCurrentItem().getItemMeta().getDisplayName()==unrankTEXT) {
					p.closeInventory();
					p.sendMessage(Main.getServerMsgPrefix()+ChatColor.WHITE+" Cela est le grade de base, par défaut. Vous n'avez pas"
							+" accès à une expérience améliorée avec celui-là.");
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==vipTEXT) {
					bdd.PlayerBDD.insertPlayerPanier(p.getUniqueId().toString(), "rank", 2);
					Confirmation.buyConfirmationGUI(p);
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==surhommeTEXT) {
					bdd.PlayerBDD.insertPlayerPanier(p.getUniqueId().toString(), "rank", 3);
					Confirmation.buyConfirmationGUI(p);	
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==backTEXT) {
					ShopMenu.openShopMenu(p);
				}
			} catch (Exception e2) {

			}
				
		}
	}
}
