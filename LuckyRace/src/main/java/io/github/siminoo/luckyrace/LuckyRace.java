package io.github.siminoo.luckyrace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LuckyRace extends JavaPlugin implements Listener {
	private EmptyWorldGen ewg = new EmptyWorldGen();
	String prefix = ChatColor.AQUA + "[LuckyRace] -> ";
	boolean copied = false;
	int id;
	LuckyRace plugin;
	List<String> arenas;
	String arenaName;
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        plugin = this;
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

    	if(getConfig().get("gamesPlayed") == null) {
    		getConfig().set("gamesPlayed", 0);
    		saveConfig();
    	}
    	
    	arenas = getConfig().getStringList("luckyrace." + ".arenas.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("lr")) {
			Player p = (Player) sender;
			if(args.length == 1) {
				sender.sendMessage(prefix + ChatColor.RED + "Syntax error!");
			}
			else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("create")) {
					if(p.hasPermission("lr.create")) {
						if (Bukkit.getWorld(args[1]) == null) {
							ewg.CreateEmptyWorld(args[1]);
							World createdWorld = Bukkit.getWorld(args[1]);
							Location loc = new Location(createdWorld, 0, 62, 0);
							createdWorld.setSpawnLocation(loc);
							Block block = createdWorld.getBlockAt(0, 60, 0);
							block.setType(Material.STONE);
							p.sendMessage(prefix + ChatColor.GREEN + "Successfully created arena!");
							getConfig().set("luckyrace." + ".arenas." + args[0] + ".world", createdWorld.getName());
							getConfig().set("luckyrace." + ".arenas." + args[0] + ".queue", null);
							saveConfig();
							arenas = getConfig().getStringList("luckyrace." + ".arenas.");
							if (sender instanceof Player) {
								p.teleport(loc);
							}
							else sender.sendMessage(prefix + ChatColor.RED + "You can't be teleported to arena!");
						}
						else sender.sendMessage(prefix + ChatColor.RED + "This world already exists!");
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
				}
				else if(args[0].equalsIgnoreCase("setspawn1")) {
					if(sender instanceof Player) {
						if(sender.hasPermission("lr.setspawn")) {
							if(getConfig().get("luckyrace." + ".arenas." + p.getWorld().getName()) != null) {
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos1." + ".x", p.getLocation().getX());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos1." + ".y", p.getLocation().getY());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos1." + ".z", p.getLocation().getZ());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos1." + ".yaw", p.getLocation().getYaw());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos1." + ".pitch", p.getLocation().getPitch());
								saveConfig();
								p.sendMessage(prefix + ChatColor.GREEN + "Successfully set spawn position!");
							}
							else sender.sendMessage(prefix + ChatColor.RED + "In world that you are currently in is not any arena!");
						}
						else sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You are not a player!");
				}
				else if(args[0].equalsIgnoreCase("setspawn2")) {
					if(sender instanceof Player) {
						if(sender.hasPermission("lr.setspawn")) {
							if(getConfig().get("luckyrace." + ".arenas." + p.getWorld().getName()) != null) {
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos2." + ".x", p.getLocation().getX());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos2." + ".y", p.getLocation().getY());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos2." + ".z", p.getLocation().getZ());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos2." + ".yaw", p.getLocation().getYaw());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos2." + ".pitch", p.getLocation().getPitch());
								saveConfig();
								p.sendMessage(prefix + ChatColor.GREEN + "Successfully set spawn position!");
							}
							else sender.sendMessage(prefix + ChatColor.RED + "In world that you are currently in is not any arena!");
						}
						else sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You are not a player!");
				}
				else if(args[0].equalsIgnoreCase("setspawn3")) {
					if(sender instanceof Player) {
						if(sender.hasPermission("lr.setspawn")) {
							if(getConfig().get("luckyrace." + ".arenas." + p.getWorld().getName()) != null) {
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos3." + ".x", p.getLocation().getX());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos3." + ".y", p.getLocation().getY());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos3." + ".z", p.getLocation().getZ());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos3." + ".yaw", p.getLocation().getYaw());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos3." + ".pitch", p.getLocation().getPitch());
								saveConfig();
								p.sendMessage(prefix + ChatColor.GREEN + "Successfully set spawn position!");
							}
							else sender.sendMessage(prefix + ChatColor.RED + "In world that you are currently in is not any arena!");
						}
						else sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You are not a player!");
				}
				else if(args[0].equalsIgnoreCase("setspawn4")) {
					if(sender instanceof Player) {
						if(sender.hasPermission("lr.setspawn")) {
							if(getConfig().get("luckyrace." + ".arenas." + p.getWorld().getName()) != null) {
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos4." + ".x", p.getLocation().getX());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos4." + ".y", p.getLocation().getY());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos4." + ".z", p.getLocation().getZ());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos4." + ".yaw", p.getLocation().getYaw());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos4." + ".pitch", p.getLocation().getPitch());
								saveConfig();
								p.sendMessage(prefix + ChatColor.GREEN + "Successfully set spawn position!");
							}
							else sender.sendMessage(prefix + ChatColor.RED + "In world that you are currently in is not any arena!");
						}
						else sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You are not a player!");
				}
				else if(args[0].equalsIgnoreCase("setspawn5")) {
					if(sender instanceof Player) {
						if(sender.hasPermission("lr.setspawn")) {
							if(getConfig().get("luckyrace." + ".arenas." + p.getWorld().getName()) != null) {
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos5." + ".x", p.getLocation().getX());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos5." + ".y", p.getLocation().getY());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos5." + ".z", p.getLocation().getZ());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos5." + ".yaw", p.getLocation().getYaw());
								getConfig().set("luckyrace." + ".arenas." + p.getWorld().getName() + ".pos5." + ".pitch", p.getLocation().getPitch());
								saveConfig();
								p.sendMessage(prefix + ChatColor.GREEN + "Successfully set spawn position!");
							}
							else sender.sendMessage(prefix + ChatColor.RED + "In world that you are currently in is not any arena!");
						}
						else sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You are not a player!");
				}
				else sender.sendMessage(prefix + ChatColor.RED + "Syntax error!");
			}
			else sender.sendMessage(prefix + ChatColor.RED + "Syntax error!");
		}
		return true;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[LuckyRace]")) {
			if(e.getPlayer().hasPermission("lr.create")) {
				if(getConfig().get("luckyrace." + ".arenas." + e.getLine(1)) != null) {
					e.setLine(0, ChatColor.AQUA + "LuckyRace");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign s = (Sign) e.getClickedBlock().getState();
				if(s.getLine(0).equalsIgnoreCase(ChatColor.AQUA + "LuckyRace")) {
					if(getConfig().getStringList("luckyrace." + ".arenas." + s.getLine(1) + ".queue").size() < 5) {
						if(getConfig().getString("luckyrace." + e.getPlayer() + ".status").equalsIgnoreCase("in queue")) {
							e.getPlayer().sendMessage(prefix + ChatColor.RED + "You already are in queue!");
						}
						else {
							if(getConfig().getStringList("luckyrace." + ".arenas." + s.getLine(1) + ".queue").size() < 5) {
								getConfig().getStringList("luckyrace." + ".arenas." + s.getLine(1) + ".queue").add(e.getPlayer().getName());
								getConfig().set("luckyrace." + e.getPlayer() + ".status", "in queue");
								saveConfig();
								e.getPlayer().sendMessage(prefix + ChatColor.GREEN + "Successfully added to queue!");
								if(getConfig().getStringList("luckyrace." + ".arenas." + s.getLine(1) + ".queue").size() == 5) {
									id = getConfig().getInt("gamesPlayed");
									final WorldCreator wc = new WorldCreator(s.getLine(1) + id);
									wc.copy(Bukkit.getWorld(s.getLine(1)));
									Bukkit.getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
										public void run() {
											wc.createWorld();
										}
									});
									int current = getConfig().getInt("gamesPlayed");
									getConfig().set("gamesPlayed", current + 1);
									saveConfig();
								}
							}
						}
					}
					else e.getPlayer().sendMessage(prefix + ChatColor.RED + "Queue is full now, please try again later!");
				}
			}
		}
	}
	
	@EventHandler
	public void onWorldLoad(final WorldLoadEvent e) {
		new BukkitRunnable() {
            public void run() {
            	int i = getConfig().getStringList("luckyrace." + ".arenas.").size();
            	int n = 0;
            	while(i > n) {
		    		if(e.getWorld().getName().contains(getConfig().getStringList("luckyrace." + ".arenas.").get(n))) {
		    			final File from = Bukkit.getWorld(getConfig().getStringList("luckyrace." + ".arenas.").get(n)).getWorldFolder();
		    			final File to = Bukkit.getWorld(getConfig().getStringList("luckyrace." + ".arenas.").get(n) + id).getWorldFolder();
		    			arenaName = getConfig().getStringList("luckyrace." + ".arenas.").get(n);
		    			if(copied) {
		    				teleportToArena(getConfig().getStringList("luckyrace." + ".arenas.").get(n));
		    			}
		    			else {
		    				Bukkit.unloadWorld(getConfig().getStringList("luckyrace." + ".arenas.").get(n) + id, false);
		    				Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
		    					public void run() {
		    						copyWorld(from, to);
		    					}
		    				});
		    			}
		    		}
		    		n++;
            	}
            }
		}.runTask(this);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(e.getPlayer().hasMetadata("PlayingLuckyRace")) {
			
		}
	}
	
	public void teleportToArena(String arena) {
		Player p1 = Bukkit.getPlayer(getConfig().getStringList("luckyrace." + ".arenas." + arena + ".queue").get(0));
		Player p2 = Bukkit.getPlayer(getConfig().getStringList("luckyrace." + ".arenas." + arena + ".queue").get(1));
		Player p3 = Bukkit.getPlayer(getConfig().getStringList("luckyrace." + ".arenas." + arena + ".queue").get(2));
		Player p4 = Bukkit.getPlayer(getConfig().getStringList("luckyrace." + ".arenas." + arena + ".queue").get(3));
		Player p5 = Bukkit.getPlayer(getConfig().getStringList("luckyrace." + ".arenas." + arena + ".queue").get(4));
		
		getConfig().getStringList("luckyrace." + ".arenas." + arena + ".queue").clear();
		saveConfig();
		
		World world = Bukkit.getWorld(arena);
		double x = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos1." + ".x");
		double y = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos1." + ".y");
		double z = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos1." + ".z");
		float yaw = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos1." + ".yaw");
		float pitch = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos1." + ".pitch");
		p1.teleport(new Location(world, x, y, z, yaw, pitch));
		getConfig().set("luckyrace." + p1.getName() + ".status", "in game");
		saveConfig();
		
		x = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos2" + ".x");
		y = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos2." + ".y");
		z = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos2." + ".z");
		yaw = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos2." + ".yaw");
		pitch = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos2." + ".pitch");
		p2.teleport(new Location(world, x, y, z, yaw, pitch));
		getConfig().set("luckyrace." + p2.getName() + ".status", "in game");
		saveConfig();
		
		x = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos3" + ".x");
		y = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos3." + ".y");
		z = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos3." + ".z");
		yaw = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos3." + ".yaw");
		pitch = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos3." + ".pitch");
		p3.teleport(new Location(world, x, y, z, yaw, pitch));
		getConfig().set("luckyrace." + p3.getName() + ".status", "in game");
		saveConfig();
		
		x = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos4" + ".x");
		y = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos4." + ".y");
		z = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos4." + ".z");
		yaw = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos4." + ".yaw");
		pitch = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos4." + ".pitch");
		p4.teleport(new Location(world, x, y, z, yaw, pitch));
		getConfig().set("luckyrace." + p4.getName() + ".status", "in game");
		saveConfig();
		
		x = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos5" + ".x");
		y = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos5." + ".y");
		z = getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos5." + ".z");
		yaw = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos5." + ".yaw");
		pitch = (float) getConfig().getDouble("luckyrace." + ".arenas." + arena + ".pos5." + ".pitch");
		p5.teleport(new Location(world, x, y, z, yaw, pitch));
		getConfig().set("luckyrace." + p5.getName() + ".status", "in game");
		saveConfig();
	}
	
	public void copyWorld(File source, File target) {
	    try {
	        ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
	        if(!ignore.contains(source.getName())) {
	            if(source.isDirectory()) {
	                if(!target.exists())
	                target.mkdirs();
	                String files[] = source.list();
	                for (String file : files) {
	                    File srcFile = new File(source, file);
	                    File destFile = new File(target, file);
	                    copyWorld(srcFile, destFile);
	                }
	                copied = true;
	                new BukkitRunnable() {
						public void run() {
							new WorldCreator(arenaName + id).createWorld();
						}
	                }.runTask(plugin);
	            } else {
	                InputStream in = new FileInputStream(source);
	                OutputStream out = new FileOutputStream(target);
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = in.read(buffer)) > 0)
	                    out.write(buffer, 0, length);
	                in.close();
	                out.close();
	            }
	        }
	    } catch (IOException e) {
	 
	    }
	}
	
	public boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	public void onDisable() {
		
	}
}