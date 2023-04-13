package fr.qunther.raw_hub.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.Overridden;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.qunther.raw_hub.Main;
import fr.qunther.raw_hub.server_switcher.ChaosSwitcher;

public class LobbyBoard extends BukkitRunnable{
		
	private Player p;
	
	public LobbyBoard(Player p) {
		this.p = p;
	}
	
	public void setScoreBoard() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        /*Objective obj = board.registerNewObjective(ChatColor.GOLD+""+ChatColor.BOLD+"\u272F"
				+ ChatColor.RED+ChatColor.BOLD + " RAW "
				+ ChatColor.GOLD+""+ChatColor.BOLD+"\u272F", "dummy");*/
        Objective obj = board.registerNewObjective(ChatColor.GOLD+""+ChatColor.BOLD+" RAW ", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        Score blank1 = obj.getScore("   ");
        blank1.setScore(11);
        
        Score name = obj.getScore(ChatColor.DARK_GRAY+"| "+
				ChatColor.GRAY+"Compte: "+ChatColor.RED+p.getDisplayName());
        name.setScore(10);
        

        
        
		//if(bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != Integer.valueOf(null)) {
			
	    if(bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
			String p_staff_rank = (String) bdd.PlayerBDD.getPlayerStaffRank(p.getUniqueId().toString()).get(0);
				
	        Team rank = board.registerNewTeam("rank");
	        rank.addEntry(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Grade: ");
	        rank.setSuffix(ChatColor.RED+p_staff_rank);
	        obj.getScore(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Grade: ").setScore(9);
			
		}else {
	        Team rank = board.registerNewTeam("rank");
	        rank.addEntry(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Grade: ");
	        rank.setSuffix(""+ChatColor.RED+bdd.PlayerBDD.getPlayerShopRank(p.getUniqueId().toString()).get(0));
	        obj.getScore(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Grade: ").setScore(9);			
		}
        
        

        
        Score blank2 = obj.getScore("  ");
        blank2.setScore(8);
        
        Team rawcoins= board.registerNewTeam("rawcoins");
        rawcoins.addEntry(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"RAWCoins: ");
        rawcoins.setSuffix(""+ChatColor.RED+bdd.PlayerBDD.getPlayerRawCoins(p.getUniqueId().toString()).get(0)+ChatColor.GOLD+" \u26C3");
        obj.getScore(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"RAWCoins: ").setScore(7);
        
        Team gemmes = board.registerNewTeam("gemmes");
        gemmes.addEntry(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Gemmes: ");
        gemmes.setSuffix(""+ChatColor.RED+bdd.PlayerBDD.getPlayerGemmes(p.getUniqueId().toString()).get(0) +ChatColor.GOLD+" \u26C0");
        obj.getScore(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Gemmes: ").setScore(6);
        
        Score blank3 = obj.getScore(" ");
        blank3.setScore(5);
        /*
        Team hub = board.registerNewTeam("hub");
        hub.addEntry(ChatColor.DARK_GRAY+"| "+
				ChatColor.GRAY+"Hub: "+ChatColor.YELLOW+p.getWorld().getName());
        obj.getScore(ChatColor.DARK_GRAY+"| "+
				ChatColor.GRAY+"Hub: "+ChatColor.YELLOW+p.getWorld().getName()).setScore(4);
        */
        
        Team inscrits = board.registerNewTeam("inscrits");
        inscrits.addEntry(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Inscrits: ");
        inscrits.setSuffix(""+ChatColor.YELLOW+bdd.PlayerBDD.getTotalPlayersInBDD().get(0));
        obj.getScore(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Inscrits: ").setScore(4);
        
        Score blank4 = obj.getScore("");
        blank4.setScore(3);
        

		// A CHANGER CAR CONNECTES QUE DE CE SERVEUR
        
        Team connectes = board.registerNewTeam("connectes");
        connectes.addEntry(ChatColor.DARK_GRAY+"\u00BB");
        try {
        	connectes.setSuffix(ChatColor.WHITE+" "+ChaosSwitcher.getPlayersCount(Main.getServerIP)+" connecté(s)");
        }catch(Exception e) {
        	connectes.setSuffix(ChatColor.WHITE+" ? connecté(s)");
        }
        obj.getScore(ChatColor.DARK_GRAY+"\u00BB").setScore(2);
        
        
        Team ip = board.registerNewTeam("ip");
        ip.addEntry(ChatColor.YELLOW+"play.raw-mc.fr");
        obj.getScore(ChatColor.YELLOW+"play.raw-mc.fr").setScore(1);
           
        Team discord = board.registerNewTeam("discord");
        discord.addEntry(ChatColor.YELLOW+"discord.gg/QaMFsrbHKW");
        obj.getScore(ChatColor.YELLOW+"discord.gg/QaMFsrbHKW").setScore(0);
        
        p.setScoreboard(board);
  }
    
    public void updateScoreBoard() {
    	Scoreboard board = p.getScoreboard();
        
    	
	    if(bdd.PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(1) != null) {
			String p_staff_rank = (String) bdd.PlayerBDD.getPlayerStaffRank(p.getUniqueId().toString()).get(0);
			board.getTeam("rank").setSuffix(ChatColor.RED+p_staff_rank);
	    }else {
			board.getTeam("rank").setSuffix(""+ChatColor.RED+bdd.PlayerBDD.getPlayerShopRank(p.getUniqueId().toString()).get(0));
	    	
	    }
        
      
        board.getTeam("rawcoins").setSuffix(""+ChatColor.RED+bdd.PlayerBDD.getPlayerRawCoins(p.getUniqueId().toString()).get(0) +ChatColor.GOLD+" \u26C3");
        board.getTeam("gemmes").setSuffix(""+ChatColor.RED+bdd.PlayerBDD.getPlayerGemmes(p.getUniqueId().toString()).get(0) +ChatColor.GOLD+" \u26C0");
        board.getTeam("inscrits").setSuffix(""+ChatColor.YELLOW+bdd.PlayerBDD.getTotalPlayersInBDD().get(0));
        try {
        	board.getTeam("connectes").setSuffix(ChatColor.WHITE+" "+ChaosSwitcher.getPlayersCount(Main.getServerIP)+" connecté(s)");
        }catch(Exception e) {
        	
        }
    }
    
	@Overridden
	public void run() {
		if(!p.isOnline()) {
			cancel();
		}else {
			try {
				updateScoreBoard();
			}catch(Exception e) {
				
			}
		}
	}
}


