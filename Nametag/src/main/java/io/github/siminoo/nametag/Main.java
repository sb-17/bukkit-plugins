package io.github.siminoo.nametag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        if(getConfig().get("hp") == null) {
        	getConfig().set("hp", false);
        	saveConfig();
        }
        
        Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        createScoreboard(e.getPlayer()); 
    }
    
    @SuppressWarnings("deprecation")
    public void createScoreboard(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        
        Team admin = board.registerNewTeam("Admin");
        Team owner = board.registerNewTeam("Owner");
        Team builder = board.registerNewTeam("Builder");
        Team helper = board.registerNewTeam("helper");
        Team youtube = board.registerNewTeam("youtube");
        Team playerOnly = board.registerNewTeam("player");
        
        admin.setPrefix(ChatColor.LIGHT_PURPLE + "[Admin] " + ChatColor.WHITE);
        owner.setPrefix(ChatColor.AQUA + "[Owner] " + ChatColor.WHITE);
        builder.setPrefix(ChatColor.YELLOW + "[Builder] " + ChatColor.WHITE);
        helper.setPrefix(ChatColor.GREEN + "[Helper] " + ChatColor.WHITE);
        helper.setPrefix(ChatColor.RED + "[YouTube] " + ChatColor.WHITE);
        playerOnly.setPrefix(ChatColor.GRAY + "[Player] " + ChatColor.WHITE);
        
        if(player.hasPermission("nametag.owner")) {
        	owner.addPlayer(player);
        	player.setDisplayName(ChatColor.AQUA + "[Owner] " + ChatColor.WHITE + player.getName());
        }
        else if(player.hasPermission("nametag.admin")) {
        	admin.addPlayer(player);
        	player.setDisplayName(ChatColor.LIGHT_PURPLE + "[Admin] " + ChatColor.WHITE + player.getName());
        }
        else if(player.hasPermission("nametag.builder")) {
        	builder.addPlayer(player);
        	player.setDisplayName(ChatColor.YELLOW + "[Builder] " + ChatColor.WHITE + player.getName());
        }
        else if(player.hasPermission("nametag.helper")) {
        	helper.addPlayer(player);
        	player.setDisplayName(ChatColor.GREEN + "[Helper] " + ChatColor.WHITE + player.getName());
        }
        else if(player.hasPermission("nametag.youtube")) {
        	youtube.addPlayer(player);
        	player.setDisplayName(ChatColor.RED + "[YouTube] " + ChatColor.WHITE + player.getName());
        }
        else {
        	playerOnly.addPlayer(player);
        	player.setDisplayName(ChatColor.GRAY + "[Player] " + ChatColor.WHITE + player.getName());
        }
        
        player.setScoreboard(board);
    }
	
	public void onDisable() {
		
	}
}
