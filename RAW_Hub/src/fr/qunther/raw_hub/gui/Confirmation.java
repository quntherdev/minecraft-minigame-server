package fr.qunther.raw_hub.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import fr.qunther.raw_hub.Main;
import fr.qunther.raw_hub.gui.shop.Ranks;
import fr.qunther.raw_hub.gui.shop.ShopMenu;

public class Confirmation implements Listener {
	private static final String alreadyBoughtTEXT = Main.getServerMsgPrefix()+ChatColor.YELLOW+" Vous possédez déjà ce Bonus !";

	private static final String orderConfirmationTEXT = GUIManagement.getMenuTypography()+"Confirmez-vous l'achat ?";
	private static final String backTEXT = ChatColor.RED+"Retour";	
	private static final String yesBuyTEXT = ChatColor.GREEN+""+ChatColor.BOLD+"CONFIRMER";
	private static final String noBuyTEXT = ChatColor.RED+""+ChatColor.BOLD+"NON";
	private static final String dommageNoBuyTEXT = Main.getServerMsgPrefix()+" C'est dommage, vous ne débloquez aucun avantage en ne dépensant pas vos RAWCoins !";
	
	public static String getOrderConfirmationTEXT() {
		return orderConfirmationTEXT;
	}
	
	public static String getBackTEXT() {
		return backTEXT;
	}
	
	public static String getYesBuyTEXT() {
		return yesBuyTEXT;
	}
	
	public static String getNoBuyText() {
		return noBuyTEXT;
	}
	
	public static String getDommageNoBuyTEXT() {
		return dommageNoBuyTEXT;
	}
	
