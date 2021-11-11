package io.github.siminoo.proessentials;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class ProEssentials extends JavaPlugin implements Listener {
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gmc")) {
			if (args.length == 0) {
				if (sender.hasPermission("gmc")) {
					Player target = (Player) sender;
					target.setGameMode(GameMode.CREATIVE);
				}
			}
			else if (args.length == 1) {
				if (sender.hasPermission("gmc.other")) {
					Player target = Bukkit.getPlayer(args[0]);
					target.setGameMode(GameMode.CREATIVE);
				}
			}
		}
		else if (cmd.getName().equalsIgnoreCase("gmsv")) {
			if (args.length == 0) {
				if (sender.hasPermission("gmsv")) {
					Player target = (Player) sender;
					target.setGameMode(GameMode.SURVIVAL);
				}
			}
			else if (args.length == 1) {
				if (sender.hasPermission("gmsv.other")) {
					Player target = Bukkit.getPlayer(args[0]);
					target.setGameMode(GameMode.SURVIVAL);
				}
			}
		}
		else if (cmd.getName().equalsIgnoreCase("gma")) {
			if (args.length == 0) {
				if (sender.hasPermission("gma")) {
					Player target = (Player) sender;
					target.setGameMode(GameMode.ADVENTURE);
				}
			}
			else if (args.length == 1) {
				if (sender.hasPermission("gma.other")) {
					Player target = Bukkit.getPlayer(args[0]);
					target.setGameMode(GameMode.ADVENTURE);
				}
			}
		}
		else if (cmd.getName().equalsIgnoreCase("gmsp")) {
			if (args.length == 0) {
				if (sender.hasPermission("gmsp")) {
					Player target = (Player) sender;
					target.setGameMode(GameMode.SPECTATOR);
				}
			}
			else if (args.length == 1) {
				if (sender.hasPermission("gmsp.other")) {
					Player target = Bukkit.getPlayer(args[0]);
					target.setGameMode(GameMode.SPECTATOR);
				}
			}
		}
		return true;
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!e.getPlayer().hasPermission("ateam")) {
			e.getPlayer().setDisplayName(ChatColor.GRAY + "[Hrac]" + ChatColor.WHITE + e.getPlayer().getName());
			e.getPlayer().setPlayerListName(ChatColor.GRAY + "[Hrac]" + ChatColor.WHITE + e.getPlayer().getName());
		}
	}
	
	public void onDisable() {
		
	}
}
