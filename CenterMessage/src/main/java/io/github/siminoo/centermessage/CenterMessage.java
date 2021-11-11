package io.github.siminoo.centermessage;

import java.awt.Label;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CenterMessage extends JavaPlugin implements Listener {
	String prefix = ChatColor.AQUA + "[CenterMessage] -> ";
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, Label label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cm")) {
			if(args.length > 2) {
				if(args[0].equalsIgnoreCase("title")) {
					if(sender.hasPermission("cm.send")) {
						Player p = Bukkit.getPlayer(args[1]);
						if(p != null) {
							StringBuilder sb = new StringBuilder();
							for (int i = 2; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String allArgs = sb.toString().trim();
							
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + p.getName() + " title " + allArgs);
							
							sender.sendMessage(prefix + ChatColor.GREEN + "Successfully sent Center Message to " + p.getName());
						}
						else sender.sendMessage(prefix + ChatColor.RED + p.getName() + " is not online!");
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
				}
			}
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(getConfig().getStringList("join").contains(e.getPlayer().getName())) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + e.getPlayer().getName() + " title" + ChatColor.GOLD + "Hey " + e.getPlayer().getName() + ",");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + e.getPlayer().getName() + " subtitle" + ChatColor.GREEN + "welcome to our server!");
		}
	}
	
	public void onDisable() {
		
	}
}
