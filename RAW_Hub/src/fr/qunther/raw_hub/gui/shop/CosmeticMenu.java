package fr.qunther.raw_hub.gui.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.qunther.raw_hub.gui.GUIManagement;
import net.md_5.bungee.api.ChatColor;

public class CosmeticMenu implements Listener{
	private static final String cosmeticMenuTEXT = GUIManagement.getMenuTypography()+"Cosmétiques";
	private static final String petsShopTEXT = ChatColor.GOLD+"Animaux de compagnie";
	private static final String particlesShopTEXT = ChatColor.GOLD+"Traînées de particules";
	private static final String closeTEXT = ChatColor.RED+"Fermer menu";
	
	public static void openCosmeticMenu(Player p) {
		Inventory cosmeticMenu = Bukkit.createInventory(p, 27, cosmeticMenuTEXT);

		ItemStack saddle = new ItemStack(Material.SADDLE);
		ItemStack blaze_powder = new ItemStack(Material.BLAZE_POWDER);
		ItemStack close_door = new ItemStack(Material.DARK_OAK_DOOR_ITEM, 1);

		ItemMeta saddleM = saddle.getItemMeta();
		ItemMeta blaze_powderM = blaze_powder.getItemMeta();
		ItemMeta close_doorM = close_door.getItemMeta();
		
		saddleM.setDisplayName(petsShopTEXT);
		blaze_powderM.setDisplayName(particlesShopTEXT);
		close_doorM.setDisplayName(closeTEXT);
		
		saddle.setItemMeta(saddleM);
		blaze_powder.setItemMeta(blaze_powderM);
		close_door.setItemMeta(close_doorM);
		
		cosmeticMenu.setItem(12, saddle);
		cosmeticMenu.setItem(14, blaze_powder);
		cosmeticMenu.setItem(26, close_door);
		
		p.closeInventory();
		p.openInventory(cosmeticMenu);
	}
	
	@EventHandler
	public void onChaosClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equalsIgnoreCase(cosmeticMenuTEXT)) {			
			try {
				if(e.getCurrentItem().getItemMeta().getDisplayName()==petsShopTEXT) {
					p.sendMessage("animaux");
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==particlesShopTEXT) {
					p.sendMessage("particules");
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==closeTEXT) {
					p.closeInventory();
				}
			} catch (Exception e2) {
			}
				
		}
	}
}
