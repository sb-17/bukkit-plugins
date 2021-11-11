package io.github.siminoo.theoryofeverything;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TheoryOfEverything extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mnginv")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission("invmng.use")) {
						Player target = (Player) sender;
						target.sendMessage(ChatColor.GREEN + "You can see someone's inventory!");
					} 
					else {
						sender.sendMessage(ChatColor.RED + "You haven't got permission!");
						return true;
					}
				}
				
			}
			else if(args.length == 1) {
				if(sender.hasPermission("invmng.use")) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						if(sender instanceof Player) {
							Inventory targetInv = target.getInventory();
							((Player) sender).openInventory(targetInv);
						}
					}
					else {
						sender.sendMessage("Player is not online!");
					}
				}
			}
			else {
				sender.sendMessage("Too many arguments!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("setlobby")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("lobby.set")) {
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
	                player.sendMessage(ChatColor.GREEN + "Successfuly set " + ChatColor.DARK_RED + "lobby!");
				}
				else {
					sender.sendMessage(ChatColor.RED + "You haventn't got enough permissions!");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "You are not a player!");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("lobby")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("lobby.teleport")) {
					if (getConfig().getConfigurationSection("lobby.") != null) {
						Player player = (Player) sender;
						World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
						double x = getConfig().getDouble("lobby." + ".x");
						double y = getConfig().getDouble("lobby." + ".y");
						double z = getConfig().getDouble("lobby." + ".z");
						float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
						float pitch = (float) getConfig().getDouble("lobby." + ".pitch");

						player.teleport(new Location(world, x, y, z, yaw, pitch));

						player.sendMessage(ChatColor.GREEN+"You have successfully teleported to the Lobby!");
					}
					else {
						sender.sendMessage("Lobby is not set!");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "You haventn't got enough permissions!");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "You are not a player!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("kill")) {
			if(args.length == 0) {
				if(sender.hasPermission("immunity.kill")) {
					((Player) sender).setHealth(0.0D);
				}
				else {
					sender.sendMessage(ChatColor.RED + "You haven't got permission!");
					return true;
				}				
			}
			else if(args.length == 1) {
				if (sender.hasPermission("immunity.kill")) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						if(target.hasPermission("immunity.immune")) {
							sender.sendMessage(ChatColor.RED + target.getName() + " is immune!"); }
						else {
							target.setHealth(0.0D);
							target.sendMessage(ChatColor.BLUE + "You killed " + target.getName());
						}
					}
					else {
						sender.sendMessage("Player is not online!");
					}
				}
				else {
					sender.sendMessage("You don't have enough permissions!");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "Too many arguments!");
				return true;
			}
		}
		else if (cmd.getName().equalsIgnoreCase("Fly")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission("fly.use")) {
						Player target = (Player) sender;
						if(target.getAllowFlight()) {
							target.setFlying(false);
							target.setAllowFlight(false);
							target.sendMessage(ChatColor.RED + "You can't fly!");
							return true;
						}
						else {
							target.setAllowFlight(true);
							target.sendMessage(ChatColor.GREEN + "You can fly!");
							return true;
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "You haven't got permission!");
						return true;
					}
				}
				
			}
			else if(args.length == 1) {
				if(sender.hasPermission("fly.others")) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						if(target.getAllowFlight()) {
							target.setFlying(false);
							target.setAllowFlight(false);
							target.sendMessage(ChatColor.RED + "You can't fly!");
							sender.sendMessage(ChatColor.RED + target.getName() + " can't fly!");
							return true;
						}
						else {
							target.setAllowFlight(true);
							target.sendMessage(ChatColor.GREEN + "You can fly!");
							sender.sendMessage(ChatColor.RED + target.getName() + " can fly!");
							return true;
						}
						}
						else {
							sender.sendMessage(ChatColor.RED + args[0] + " is not online!");
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "You haven't got permission!");
					}
				}
			}
			else if (label.equalsIgnoreCase("setwarp")) {
				Player player = (Player) sender;
	            String arg = args[0].toLowerCase();
                getConfig().set("warps."+arg+".world", player.getWorld().getName());
                getConfig().set("warps."+arg+".x", player.getLocation().getX());
                getConfig().set("warps."+arg+".y", player.getLocation().getY());
                getConfig().set("warps."+arg+".z", player.getLocation().getZ());
                getConfig().set("warps."+arg+".yaw", player.getLocation().getYaw());
                getConfig().set("warps."+arg+".pitch", player.getLocation().getPitch());
                saveConfig();
                player.sendMessage(ChatColor.GREEN+"Successfuly added a new warp: "+ChatColor.DARK_GREEN+arg+ChatColor.GREEN+"!");
            } else if (label.equalsIgnoreCase("warp")) {
            	Player player = (Player) sender;
	            String arg = args[0].toLowerCase();
                if (getConfig().getConfigurationSection("warps."+arg) == null) {
                    player.sendMessage(ChatColor.RED+"A warp with that name does not exist!");
                    return true;
                }

                World world = Bukkit.getWorld(getConfig().getString("warps."+arg+".world"));
                double x = getConfig().getDouble("warps."+arg+".x");
                double y = getConfig().getDouble("warps."+arg+".y");
                double z = getConfig().getDouble("warps."+arg+".z");
                float yaw = (float) getConfig().getDouble("warps."+arg+".yaw");
                float pitch = (float) getConfig().getDouble("warps."+arg+".pitch");

                player.teleport(new Location(world, x, y, z, yaw, pitch));

                player.sendMessage(ChatColor.GREEN+"You successfully teleported to the warp: "+ChatColor.DARK_GREEN+arg+ChatColor.GREEN+"!");
            }
            else if(label.equalsIgnoreCase("delwarp")) {
            	if (sender.hasPermission("warp.delete")) {
            		Player player = (Player) sender;
    	            String arg = args[0].toLowerCase();
            		if (getConfig().getConfigurationSection("warps."+arg) == null) {
            			player.sendMessage(ChatColor.RED+"A warp with that name does not exist!");
            			return true;
            		}
            		 getConfig().set("warps." + arg, null);
	            	 getConfig().set("warps." + arg + ".world", null);
	                 getConfig().set("warps." + arg + ".x", null);
	                 getConfig().set("warps." + arg + ".y", null);
	                 getConfig().set("warps." + arg + ".z", null);
	                 getConfig().set("warps." + arg + ".yaw", null);
	                 getConfig().set("warps." + arg + ".pitch", null);
	                 saveConfig();
	                 player.sendMessage(ChatColor.GREEN + "Successfully deleted warp "+ ChatColor.DARK_GREEN + arg + ChatColor.GREEN + "!");
	            }
            }
            else if (cmd.getName().equalsIgnoreCase("AFK")) {
    			if(args.length == 0) {
    				if(sender instanceof Player) {
    					if(sender.hasPermission("afk.use")) {
    						Player target = (Player) sender;
    						target.sendMessage(ChatColor.GREEN + "You can jump into AFK mode!");
    					}
    					else {
    						sender.sendMessage(ChatColor.RED + "You haven't got permission!");
    						return true;
    					}
    				}
    				
    			}
    			else if(args.length == 1) {
    				if(args[0].equalsIgnoreCase("on")) {
    					if(sender instanceof Player) {
    						if(sender.hasPermission("afk.use")) {
    							Player target = (Player) sender;
    							target.sendMessage(ChatColor.GREEN + "You are in AFK mode now!");
    							Bukkit.broadcastMessage(target.getName() + " is now AFK!");
    							target.setMetadata("afk", new FixedMetadataValue((Plugin) this, "afk"));
    							target.setAllowFlight(false);
    						}
    						else
    						{
    							sender.sendMessage(ChatColor.RED + "You haven't got permission!");
    						}
    							}
    						}
    				else if(args[0].equalsIgnoreCase("off"))
    				{
    					if(sender instanceof Player)
    					{
    						if(sender.hasPermission("afk.use"))
    						{
    							Player target = (Player) sender;
    							target.sendMessage(ChatColor.GREEN + "AFK mode was turned off!");
    							Bukkit.broadcastMessage(target.getName() + " is no longer AFK!");
    							target.setAllowFlight(true);
    							target.removeMetadata("afk", this);
    							}
    						else {
    							sender.sendMessage(ChatColor.RED + "You have not enough permissions!");
    						}
    						}
    						else
    						{
    							sender.sendMessage(ChatColor.RED + "You are not a player!");
    						}
    					}
    				}
            }
		return true;
            }
    	@EventHandler
    	public void onPlayerMove(PlayerMoveEvent event) {
    		if (event.getPlayer().hasMetadata("afk")) {
    			event.setCancelled(true);
    		}
	}
    @EventHandler
    public void onPlayerInteract1(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if (!(e.getClickedBlock().getType() == Material.OAK_SIGN) && !(e.getClickedBlock().getType() == Material.LEGACY_SIGN_POST)) return;
		
		Sign s = (Sign) e.getClickedBlock().getState();
		
		
		if (s.getLine(0).equals(ChatColor.GOLD + "ProWarps")) {
			if (e.getPlayer().hasPermission("warp.sign.use")) {
                Player player = e.getPlayer();
				if (getConfig().getConfigurationSection("warps." + s.getLine(1)) != null) {
					World world = Bukkit.getWorld(getConfig().getString("warps." + s.getLine(1) + ".world"));
	                double x = getConfig().getDouble("warps." + s.getLine(1) + ".x");
	                double y = getConfig().getDouble("warps." + s.getLine(1) + ".y");
	                double z = getConfig().getDouble("warps." + s.getLine(1) + ".z");
	                float yaw = (float) getConfig().getDouble("warps." + s.getLine(1) + ".yaw");
	                float pitch = (float) getConfig().getDouble("warps." + s.getLine(1) + ".pitch");
	                
	                player.teleport(new Location(world, x, y, z, yaw, pitch));

	                player.sendMessage(ChatColor.GREEN+"You successfully teleported to the warp: " + ChatColor.DARK_GREEN + s.getLine(1) + ChatColor.GREEN + "!");
				}
				else {
					player.sendMessage(ChatColor.RED + "Warp with that name doesn't exist!");
				}
			}
			else {
				Player player = e.getPlayer();
            	player.sendMessage(ChatColor.RED + "You don't have enough permissions!");
			}
		}
		}
    
    @EventHandler
	public void onSignChange1(SignChangeEvent e) {
		if(e.getPlayer().hasPermission("warp.sign.create")) {
			if(e.getLine(0).equalsIgnoreCase("[ProWarps]")) {
				if (getConfig().getConfigurationSection("warps." + e.getLine(1)) != null) {
					e.setLine(0, ChatColor.GOLD + "ProWarps");
				}
				else {
				onSignChange1(e);
				e.getBlock().breakNaturally();
				}
			}
			else {
				onSignChange1(e);
				e.getBlock().breakNaturally();
			}
		}
		else {
			Player player = e.getPlayer();
			player.sendMessage(ChatColor.RED + "You don't have enough permissions!");
		}
	}
	
