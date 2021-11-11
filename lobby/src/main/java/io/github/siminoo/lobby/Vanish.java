package io.github.siminoo.lobby;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Vanish implements Listener, CommandExecutor {
	private Lobby plugin;
	public Vanish(Lobby plugin) {
		super();
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	public static ArrayList<Player> vanished = new ArrayList<Player>();
	public String prefix = ChatColor.AQUA + "[Vanish] -> ";
	
	@SuppressWarnings("deprecation")
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
						p.sendMessage(prefix + ChatColor.RED + "OFF");
					}
					else {
						for(Player player : Bukkit.getOnlinePlayers()) {
							if(!p.hasPermission("vanish.see")) {
								player.hidePlayer(p);
							}
						}
						vanished.add(p);
						p.sendMessage(prefix + ChatColor.BLUE + "ON");
					}
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for(Player player : vanished) {
			if(!p.hasPermission("vanish.see")) {
				p.hidePlayer(player);
			}
		}
	}
}
