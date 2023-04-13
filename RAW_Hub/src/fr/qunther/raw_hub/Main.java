package fr.qunther.raw_hub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.Overridden;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_hub.commands.HubCommands;
import fr.qunther.raw_hub.gui.Confirmation;
import fr.qunther.raw_hub.gui.GUIManagement;
import fr.qunther.raw_hub.gui.friends.FriendsMenu;
import fr.qunther.raw_hub.gui.main.ChaosMapChoose;
import fr.qunther.raw_hub.gui.main.MainMenu;
import fr.qunther.raw_hub.gui.shop.ChaosBonusGUI;
import fr.qunther.raw_hub.gui.shop.CosmeticMenu;
import fr.qunther.raw_hub.gui.shop.Gemmes;
import fr.qunther.raw_hub.gui.shop.Particles;
import fr.qunther.raw_hub.gui.shop.Pets;
import fr.qunther.raw_hub.gui.shop.Ranks;
import fr.qunther.raw_hub.gui.shop.ShopMenu;
import fr.qunther.raw_hub.hub_games.VIPAreaAccess;
import fr.qunther.raw_hub.hub_games.pvp_zone.PvpFightGestion;
import fr.qunther.raw_hub.join.JoinLeaveManagement;
import fr.qunther.raw_hub.restrictions.HubRestrictionsListener;
import fr.qunther.raw_hub.scoreboard.Hologram;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;

public class Main extends JavaPlugin{
	private Hologram hologram;
	
	public static Main plugin;
	public static String getServerIP = "49.12.110.143";
	
	private static String serverMsgPrefix=ChatColor.GOLD+""+ChatColor.BOLD+"RAW »"+ChatColor.RESET;
	private static String simpleServerMsgPrefix=ChatColor.GOLD+""+ChatColor.BOLD+"[RAW]"+ChatColor.RESET;	
	
	public static String world = "world";
	
	public static Main getPlugin() {
		return plugin;
	}
	
	public static String getServerMsgPrefix() {
		return serverMsgPrefix;
	}
	
	public static String getSimpleServerMsgPrefix() {
		return simpleServerMsgPrefix;
	}
	
	
	@Overridden
	public void onEnable() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepinventory true");

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord"); // Pour tp un joueur sur un autre serveur
		
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListener());
		
		getServer().getPluginManager().registerEvents(new HubRestrictionsListener(), this);
		getServer().getPluginManager().registerEvents(new JoinLeaveManagement(this), this);

		
		getServer().getPluginManager().registerEvents(new GUIManagement(), this);

		getServer().getPluginManager().registerEvents(new MainMenu(this), this);
		getServer().getPluginManager().registerEvents(new ChaosMapChoose(this), this);
		
		
		getServer().getPluginManager().registerEvents(new ShopMenu(), this);
		getServer().getPluginManager().registerEvents(new Ranks(), this);
		getServer().getPluginManager().registerEvents(new Gemmes(), this);
		
		getServer().getPluginManager().registerEvents(new ChaosBonusGUI(), this);
		getServer().getPluginManager().registerEvents(new Pets(), this);
		getServer().getPluginManager().registerEvents(new Particles(), this);
		getServer().getPluginManager().registerEvents(new Confirmation(), this);
		getServer().getPluginManager().registerEvents(new PvpFightGestion(this), this);

		getServer().getPluginManager().registerEvents(new CosmeticMenu(), this);
		getServer().getPluginManager().registerEvents(new FriendsMenu(), this);
		
        this.getCommand("spawn").setExecutor(new HubCommands());
		
		
		hologram = new Hologram(this);
		hologram.spawnHologram();
		
		VIPAreaAccess checkVIPArea = new VIPAreaAccess(this);
		checkVIPArea.runTaskTimer(this, 0, 20*2);

		PvpFightGestion pvp = new PvpFightGestion(this);
		pvp.runCheckTimer();
		
		
		/*
		 * Si un gars tombe dans le vide dans le HUB, il est re-tp au spawn
		 * 
		 */
		Location spawn = new Location(Bukkit.getWorld(Main.world), 0.5, 90, 0.5);
		spawn.setYaw(180.0f);
		spawn.setPitch(-5.0f);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getLocation().getY()<=59) {
						p.teleport(spawn);
					}
				}
			}
					
		}.runTaskTimer(this, 0, 20*3);
		
	}
	
	public void instantdeath(Player p) {
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
                ((CraftPlayer) p).getHandle().playerConnection.a(packet);
                
            }
            
        }, 1L);
    }
	
	
	@Override
	public void onDisable() {
		hologram.removeHologram();
	}

}