@EventHandler
public void onPlayerInteract(PlayerInteractEvent e) {
	if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
	if (!(e.getClickedBlock().getType() == Material.OAK_SIGN) && !(e.getClickedBlock().getType() == Material.LEGACY_SIGN_POST)) return;
	
	Sign s = (Sign) e.getClickedBlock().getState();
	
	
	if (s.getLine(0).equals(ChatColor.GOLD + "Gamemode") && s.getLine(1).equals(ChatColor.GREEN + "Survival")) {
		if (e.getPlayer().hasPermission("gamemode.sign.change")) {
			if (e.getPlayer() instanceof Player) {
				e.getPlayer().setGameMode(GameMode.SURVIVAL);
				e.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode changed to Survival!");
			}
			else {
				e.getPlayer().sendMessage("You are not a player!");
			}
		}
		else {
			e.getPlayer().sendMessage("You have not enough permissions!");
		}
	}
	else if (s.getLine(0).equals(ChatColor.GOLD + "Gamemode") && s.getLine(1).equals(ChatColor.GREEN + "Creative")) {
		if (e.getPlayer().hasPermission("gamemode.sign.change")) {
			if (e.getPlayer() instanceof Player) {
				e.getPlayer().setGameMode(GameMode.CREATIVE);
				e.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode changed to Creative!");
			}
			else {
				e.getPlayer().sendMessage("You are not a player!");
			}
		}
		else {
			e.getPlayer().sendMessage("You have not enough permissions!");
		}
	}
	else if (s.getLine(0).equals(ChatColor.GOLD + "Gamemode") && s.getLine(1).equals(ChatColor.GREEN + "Spectator")) {
		if (e.getPlayer().hasPermission("gamemode.sign.change")) {
			if (e.getPlayer() instanceof Player) {
				e.getPlayer().setGameMode(GameMode.SPECTATOR);
				e.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode changed to Spectator!");
			}
			else {
				e.getPlayer().sendMessage("You are not a player!");
			}
		}
		else {
			e.getPlayer().sendMessage("You have not enough permissions!");
		}
	}
	else if (s.getLine(0).equals(ChatColor.GOLD + "Gamemode") && s.getLine(1).equals(ChatColor.GREEN + "Adventure")) {
		if (e.getPlayer().hasPermission("gamemode.sign.change")) {
			if (e.getPlayer() instanceof Player) {
				e.getPlayer().setGameMode(GameMode.ADVENTURE);
				e.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode changed to Adventure!");
			}
			else {
				e.getPlayer().sendMessage("You are not a player!");
			}
		}
		else {
			e.getPlayer().sendMessage("You have not enough permissions!");
		}
	}
	
}


@EventHandler
public void onSignChange(SignChangeEvent e) {
	if(e.getPlayer().hasPermission("gamemode.sign.create")) {
		if(e.getLine(1).equalsIgnoreCase("[Survival]")) {
			e.setLine(0, ChatColor.GOLD + "Gamemode");
			e.setLine(1, ChatColor.GREEN + "Survival");
		}
		else if(e.getLine(1).equalsIgnoreCase("[Creative]")) {
			e.setLine(0, ChatColor.GOLD + "Gamemode");
			e.setLine(1, ChatColor.GREEN + "Creative");
		}
		else if(e.getLine(1).equalsIgnoreCase("[Spectator]")) {
			e.setLine(0, ChatColor.GOLD + "Gamemode");
			e.setLine(1, ChatColor.GREEN + "Spectator");
		}
		else if(e.getLine(1).equalsIgnoreCase("[Adventure]")) {
			e.setLine(0, ChatColor.GOLD + "Gamemode");
			e.setLine(1, ChatColor.GREEN + "Adventure");
		}
	}
	else {
		e.getPlayer().sendMessage(ChatColor.RED + "You don't have enough permissions!");
	}
}

public void onDisable() {
	
}
}