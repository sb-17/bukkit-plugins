package io.github.siminoo.proskywars;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.metadata.FixedMetadataValue;

public class PSWCommand implements CommandExecutor, Listener {
	private ProSkyWars psw;
	public PSWCommand(ProSkyWars psw) {
		super();
		this.psw = psw;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("psw")) {
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("create" )) {
					if(sender.hasPermission("psw.create")) {
						if(sender instanceof Player) {
							if (Bukkit.getWorld(args[1]) == null) {
								CreateEmptyWorld(args[1]);
								World createdWorld = Bukkit.getWorld(args[1]);
								Location loc = new Location(createdWorld, 0, 62, 0);
								createdWorld.setSpawnLocation(loc);
								Block block = createdWorld.getBlockAt(0, 60, 0);
								block.setType(Material.STONE);
								int id = psw.getConfig().getInt("id");
								int realID = id + 1;
								sender.sendMessage(psw.prefix + ChatColor.GREEN + "Successfully created arena with id: " + realID + "!");
								((Player) sender).teleport(loc);
								((Player) sender).setMetadata("EditingSkyWarsMap", new FixedMetadataValue(psw, this));
								psw.getConfig().set("arenas." + realID + ".name", args[1]);
								psw.getConfig().set("id", id + 1);
								psw.saveConfig();
							}
							else sender.sendMessage(psw.prefix + ChatColor.RED + "This world already exists!");
						}
						else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not a player!");
					}
					else sender.sendMessage(psw.prefix + psw.perm);
				}
				else if(args[0].equalsIgnoreCase("edit")) {
					if(sender.hasPermission("psw.edit" )) {
						if(sender instanceof Player) {
							if(psw.getConfig().get("arenas." + args[1]) != null) {
								World world = Bukkit.getWorld(psw.getConfig().getString("arenas." + args[1] + ".name"));
								((Player) sender).teleport(new Location(world, 0, 62, 0));
								((Player) sender).setMetadata("EditingSkyWarsMap", new FixedMetadataValue(psw, this));
								psw.getConfig().set(((Player) sender).getName() + ".editing", args[1]);
							}
							else sender.sendMessage(psw.prefix + ChatColor.RED + "This arena doesn't exist!");
						}
						else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not a player!");
					}
					else sender.sendMessage(psw.prefix + psw.perm);
				}
				else sender.sendMessage(psw.prefix + ChatColor.RED + "Syntax error!");
			}
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("setlobby")) {
					if(sender.hasPermission("psw.setlobby" )) {
						if(sender instanceof Player) {
							Player player = (Player) sender;
							psw.getConfig().set("lobby." + ".world", player.getWorld().getName());
			                psw.getConfig().set("lobby." + ".x", player.getLocation().getX());
			                psw.getConfig().set("lobby." + ".y", player.getLocation().getY());
			                psw.getConfig().set("lobby." + ".z", player.getLocation().getZ());
			                psw.getConfig().set("lobby." + ".yaw", player.getLocation().getYaw());
			                psw.getConfig().set("lobby." + ".pitch", player.getLocation().getPitch());
			                psw.saveConfig();
			                player.sendMessage(psw.prefix + ChatColor.GREEN + "Successfully set lobby!");
						}
						else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not a player!");
					}
					else sender.sendMessage(psw.prefix + psw.perm);
				}
				else if(args[0].equalsIgnoreCase("save" )) {
					if(sender.hasPermission("psw.save")) {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							if(p.hasMetadata("EditingSkyWarsMap")) {
								p.removeMetadata("EditingSkyWarsMap", psw);
								TeleportToLobby(p);
								p.sendMessage(psw.prefix + ChatColor.GREEN + "Successfully saved SkyWars map!");
								p.setMetadata("SkyWarsLobby", new FixedMetadataValue(psw, this));
							}
							else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not editing a SkyWars map!");
						}
						else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not a player!");
					}
					else sender.sendMessage(psw.prefix + psw.perm);
				}
				else if(args[0].equalsIgnoreCase("join")) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(psw.queue.contains(p)) {
							p.sendMessage(psw.prefix + ChatColor.RED + "You are already in queue!");
						}
						else {
							psw.queue.add(p);
							p.setMetadata("WaitingForSkyWarsMatch", new FixedMetadataValue(psw, this));
							p.sendMessage(psw.prefix + ChatColor.GREEN + "Successfully joined to the queue!");
							if(psw.queue.size() == 1) {
								int arenas = psw.getConfig().getInt("id");
								int min = 1;
								int max = arenas;
								int randomArena = ThreadLocalRandom.current().nextInt(min, max + 1);
								
								psw.arena = Bukkit.getWorld(psw.getConfig().getString("arenas." + randomArena + ".name"));
								
								psw.id = psw.getConfig().getInt("gamesPlayed");
								psw.arenaID = randomArena;
								final WorldCreator wc = new WorldCreator(psw.arena.getName() + psw.id);
								wc.copy(Bukkit.getWorld(psw.arena.getName()));
								Bukkit.getServer().getScheduler().runTaskAsynchronously(psw, new Runnable() {
									public void run() {
										wc.createWorld();
									}
								});
								int current = psw.getConfig().getInt("gamesPlayed");
								psw.getConfig().set("gamesPlayed", current + 1);
								psw.saveConfig();
							}
						}
					}
					else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not a player!");
				}
				else sender.sendMessage(psw.prefix + ChatColor.RED + "Syntax error!");
			}
			else if(args.length == 3) {
				if(args[0].equalsIgnoreCase("spawn")) {
					if(sender.hasPermission("psw.setspawn")) {
						if(args[1].equalsIgnoreCase("player")) {
							int arena = Integer.parseInt(args[2]);
							Player p = (Player) sender;
							if(p.hasMetadata("EditingSkyWarsMap" )) {
								if(p.getWorld().getName().equalsIgnoreCase(psw.getConfig().getString("arenas.") + arena + ".name")) {
									if(psw.getConfig().get("arenas." + arena + ".players") == null) {
										psw.getConfig().set("arenas." + arena + ".players", 0);
										psw.saveConfig();
									}
									int players = psw.getConfig().getInt("arenas." + arena + ".players");
									psw.getConfig().set("arenas." + arena + ".players", players + 1);
									psw.saveConfig();
									psw.getConfig().set("arenas." + arena + ".spawnpos." + players + ".world", p.getWorld().getName());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + players + ".x", p.getLocation().getX());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + players + ".y", p.getLocation().getY());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + players + ".z", p.getLocation().getZ());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + players + ".yaw", p.getLocation().getYaw());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + players + ".pitch", p.getLocation().getPitch());
									psw.saveConfig();
									p.sendMessage(psw.prefix + ChatColor.GREEN + "Successfully set player spawn " + ChatColor.GOLD + players + ChatColor.GREEN + "!");
								}
								else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not editing SkyWars map that you selected!");
							}
							else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not editing SkyWars map!");
						}
						else if(args[1].equalsIgnoreCase("spectator")) {
							int arena = Integer.parseInt(args[2]);
							Player p = (Player) sender;
							if(p.hasMetadata("EditingSkyWarsMap" )) {
								if(p.getWorld().getName().equalsIgnoreCase(psw.getConfig().getString("arenas.") + arena + ".name")) {
									psw.getConfig().set("arenas." + arena + ".spawnpos." + ".spec." + ".world", p.getWorld().getName());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + ".spec." + ".x", p.getLocation().getX());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + ".spec." + ".y", p.getLocation().getY());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + ".spec." + ".z", p.getLocation().getZ());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + ".spec." + ".yaw", p.getLocation().getYaw());
									psw.getConfig().set("arenas." + arena + ".spawnpos." + ".spec." + ".pitch", p.getLocation().getPitch());
									psw.saveConfig();
									p.sendMessage(psw.prefix + ChatColor.GREEN + "Successfully set spectator spawn!");
								}
								else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not editing SkyWars map that you selected!");
							}
							else sender.sendMessage(psw.prefix + ChatColor.RED + "You are not editing SkyWars map!");
						}
						else sender.sendMessage(psw.prefix + ChatColor.RED + "Syntax error!");
					}
					else sender.sendMessage(psw.prefix + psw.perm);
				}
				else sender.sendMessage(psw.prefix + ChatColor.RED + "Syntax error!");
			}
			else sender.sendMessage(psw.prefix + ChatColor.RED + "Syntax error!");
		}
		return true;
	}
	
	public void CreateEmptyWorld(String worldname) {
		WorldCreator wc = new WorldCreator(worldname);
		wc.generator(new ChunkGenerator() {
		    public byte[] generate(World world, Random random, int x, int z) {
		        return new byte[32768];
		    }
		});
		World world = wc.createWorld();
	}
	
	public void TeleportToLobby(Player target) {
		World world = Bukkit.getWorld(psw.getConfig().getString("lobby." + ".world"));
		double x = psw.getConfig().getDouble("lobby." + ".x");
		double y = psw.getConfig().getDouble("lobby." + ".y");
		double z = psw.getConfig().getDouble("lobby." + ".z");
		float yaw = (float) psw.getConfig().getDouble("lobby." + ".yaw");
		float pitch = (float) psw.getConfig().getDouble("lobby." + ".pitch");

		target.teleport(new Location(world, x, y, z, yaw, pitch));
	}
}
