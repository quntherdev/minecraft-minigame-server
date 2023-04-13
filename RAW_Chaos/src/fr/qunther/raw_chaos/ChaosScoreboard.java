package fr.qunther.raw_chaos;

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

import fr.qunther.raw_chaos.management.State;


public class ChaosScoreboard extends BukkitRunnable{
	private Chaos main;
	private PVPPhase pvp_timer;
	private Player p;
	
	public ChaosScoreboard(Chaos main, PVPPhase pvp_timer, Player p) {
		this.p = p;
		this.pvp_timer = pvp_timer;
		this.main = main;
	}
	
	public String pvp_timer_format() {
		if(pvp_timer.timer%60==0) {
			return pvp_timer.timer/60+"m";
		}else {
			return pvp_timer.timer/60+"m"+pvp_timer.timer%60+"s";
		}
	}
	
	
	public void setScoreBoard() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective(ChatColor.GOLD+""+ChatColor.BOLD+" CHAOS ", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        Score blank1 = obj.getScore("   ");
        blank1.setScore(5);
        
	    Team time_before_pvp = board.registerNewTeam("pvp");
	    time_before_pvp.addEntry(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Combat dans: ");
	    time_before_pvp.setSuffix(ChatColor.RED+""+pvp_timer_format());
	    obj.getScore(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"Combat dans: ").setScore(4);
        
	    Team alive = board.registerNewTeam("alive");
	    alive.addEntry(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"En vie: ");
	    alive.setSuffix(ChatColor.GREEN+""+main.getPlayers().size());
	    obj.getScore(ChatColor.DARK_GRAY+"| "+ChatColor.GRAY+"En vie: ").setScore(3);
	    
	    Score blank2 = obj.getScore("  ");
	    blank2.setScore(2);
	    
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
    	
    	if(main.isState(State.FARMING)) {
    		board.getTeam("pvp").setSuffix(ChatColor.RED+""+pvp_timer_format());
            board.getTeam("alive").setSuffix(ChatColor.GREEN+""+main.getPlayers().size());		
    	}else if(main.isState(State.PVP)) {
    		board.getTeam("pvp").setSuffix(ChatColor.DARK_GREEN+"Maintenant !");
            board.getTeam("alive").setSuffix(ChatColor.GREEN+""+main.getPlayers().size());
    	}

    }
    
	@Overridden
	public void run() {
		if(main.isState(State.FARMING) || main.isState(State.PVP) || main.isState(State.FINISHING)) {
			if(!p.isOnline()) {
				cancel();
			}else {
				try {
					updateScoreBoard();
				}catch(Exception e) {
					
				}
			}
		}else {
			cancel();
		}
	}
}
