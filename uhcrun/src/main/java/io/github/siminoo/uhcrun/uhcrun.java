package io.github.siminoo.uhcrun;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class uhcrun extends JavaPlugin implements Listener {
	public ArrayList<Player> queueSolo = new ArrayList<Player>();
	public ArrayList<Player> queueDuo = new ArrayList<Player>();
	public String prefix = ChatColor.AQUA + "[UHCRun] -> ";
	public String syntaxErr = ChatColor.RED + "Syntax Error!";
	public String permErr = ChatColor.RED + "You don't have enough permissions!";
	World world;
	public ItemStack leaveGameBed = new ItemStack(Material.BED);
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        if(getConfig().get("gamesPlayed") == null) {
        	getConfig().set("gamesPlayed", 0);
        	saveConfig();
        }
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        
        ItemMeta leaveGameBedMeta = leaveGameBed.getItemMeta();
        leaveGameBedMeta.setDisplayName(ChatColor.RED + "Leave");
        leaveGameBed.setItemMeta(leaveGameBedMeta);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("uhcrun")) {
			if(args.length == 0) {
				sender.sendMessage(prefix + syntaxErr);
			}
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("leave")) {
					Player p = (Player) sender;
					if(queueSolo.contains(p)) {
						queueSolo.remove(p);
						for(Player player : queueSolo) {
							player.sendMessage(prefix + ChatColor.RED + p.getName() + " left the game!");
						}
						p.removeMetadata("WaitingForUhcRun", this);
					}
					else if(p.hasMetadata("PlayingUhcRun")) {
						for(Player player : p.getWorld().getPlayers()) {
							player.sendMessage(prefix + ChatColor.RED + p.getName() + " left the game!");
						}
						p.removeMetadata("PlayingUhcRun", this);
						Bukkit.getServer().dispatchCommand(p, "uhcrun lobby");
					}
					else if(p.hasMetadata("PlayingUhcRun1")) {
						for(Player player : p.getWorld().getPlayers()) {
							player.sendMessage(prefix + ChatColor.RED + p.getName() + " left the game!");
						}
						p.removeMetadata("PlayingUhcRun1", this);
						Bukkit.getServer().dispatchCommand(p, "uhcrun lobby");
					}
					else if(p.hasMetadata("SpectatingUhcRun")) {
						p.removeMetadata("SpectatingUhcRun", this);
						Bukkit.getServer().dispatchCommand(p, "uhcrun lobby");
					}
				}
				else if(args[0].equalsIgnoreCase("setlobby")) {
					if(sender.hasPermission("uhcrun.setlobby")) {
						Player player = (Player) sender;
						getConfig().set("lobby." + ".world", player.getWorld().getName());
		                getConfig().set("lobby." + ".x", player.getLocation().getX());
		                getConfig().set("lobby." + ".y", player.getLocation().getY());
		                getConfig().set("lobby." + ".z", player.getLocation().getZ());
		                getConfig().set("lobby." + ".yaw", player.getLocation().getYaw());
		                getConfig().set("lobby." + ".pitch", player.getLocation().getPitch());
		                saveConfig();
		                sender.sendMessage(prefix + ChatColor.GREEN + "Successfuly set " + ChatColor.DARK_RED + " UHCRun lobby!");
					}
				}
				else if(args[0].equalsIgnoreCase("lobby")) {
					Player player = (Player) sender;
					World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
					double x = getConfig().getDouble("lobby." + ".x");
					double y = getConfig().getDouble("lobby." + ".y");
					double z = getConfig().getDouble("lobby." + ".z");
					float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
					float pitch = (float) getConfig().getDouble("lobby." + ".pitch");

					player.teleport(new Location(world, x, y, z, yaw, pitch));

					player.sendMessage(prefix + ChatColor.GREEN+"You have successfully left the game!");
					
					player.getInventory().clear();
					
					player.getActivePotionEffects().clear();
					
					createScoreboard(player);
				}
			}
			else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("join")) {
					if(args[1].equalsIgnoreCase("solo")) {
						if(sender instanceof Player) {
							Player p = (Player) sender;
							if(queueSolo.contains(p)) {
								p.sendMessage(prefix + ChatColor.RED + "You are already in the queue!");
							}
							else {
								queueSolo.add(p);
								p.sendMessage(prefix + ChatColor.GREEN + "Successfully added into the queue!");
								p.setMetadata("WaitingForUhcRun", new FixedMetadataValue(this, this));
								for(Player player : queueSolo) {
									player.sendMessage(prefix + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " joined the game!");
								}
								if(queueSolo.size() == 1) {
									Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
							            int time = 60;

							            @Override
							            public void run() {
							                if (this.time == 0) {
							                    return;
							                }
							               
							                for (final Player player : queueSolo) {
												player.sendMessage(prefix + ChatColor.GREEN + "Game starts in " + this.time + " second(s)!");
							                }
							               
							                this.time--;
							            }
							        }, 0L, 20L);
									int arenaID = getConfig().getInt("gamesPlayed") + 1;
									getConfig().set("gamesPlayed", arenaID + 1);
									saveConfig();
									WorldCreator wc = new WorldCreator("uhcrunArena" + arenaID);
									wc.type(WorldType.NORMAL);
									wc.generateStructures(true);
									world = wc.createWorld();
									WorldBorder border = world.getWorldBorder();
									border.setSize(500.0);
									border.setCenter(0.0, 0.0);
									
									for(Player player : queueSolo) {
										int randomX = new Random(100).nextInt();
										int randomZ = new Random(100).nextInt();
										Location loc = null;
										for (int y = 30; y < 100; y++) {
											loc = new Location(world, randomX, y, randomZ);
											if (loc.getBlock() == null && loc.add(0,1,0).getBlock() == null) {
												player.teleport(loc);
												player.removeMetadata("WaitingForUhcRun", this);
												player.setMetadata("PlayingUhcRun", new FixedMetadataValue(this, this));
												player.sendMessage(prefix + ChatColor.GREEN + "Game Started!");
												getConfig().set("players." + player.getName() + ".currentKills", 0);
												saveConfig();
												createScoreboardInGame(player);
											} 
											else {
												if (loc.getBlock().getType() == Material.AIR && loc.add(0,1,0).getBlock().getType() == Material.AIR) {
													player.teleport(loc);
													player.removeMetadata("WaitingForUhcRun", this);
													player.setMetadata("PlayingUhcRun", new FixedMetadataValue(this, this));
													player.sendMessage(prefix + ChatColor.GREEN + "Game Started!");
													getConfig().set("players." + player.getName() + ".currentKills", 0);
													saveConfig();
													createScoreboardInGame(player);
												}
											}
											break;
										}
									}
									wait(world);
									for(Player player : world.getPlayers()) {
										player.sendMessage(prefix + ChatColor.GREEN + "PVP enabled!");
										if(player.hasMetadata("PlayingUhcRun" )) {
											player.setMetadata("PlayingUhcRun1", new FixedMetadataValue(this, this));
										}
									}
									gameStart(world);
									queueSolo.clear();
								}
							}
						}
					}
					else if(args[1].equalsIgnoreCase("duo")) {
						if(sender instanceof Player) {
							sender.sendMessage(prefix + syntaxErr);
						}
					}
					else sender.sendMessage(prefix + syntaxErr);
				}
			}
		}
		return true;
	}
	
	public void gameStart(final World world) {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            int time = 500;
            int i = 499;

            @Override
            public void run() {
                if (this.time == 0) {
                    return;
                }
                
                WorldBorder border = world.getWorldBorder();
                border.setCenter(0.0, 0.0);
                border.setSize(i);
               
                this.time--;
                this.i--;
            }
        }, 0L, 20L);
	}
	
	public void wait(final World world) {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            int time = 30;

            @Override
            public void run() {
                if (this.time == 0) {
                    return;
                }
               
                this.time--;
            }
        }, 0L, 20L);
	}
	
	public void createScoreboard(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard uhcrunLobbyBoard = m.getNewScoreboard();
		Objective uhcrun = uhcrunLobbyBoard.registerNewObjective(ChatColor.AQUA + "UHCRun", "");
		uhcrun.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score wins = uhcrun.getScore(ChatColor.GOLD + "Wins:" + ChatColor.RED);
		wins.setScore(getConfig().getInt("players." + player.getName() + ".wins"));
		Score kills = uhcrun.getScore(ChatColor.GOLD + "Kills:" + ChatColor.RED);
		kills.setScore(getConfig().getInt("players." + player.getName() + ".kills"));
		
		player.setScoreboard(uhcrunLobbyBoard);
	}
	
	public void createScoreboardInGame(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard uhcrunGameBoard = m.getNewScoreboard();
		Objective uhcrun = uhcrunGameBoard.registerNewObjective(ChatColor.AQUA + "UHCRun", "");
		uhcrun.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score players = uhcrun.getScore(ChatColor.GOLD + "Players Left:" + ChatColor.RED);
		players.setScore(player.getWorld().getPlayers().size());
		Score kills = uhcrun.getScore(ChatColor.GOLD + "Kills:" + ChatColor.RED);
		kills.setScore(getConfig().getInt("players." + player.getName() + ".kills"));
		
		player.setScoreboard(uhcrunGameBoard);
	}
	
	public void updateScoreboardInGame(Player player) {
		Score players = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GOLD + "Players Left:" + ChatColor.RED);
		players.setScore(player.getWorld().getPlayers().size());
		Score kills = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GOLD + "Kills:" + ChatColor.RED);
		kills.setScore(getConfig().getInt("players." + player.getName() + ".kills"));
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(getConfig().get("players." + p.getName()) == null) {
			getConfig().set("players." + p.getName() + ".wins", 0);
			getConfig().set("players." + p.getName() + ".kills", 0);
			getConfig().set("players." + p.getName() + ".currentKills", 0);
			saveConfig();
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if(queueSolo.contains(e.getPlayer())) {
			queueSolo.remove(e.getPlayer());
			for(Player p : queueSolo) {
				p.sendMessage(prefix + ChatColor.RED + e.getPlayer().getName() + " left the game!");
			}
		}
		else if(queueDuo.contains(e.getPlayer())) {
			queueSolo.remove(e.getPlayer());
			for(Player p : queueSolo) {
				p.sendMessage(prefix + ChatColor.RED + e.getPlayer().getName() + " left the game!");
			}
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if(e.getPlayer().hasMetadata("WaitingForUhcRun") || e.getPlayer().hasMetadata("PlayingUhcRun") || e.getPlayer().hasMetadata("SpectatingUhcRun")) {
			Bukkit.getServer().dispatchCommand(e.getPlayer(), "uhcrun leave");
		}
		if(e.getTo().getWorld().getName() == getConfig().getString("lobby." + ".world")) {
			createScoreboard(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerMine(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p instanceof Player) {
			if(p.hasMetadata("PlayingUhcRun")) {
				if(e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
					e.setDropItems(false);
					p.getInventory().addItem(new ItemStack(Material.DIAMOND));
				}
				else if(e.getBlock().getType().equals(Material.IRON_ORE)) {
					e.setDropItems(false);
					p.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
				}
				else if(e.getBlock().getType().equals(Material.GOLD_ORE)) {
					e.setDropItems(false);
					p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
				}
				else if(e.getBlock().getType().equals(Material.EMERALD_ORE)) {
					e.setDropItems(false);
					p.giveExpLevels(2);
					p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE));
				}
				else if(e.getBlock().getType().equals(Material.COAL_ORE)) {
					e.setDropItems(false);
					p.getInventory().addItem(new ItemStack(Material.TORCH));
				}
			}
			else if(p.hasMetadata("SpectatingUhcRun")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerPlace(BlockPlaceEvent e) {
		if(e.getPlayer().hasMetadata("SpectatingUhcRun")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		if(e.getPlayer().hasMetadata("SpectatingUhcRun")) {
			e.setCancelled(true);
		}
		if(e.getPlayer().getLocation().getWorld().getName() == getConfig().getString("lobby." + ".world")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player || e.getEntity() instanceof Player) {
			Player killed = (Player) e.getEntity();
			Player killer = (Player) e.getDamager();
			if(killed.hasMetadata("PlayingUhcRun1") || killer.hasMetadata("PlayingUhcRun1")) {
				if(killed.getHealth() - e.getDamage() < 1) {
					e.setCancelled(true);
					killed.removeMetadata("PlayingUhcRun", this);
					killed.getInventory().clear();
					killed.setMetadata("SpectatingUhcRun", new FixedMetadataValue(this, this));
					killed.teleport(new Location(killed.getWorld(), 0, 66, 0, 0, 0));
					killed.setAllowFlight(true);
					killed.setFlying(true);
					killed.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0), true);
					killed.getInventory().setItem(8, leaveGameBed);
					int kills = getConfig().getInt("players." + killer.getName() + ".kills");
					getConfig().set("players." + killer.getName() + ".kills", kills + 1);
					int currentKills = getConfig().getInt("players." + killer.getName() + ".currentKills");
					getConfig().set("players." + killer.getName() + ".currentKills", currentKills + 1);
					saveConfig();
					int i = 0;
					for(Player p : killed.getWorld().getPlayers()) {
						p.sendMessage(prefix + ChatColor.GREEN + killer.getName() + ChatColor.DARK_AQUA + " -> " + ChatColor.RED + killed.getName());
						if(p.hasMetadata("PlayingUhcRun")) {
							i++;
						}
						updateScoreboardInGame(p);
					}
					if(i>1) {
						for(Player p : killed.getWorld().getPlayers()) {
							p.sendMessage(prefix + ChatColor.GREEN + "Only " + ChatColor.GOLD + i + ChatColor.GREEN + " player(s) remaining!");
							updateScoreboardInGame(p);
						}
					}
					else if(i==1) {
						for(Player p : killed.getWorld().getPlayers()) {
							p.sendMessage(prefix + ChatColor.GOLD + killer.getName() + ChatColor.GREEN + " won the game! Congratulations!");
							updateScoreboardInGame(p);
						}
						Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
				            int time = 60;
				            int i = 499;

				            @Override
				            public void run() {
				                if (this.time == 0) {
				                    return;
				                }
				               
				                this.time--;
				            }
				        }, 0L, 60L);
						for(Player p : killed.getWorld().getPlayers()) {
							Bukkit.dispatchCommand(p, "uhcrun leave");
						}
						int wins = getConfig().getInt("players." + killer.getName() + ".wins");
						getConfig().set("players." + killer.getName() + ".wins", wins + 1);
						saveConfig();
					}
				}
			}
			else if(killed.hasMetadata("PlayingUhcRun") || killer.hasMetadata("PlayingUhcRun")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeathNotByPlayer(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			Player killed = (Player) e.getEntity();
			if(e.getEntity().hasMetadata("PlayingUhcRun")) {
				killed.setHealth(killed.getMaxHealth());
				e.getEntity().removeMetadata("PlayingUhcRun", this);
				killed.getInventory().clear();
				killed.setMetadata("SpectatingUhcRun", new FixedMetadataValue(this, this));
				killed.teleport(new Location(killed.getWorld(), 0, 66, 0, 0, 0));
				killed.setAllowFlight(true);
				killed.setFlying(true);
				killed.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0), true);
				killed.getInventory().setItem(8, leaveGameBed);
				e.setDeathMessage(null);
				int i = 0;
				for(Player p : killed.getWorld().getPlayers()) {
					p.sendMessage(prefix + ChatColor.RED + e.getEntity().getName() + " died");
					if(p.hasMetadata("PlayingUhcRun") || p.hasMetadata("PlayingUhcRun")) {
						i++;
					}
					updateScoreboardInGame(p);
				}
				if(i>1) {
					for(Player p : killed.getWorld().getPlayers()) {
						p.sendMessage(prefix + ChatColor.GREEN + "Only " + ChatColor.GOLD + i + ChatColor.GREEN + " player(s) remaining!");
						updateScoreboardInGame(p);
					}
				}
				else if(i==1) {
					for(Player p : killed.getWorld().getPlayers()) {
						if(p.hasMetadata("PlayingUhcRun") || p.hasMetadata("PlayingUhcRun1")) {
							Bukkit.broadcast(prefix + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " won the game! Congratulations!", p.getWorld().getPlayers().toString());
							updateScoreboardInGame(p);
						}
					}
					Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			            int time = 60;

			            @Override
			            public void run() {
			                if (this.time == 0) {
			                    return;
			                }
			               
			                this.time--;
			            }
			        }, 0L, 60L);
					for(Player p : killed.getWorld().getPlayers()) {
						Bukkit.dispatchCommand(p, "uhcrun leave");
						p.getActivePotionEffects().clear();
					}
				}
				else if(i==0) {
					for(Player p : killed.getWorld().getPlayers()) {
						p.sendMessage(prefix + ChatColor.RED + "Nobody won the game! :D");
					}
					Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			            int time = 60;

			            @Override
			            public void run() {
			                if (this.time == 0) {
			                    return;
			                }
			               
			                this.time--;
			            }
			        }, 0L, 60L);
					for(Player p : killed.getWorld().getPlayers()) {
						Bukkit.dispatchCommand(p, "uhcrun leave");
					}
				}
			}
			else if(e.getEntity().hasMetadata("PlayingUhcRun1")) {
				e.getEntity().removeMetadata("PlayingUhcRun1", this);
				killed.getInventory().clear();
				killed.setMetadata("SpectatingUhcRun", new FixedMetadataValue(this, this));
				killed.teleport(new Location(killed.getWorld(), 0, 66, 0, 0, 0));
				killed.setAllowFlight(true);
				killed.setFlying(true);
				killed.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0), true);
				killed.getInventory().setItem(8, leaveGameBed);
				e.setDeathMessage(null);
				int i = 0;
				for(Player p : killed.getWorld().getPlayers()) {
					p.sendMessage(prefix + ChatColor.RED + e.getEntity().getName() + " died");
					if(p.hasMetadata("PlayingUhcRun") || p.hasMetadata("PlayingUhcRun1")) {
						i++;
					}
					updateScoreboardInGame(p);
				}
				if(i>1) {
					for(Player p : killed.getWorld().getPlayers()) {
						p.sendMessage(prefix + ChatColor.GREEN + "Only " + ChatColor.GOLD + i + ChatColor.GREEN + " player(s) remaining!");
						updateScoreboardInGame(p);
					}
				}
				else if(i==1) {
					for(Player p : killed.getWorld().getPlayers()) {
						if(p.hasMetadata("PlayingUhcRun") || p.hasMetadata("PlayingUhcRun1")) {
							Bukkit.broadcast(prefix + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " won the game! Congratulations!", p.getWorld().getPlayers().toString());
							updateScoreboardInGame(p);
						}
					}
					Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			            int time = 60;
			            int i = 499;

			            @Override
			            public void run() {
			                if (this.time == 0) {
			                    return;
			                }
			               
			                this.time--;
			            }
			        }, 0L, 60L);
					for(Player p : killed.getWorld().getPlayers()) {
						Bukkit.dispatchCommand(p, "uhcrun leave");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getPlayer().hasMetadata("SpectatingUhcRun")) {
			if(e.getItem().getType().equals(Material.BED)) {
				Bukkit.getServer().dispatchCommand(e.getPlayer(), "uhcrun leave");
			}
		}
	}
	
	public void onDisable() {
		
	}
}
