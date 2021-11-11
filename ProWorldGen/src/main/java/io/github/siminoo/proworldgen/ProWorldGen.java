package io.github.siminoo.proworldgen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ProWorldGen extends JavaPlugin {
String prefix = ChatColor.AQUA + "[WorldGenerator] -> ";
	
	private EmptyWorldGen ewg = new EmptyWorldGen();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("wg")) {
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("create")) {
						if(args[1].equalsIgnoreCase("empty")) {
							if(sender.hasPermission("wg.create")) {
								if (Bukkit.getWorld(args[2]) == null) {
									ewg.CreateEmptyWorld(args[2]);
									World createdWorld = Bukkit.getWorld(args[2]);
									Location loc = new Location(createdWorld, 0, 62, 0);
									createdWorld.setSpawnLocation(loc);
									Block block = createdWorld.getBlockAt(0, 60, 0);
									block.setType(Material.STONE);
									p.sendMessage(prefix + ChatColor.GREEN + "Successfully created and teleported into an empty world!");
									p.teleport(loc);
								}
								else p.sendMessage("This world already exists!");
							}
							else p.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
						}
						else if(args[1].equalsIgnoreCase("flat")) {
							if(p.hasPermission("wg.create")) {
								if(Bukkit.getWorld(args[2]) == null) {
									World world = Bukkit.createWorld(new WorldCreator(args[2]).type(WorldType.FLAT));
									p.teleport(world.getSpawnLocation());
									p.sendMessage(prefix + ChatColor.GREEN + "Successfully created and teleported into a flat world!");
								}
								else p.sendMessage(prefix + ChatColor.RED + "World with that name already exists!");
							}
							else p.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
						}
						else if(args[1].equalsIgnoreCase("normal")) {
							if(p.hasPermission("wg.create")) {
								if(Bukkit.getWorld(args[2]) == null) {
									World world = Bukkit.createWorld(new WorldCreator(args[2]).type(WorldType.NORMAL));
									p.teleport(world.getSpawnLocation());
									p.sendMessage(prefix + ChatColor.GREEN + "Successfully created and teleported into a normal world!");
								}
								else p.sendMessage(prefix + ChatColor.RED + "World with that name already exists!");
							}
							else p.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
						}
						else if(args[1].equalsIgnoreCase("custom")) {
							if(p.hasPermission("wg.create")) {
								if(Bukkit.getWorld(args[2]) == null) {
									if(args.length == 6) {
										WorldCreator wc = new WorldCreator(args[2]);
										if(args[3].equalsIgnoreCase("true")) {
											wc.generateStructures(true);
										}
										else if(args[3].equalsIgnoreCase("false")) {
											wc.generateStructures(false);
										}
										World world = wc.createWorld();
										world.setTime(Integer.parseInt(args[4]));
									}
								}
								else p.sendMessage(prefix + ChatColor.RED + "World with that name already exists!");
							}
							else p.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
						}
						else p.sendMessage(prefix + ChatColor.RED + "Syntax error!");
					}
					else p.sendMessage(prefix + ChatColor.RED + "Syntax error!");
				}
				else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("tp")) {
						if(Bukkit.getWorld(args[1]) != null) {
							if(p.hasPermission("wg.tp")) {
								p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
								p.sendMessage(prefix + ChatColor.GREEN + "Successfully teleported to the world " + args[1] + "!");
							}
							else p.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
						}
						else p.sendMessage(prefix + ChatColor.RED + "World with that name doesn't exists!");
					}
					else p.sendMessage(prefix + ChatColor.RED + "Syntax error!");
				}
				else p.sendMessage(prefix + ChatColor.RED + "Syntax error!");
			}
		}
		else sender.sendMessage(prefix + ChatColor.RED + "You are not a player!");
		return true;
	}
}
