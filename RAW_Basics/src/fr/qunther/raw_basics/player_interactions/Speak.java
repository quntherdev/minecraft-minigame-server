package fr.qunther.raw_basics.player_interactions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import fr.qunther.raw_basics.bdd.PlayerBDD;

@SuppressWarnings("deprecation")
public class Speak implements Listener{

	@EventHandler(priority=EventPriority.NORMAL)
	public void onSpeak(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if(PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
			int id_staff_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1);
			String p_staff_rank = (String) PlayerBDD.getPlayerStaffRank(p.getUniqueId().toString()).get(0);

			switch(id_staff_rank) {
			case 1:
				e.setFormat(ChatColor.DARK_GREEN+""+ChatColor.BOLD+p_staff_rank+" "+ChatColor.RESET+ChatColor.DARK_GREEN+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage());							
				break;
			case 2:
				e.setFormat(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+p_staff_rank+" "+ChatColor.RESET+ChatColor.LIGHT_PURPLE+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage());							
				break;
			case 3:
				e.setFormat(ChatColor.RED+""+ChatColor.BOLD+p_staff_rank+" "+ChatColor.RESET+ChatColor.RED+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage());							
				break;
			case 4:
				e.setFormat(ChatColor.BLUE+""+ChatColor.BOLD+p_staff_rank+" "+ChatColor.RESET+ChatColor.BLUE+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage());							
				break;
			case 5:
				e.setFormat(ChatColor.RED+""+ChatColor.BOLD+p_staff_rank+" "+ChatColor.RESET+ChatColor.RED+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage());							
				break;
			}
		}else {
			int id_shop_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2);
			String p_shop_rank = (String) PlayerBDD.getPlayerShopRank(p.getUniqueId().toString()).get(0);
	
			switch(id_shop_rank) {
			case 1:
				e.setFormat(ChatColor.GRAY+p.getDisplayName()+": "+e.getMessage());							
				break;
			case 2:
				e.setFormat((ChatColor.BLUE+""+ChatColor.BOLD+p_shop_rank+" "+ChatColor.RESET+ChatColor.BLUE+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage()));							
				break;
			case 3:
				e.setFormat((ChatColor.GREEN+""+ChatColor.BOLD+p_shop_rank+" "+ChatColor.RESET+ChatColor.GREEN+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage()));							
				break;				
			case 4:
				e.setFormat((ChatColor.DARK_PURPLE+""+ChatColor.BOLD+p_shop_rank+" "+ChatColor.RESET+ChatColor.DARK_PURPLE+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage()));							
				break;	
			case 5:
				e.setFormat((ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+p_shop_rank+" "+ChatColor.RESET+ChatColor.LIGHT_PURPLE+p.getDisplayName()+": "+ChatColor.WHITE+e.getMessage()));							
				break;	
			}
		}
	}
}
