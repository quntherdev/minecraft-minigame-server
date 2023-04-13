package fr.qunther.raw_hub.gui.main;

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
import fr.qunther.raw_hub.server_switcher.ServerMove;

public class ChaosMapChoose implements Listener{
	
	private Main main;
	private static final String ChooseMapMenuTEXT = ChatColor.GOLD+"Choisir carte de jeu";
	
	private static final String aquariumTEXT = ChatColor.BLUE+"Aquarium";	
	private static final String starwarsTEXT = ChatColor.GRAY+"StarWars";	

	private static final String backTEXT = ChatColor.RED+"Retour";	
	
	public ChaosMapChoose(Main main) {
		this.main=main;
	}
	
	public static void openChooseChaosMap(Player p) {
		p.closeInventory();
		
		Inventory mapMenu = Bukkit.createInventory(p, 9, ChooseMapMenuTEXT);

		ItemStack aqua = new ItemStack(Material.WOOL,1, (byte)9);
		ItemStack star = new ItemStack(Material.WOOL,1,(byte)7);

		ItemMeta aquaM = aqua.getItemMeta();
		ItemMeta starM = star.getItemMeta();
		
		aquaM.setDisplayName(aquariumTEXT);
		starM.setDisplayName(starwarsTEXT);
		
		aqua.setItemMeta(aquaM);
		star.setItemMeta(starM);
		
		mapMenu.setItem(3, aqua);
		mapMenu.setItem(5, star);		
		
		ItemStack back_door = new ItemStack(Material.SPRUCE_DOOR_ITEM, 1);
		ItemMeta back_doorM = back_door.getItemMeta();
		back_doorM.setDisplayName(backTEXT);
		back_door.setItemMeta(back_doorM);
		mapMenu.setItem(8, back_door);
		
		
		p.closeInventory();
		p.openInventory(mapMenu);
	}
	
	@EventHandler
	public void onMapChooseClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equalsIgnoreCase(ChooseMapMenuTEXT)) {			
			try {
				if(e.getCurrentItem().getItemMeta().getDisplayName()==aquariumTEXT) {
					
					// Pour chaque serveur (fichier), check un fichier de config qui enregistre true ou false en fonction de la capacité de rejoignabilité
					String server_name = ChaosLookServers.getAvailableAquarium();
					
					if(server_name==null) {
						p.sendMessage(Main.getServerMsgPrefix()+ChatColor.BLUE+" Tous les serveurs de jeu du Chaos sont démarrés, relancez une recherche régulièrement.");
					}else {
						p.sendMessage(Main.getServerMsgPrefix()+ChatColor.YELLOW+ChatColor.ITALIC+" Un serveur de jeu a été trouvé !");
						
						ServerMove sm = new ServerMove(main);
						sm.sendPlayerToServer(p, server_name);
					}
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==starwarsTEXT) {
					
					// Pour chaque serveur (fichier), check un fichier de config qui enregistre true ou false en fonction de la capacité de rejoignabilité
					String server_name = ChaosLookServers.getAvailableStarwars();
					
					if(server_name==null) {
						p.sendMessage(Main.getServerMsgPrefix()+ChatColor.BLUE+" Tous les serveurs de jeu du Chaos sont démarrés, relancez une recherche régulièrement.");
					}else {
						p.sendMessage(Main.getServerMsgPrefix()+ChatColor.YELLOW+ChatColor.ITALIC+" Un serveur de jeu a été trouvé !");
						
						ServerMove sm = new ServerMove(main);
						sm.sendPlayerToServer(p, server_name);
					}
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==backTEXT) {
					p.closeInventory();
					MainMenu.openGameMenu(p);
				}
			} catch (Exception e2) {
			}
				
		}
	}
}
