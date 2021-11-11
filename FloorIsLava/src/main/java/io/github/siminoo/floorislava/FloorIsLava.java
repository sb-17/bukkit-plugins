package io.github.siminoo.floorislava;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FloorIsLava extends JavaPlugin {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("fil")) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("create" )) {
					if (sender.hasPermission("fil.create")) {
						Player player =  (Player) sender;
						getConfig().set("arenas." + args[1] + ".world", player.getWorld().getName());
						getConfig().set("arenas." + args[1] + ".x", player.getLocation().getX());
						getConfig().set("arenas." + args[1] + ".y", player.getLocation().getY());
						getConfig().set("arenas." + args[1] + ".z", player.getLocation().getZ());
						getConfig().set("arenas." + args[1] + ".yaw", player.getLocation().getYaw());
						getConfig().set("arenas." + args[1] + ".pitch", player.getLocation().getPitch());
						
						player.sendMessage(ChatColor.GREEN + "Successfully created new arena!");
					}
					else {
						sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
					}
				}
				else if (args[0].equalsIgnoreCase("join")) {
					if (sender instanceof Player) {
						Player player = (Player) sender;
						player.setMetadata("WaitinfForFIL", null);
						World world = Bukkit.getWorld(getConfig().getString("arenas." + args[1] + ".world"));
						double x = getConfig().getDouble("arenas." + args[1] + ".x");
						double y = getConfig().getDouble("arenas." + args[1] + ".y");
						double z = getConfig().getDouble("arenas." + args[1] + ".z");
						float yaw = (float) getConfig().getDouble("arenas." + args[1] + ".yaw");
						float pitch = (float) getConfig().getDouble("arenas." + args[1] + ".pitch");
						
						player.teleport(new Location(world, x, y, z, yaw, pitch));
					}
				}
				else if (args[0].equalsIgnoreCase("start")) {
					final Player player = (Player) sender;
					if (player.hasMetadata("WaitingForFIL")) {
						new BukkitRunnable() {
							@Override
							public void run() {
								World world = player.getWorld();
								World world1 = Bukkit.getWorld(getConfig().getString("arenas." + args[1] + ".world"));
								double x = getConfig().getDouble("arenas." + args[1] + ".x");
								double y = getConfig().getDouble("arenas." + args[1] + ".y");
								double z = getConfig().getDouble("arenas." + args[1] + ".z");
								Block block = world.getBlockAt(new Location(world1, x, y, z));
								int i = 0;
								int v = 10;
								while (i < 100) {
									i++;
									block = world.getBlockAt(new Location(world1, x - v, y, z));
									block.setType();
									v--;
								}
							}
						}.runTaskTimer(null, 200L, 200L);
					}
				}
			}
			else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("leave")) {
					if (sender instanceof Player) {
						if (((Player) sender).hasMetadata("WaitingForFIL")) {
							Player player = (Player) sender;
							World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
							double x = getConfig().getDouble("lobby." + ".x");
							double y = getConfig().getDouble("lobby." + ".y");
							double z = getConfig().getDouble("lobby." + ".z");
							float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
							float pitch = (float) getConfig().getDouble("lobby." + ".pitch");

							player.teleport(new Location(world, x, y, z, yaw, pitch));

							player.sendMessage(ChatColor.GREEN + "You have successfully left FIL match!");
							player.removeMetadata("WaitingForFIL", null);
						}
					}
				}
				else if (args[0].equalsIgnoreCase("setlobby")) {
					if (sender.hasPermission("fil.setlobby")) {
						if (sender instanceof Player) {
							Player player = (Player) sender;
							getConfig().set("lobby." + ".world", null);
							getConfig().set("lobby." + ".x", null);
							getConfig().set("lobby." + ".y", null);
							getConfig().set("lobby." + ".z", null);
							getConfig().set("lobby." + ".yaw", null);
							getConfig().set("lobby." + ".pitch", null);
							getConfig().set("lobby." + ".world", player.getWorld().getName());
			                getConfig().set("lobby." + ".x", player.getLocation().getX());
			                getConfig().set("lobby." + ".y", player.getLocation().getY());
			                getConfig().set("lobby." + ".z", player.getLocation().getZ());
			                getConfig().set("lobby." + ".yaw", player.getLocation().getYaw());
			                getConfig().set("lobby." + ".pitch", player.getLocation().getPitch());
			                saveConfig();
			                player.sendMessage(ChatColor.GREEN + "Successfuly set " + ChatColor.DARK_RED + "FIL lobby!");
						}
					}
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerBreak(BlockBreakEvent e) {
		if (e.getPlayer().hasMetadata("WaitingForFIL")) {
			e.setCancelled(true);
		}
	}
	
	public void onDisable() {
		
	}
}