	public static void buyConfirmationGUI(Player p) {
		Inventory confirmMenu = Bukkit.createInventory(p, 27, getOrderConfirmationTEXT());
		
		ItemStack green_clay = new ItemStack(Material.STAINED_CLAY, 1, (byte)5);
		ItemMeta green_clayM = green_clay.getItemMeta();
		green_clayM.setDisplayName(getYesBuyTEXT());
		green_clay.setItemMeta(green_clayM);
		confirmMenu.setItem(11, green_clay);
		
		ItemStack red_clay = new ItemStack(Material.STAINED_CLAY, 1, (byte)14);
		ItemMeta red_clayM = green_clay.getItemMeta();
		red_clayM.setDisplayName(getNoBuyText());
		red_clay.setItemMeta(red_clayM);
		confirmMenu.setItem(15, red_clay);
		
		ItemStack back_door = new ItemStack(Material.SPRUCE_DOOR_ITEM, 1);
		ItemMeta back_doorM = back_door.getItemMeta();
		back_doorM.setDisplayName(getBackTEXT());
		back_door.setItemMeta(back_doorM);
		confirmMenu.setItem(26, back_door);
		
		p.closeInventory();
		p.openInventory(confirmMenu);
	}
	
	
	@EventHandler
	public void onConfirmationClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equalsIgnoreCase(Confirmation.getOrderConfirmationTEXT())) {			
			try {
				if(e.getCurrentItem().getItemMeta().getDisplayName()==Confirmation.getYesBuyTEXT()) {
					p.closeInventory();
					String product_type = (String) bdd.PlayerBDD.getLastPlayerPanier(p.getUniqueId().toString()).get(2);
					int product_id = (int) bdd.PlayerBDD.getLastPlayerPanier(p.getUniqueId().toString()).get(3);	

					
					if(product_type.equalsIgnoreCase("rank")) {
						if(checkIfPlayerCanBuyRank(p, product_id)) {
							if(bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
								p.sendMessage(Main.getServerMsgPrefix()+ChatColor.RED+ " En tant que membre du staff, vous ne pouvez pas acheter de grade!");
							}else if((int)bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2)==3 && product_id==2) {
								p.sendMessage(Main.getServerMsgPrefix()+ChatColor.RED+ " Vous êtes SURHOMME, vous ne pouvez pas rétrograder en VIP !");
							}else if((int)bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2)==3 && product_id==3) {
								p.sendMessage(Main.getServerMsgPrefix()+ChatColor.RED+ " Vous êtes déjà SURHOMME !");
							}else if((int)bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2)==2 && product_id==2) {
								p.sendMessage(Main.getServerMsgPrefix()+ChatColor.RED+ " Vous êtes déjà VIP !");
							}else {
								bdd.PlayerBDD.insertPlayerOrderShopRank(p.getUniqueId().toString(), product_id);
								bdd.PlayerBDD.addOrRemovePlayerRawCoins(p.getUniqueId().toString(), -(int)bdd.PlayerBDD.getRankRawCoins(product_id).get(0));	
								
								p.sendMessage(Main.getServerMsgPrefix()+ChatColor.GOLD+ChatColor.ITALIC+" Félicitations ! Vous êtes "+bdd.PlayerBDD.getPlayerShopRank(p.getUniqueId().toString()).get(0)+", désormais !");
								
								Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);
						        FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
						        data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.GREEN).with(Type.BALL_LARGE).withFlicker().build());
						        data.setPower(1);
							}
						}else {
							p.sendMessage(ShopMenu.getBoutiqueMessage());
						}
						
						
					}else if(product_type.equalsIgnoreCase("chaos_bonus")){
						if(checkIfPlayerCanBuyChaosBonus(p, product_id)) {
							if(bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
								p.sendMessage(Main.getServerMsgPrefix()+ChatColor.RED+ " En tant que membre du staff, vous ne pouvez pas acheter de grade!");
								
							}else if(bdd.PlayerBDD.playerBoughtBonus(p.getUniqueId().toString(), product_id)) {
								p.sendMessage(alreadyBoughtTEXT);
								
							}else {
								bdd.PlayerBDD.insertPlayerOrderChaosBonus(p.getUniqueId().toString(), product_id);
								
								if(product_id==1 || product_id==2) {
									Long gemmes_bonus = -(Long) bdd.PlayerBDD.getChaosBonusGemmes(product_id).get(0);
									bdd.PlayerBDD.addOrRemovePlayerGemmes(p.getUniqueId().toString(),gemmes_bonus.intValue());	
								}else if(product_id==5) {
									bdd.PlayerBDD.addOrRemovePlayerRawCoins(p.getUniqueId().toString(), -(int)bdd.PlayerBDD.getChaosBonusRawCoins(product_id).get(0));	
								}
								
								p.sendMessage(Main.getServerMsgPrefix()+ChatColor.GOLD+ChatColor.ITALIC+" Félicitations ! Vous pouvez maintenant jouer le bonus "+
												ChatColor.BLUE+ChatColor.BOLD+bdd.PlayerBDD.getGameBonusInfos(product_id).get(4)+ChatColor.GOLD+ChatColor.ITALIC+" !");
								
								Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);
						        FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
						        data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.GREEN).with(Type.BALL_LARGE).withFlicker().build());
						        data.setPower(1);
							}
						}else {
							p.sendMessage(ShopMenu.getBoutiqueMessage());
						}
						
					}else if(product_type.equalsIgnoreCase("gemmes")){
						
						if(checkIfPlayerCanBuyGemmes(p, product_id)) {
							bdd.PlayerBDD.insertPlayerOrderGemmes(p.getUniqueId().toString(), product_id);
							
							int equivalent_rawcoins = (int) bdd.PlayerBDD.getGemmesInfos(product_id).get(1);
							bdd.PlayerBDD.addOrRemovePlayerRawCoins(p.getUniqueId().toString(), -equivalent_rawcoins);

							bdd.PlayerBDD.addOrRemovePlayerGemmes(p.getUniqueId().toString(), product_id);	

							p.sendMessage(Main.getServerMsgPrefix()+ChatColor.GOLD+ChatColor.ITALIC+" Félicitations ! Vous recevez "+ChatColor.GREEN+product_id+ChatColor.GOLD+" Gemmes !");								
							Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);
						    FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
						    data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.GREEN).with(Type.BALL_LARGE).withFlicker().build());
						    data.setPower(1);
						}else {
							p.sendMessage(ShopMenu.getBoutiqueMessage());
						}
						
					}else if(product_type.equalsIgnoreCase("cosmetics")){
						
					}
					
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==Confirmation.getNoBuyText()) {
					p.sendMessage(getDommageNoBuyTEXT());
					p.closeInventory();
				}else if(e.getCurrentItem().getItemMeta().getDisplayName()==Confirmation.getBackTEXT()) {
					Ranks.openRankMenu(p);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
				
		}
	}
	
	public boolean checkIfPlayerCanBuyRank(Player p, int rank_id) {
		if((long)bdd.PlayerBDD.getPlayerRawCoins(p.getUniqueId().toString()).get(0) >= (int) bdd.PlayerBDD.getRankRawCoins(rank_id).get(0)) {
			return true;
		}
		return false;
	}
	
	public boolean checkIfPlayerCanBuyChaosBonus(Player p, int bonus_id) {
		if(bonus_id==1 || bonus_id==2) {
			if((long)bdd.PlayerBDD.getPlayerGemmes(p.getUniqueId().toString()).get(0) >= (long) bdd.PlayerBDD.getChaosBonusGemmes(bonus_id).get(0)) {
				return true;
			}
			
		}else if(bonus_id==5) {
			if((long)bdd.PlayerBDD.getPlayerRawCoins(p.getUniqueId().toString()).get(0) >= (int) bdd.PlayerBDD.getChaosBonusRawCoins(bonus_id).get(0)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkIfPlayerCanBuyGemmes(Player p, int gemmes) {		
		if((long)bdd.PlayerBDD.getPlayerRawCoins(p.getUniqueId().toString()).get(0) >= (int) bdd.PlayerBDD.getGemmesInfos(gemmes).get(1)) {
			return true;
		}
		return false;
	}
	
	public boolean checkIfPlayerCanBuyCosmetic(Player p, int cosmetic_id) {
		if((long)bdd.PlayerBDD.getPlayerRawCoins(p.getUniqueId().toString()).get(0) >= (int) bdd.PlayerBDD.getRankRawCoins(cosmetic_id).get(0)) {
			return true;
		}
		return false;
	}
	
}
