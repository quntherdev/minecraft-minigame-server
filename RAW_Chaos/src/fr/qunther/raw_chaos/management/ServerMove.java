package fr.qunther.raw_chaos.management;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.qunther.raw_chaos.Chaos;

public class ServerMove {
	Chaos main;
	
	public ServerMove(Chaos main) {
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
