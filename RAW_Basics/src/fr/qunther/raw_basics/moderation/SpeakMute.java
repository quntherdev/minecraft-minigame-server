package fr.qunther.raw_basics.moderation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import fr.qunther.raw_basics.bdd.PlayerBDD;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class SpeakMute implements Listener{

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onSpeak(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if(PlayerBDD.isPlayerPermanentMuted(p.getUniqueId().toString())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.GRAY+""+ChatColor.ITALIC+"Vous êtes réduit au silence à vie. "
					+ "Votre comportement textuel a été considéré comme inacceptable par un membre du staff. ");
			p.sendMessage(ChatColor.GRAY+""+ChatColor.ITALIC+"Si vous jugez le mute injustifié, veuillez vous rendre sur le discord.");
			return;
		}
		
		if(PlayerBDD.isPlayerTemporaryMuted(p.getUniqueId().toString())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.GRAY+""+ChatColor.ITALIC+"Vous êtes réduit au silence pendant encore " +PlayerBDD.getPlayerMuteRemainingHeures(p.getUniqueId().toString())
						+ " heure(s). Veillez à mieux vous comporter une fois que vous serez unmute.");
			p.sendMessage(ChatColor.GRAY+""+ChatColor.ITALIC+"Si vous jugez le mute injustifié, veuillez vous rendre sur le discord.");

		}
	}
	
}
