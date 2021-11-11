package io.github.siminoo.procustomchats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class ProCustomChats extends JavaPlugin {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pcccreate")) {
			if (args.length == 1) {
				getConfig().set("chats." + args[0], args[0]);
				saveConfig();
			}
			else {
				sender.sendMessage(ChatColor.RED + "More arguments please!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("pccremove")) {
			if (args.length == 1) {
				getConfig().set("chats." + args[0], null);
				saveConfig();
			}
			else {
				sender.sendMessage(ChatColor.RED + "More arguments please!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase((String) getConfig().get("chats." + cmd.getName()))) {
			if (args.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}
				 
				String allArgs = sb.toString().trim();
				
				Bukkit.broadcast(ChatColor.GREEN + "[PCC]" + ChatColor.RED + " -> " + ChatColor.WHITE + ((Player) sender).getName() + ChatColor.RED + " -> " + ChatColor.WHITE + allArgs, "pch.chat." + cmd.getName());
			}
			else {
				sender.sendMessage(ChatColor.RED + "You can't send blank message!");
			}
		}
		return false;
	}
	
	public void onDisable() {
		
	}
}
