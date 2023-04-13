package fr.qunther.raw_hub.gui;

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

import fr.qunther.raw_hub.Main;
import fr.qunther.raw_hub.gui.main.MainMenu;
import fr.qunther.raw_hub.gui.shop.ShopMenu;

public class GUIManagement implements Listener {
	private static final String titlesTypography = ChatColor.GOLD+""+ChatColor.BOLD;
	private static final String menuTypography = ChatColor.BLUE+"";
	private static String featureSoonAvailable = Main.getServerMsgPrefix()+" Cette fonctionnalité sera déployée prochainement, restez informés ! :)";
	
	public static String getTitlesTypography() {
		return titlesTypography;
	}
	
	public static String getMenuTypography() {
		return menuTypography;
	}
	
	public static String getFeatureSoonAvailableMsg() {
		return featureSoonAvailable;
	}
	
	public static void setHUBInventory(Player p) {
		p.getInventory().clear();
		p.updateInventory();

		Inventory pI = p.getInventory();
		
		ItemStack menuItem = new ItemStack(Material.COMPASS,1);
		ItemStack shopItem = new ItemStack(Material.EMERALD,1);
		//ItemStack cosmeticsItem = new ItemStack(Material.SADDLE,1);
		//ItemStack friendsItem = new ItemStack(Material.COOKED_FISH,1,(byte)1);
		
		ItemMeta m_menuItem = menuItem.getItemMeta();
		ItemMeta m_shopItem = shopItem.getItemMeta();
		//ItemMeta m_cosmeticsItem = cosmeticsItem.getItemMeta();
		//ItemMeta m_friendsItem = friendsItem.getItemMeta();
		
		m_menuItem.setDisplayName(titlesTypography+"Jeux");
		menuItem.setItemMeta(m_menuItem);
		
		m_shopItem.setDisplayName(titlesTypography+"Boutique");
		shopItem.setItemMeta(m_shopItem);
		/*
		m_cosmeticsItem.setDisplayName(titlesTypography+"Cosmétiques");
		cosmeticsItem.setItemMeta(m_cosmeticsItem);
		
		m_friendsItem.setDisplayName(titlesTypography+"Amis");
		friendsItem.setItemMeta(m_friendsItem);
		*/
		pI.setItem(0,menuItem);
		pI.setItem(1,shopItem);
		//pI.setItem(4,cosmeticsItem);
		//pI.setItem(8,friendsItem);
		
		p.updateInventory();
	}
	
	@EventHandler
	public void onClickItemInventory(InventoryClickEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
	
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		try {
			if(e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK
					|| e.getAction()==Action.LEFT_CLICK_AIR || e.getAction()==Action.LEFT_CLICK_BLOCK) {
				if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(titlesTypography+"Jeux")){
					MainMenu.openGameMenu(p);
				}else if((p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(titlesTypography+"Boutique"))) {
					ShopMenu.openShopMenu(p);
				}else if((p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(titlesTypography+"Cosmétiques"))) {
					p.sendMessage(getFeatureSoonAvailableMsg());
					//CosmeticMenu.openCosmeticMenu(p);
				}else if((p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(titlesTypography+"Amis"))) {
					p.sendMessage(getFeatureSoonAvailableMsg());
					//FriendsMenu.openFriendsMenu(p);
				}
			}			
		} catch(Exception e4) {
			
		}

	}

	
}
