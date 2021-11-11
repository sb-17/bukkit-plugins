package io.github.siminoo.diamondhunt;

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
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DiamondHunt extends JavaPlugin {
	public List<Player> queue = new ArrayList<Player>();
	String prefix = ChatColor.AQUA + "[DiamondHunt] -> ";
	String permErr = ChatColor.RED + "You don't have enough permissions!";
	ItemStack leave = new ItemStack(Material.BED);
	DiamondHunt plugin;
	int id;
	boolean copied = false;
	Player playerInQueue;
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        ItemMeta leaveMeta = leave.getItemMeta();
    	leaveMeta.setDisplayName(ChatColor.RED + "Leave");
    	leave.setItemMeta(leaveMeta);
    	
    	if(getConfig().get("gamesPlayed") == null) {
    		getConfig().set("gamesPlayed", 0);
    		saveConfig();
    	}
    	
    	plugin = this;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("dh")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("setlobby")) {
					if(sender.hasPermission("diamondhunt.setlobby")) {
						getConfig().set("dh." + ".lobby." + ".world", p.getWorld().getName());
						getConfig().set("dh." + ".lobby." + ".x", p.getLocation().getX());
						getConfig().set("dh." + ".lobby." + ".y", p.getLocation().getY());
						getConfig().set("dh." + ".lobby." + ".z", p.getLocation().getZ());
						getConfig().set("dh." + ".lobby." + ".yaw", p.getLocation().getYaw());
						getConfig().set("dh." + ".lobby." + ".pitch", p.getLocation().getPitch());
						saveConfig();
						
						sender.sendMessage(prefix + ChatColor.GREEN + "Successfully set DiamondHunt lobby!");
					}
					else sender.sendMessage(prefix + permErr);
				}
				else if(args[0].equalsIgnoreCase("lobby")) {
					if(p.hasMetadata("PlayingDiamondHunt")) {
						p.sendMessage(prefix + ChatColor.RED + "You can't do this right now! '/dh leave' for leave");
					}
					else {
						teleportToLobby(p);
						p.sendMessage(prefix + ChatColor.RED + "Successfully teleported to the Diamond Hunt Lobby!");
					}
				}
				else if(args[0].equalsIgnoreCase("setspawnpos")) {
					if(p.hasPermission("diamondhunt.setspawnpos")) {
						getConfig().set("dh." + ".spawn." + ".world", p.getWorld().getName());
						getConfig().set("dh." + ".spawn." + ".x", p.getLocation().getX());
						getConfig().set("dh." + ".spawn." + ".y", p.getLocation().getY());
						getConfig().set("dh." + ".spawn." + ".z", p.getLocation().getZ());
						getConfig().set("dh." + ".spawn." + ".yaw", p.getLocation().getYaw());
						getConfig().set("dh." + ".spawn." + ".pitch", p.getLocation().getPitch());
						saveConfig();
						
						sender.sendMessage(prefix + ChatColor.GREEN + "Successfully set DiamondHunt arena spawn position!");
					}
				}
				else if(args[0].equalsIgnoreCase("join")) {
					if(queue.size() > 0) {
						p.sendMessage(prefix + ChatColor.RED + "Queue is full! Try it again in few seconds");
					}
					else {
						queue.add(p);
						p.sendMessage(prefix + ChatColor.GREEN + "Successfully added to queue!");
						playerInQueue = Bukkit.getPlayer(queue.get(0).getName());
						id = getConfig().getInt("gamesPlayed");
						final WorldCreator wc = new WorldCreator("diamondhuntarena" + id);
						wc.copy(Bukkit.getWorld(getConfig().getString("dh." + ".spawn." + ".world")));
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
				else if(args[0].equalsIgnoreCase("leave")) {
					if(p.hasMetadata("PlayingDiamondHunt")) {
						
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You are not playing Diamond Hunt!");
				}
				else sender.sendMessage(prefix + ChatColor.RED + "Syntax error!");
			}
			else sender.sendMessage(prefix + ChatColor.RED + "Syntax error!");
		}
		return true;
	}
	
	@EventHandler
	public void onWorldLoad(final WorldLoadEvent e) {
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(e.getWorld().getName().contains("diamondhuntarena")) {
        			final File from = Bukkit.getWorld("diamondhuntArena").getWorldFolder();
        			final File to = Bukkit.getWorld("diamondhuntarena" + id).getWorldFolder();
        			if(copied) {
        				teleportToArena();
        			}
        			else {
        				Bukkit.unloadWorld("diamondhuntarena" + id, false);
        				Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
        					public void run() {
        						copyWorld(from, to);
        					}
        				});
        			}
        		}
            }
		}.runTask(this);
	}
	
	@EventHandler
	public void onPlayerBlockBreak(BlockBreakEvent e) {
		if(e.getPlayer().hasMetadata("PlayingDiamondHunt")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if(e.getClickedBlock() instanceof Chest) {
				Chest chest = (Chest) e.getClickedBlock();
				if(chest.getBlockInventory().contains(Material.DIAMOND)) {
					e.getPlayer().sendMessage(prefix + ChatColor.GOLD + "You found the diamond! Congratulations. Now teleporting to the lobby");
					teleportToLobby(e.getPlayer());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addmoney 5 " + e.getPlayer().getName());
					e.getPlayer().removeMetadata("PlayingDiamondHunt", this);
					World arena = e.getPlayer().getWorld();
					Bukkit.unloadWorld(arena, false);
					deleteWorld(arena.getWorldFolder());
				}
			}
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
	
	public void teleportToArena() {
		playerInQueue.setMetadata("PlayingDiamondHunt", new FixedMetadataValue(this, this));
		World world = Bukkit.getWorld("diamondhuntarena" + id);
		double x = getConfig().getDouble("dh." + ".spawn." + ".x");
		double y = getConfig().getDouble("dh." + ".spawn." + ".y");
		double z = getConfig().getDouble("dh." + ".spawn." + ".z");
		float yaw = (float) getConfig().getDouble("dh." + ".spawn." + ".yaw");
		float pitch = (float) getConfig().getDouble("dh." + ".spawn." + ".pitch");
		
		for(Chunk c : world.getLoadedChunks()){
            for(BlockState b : c.getTileEntities()){
                if(b instanceof Chest) {
                	Chest chest = (Chest) b;
                	chest.getBlockInventory().addItem(new ItemStack(Material.DIAMOND));
                }
            }
        }
		
		playerInQueue.getInventory().clear();
		playerInQueue.teleport(new Location(world, x, y, z, yaw, pitch));
		playerInQueue.setGameMode(GameMode.SURVIVAL);
		playerInQueue.setHealth(playerInQueue.getMaxHealth());
		Player p = playerInQueue;
		queue.remove(playerInQueue);
		p.sendMessage(prefix + ChatColor.GREEN + "Successfully joined the Diamond Hunt!");
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
						@Override
						public void run() {
							new WorldCreator("diamondhuntarena" + id).createWorld();
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
	
	public void teleportToLobby(Player p) {
		World world = Bukkit.getWorld(getConfig().getString("dh." + ".lobby." + ".world"));
		double x = getConfig().getDouble("dh." + ".lobby." + ".x");
		double y = getConfig().getDouble("dh." + ".lobby." + ".y");
		double z = getConfig().getDouble("dh." + ".lobby." + ".z");
		float yaw = (float) getConfig().getDouble("dh." + ".lobby." + ".yaw");
		float pitch = (float) getConfig().getDouble("dh." + ".lobby." + ".pitch");
		p.teleport(new Location(world, x, y, z, yaw, pitch));
		p.setMetadata("DiamondHuntLobby", new FixedMetadataValue(this, this));
	}
	
	public void onDisable() {
		
	}
}
