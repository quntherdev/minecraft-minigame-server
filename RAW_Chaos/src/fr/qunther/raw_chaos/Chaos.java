package fr.qunther.raw_chaos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import fr.qunther.raw_chaos.listeners.DamageListener;
import fr.qunther.raw_chaos.listeners.PlayerListener;
import fr.qunther.raw_chaos.management.AdminCommands;
import fr.qunther.raw_chaos.management.AlterFileState;
import fr.qunther.raw_chaos.management.Hologram;
import fr.qunther.raw_chaos.management.ServerMove;
import fr.qunther.raw_chaos.management.ServerReloader;
import fr.qunther.raw_chaos.management.State;
import fr.qunther.raw_chaos.tasks.CloudChangerTask;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;

public class Chaos extends JavaPlugin{
	//public static String world_name = "chaos_starwars";
	public static String world_name = "chaos_aquarium";

	public static Long matchID;
	public static final String serverMsgPrefix=ChatColor.GOLD+""+ChatColor.BOLD+"RAW »"+ChatColor.RESET;
	
	public Hologram hologram;
	private Player gagnant;
	private State state;
	private ArrayList<Player> players = new ArrayList<>();

	//public boolean acid_bonus_cooldown=false;
	//public int acid_cooldown_timer=10;

	
	
	/*
	 * POUR CHANGER LES SETTINGS PRINCIPAUX :
	 * 
	 * attribut "world_name" dans Chaos : nom de la map
	 * attribut "timer" dans PVPPhase : temps en secondes avant activation pvp, à partir de la phase farm
	 * attribut "time_before_end" dans PVPPhase : temps en secondes avant fin de la partie, à partir de la phase pvp
	 * Ligne < GameAutoStart start = new GameAutoStart(main,45); > dans PlayerListener : changer 45 par valeur qui correspond au temps en seconde
	 *                                                                                   du timer qui se met en route quand au moins 2 joueurs ont rejoin la partie
	 *                                                                                   
	 * Les 2 derniers "runTaskTimer" dans CloudChangerTask : temps avant élévation nuages
	 * 
	 */
	
	
	
	public void onEnable() {
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord"); // Pour tp un joueur sur le serveur du hub

		getConfig().options().copyDefaults();
		saveDefaultConfig();

		ServerReloader.killAllEntities();
		ServerReloader.eraseMap();
        
        hologram = new Hologram();
        hologram.spawnHologram();
        
		Plateform.setAll();
		this.state=State.WAITING;
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new DamageListener(this), this);
		pm.registerEvents(new Bonus(this), this);
		pm.registerEvents(new BonusActivation(this), this);
		
        this.getCommand("force_erase").setExecutor(new AdminCommands());
		
		CloudChangerTask sup = new CloudChangerTask(this, 65, 1);
		sup.runTaskTimer(this, 0, 15);
		
