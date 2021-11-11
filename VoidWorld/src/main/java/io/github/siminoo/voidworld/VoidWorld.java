package io.github.siminoo.voidworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class VoidWorld extends JavaPlugin {
	
	private EmptyWorldGen ewg = new EmptyWorldGen();
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vwcreate")) {
			if(args.length == 1) {
				if(sender instanceof Player) {
					if(sender.hasPermission("voidworld.create")) {
						if (Bukkit.getWorld(args[0]) == null) {
							ewg.CreateEmptyWorld(args[0]);
							World createdWorld = Bukkit.getWorld(args[0]);
							Location loc = new Location(createdWorld, 0, 62, 0);
							createdWorld.setSpawnLocation(loc);
							Block block = createdWorld.getBlockAt(0, 60, 0);
							block.setType(Material.STONE);
							sender.sendMessage(ChatColor.GREEN + "Successfully created arena!");
							if (sender instanceof Player) {
								((Player) sender).teleport(loc);
							}
							else sender.sendMessage(ChatColor.RED + "You can't be teleported to arena!");
						}
						else sender.sendMessage("This world already exists!");
					}
					else {
						sender.sendMessage(ChatColor.DARK_RED + "You don't have enough permissions!");
					}
				}
				else sender.sendMessage(ChatColor.RED + "You are not a player!");
			}
			else {
				sender.sendMessage("More arguments please!");
			}
		}
		return true;
	}
	
	@Override
	public void onDisable() {
		
	}
}

