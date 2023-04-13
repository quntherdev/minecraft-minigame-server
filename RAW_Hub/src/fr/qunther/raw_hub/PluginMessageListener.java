package fr.qunther.raw_hub;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] msg) {
		if(!channel.equalsIgnoreCase("BungeeCord")) {
			return;
		}
		
		final ByteArrayDataInput in = ByteStreams.newDataInput(msg);
		final String subChannel = in.readUTF();
		
		switch(subChannel) {
		case "Connect":
			//TO DO
			break;
		default:
			p.sendMessage("pm n'existe pas");
			break;
		}
	}

}
