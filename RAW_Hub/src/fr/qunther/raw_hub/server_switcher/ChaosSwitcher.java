package fr.qunther.raw_hub.server_switcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChaosSwitcher {
	public static int getPlayersCount(String ip) {
		try {
			@SuppressWarnings("resource")
			Socket sock = new Socket(ip, 25565);
			 
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			DataInputStream in = new DataInputStream(sock.getInputStream());
			 
			out.write(0xFE);
			 
			int b;
			StringBuffer str = new StringBuffer();
			try {
				while ((b = in.read()) != -1) {
					if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
						// Not sure what use the two characters are so I omit them
						str.append((char) b);
					}
				}
			}catch(Exception e) {
				
			}
			 
			String[] data = str.toString().split("§");
			@SuppressWarnings("unused")
			String serverMotd = data[0];
			int onlinePlayers = Integer.parseInt(data[1]);
			@SuppressWarnings("unused")
			int maxPlayers = Integer.parseInt(data[2]);

			 return onlinePlayers;
			 
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		return 0;
	}
	
	public static boolean isServerAvailable() {
		return true;
	}
	
}
