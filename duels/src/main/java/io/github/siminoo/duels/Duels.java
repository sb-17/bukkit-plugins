package io.github.siminoo.duels;

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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public final class Duels extends JavaPlugin implements Listener {
	String prefix = ChatColor.AQUA + "[Duels] -> ";
	String permErr = ChatColor.RED + "You don't have enough permissions!";
	ItemStack leaveKP = new ItemStack(Material.BED);
	
	public List<Player> queueClassic = new ArrayList<Player>();
	
	int id;
	
	Player p1;
	Player p2;
	
	Duels plugin;
	
	boolean copied = false;
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        
        ItemMeta leaveKPMeta = leaveKP.getItemMeta();
    	leaveKPMeta.setDisplayName(ChatColor.RED + "Leave");
    	leaveKP.setItemMeta(leaveKPMeta);
    	
    	if(getConfig().get("gamesPlayed") == null) {
    		getConfig().set("gamesPlayed", 0);
    		saveConfig();
    	}
    	
    	plugin = this;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("duels")) {
			if(sender instanceof Player) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("setspawn1")) {
						if(sender.hasPermission("duels.setpos1")) {
							getConfig().set("classicArena." + ".pos1." + ".world", p.getWorld().getName());
							getConfig().set("classicArena." + ".pos1." + ".x", p.getLocation().getX());
							getConfig().set("classicArena." + ".pos1." + ".y", p.getLocation().getY());
							getConfig().set("classicArena." + ".pos1." + ".z", p.getLocation().getZ());
							getConfig().set("classicArena." + ".pos1." + ".yaw", p.getLocation().getYaw());
							getConfig().set("classicArena." + ".pos1." + ".pitch", p.getLocation().getPitch());
							saveConfig();
							sender.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.GREEN + "Successfully set SpawnPos1!");
						}
						else sender.sendMessage(prefix + permErr);
					}
					else if(args[0].equalsIgnoreCase("setspawn2")) {
						if(sender.hasPermission("duels.setpos2")) {
							getConfig().set("classicArena." + ".pos2." + ".world", p.getWorld().getName());
							getConfig().set("classicArena." + ".pos2." + ".x", p.getLocation().getX());
							getConfig().set("classicArena." + ".pos2." + ".y", p.getLocation().getY());
							getConfig().set("classicArena." + ".pos2." + ".z", p.getLocation().getZ());
							getConfig().set("classicArena." + ".pos2." + ".yaw", p.getLocation().getYaw());
							getConfig().set("classicArena." + ".pos2." + ".pitch", p.getLocation().getPitch());
							saveConfig();
							sender.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.GREEN + "Successfully set SpawnPos1!");
						}
						else sender.sendMessage(prefix + permErr);
					}
					else if(args[0].equalsIgnoreCase("join")) {
						if(queueClassic.contains(p)) {
							p.sendMessage(prefix + ChatColor.RED + "You already are in queue!");
						}
						else {
							p.setMetadata("WaitingForDuel", new FixedMetadataValue(this, this));
							queueClassic.add(p);
							p.sendMessage(prefix + ChatColor.GREEN + "Successfully added to queue!");
							if(queueClassic.size() == 2) {
								p1 = Bukkit.getPlayer(queueClassic.get(0).getName());
								p2 = Bukkit.getPlayer(queueClassic.get(1).getName());
								
								id = getConfig().getInt("gamesPlayed");
								final WorldCreator wc = new WorldCreator("classicarena" + id);
								wc.copy(Bukkit.getWorld("duelArena"));
								Bukkit.getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
									public void run() {
										wc.createWorld();
									}
								});
								int current = getConfig().getInt("gamesPlayed");
								getConfig().set("gamesPlayed", current + 1);
								saveConfig();
							}
							else sender.sendMessage(prefix + ChatColor.GOLD + "Waiting for another player!");
						}
					}
					else if(args[0].equalsIgnoreCase("setlobby")) {
						if(sender.hasPermission("duels.setlobby")) {
							getConfig().set("lobby." + ".world", p.getWorld().getName());
							getConfig().set("lobby." + ".x", p.getLocation().getX());
							getConfig().set("lobby." + ".y", p.getLocation().getY());
							getConfig().set("lobby." + ".z", p.getLocation().getZ());
							getConfig().set("lobby." + ".yaw", p.getLocation().getYaw());
							getConfig().set("lobby." + ".pitch", p.getLocation().getPitch());
							saveConfig();
							
							sender.sendMessage(prefix + ChatColor.GREEN + "Successfully set Duels lobby!");
						}
						else sender.sendMessage(prefix + permErr);
					}
					else if(args[0].equalsIgnoreCase("edit")) {
						if(sender.hasPermission("duels.edit")) {
							p.setMetadata("EditingDuelArena", new FixedMetadataValue(this, this));
							Bukkit.dispatchCommand(p, "mvtp duelArena");
						}
						else sender.sendMessage(prefix + permErr);
					}
					else if(args[0].equalsIgnoreCase("save")) {
						if(sender.hasPermission("duels.save")) {
							p.removeMetadata("EditingDuelArena", this);
							World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
							double x = getConfig().getDouble("lobby." + ".x");
							double y = getConfig().getDouble("lobby." + ".y");
							double z = getConfig().getDouble("lobby." + ".z");
							float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
							float pitch = (float) getConfig().getDouble("lobby." + ".pitch");
							
							p.sendMessage(prefix + ChatColor.GREEN + "Successfully saved Duel Arena!");
							
							p.teleport(new Location(world, x, y, z, yaw, pitch));
							
							createScoreboard(p);
							p.getInventory().clear();
							p.setMetadata("DuelsLobby", new FixedMetadataValue(this, this));
							p.getInventory().setItem(8, leaveKP);
						}
						else sender.sendMessage(prefix + permErr);
					}
					else sender.sendMessage(prefix + ChatColor.RED + "Syntax Error!");
				}
				else sender.sendMessage(prefix + ChatColor.RED + "Syntax Error!");
			}
			else sender.sendMessage(prefix + ChatColor.RED + "You are not a player!");
		}
		return true;
	}
	
	@EventHandler
	public void onWorldLoad(final WorldLoadEvent e) {
		new BukkitRunnable() {
            @Override
            public void run() {
        		if(e.getWorld().getName().contains("classicarena")) {
        			final File from = Bukkit.getWorld("duelArena").getWorldFolder();
        			final File to = Bukkit.getWorld("classicarena" + id).getWorldFolder();
        			if(copied) {
        				teleportToArena();
        			}
        			else {
        				Bukkit.unloadWorld("classicarena" + id, false);
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
		
	public void teleportToArena() {
		if(p1.hasMetadata("WaitingForDuel")) {
			p1.removeMetadata("WaitingForDuel", plugin);
			p1.setMetadata("PlayingDuel", new FixedMetadataValue(plugin, this));
			
			World world = Bukkit.getWorld("classicarena" + id);
			double x = getConfig().getDouble("classicArena." + ".pos1." + ".x");
			double y = getConfig().getDouble("classicArena." + ".pos1." + ".y");
			double z = getConfig().getDouble("classicArena." + ".pos1." + ".z");
			float yaw = (float) getConfig().getDouble("classicArena." + ".pos1." + ".yaw");
			float pitch = (float) getConfig().getDouble("classicArena." + ".pos1." + ".pitch");

			p1.getInventory().clear();
			
			p1.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			p1.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			p1.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			p1.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			p1.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			p1.getInventory().setItem(1, new ItemStack(Material.FISHING_ROD));
			p1.getInventory().setItem(3, new ItemStack(Material.GOLDEN_APPLE, 8));
			
			p1.teleport(new Location(world, x, y, z, yaw, pitch));
			
			p1.setGameMode(GameMode.SURVIVAL);
			p1.setHealth(p1.getMaxHealth());

			queueClassic.remove(p1);
			
			p1.sendMessage(prefix + ChatColor.GREEN + "Successfully joined the Duel!");
		}
		else {
			queueClassic.remove(p1);
			queueClassic.remove(p2);
			
			p2.sendMessage(prefix + ChatColor.RED + "Duel cancelled!");
		}
		
		if(p2.hasMetadata("WaitingForDuel")) {
			p2.removeMetadata("WaitingForDuel", plugin);
			p2.setMetadata("PlayingDuel", new FixedMetadataValue(plugin, this));
			
			World world = Bukkit.getWorld("classicarena" + id);
			double x = getConfig().getDouble("classicArena." + ".pos2." + ".x");
			double y = getConfig().getDouble("classicArena." + ".pos2." + ".y");
			double z = getConfig().getDouble("classicArena." + ".pos2." + ".z");
			float yaw = (float) getConfig().getDouble("classicArena." + ".pos2." + ".yaw");
			float pitch = (float) getConfig().getDouble("classicArena." + ".pos2." + ".pitch");

			p2.getInventory().clear();
			
			p2.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			p2.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			p2.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			p2.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			p2.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
			p2.getInventory().setItem(1, new ItemStack(Material.FISHING_ROD));
			p2.getInventory().setItem(3, new ItemStack(Material.GOLDEN_APPLE, 8));
			
			p2.teleport(new Location(world, x, y, z, yaw, pitch));
			
			p2.setGameMode(GameMode.SURVIVAL);
			p2.setHealth(p2.getMaxHealth());
			queueClassic.remove(p2);
			
			p2.sendMessage(prefix + ChatColor.GREEN + "Successfully joined the Duel!");
		}
		else {
			queueClassic.remove(p1);
			queueClassic.remove(p2);
			
			p1.sendMessage(prefix + ChatColor.RED + "Duel cancelled!");
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
		if (path.exists()) {
			File files[] = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteWorld(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(getConfig().get("players." + p.getName()) == null) {
			getConfig().set("players." + p.getName() + ".wins", 0);
			getConfig().set("players." + p.getName() + ".loses", 0);
			saveConfig();
		}
		else {
			return;
		}
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			if(e.getDamager().hasMetadata("PlayingDuel") && e.getEntity().hasMetadata("PlayingDuel")) {
				Player killer = (Player) e.getDamager();
				Player killed = (Player) e.getEntity();
				if(killed.getHealth() - e.getDamage() < 0.1) {
					e.setCancelled(true);
					killer.removeMetadata("PlayingDuel", this);
					killer.setMetadata("DuelsLobby", new FixedMetadataValue(this, this));
					int killerWins = getConfig().getInt("players." + killer.getName() + ".wins");
					int killedLoses = getConfig().getInt("players." + killer.getName() + ".loses");
					
					getConfig().set("players." + killer.getName() + ".wins", killerWins + 1);
					getConfig().set("players." + killed.getName() + ".loses", killedLoses + 1);
					
					World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
					double x = getConfig().getDouble("lobby." + ".x");
					double y = getConfig().getDouble("lobby." + ".y");
					double z = getConfig().getDouble("lobby." + ".z");
					float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
					float pitch = (float) getConfig().getDouble("lobby." + ".pitch");
					
					killer.getInventory().clear();
					killed.getInventory().clear();
					
					killer.sendMessage(prefix + ChatColor.GOLD + "Congratulations, you won the Duel! Now teleporting to Duels lobby.");
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addmoney 5 " + killer.getName());
					
					World arena = killer.getWorld();
					
					killer.teleport(new Location(world, x, y, z, yaw, pitch));
					
					killed.removeMetadata("PlayingDuel", this);
					killed.setMetadata("DuelsLobby", new FixedMetadataValue(this, this));

					killed.getInventory().setItem(8, leaveKP);
					killer.getInventory().setItem(8, leaveKP);
					
					world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
					x = getConfig().getDouble("lobby." + ".x");
					y = getConfig().getDouble("lobby." + ".y");
					z = getConfig().getDouble("lobby." + ".z");
					yaw = (float) getConfig().getDouble("lobby." + ".yaw");
					pitch = (float) getConfig().getDouble("lobby." + ".pitch");
					
					killed.sendMessage(prefix + ChatColor.GOLD + "Better luck next time! Now teleporting to Duels lobby.");
					
					killed.teleport(new Location(world, x, y, z, yaw, pitch));
					
					killed.setHealth(killed.getMaxHealth());
					killer.setHealth(killer.getMaxHealth());
					
					for(Player p : arena.getPlayers()) {
						world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
						x = getConfig().getDouble("lobby." + ".x");
						y = getConfig().getDouble("lobby." + ".y");
						z = getConfig().getDouble("lobby." + ".z");
						yaw = (float) getConfig().getDouble("lobby." + ".yaw");
						pitch = (float) getConfig().getDouble("lobby." + ".pitch");
						
						p.sendMessage(prefix + ChatColor.GOLD + "Game has ended!");
						
						p.teleport(new Location(world, x, y, z, yaw, pitch));
					}
					
					Bukkit.unloadWorld(arena, false);
					deleteWorld(arena.getWorldFolder());
				}
			}
			else {
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if(e.getPlayer().hasMetadata("DuelsLobby")) {
			if(item == null) return;
			if(item.getType() == Material.BED) {
				e.setCancelled(true);
				Bukkit.dispatchCommand(p, "lobby");
				e.getPlayer().removeMetadata("DuelsLobby", this);
			}
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Location to = e.getTo();
		
		if(to.getWorld().getName() == getConfig().getString("lobby." + ".world")) {
			createScoreboard(e.getPlayer());
			e.getPlayer().getInventory().clear();
			e.getPlayer().setMetadata("DuelsLobby", new FixedMetadataValue(this, this));
			e.getPlayer().getInventory().setItem(8, leaveKP);
		}
		else {
			return;
		}
	}
	
	@EventHandler
	public void onPlayerBlockBreak(BlockBreakEvent e) {
		if(e.getPlayer().hasMetadata("PlayingDuel")) {
			e.setCancelled(true);
		}
	}
	
	public void createScoreboard(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard duelsLobbyBoard = m.getNewScoreboard();
		Objective duels = duelsLobbyBoard.registerNewObjective(ChatColor.AQUA + "Duels", "");
		duels.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score wins = duels.getScore(ChatColor.GOLD + "Wins:" + ChatColor.RED);
		wins.setScore(getConfig().getInt("players." + player.getName() + ".wins"));
		Score loses = duels.getScore(ChatColor.GOLD + "Loses:" + ChatColor.RED);
		loses.setScore(getConfig().getInt("players." + player.getName() + ".loses"));
		
		player.setScoreboard(duelsLobbyBoard);
	}

	@Override
	public void onDisable() {
		
	}
}