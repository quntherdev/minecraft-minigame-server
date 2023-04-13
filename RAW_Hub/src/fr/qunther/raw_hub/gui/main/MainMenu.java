package fr.qunther.raw_hub.gui.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import fr.qunther.raw_hub.server_switcher.ServerMove;
import net.md_5.bungee.api.ChatColor;

public class MainMenu implements Listener{
	
	private Main main;
	
	private static final String gameMenuTEXT = GUIManagement.getMenuTypography()+"Jeux";
	//private static final String featherTEXT = ChatColor.GOLD+"Survivalisme"+ChatColor.RED+ChatColor.BOLD+" [A VENIR]";
	private static final String chaosTEXT = ChatColor.GOLD+"Chaos"+ChatColor.RED+ChatColor.BOLD+" [VEDETTE]";
	private static final String sumoTEXT = ChatColor.GOLD+"Clutch Sumo"+ChatColor.RED+ChatColor.BOLD+" [BUGS A REGLER]";
	private static final String gamesTEXT = ChatColor.GOLD+"Jeux du HUB - "+ChatColor.GOLD+ChatColor.BOLD+"ZONE VIP";
	private static final String lobbyTEXT = ChatColor.GOLD+"Lobby";
	private static final String closeTEXT = ChatColor.RED+"Fermer menu";
	
	private static final String clutchSumo_SOON_TEXT = Main.getServerMsgPrefix()+ " Le "+ChatColor.GOLD+"Clutch Sumo"+ChatColor.RESET+" est en développement en ce moment"
			+" même ! :) Patientez quelques temps avant de pouvoir profiter de son ouverture. Pour rester informés, rejoignez le discord: /discord";

	public MainMenu(Main main) {
		this.main=main;
	}
	
	public static void openGameMenu(Player p){
		Inventory gameMenuInv = Bukkit.createInventory(p, 9, gameMenuTEXT);
		
		//ItemStack feather = new ItemStack(Material.FEATHER, 1);
		ItemStack fireball = new ItemStack(Material.FIREBALL, 1);
		ItemStack sumo = new ItemStack(Material.SLIME_BLOCK, 1);
		ItemStack gold_nugget = new ItemStack(Material.GOLD_NUGGET,1);
		//ItemStack clay = new ItemStack(Material.INK_SACK, 1, (byte)8);
		ItemStack close_door = new ItemStack(Material.DARK_OAK_DOOR_ITEM, 1);
	

		//ItemMeta featherM = feather.getItemMeta();
		ItemMeta fireballM = fireball.getItemMeta();
		ItemMeta sumoM = sumo.getItemMeta();
		ItemMeta gold_nuggetM = gold_nugget.getItemMeta();
		//ItemMeta clayM = clay.getItemMeta();
		ItemMeta close_doorM = close_door.getItemMeta();

		//featherM.setDisplayName(featherTEXT);
		fireballM.setDisplayName(chaosTEXT);
		sumoM.setDisplayName(sumoTEXT);
		gold_nuggetM.setDisplayName(gamesTEXT);
		//clayM.setDisplayName(lobbyTEXT);
		close_doorM.setDisplayName(closeTEXT);
		
		
		ArrayList<String> fireball_lore = new ArrayList<String>();
		fireball_lore.add("");
		fireball_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Description du jeu");
		fireball_lore.add("");
		fireball_lore.add(ChatColor.GREEN+"Profitez du précieux temps de 5 minutes");
		fireball_lore.add(ChatColor.GREEN+"pour fabriquer votre meilleure armature");
		fireball_lore.add(ChatColor.GREEN+"et vos armes de combats en vous aidant");
		fireball_lore.add(ChatColor.GREEN+"des minerais à votre disposition au sol.");
		fireball_lore.add(ChatColor.GREEN+"Une fois ce temps passé, un nuage toxique");
		fireball_lore.add(ChatColor.GREEN+"apparaît à vos pieds et s'élève d'un bloc");
		fireball_lore.add(ChatColor.GREEN+"toutes les 30 secondes ! Perchez-vous");
		fireball_lore.add(ChatColor.GREEN+"du haut d'une tour, réglez leurs comptes");
		fireball_lore.add(ChatColor.GREEN+"à vos adversaires et faites les tomber");
		fireball_lore.add(ChatColor.GREEN+"avant d'être annihilé par le gaz.");

		fireball_lore.add("");
		fireballM.setLore(fireball_lore);
		
		
		
		ArrayList<String> sumo_lore = new ArrayList<String>();
		sumo_lore.add("");
		sumo_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Description du jeu");
		sumo_lore.add("");
		sumo_lore.add(ChatColor.GREEN+"Vous connaissiez le jeu Sumo, vous");
		sumo_lore.add(ChatColor.GREEN+"adorerez forcément le Clutch Sumo !");
		sumo_lore.add(ChatColor.GREEN+"Vous avez 3 vies, à chaque fois que");
		sumo_lore.add(ChatColor.GREEN+"vous tombez, vous devez MLG avec un");
		sumo_lore.add(ChatColor.GREEN+"seau d'eau. Vous réussissez :");
		sumo_lore.add(ChatColor.GREEN+"Félicitation, vous êtes re-tp sur la");
		sumo_lore.add(ChatColor.GREEN+"plateforme. Vous ratez... Aie : vous");
		sumo_lore.add(ChatColor.GREEN+"perdez une vie et êtes re-tp sur la");
		sumo_lore.add(ChatColor.GREEN+"plateforme. Que le meilleur gagne !");
		sumo_lore.add("");
		sumoM.setLore(sumo_lore);
		
		
		
		ArrayList<String> games_lore = new ArrayList<String>();
		games_lore.add("");
		games_lore.add(ChatColor.YELLOW+""+ChatColor.BOLD+"\u2022 Jeux disponibles");
		games_lore.add("");
		games_lore.add(ChatColor.GREEN+"- Arène de combat FFA");
		games_lore.add(ChatColor.GREEN+"- Arène bataille de boules de neiges"+ChatColor.RED+ChatColor.BOLD+" [A VENIR]");
		games_lore.add(ChatColor.GREEN+"- MLG"+ChatColor.RED+ChatColor.BOLD+" [A VENIR]");
		games_lore.add("");
		gold_nuggetM.setLore(games_lore);

		
		//feather.setItemMeta(featherM);
		fireball.setItemMeta(fireballM);
		sumo.setItemMeta(sumoM);
		gold_nugget.setItemMeta(gold_nuggetM);
		//clay.setItemMeta(clayM);
		close_door.setItemMeta(close_doorM);
		
		//gameMenuInv.setItem(3,feather);
		gameMenuInv.setItem(3, sumo);
		gameMenuInv.setItem(4,fireball);
		gameMenuInv.setItem(5, gold_nugget);
		//gameMenuInv.setItem(7,clay);
		gameMenuInv.setItem(8, close_door);
		
		p.closeInventory();
		p.openInventory(gameMenuInv);
	}
	
