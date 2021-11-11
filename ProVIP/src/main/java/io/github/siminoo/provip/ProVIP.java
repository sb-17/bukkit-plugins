package io.github.siminoo.provip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;


public class ProVIP extends JavaPlugin {

	public void onEnable() {
		getLogger().info("ProVIP was enabled!");;
		getConfig().options().copyDefaults(true);
        saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("addvip")) {
			if (sender.hasPermission("vip.add")) {
				if (args.length == 3) {
					if (args[0].equalsIgnoreCase("MegaVIP")) {
						Player target = Bukkit.getPlayer(args[0]);
						if (target != null) {
							getConfig().set("groups." + ".megavip." + target.getName(), "active");
							target.getServer().getPluginManager().addPermission(new Permission("vip.megavip"));
						}
						else {
							getConfig().set("groups." + target.getName(), "waiting");
						}
					}
					else if (args[0].equalsIgnoreCase("VIP")) {
						Player target = Bukkit.getPlayer(args[0]);
						if (target != null) {
							getConfig().set("groups." + ".vip." + target.getName(), "active");
							target.getServer().getPluginManager().addPermission(new Permission("vip.vip"));
						}
						else {
							getConfig().set("groups." + target.getName(), "waiting");
						}
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "More arguments please!");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
			}
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (getConfig().get("groups." + e.getPlayer().getName()) == "waiting") {
			getConfig().set("groups." + e.getPlayer().getName(), null);
			getConfig().set("groups." + ".megavip." + e.getPlayer().getName(), "active");
			e.getPlayer().getServer().getPluginManager().addPermission(new Permission("vip.megavip"));
		}
		if (getConfig().get("groups." + ".megavip." + e.getPlayer().getName()) == "active") {
			
		}
	}
	
	public void onDisable() {
		
	}
	
}

