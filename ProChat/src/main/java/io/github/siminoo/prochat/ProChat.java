package io.github.siminoo.prochat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ProChat extends JavaPlugin implements Listener {
	
	boolean chatMuted = false;
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pch")) {
			if (sender instanceof Player) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("clear")) {
						if (sender.hasPermission("pch.chat.clear")) {
							for(int i=0; i < 100; i ++) {
								Bukkit.broadcastMessage("");
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
						}
					}
					else if (args[0].equalsIgnoreCase("mute")) {
						if (sender.hasPermission("pch.chat.mute")) {
							chatMuted = !chatMuted;
							if (chatMuted) {
								Bukkit.broadcastMessage(ChatColor.RED + "CHAT IS NOW MUTED!");
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
						}
					}
				}
				else if (args.length == 0) {
					sender.sendMessage("More arguments please!");
				}
				else if (args.length == 2) { 
					if (args[0].equals("ban")) {
						if (sender.hasPermission("pch.ban")) {
							Bukkit.getBanList(BanList.Type.NAME).addBan(args[1], args[2], new Date(System.currentTimeMillis() + 60*60*1000), null);
							if (Bukkit.getOfflinePlayer(args[1]) != null) {
								Bukkit.getPlayer(args[1]).kickPlayer(args[2]);
							}
							return true;
						}
						else {
							sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
						}
					}
				}
				else if (args.length > 2) {
					sender.sendMessage(ChatColor.RED + "Too many arguments!");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "You are not a player!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("a")) {
			if (sender instanceof Player) {
				if (args.length > 0) {
					if (sender.hasPermission("pch.chat.admin")) {
						
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						 
						String allArgs = sb.toString().trim();
						
						Bukkit.broadcast(ChatColor.RED + "[AdminChat]" + " -> " + ChatColor.WHITE + ((Player) sender).getName() + ChatColor.RED + " -> " + ChatColor.WHITE + allArgs, "pch.chat.admin");
					}
					else {
						sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "You can't send blank message!");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "You are not a player!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("m")) {
			if (sender instanceof Player) {
				if (args.length > 0) {
					if (sender.hasPermission("pch.chat.muted")) {
						if (chatMuted) {
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
						 
							String allArgs = sb.toString().trim();
						
							Bukkit.broadcastMessage(ChatColor.GOLD + "[MutedChat]" + " -> " + ChatColor.WHITE + ((Player) sender).getName() + ChatColor.GOLD + " -> " + ChatColor.WHITE + allArgs);
						}
						else {
							sender.sendMessage(ChatColor.RED + "Chat is not muted!");
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "You can't send blank message!");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "You are not a player!");
			}
		}
		return true;
	}
	
	@EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (chatMuted) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Chat is muted!");
        }
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Bukkit.broadcastMessage(ChatColor.BLUE + "[" + ChatColor.GREEN + "+" + ChatColor.BLUE + "]" + ChatColor.WHITE + e.getPlayer().getName());
		createScoreboard(e.getPlayer());
		updateScoreboard();
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Bukkit.broadcastMessage(ChatColor.BLUE + "[" + ChatColor.RED + "-" + ChatColor.BLUE + "]" + ChatColor.WHITE + e.getPlayer().getName());
		updateScoreboard();
	}
	
	public void updateScoreboard() {
		for (Player online : Bukkit.getOnlinePlayers()) {
			Score onlinePlayers = online.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GOLD + "Online Players: " + ChatColor.RED);
			onlinePlayers.setScore(Bukkit.getOnlinePlayers().size());
		}
	}
	
	public void createScoreboard(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard b = m.getNewScoreboard();
		Objective players = b.registerNewObjective("onlinePlayers:", "");
		players.setDisplaySlot(DisplaySlot.SIDEBAR);
		players.setDisplayName(ChatColor.DARK_AQUA + "SASKYO server");
		Score onlinePlayers = players.getScore(ChatColor.GOLD + "Online Players: " + ChatColor.RED);
		onlinePlayers.setScore(Bukkit.getOnlinePlayers().size());
		player.setScoreboard(b);
	}
	
	public void onDisable() {
		
	}
}
