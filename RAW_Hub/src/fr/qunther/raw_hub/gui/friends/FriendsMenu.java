package fr.qunther.raw_hub.gui.friends;

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

public class FriendsMenu implements Listener{
	private static final String friendsMenuTEXT = GUIManagement.getMenuTypography()+"Amis";
	private static final String closeTEXT = ChatColor.RED+"Fermer menu";

	
	public static void openFriendsMenu(Player p) {
		Inventory cosmeticMenu = Bukkit.createInventory(p, 27, friendsMenuTEXT);

		ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(byte)3);

		cosmeticMenu.setItem(0, skull);
		cosmeticMenu.setItem(1, skull);
		cosmeticMenu.setItem(2, skull);
		cosmeticMenu.setItem(3, skull);
		
		ItemStack close_door = new ItemStack(Material.DARK_OAK_DOOR_ITEM, 1);
		ItemMeta close_doorM = close_door.getItemMeta();
		close_doorM.setDisplayName(closeTEXT);
		close_door.setItemMeta(close_doorM);
		cosmeticMenu.setItem(26, close_door);
		
		
		p.closeInventory();
		p.openInventory(cosmeticMenu);
	}
	
	@EventHandler
	public void onFriendsClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equalsIgnoreCase(friendsMenuTEXT)) {			
			try {
				if(e.getCurrentItem().getItemMeta().getDisplayName()==closeTEXT) {
					p.closeInventory();
				}
			} catch (Exception e2) {
			}
				
		}
	}
	
	
	
}