	@EventHandler
	public void onChaosClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equalsIgnoreCase(gameMenuTEXT)) {			
			try {
				if(e.getCurrentItem().getItemMeta().getDisplayName()==sumoTEXT) {
					String server_name = "clutch_sumo_1";

					ServerMove sm = new ServerMove(main);
					sm.sendPlayerToServer(p, server_name);
					//p.sendMessage(clutchSumo_SOON_TEXT);
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==chaosTEXT) {

					/*
					
					// Pour chaque serveur (fichier), check un fichier de config qui enregistre true ou false en fonction de la capacité de rejoignabilité
					String server_name = ChaosLookServers.getAvailableServerName();
					
					if(server_name==null) {
						p.sendMessage(Main.getServerMsgPrefix()+ChatColor.BLUE+" Tous les serveurs de jeu du Chaos sont démarrés, relancez une recherche régulièrement.");
					}else {
						p.sendMessage(Main.getServerMsgPrefix()+ChatColor.YELLOW+ChatColor.ITALIC+" Un serveur de jeu a été trouvé !");
						
						ServerMove sm = new ServerMove(main);
						sm.sendPlayerToServer(p, server_name);
					}
					
					*/
					ChaosMapChoose.openChooseChaosMap(p);
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==lobbyTEXT) {
					p.sendMessage("lobby");
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==gamesTEXT) {
					
					p.closeInventory();
					Location vip_loc = new Location(Bukkit.getWorld(Main.world),0.228,103,-185.285);
					vip_loc.setYaw(-178.9f);
					vip_loc.setPitch(0.1f);
					
					p.teleport(vip_loc);
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==closeTEXT) {
					p.closeInventory();
				}
			} catch (Exception e2) {
			}
				
		}
	}
}
