package fr.qunther.raw_basics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.qunther.raw_basics.moderation.Banning;
import fr.qunther.raw_basics.moderation.Muting;
import fr.qunther.raw_basics.moderation.SpeakMute;
import fr.qunther.raw_basics.moderation.Unbanning;
import fr.qunther.raw_basics.moderation.Unmute;
import fr.qunther.raw_basics.others.PlayerRankProviding;
import fr.qunther.raw_basics.player_interactions.AdminMode;
import fr.qunther.raw_basics.player_interactions.PlayerCommands;
import fr.qunther.raw_basics.player_interactions.Speak;
import fr.qunther.raw_basics.player_interactions.UnknownCommand;
import fr.qunther.raw_basics.world.AlwaysDayTask;
import fr.qunther.raw_basics.world.GoodWeather;

public class Main extends JavaPlugin implements CommandExecutor{
	public static String serverMsgPrefix=ChatColor.GOLD+""+ChatColor.BOLD+"RAW »"+ChatColor.RESET;

	public void onEnable() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
        
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Speak(), this);
		pm.registerEvents(new SpeakMute(),this);
		pm.registerEvents(new GoodWeather(),this);
		pm.registerEvents(new AdminMode(), this);
		pm.registerEvents(new UnknownCommand(),this);

        this.getCommand("discord").setExecutor(new PlayerCommands(this));
        this.getCommand("boutique").setExecutor(new PlayerCommands(this));
        this.getCommand("site").setExecutor(new PlayerCommands(this));
        this.getCommand("classement_chaos").setExecutor(new PlayerCommands(this));
        this.getCommand("admin").setExecutor(new PlayerCommands(this));
        
        this.getCommand("addcoins").setExecutor(new PlayerRankProviding());
        
        
        this.getCommand("rban").setExecutor(new Banning());
        this.getCommand("runban").setExecutor(new Unbanning());
        
        this.getCommand("rmute").setExecutor(new Muting());
        this.getCommand("runmute").setExecutor(new Unmute());

        
        AlwaysDayTask keep_day = new AlwaysDayTask();
        keep_day.runTaskTimer(this, 0, 20);
	}
	
}
