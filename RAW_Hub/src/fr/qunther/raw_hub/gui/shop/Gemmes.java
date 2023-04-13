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

import fr.qunther.raw_hub.gui.Confirmation;
import fr.qunther.raw_hub.gui.GUIManagement;

public class Gemmes implements Listener{
	private static final String gemmesMenuTEXT = GUIManagement.getMenuTypography()+"Gemmes";
	private static final String backTEXT = ChatColor.RED+"Retour";	
	private static final String offre1TEXT = ChatColor.RED+""+ChatColor.BOLD+"5 600 Gemmes";
	private static final String offre2TEXT = ChatColor.RED+""+ChatColor.BOLD+"9 800 Gemmes";
	
	public static void openGemmesMenu(Player p) {
		Inventory gemmesMenu = Bukkit.createInventory(p, 27, gemmesMenuTEXT);
		
		ItemStack slime_ball = new ItemStack(Material.SLIME_BALL, 1);
		ItemStack magma = new ItemStack(Material.MAGMA_CREAM, 1);
		
		ItemMeta slime_ballM = slime_ball.getItemMeta();
		ItemMeta magmaM = magma.getItemMeta();

		slime_ballM.setDisplayName(offre1TEXT);
		magmaM.setDisplayName(offre2TEXT);

		ArrayList<String> offre1_lore = new ArrayList<String>();
		offre1_lore.add(ChatColor.GOLD+""+ChatColor.UNDERLINE+"Informations");
		offre1_lore.add("");
		offre1_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Général");
		offre1_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+" Vous recevez 5 600 Gemmes qui vous");
		offre1_lore.add(ChatColor.GRAY+"permettent d'acheter des bonus en jeu.");

		offre1_lore.add("");
		offre1_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Prix: "+ChatColor.RED+"4 500 RAWCoins");
		slime_ballM.setLore(offre1_lore);

		
		ArrayList<String> offre2_lore = new ArrayList<String>();
		offre2_lore.add(ChatColor.GOLD+""+ChatColor.UNDERLINE+"Informations");
		offre2_lore.add("");
		offre2_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Général");
		offre2_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+" Vous recevez 9 800 Gemmes qui vous");
		offre2_lore.add(ChatColor.GRAY+"permettent d'acheter des bonus en jeu.");
		offre2_lore.add("");
		offre2_lore.add(ChatColor.DARK_GRAY+"- "+ChatColor.GRAY+"Prix: "+ChatColor.RED+"7 000 RAWCoins");
		magmaM.setLore(offre2_lore);

		
		slime_ball.setItemMeta(slime_ballM);
		magma.setItemMeta(magmaM);
		
		gemmesMenu.setItem(12, slime_ball);
		gemmesMenu.setItem(14, magma);
		
		ItemStack back_door = new ItemStack(Material.SPRUCE_DOOR_ITEM, 1);
		ItemMeta back_doorM = back_door.getItemMeta();
		back_doorM.setDisplayName(backTEXT);
		back_door.setItemMeta(back_doorM);
		
		gemmesMenu.setItem(26, back_door);
		
		p.closeInventory();
		p.openInventory(gemmesMenu);
	}
	
	@EventHandler
	public void onGemmesClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(e.getView().getTitle().equalsIgnoreCase(gemmesMenuTEXT)) {			
			try {
				if(e.getCurrentItem().getItemMeta().getDisplayName()==offre1TEXT) {
					bdd.PlayerBDD.insertPlayerPanier(p.getUniqueId().toString(), "gemmes", 5600);
					Confirmation.buyConfirmationGUI(p);
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==offre2TEXT) {
					bdd.PlayerBDD.insertPlayerPanier(p.getUniqueId().toString(), "gemmes", 9800);
					Confirmation.buyConfirmationGUI(p);	
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==backTEXT) {
					ShopMenu.openShopMenu(p);
				}
			} catch (Exception e2) {

			}
				
		}
	}
}