		/*
		 *  EDIT SERVER STATE
		 */
		AlterFileState.setAVAILABLE(this);
	}
	
	
	public void setState(State state) {
		this.state=state;
	}

	public State getState() {
		return this.state;
	}
	
	public boolean isState(State state) {
		return this.state==state;
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void eliminatePlayer(Player p) {
		
		if(isState(State.WAITING)) {
			//REMOVE FROM WAITING ROOM LIST
			getPlayers().remove(p);
		}else {
			for(PotionEffect effect : p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}
			
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage(ChatColor.RED+"Vous êtes éliminé ! Vous entrez en mode spectateur.");
			
			insertPlayerMatchIntoBDD(p);
	
			// REMOVE PLAYER FROM PLAYERS MATCH LIST
			getPlayers().remove(p);
			
			if(checkWin()) {
				endGame();
			}
		}
		
		if(isState(State.FARMING) || isState(State.PVP)) {
			if(getPlayers().size()<=0) {
				ServerReloader.launchNewGame(this);
			}
		}

	}
	
	
	public void insertPlayerMatchIntoBDD(Player p) {

		// INSERT PLAYER MATCH INTO BDD		
		if(PlayerBDD.hasPlayerTempoBonus(p.getUniqueId().toString())) {
			
			if(DamageListener.kills.containsKey(p)) {
				PlayerBDD.insertChaosPlayerMatch(p.getUniqueId().toString(),
						(int)PlayerBDD.getPlayerTempoBonus(p.getUniqueId().toString()).get(0),
						(Long)PlayerBDD.getLastChaosMatchID().get(0),
						players.size(),
						DamageListener.kills.get(p),
						calculateSurvivalTime(p));
			}else {
				PlayerBDD.insertChaosPlayerMatch(p.getUniqueId().toString(),
						(int)PlayerBDD.getPlayerTempoBonus(p.getUniqueId().toString()).get(0),
						(Long)PlayerBDD.getLastChaosMatchID().get(0),
						players.size(),
						0,
						calculateSurvivalTime(p));	
			}
			
			PlayerBDD.removePlayerTempoBonus(p.getUniqueId().toString());
			
		}else {
			
			if(DamageListener.kills.containsKey(p)) {
				PlayerBDD.insertChaosPlayerMatch(p.getUniqueId().toString(),
						0,
						(Long)PlayerBDD.getLastChaosMatchID().get(0),
						players.size(),
						DamageListener.kills.get(p),
						calculateSurvivalTime(p));
			}else {
				PlayerBDD.insertChaosPlayerMatch(p.getUniqueId().toString(),
						0,
						(Long)PlayerBDD.getLastChaosMatchID().get(0),
						players.size(),
						0,
						calculateSurvivalTime(p));	
			}
			
		}
		
		if(p != gagnant) {
			int player_gemmes = getPlayerGemmes(p);
			
			int id_shop_rank = (int) PlayerBDD.getPlayerInfos(p.getUniqueId().toString()).get(2);
			if(id_shop_rank==2) {
				PlayerBDD.addOrRemovePlayerGemmes(p.getUniqueId().toString(), (int)(player_gemmes*1.65));
				p.sendMessage(ChatColor.BLUE+"Vous recevez "+ChatColor.YELLOW+(int)(player_gemmes*1.65)+ChatColor.BLUE+" Gemmes pour votre temps de survie !"
							+ChatColor.RESET+ChatColor.GRAY+ChatColor.ITALIC+" (Si vous étiez VIP, vous auriez gagné "+(int)(player_gemmes*3)+" Gemmes)");
				
			}else if(id_shop_rank==3 || id_shop_rank==4 || id_shop_rank==5) {
				PlayerBDD.addOrRemovePlayerGemmes(p.getUniqueId().toString(), (int)(player_gemmes*3));
				p.sendMessage(ChatColor.BLUE+"Vous recevez "+ChatColor.YELLOW+(int)(player_gemmes*3)+ChatColor.BLUE+" Gemmes pour votre temps de survie !");
				
			}else {
				PlayerBDD.addOrRemovePlayerGemmes(p.getUniqueId().toString(), player_gemmes);	
				p.sendMessage(ChatColor.BLUE+"Vous recevez "+ChatColor.YELLOW+getPlayerGemmes(p)+ChatColor.BLUE+" Gemmes pour votre temps de survie !"
						+ChatColor.RESET+ChatColor.GRAY+ChatColor.ITALIC+" (Si vous étiez VIP, vous auriez gagné "+(int)(player_gemmes*1.65)+" Gemmes)");
			}
		}
	}
	
	
	
	
	
	public int calculateSurvivalTime(Player p) {
		try {
	        String current_time = (String) PlayerBDD.getCurrentHour().get(0)+":"
	        					+ (String) PlayerBDD.getCurrentMinutes().get(0)+":"
	        					+(String) PlayerBDD.getCurrentSeconds().get(0);
	        
	        
	        String match_time = (String) PlayerBDD.getMatchHour(matchID).get(0)+":"
	        					+ (String) PlayerBDD.getMatchMinutes(matchID).get(0)+":"
	        					+(String) PlayerBDD.getMatchSeconds(matchID).get(0);
	        
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
	        
	
			Date current = simpleDateFormat.parse(current_time);
		    Date match = simpleDateFormat.parse(match_time);
	
		    long differenceInMilliSeconds = Math.abs(match.getTime() - current.getTime());
	    
	    	return (int) (differenceInMilliSeconds/1000);
		}catch(Exception e) {
			return 0;
		}
	    
	}

	
	
	
	
	public void endGame() {
		setState(State.FINISHING);

		Player winner = players.get(0);
		gagnant = winner;
		
		insertPlayerMatchIntoBDD(winner);

		Bukkit.broadcastMessage(ChatColor.GREEN+winner.getDisplayName()+ChatColor.GOLD+" est le vainqueur de la partie !");
		//Bukkit.broadcastMessage("survie :"+calculateSurvivalTime(winner));
		
		int id_shop_rank = (int) PlayerBDD.getPlayerInfos(winner.getUniqueId().toString()).get(2);
		
		if(id_shop_rank==2) {
			PlayerBDD.addOrRemovePlayerGemmes(winner.getUniqueId().toString(), (int)(45*1.65));
			PlayerBDD.addOrRemovePlayerRawCoins(winner.getUniqueId().toString(), (int)(8*1.65));
			
			winner.sendMessage(ChatColor.BLUE+"Vous recevez "+ChatColor.YELLOW+(int)(45*1.65)+ChatColor.BLUE+" Gemmes et "
					+ChatColor.YELLOW+(int)(8*1.65)+ChatColor.BLUE+" RAWCoins !"+ChatColor.RESET
					+ChatColor.GRAY+ChatColor.ITALIC+" (Si vous étiez SURHOMME, vous auriez gagné "+(int)(45*3)+" Gemmes et "+(int)(8*3)+" RAWCoins)");

		}else if(id_shop_rank==3 || id_shop_rank==4 || id_shop_rank==5) {
			PlayerBDD.addOrRemovePlayerGemmes(winner.getUniqueId().toString(), (int)(45*3));
			PlayerBDD.addOrRemovePlayerRawCoins(winner.getUniqueId().toString(), (int)(8*3));
			
			winner.sendMessage(ChatColor.BLUE+"Vous recevez "+ChatColor.YELLOW+(int)(45*3)+ChatColor.BLUE+" Gemmes et "
					+ChatColor.YELLOW+(int)(8*3)+ChatColor.BLUE+" RAWCoins !");
		}else {
			PlayerBDD.addOrRemovePlayerGemmes(winner.getUniqueId().toString(), 45);
			PlayerBDD.addOrRemovePlayerRawCoins(winner.getUniqueId().toString(), 8);

			winner.sendMessage(ChatColor.BLUE+"Vous recevez "+ChatColor.YELLOW+45+ChatColor.BLUE+" Gemmes et "
								+ChatColor.YELLOW+8+ChatColor.BLUE+" RAWCoins !"+ChatColor.RESET
								+ChatColor.GRAY+ChatColor.ITALIC+" (Si vous étiez VIP, vous auriez gagné "+(int)(45*1.65)+" Gemmes et "+(int)(8*1.65)+" RAWCoins)");
		}
		
		kickChrono(this);
	}
	
	
	
	
	/*
	public void endGameZeroPlayer(Player p) { //When the last player leaves the game
		setState(State.FINISHING);

		Player winner = p;
		
		insertPlayerMatchIntoBDD(winner);
		
		Bukkit.broadcastMessage(ChatColor.GREEN+winner.getDisplayName()+ChatColor.GOLD+" est le vainqueur de la partie !");
		kickChrono();
	}*/
	
	
	
	
	
	public boolean checkWin() {
		if(players.size()==1) return true;
		return false;
	}
	
	
	
	
	// Attention, renvoie le nb de gemmes si le joueur n'est pas rank, il faut multiplier dans la fonction appelante en fonction du grade (bonus multiplicateur)
	public int getPlayerGemmes(Player p) {
		if(getPlayers().size()!=0) {
			int minutes = calculateSurvivalTime(p)/60;
			int gemmes = minutes*2;
						
			return gemmes;
		}
		return 0;
	}
	
	
	
	
	
	public void kickChrono(Chaos main) {
		// 10 secondes avant d'être kick
		new BukkitRunnable() {
			private int timer=10;
			
			@Override
			public void run() {
				if(timer==0) {
					cancel();
					for(Entity e : Bukkit.getWorld(world_name).getEntities()) {
						if(e instanceof Player) {
							Player p = (Player)e;
							
							if(getPlayers().contains(p)) {
								getPlayers().remove(p);
							}
							
							ServerMove sm = new ServerMove(main);
							sm.sendPlayerToServer(p,"hub-1");
						}
					}
					
					ServerReloader.launchNewGame(main);
					Bukkit.reload();

				}
				timer--;
			}
		}.runTaskTimer(this, 0, 20);
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
		AlterFileState.setBUSY(this);
		
		try {
			for(Entity e : Bukkit.getWorld(world_name).getEntities()) {
				if(e instanceof Animals || e instanceof Monster) {
					e.remove();
					hologram.removeHologram();
				}
			}
		}catch(Exception e) {
			
		}
		
		
	}
	
	
}
