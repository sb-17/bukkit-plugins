package io.github.siminoo.provanish;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class ProVanish extends JavaPlugin implements Listener {
	public static ArrayList<Player> vanished = new ArrayList<Player>();
	public String prefix = ChatColor.AQUA + "[Vanish] -> ";
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("v") || cmd.getName().equalsIgnoreCase("vanish")) {
			if(sender instanceof Player) {
				if(sender.hasPermission("vanish.vanish")) {
					Player p = (Player) sender;
					if(vanished.contains(p)) {
						for(Player player : Bukkit.getOnlinePlayers()) {
							player.showPlayer(p);
						}
						vanished.remove(p);
						p.sendMessage(prefix + ChatColor.GREEN + "Unvanished");
					}
					else {
						for(Player player : Bukkit.getOnlinePlayers()) {
							if(!p.hasPermission("vanish.see")) {
								player.hidePlayer(p);
							}
						}
						vanished.add(p);
						p.sendMessage(prefix + ChatColor.GREEN + "Vanished");
					}
				}
			}
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for(Player player : vanished) {
			if(!p.hasPermission("vanish.see")) {
				p.hidePlayer(player);
			}
		}
	}
	
	public void onDisable() {
		
	}
}
