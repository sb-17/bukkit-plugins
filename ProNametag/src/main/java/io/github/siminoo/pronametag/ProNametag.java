package io.github.siminoo.pronametag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ProNametag extends JavaPlugin {

	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (cmd.getName().equalsIgnoreCase("nt")) {
		if (sender instanceof Player) {
			if (sender.hasPermission("pront.set")) {
				if (args.length > 3) {
					if (args[0].equalsIgnoreCase("set")) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target != null) {
							StringBuilder sb = new StringBuilder();
							for (int i = 3; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String nametag = sb.toString().trim();
							if (args[2].equalsIgnoreCase("red")) {
								target.setDisplayName(ChatColor.RED + nametag + " " + target.getName());
								getConfig().set("prefix." + target.getName() + ".color.", args[2]);
								getConfig().set("prefix." + target.getName() + ".name.", nametag);
							}
							else if (args[2].equalsIgnoreCase("blue")) {
								target.setDisplayName(ChatColor.BLUE + nametag + " " + target.getName());
								getConfig().set("prefix." + target.getName() + ".color.", args[2]);
								getConfig().set("prefix." + target.getName() + ".name.", nametag);
							}
							else if (args[2].equalsIgnoreCase("gold")) {
								target.setDisplayName(ChatColor.GOLD + nametag + " " + target.getName());
								getConfig().set("prefix." + target.getName() + ".color.", args[2]);
								getConfig().set("prefix." + target.getName() + ".name.", nametag);
							}
							else if (args[2].equalsIgnoreCase("purple")) {
								target.setDisplayName(ChatColor.DARK_PURPLE + nametag + " " + target.getName());
								getConfig().set("prefix." + target.getName() + ".color.", args[2]);
								getConfig().set("prefix." + target.getName() + ".name.", nametag);
							}
							else if (args[2].equalsIgnoreCase("pink")) {
								target.setDisplayName(ChatColor.LIGHT_PURPLE + nametag + " " + target.getName());
								getConfig().set("prefix." + target.getName() + ".color.", args[2]);
								getConfig().set("prefix." + target.getName() + ".name.", nametag);
							}
							else if (args[2].equalsIgnoreCase("aqua")) {
								target.setDisplayName(ChatColor.DARK_AQUA + nametag + " " + target.getName());
								getConfig().set("prefix." + target.getName() + ".color.", args[2]);
								getConfig().set("prefix." + target.getName() + ".name.", nametag);
							}
							else if (args[2].equalsIgnoreCase("green")) {
								target.setDisplayName(ChatColor.GREEN + nametag + " " + target.getName());
								getConfig().set("prefix." + target.getName() + ".color.", args[2]);
								getConfig().set("prefix." + target.getName() + ".name.", nametag);
							}
						}
					}
				}
			}
 		}
	}
		
		return false;
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(getConfig().getConfigurationSection("prefix." + e.getPlayer().getName() + ".name.") != null) {
			String prefix = (String) getConfig().get("prefix." + e.getPlayer().getName() + ".name.");
			String color = (String) getConfig().get("prefix." + e.getPlayer().getName() + ".color.");
			if (color == "red") {
				e.getPlayer().setDisplayName(ChatColor.RED + prefix);
			}
			else if (color == "blue") {
				e.getPlayer().setDisplayName(ChatColor.BLUE + prefix);
			}
			else if (color == "gold") {
				e.getPlayer().setDisplayName(ChatColor.GOLD + prefix);
			}
			else if (color == "purple") {
				e.getPlayer().setDisplayName(ChatColor.DARK_PURPLE + prefix);
			}
			else if (color == "pink") {
				e.getPlayer().setDisplayName(ChatColor.LIGHT_PURPLE + prefix);
			}
			else if (color == "aqua") {
				e.getPlayer().setDisplayName(ChatColor.DARK_AQUA + prefix);
			}
			else if (color == "green") {
				e.getPlayer().setDisplayName(ChatColor.GREEN + prefix);
			}
		}
	}
	
	public void onDisable() {
		
	}
	
}
