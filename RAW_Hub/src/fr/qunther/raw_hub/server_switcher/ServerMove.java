package fr.qunther.raw_hub.server_switcher;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.qunther.raw_hub.Main;

public class ServerMove {
	Main main;
	
	public ServerMove(Main main) {
		this.main=main;
	}
	
    public void sendPlayerToServer(Player p, String server) {
        try {
        	  ByteArrayDataOutput out = ByteStreams.newDataOutput();
        	  out.writeUTF("Connect");
        	  out.writeUTF(server);

        	  p.sendPluginMessage(main, "BungeeCord", out.toByteArray());
        }catch(Exception e) {
        	
        }
    }
}
