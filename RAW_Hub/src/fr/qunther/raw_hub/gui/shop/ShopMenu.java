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

import fr.qunther.raw_hub.Main;
import fr.qunther.raw_hub.gui.GUIManagement;
import net.md_5.bungee.api.ChatColor;

public class ShopMenu implements Listener{
	private static final String goToBoutiqueMessageTEXT = Main.getSimpleServerMsgPrefix()+ChatColor.RESET+" Avec "+ChatColor.GOLD+"/boutique"+ChatColor.RESET+", voyez comment vous procurer des RAWCoins bien raffinés !";
	private static final String shopMenuTEXT = GUIManagement.getMenuTypography()+"Boutique";
	private static final String ranksShopTEXT = ChatColor.GOLD+"Grades";
	private static final String bonusChaosTEXT = ChatColor.GOLD+"Chaos";
	private static final String gemmesTEXT = ChatColor.GOLD+"Gemmes";
	private static final String closeTEXT = ChatColor.RED+"Fermer menu";
	
	public static String getBoutiqueMessage() {
		return goToBoutiqueMessageTEXT;
	}
	
	public static void openShopMenu(Player p) {
		Inventory shopMenu = Bukkit.createInventory(p, 27, shopMenuTEXT);

		ItemStack diamond = new ItemStack(Material.DIAMOND);
		ItemStack fireball = new ItemStack(Material.FIREBALL);
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemStack close_door = new ItemStack(Material.DARK_OAK_DOOR_ITEM, 1);

		ItemMeta diamondM = diamond.getItemMeta();
		ItemMeta fireballM = fireball.getItemMeta();
		ItemMeta emeraldM = emerald.getItemMeta();
		ItemMeta close_doorM = close_door.getItemMeta();

		diamondM.setDisplayName(ranksShopTEXT);
		fireballM.setDisplayName(bonusChaosTEXT);
		emeraldM.setDisplayName(gemmesTEXT);
		close_doorM.setDisplayName(closeTEXT);
		
		diamond.setItemMeta(diamondM);
		fireball.setItemMeta(fireballM);
		emerald.setItemMeta(emeraldM);
		close_door.setItemMeta(close_doorM);

		shopMenu.setItem(11, diamond);
		shopMenu.setItem(13, fireball);
		shopMenu.setItem(15, emerald);
		shopMenu.setItem(26, close_door);
		
		p.closeInventory();
		p.openInventory(shopMenu);
	}
	
	@EventHandler
	public void onShopClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equalsIgnoreCase(shopMenuTEXT)) {			
			try {
				if(e.getCurrentItem().getItemMeta().getDisplayName()==ranksShopTEXT) {
					Ranks.openRankMenu(p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==bonusChaosTEXT) {
					ChaosBonusGUI.openBonusInventory(p);
					//p.sendMessage(GUIManagement.getFeatureSoonAvailableMsg());
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==gemmesTEXT) {
					Gemmes.openGemmesMenu(p);
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==closeTEXT) {
					p.closeInventory();
				}
			} catch (Exception e2) {
			}
				
		}
	}
}
