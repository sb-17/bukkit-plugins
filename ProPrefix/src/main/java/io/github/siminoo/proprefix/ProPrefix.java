package io.github.siminoo.proprefix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.siminoo.proprefix.*;


public class ProPrefix extends JavaPlugin implements Listener {
	
	public String prefix = ChatColor.AQUA + "[Prefix] -> ";

	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("prefix")) {
			if(args.length == 3) {
				Player target = Bukkit.getPlayer(args[0]);
				String newPrefix = args[1];
				String color = args[2];
				if(sender.hasPermission("proprefix.prefix")) {
					if(ChatColor.valueOf(color.toUpperCase()) != null) {
						NametagChanger.changePlayerName(target, ChatColor.valueOf(color.toUpperCase()) + newPrefix + " " + ChatColor.WHITE + target.getName(), null, null);
						target.setDisplayName(ChatColor.valueOf(color.toUpperCase()) + newPrefix + " " + ChatColor.WHITE + target.getName());
						getConfig().set("players." + target.getName() + ".prefix", ChatColor.valueOf(color.toUpperCase()) + newPrefix + " " + ChatColor.WHITE + target.getName());
						saveConfig();
					}
				}
				else sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
			}
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(getConfig().get("players." + e.getPlayer().getName() + ".prefix") != null) {
			NametagChanger.changePlayerName(e.getPlayer(), getConfig().getString("players." + e.getPlayer().getName() + ".prefix"), null, null);
		}
	}
	
	public void onDisable() {
		
	}
		
}
