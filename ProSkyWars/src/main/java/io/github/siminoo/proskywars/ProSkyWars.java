package io.github.siminoo.proskywars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ProSkyWars extends JavaPlugin implements Listener {
	public static Inventory SWSelector = Bukkit.createInventory(null, 27, "ProSkyWars menu");
	public String prefix = ChatColor.AQUA + "[SkyWars] -> ";
	public String perm = ChatColor.RED + "You don't have enough permissions!";
	public List<Player> queue = new ArrayList<Player>();
	boolean copied = false;
	public World arena;
	public int id;
	public int arenaID;
	ProSkyWars plugin;
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        this.getCommand("psw").setExecutor(new PSWCommand(this));
        if(getConfig().get("id") == null) {
        	getConfig().set("id", 0);
        	saveConfig();
        }
        if(getConfig().get("gamesPlayed") == null) {
    		getConfig().set("gamesPlayed", 0);
    		saveConfig();
    	}
        plugin = this;
	}
	
	@EventHandler
	public void onWorldLoad(final WorldLoadEvent e) {
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(e.getWorld().getName().contains(arena.getName())) {
        			final File from = Bukkit.getWorld(arena.getName()).getWorldFolder();
        			final File to = Bukkit.getWorld(arena.getName() + id).getWorldFolder();
        			if(copied) {
        				int i = 1;
        				for(Player p : queue) {
        					World world = Bukkit.getWorld(arena.getName() + id);
	        				double x = getConfig().getDouble("arenas." + arenaID + ".spawnpos." + i + ".x");
	        				double y = getConfig().getDouble("arenas." + arenaID + ".spawnpos." + i + ".y");
	        				double z = getConfig().getDouble("arenas." + arenaID + ".spawnpos." + i + ".z");
	        				float yaw = (float) getConfig().getDouble("arenas." + arenaID + ".spawnpos." + i + ".yaw");
	        				float pitch = (float) getConfig().getDouble("arenas." + arenaID + ".spawnpos." + i + ".pitch");
	        				teleportToArena(world, x, y, z, yaw, pitch, p);
        					i++;
        				}
        				i = 1;
        				World arena1 = Bukkit.getWorld(arena.getName() + id);
    					for(Chunk c : arena1.getLoadedChunks()) {
        					lootChest(id);
    					}
        				id = 0;
    					countdown();
    					for(Player p : arena1.getPlayers()) {
        					startGame(p);
    					}
        			}
        			else {
        				Bukkit.unloadWorld(arena.getName() + id, false);
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
	
	public void teleportToArena(World arena, double x, double y, double z, float yaw, float pitch, Player target) {
		target.teleport(new Location(arena, x, y, z, yaw, pitch));
		target.removeMetadata("WaitingForSkyWarsMatch", this);
		target.setMetadata("WaitingForSkyWars", new FixedMetadataValue(this, this));
		queue.remove(target);
	}
	
	public void startGame(Player target) {
		target.removeMetadata("WaitingForSkyWars", this);
		target.setMetadata("PlayingSkyWars", new FixedMetadataValue(this, this));
	}
	
	public void lootChest(int id) {
		int min = 4;
		int max = 15;
		int x = ThreadLocalRandom.current().nextInt(min, max + 1);
		for(int i = 0; i < x; i++) {
			List<String> items = this.getConfig().getStringList("ChestItems");
			int index = new Random().nextInt(items.size());
			String items1 = items.get(index);
			ItemStack item = new ItemStack(Material.getMaterial(items1));
			Location l = (Location) getConfig().get("arenas." + id + ".chests." + i+1);
			if(l.getBlock() instanceof Chest) {
				Chest chest = (Chest) l.getBlock();
				chest.getBlockInventory().addItem(item);
			}
		}
	}
	
	public void countdown() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			int time = 15;
			@Override
			public void run() {
				if(this.time == 0) {
					return;
				}
				for(Player p : Bukkit.getWorld(arena.getName() + id).getPlayers()) {
					p.sendMessage(prefix + ChatColor.GOLD + "Game starts in " + this.time + " second(s)!");
				}
			}
		}, 0L, 20L);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(e.getPlayer().hasMetadata("WaitingForSkyWars")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerPlace(BlockPlaceEvent e) {
		if(e.getPlayer().hasMetadata("EditingSkyWarsMap")) {
			if(e.getBlock().getType().equals(Material.CHEST)) {
				int id1 = getConfig().getInt(e.getPlayer().getName() + ".editing");
				if(getConfig().get("arenas." + id + ".chests." + ".count") == null) {
					getConfig().set("arenas." + id + ".chests." + ".count", 0);
					saveConfig();
				}
				int chestID = getConfig().getInt("arenas." + id + ".chests." + ".count");
				getConfig().set("arenas." + id1 + ".chests." + chestID + 1, e.getBlock().getLocation());
				getConfig().set("arenas." + id + ".chests." + ".count", chestID + 1);
				saveConfig();
			}
		}
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
							new WorldCreator("classicarena" + id).createWorld();
						}
	                }.runTask(this);
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
	
	public void onDisable() {
	}
}
