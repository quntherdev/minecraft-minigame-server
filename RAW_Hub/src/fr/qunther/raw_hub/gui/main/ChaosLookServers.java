package fr.qunther.raw_hub.gui.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChaosLookServers {

	@SuppressWarnings("resource")
	public static String getAvailableServerName() throws FileNotFoundException {

		for(int i=1; i<5; i++) {
	    	Scanner input_aqua = new Scanner(new File("/home/chaos_aquarium_"+i+"/plugins/RAW_Chaos/config.yml"));
			Scanner input_end = new Scanner(new File("/home/chaos_end_"+i+"/plugins/RAW_Chaos/config.yml"));
			
		    while (input_aqua.hasNext()) {
		    	String word  = input_aqua.next();
		    	if(word.equalsIgnoreCase("available")) {

			    	if(isServerActive("chaos_aquarium_"+i)) return "chaos_aquarium_"+i;
			    	
		    	}
		    }
		    
		    while (input_end.hasNext()) {
		    	String word  = input_end.next();
			    if(word.equalsIgnoreCase("available")) {
			    	
			    	if(isServerActive("chaos_starwars_"+i)) return "chaos_starwars_"+i;
			    	
			    }
			}

		}

		return null;
	}
	
	
	@SuppressWarnings("resource")
	public static String getAvailableStarwars() throws FileNotFoundException {

		for(int i=1; i<5; i++) {
			Scanner input_end = new Scanner(new File("/home/chaos_end_"+i+"/plugins/RAW_Chaos/config.yml"));
		    
		    while (input_end.hasNext()) {
		    	String word  = input_end.next();
			    if(word.equalsIgnoreCase("available")) {
			    	
			    	if(isServerActive("chaos_starwars_"+i)) return "chaos_starwars_"+i;
			    }
			}
		}
		return null;
	}
	
	
	@SuppressWarnings("resource")
	public static String getAvailableAquarium() throws FileNotFoundException {

		for(int i=1; i<5; i++) {
	    	Scanner input_aqua = new Scanner(new File("/home/chaos_aquarium_"+i+"/plugins/RAW_Chaos/config.yml"));
			
		    while (input_aqua.hasNext()) {
		    	String word  = input_aqua.next();
		    	if(word.equalsIgnoreCase("available")) {

			    	if(isServerActive("chaos_aquarium_"+i)) return "chaos_aquarium_"+i; 	
		    	}
		    }
		}
		return null;
	}
	
	
	
	
	public static boolean isServerActive(String server_name) {
		int port = 0;
		
		switch(server_name) {
			case "chaos_aquarium_1":
				port=25569;
				break;
			case "chaos_aquarium_2":
				port=25570;
				break;
			case "chaos_aquarium_3":
				port=25571;
				break;
			case "chaos_aquarium_4":
				port=25572;
				break;
				
			case "chaos_starwars_1":
				port=25567;
				break;
			case "chaos_starwars_2":
				port=25568;
				break;
			case "chaos_starwars_3":
				port=25573;
				break;
			case "chaos_starwars_4":
				port=25574;
				break;
		}
		
	    try {
	    	Socket s = new Socket();
	    	s.connect(new InetSocketAddress("49.12.110.143", port), 20); //good timeout is 10-20
	    	s.close();
	    	
	    	return true;
	     } catch (UnknownHostException e) {
	    	 return false;
	     } catch (IOException e) {
	    	 return false;
	     }
	}
}
