package io.github.siminoo.warps;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class Warps extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED+"Only players can execute this command!");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED+"Please specify the name of the warp.");
        } else if (args.length == 1) {
            String arg = args[0].toLowerCase();

            if (label.equalsIgnoreCase("setwarp")) {
            	if (sender.hasPermission("warp.create")) {
	                getConfig().set("warps."+arg+".world", player.getWorld().getName());
	                getConfig().set("warps."+arg+".x", player.getLocation().getX());
	                getConfig().set("warps."+arg+".y", player.getLocation().getY());
	                getConfig().set("warps."+arg+".z", player.getLocation().getZ());
	                getConfig().set("warps."+arg+".yaw", player.getLocation().getYaw());
	                getConfig().set("warps."+arg+".pitch", player.getLocation().getPitch());
	                saveConfig();
	                player.sendMessage(ChatColor.GREEN+"Successfuly added a new warp: "+ChatColor.DARK_GREEN+arg+ChatColor.GREEN+"!");
            	}
            } else if (label.equalsIgnoreCase("warp")) {
            	if (sender.hasPermission("warp.use") && sender.hasPermission("warp.use." + args[0])) {
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
            	else {
            		sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
            	}
            }
            else if(label.equalsIgnoreCase("delwarp")) {
            	if (sender.hasPermission("warp.delete")) {
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
            else if (label.equalsIgnoreCase("setportal")) {
                getConfig().set("Portals." + player.getLocation() + ".warp.", args[0]);
                saveConfig();
                player.sendMessage(ChatColor.GREEN + "Successfuly added a new portal to warp: " + ChatColor.DARK_GREEN + args[0] + ChatColor.GREEN+"!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Too many arguments.");
        }
		return true;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if (!(e.getClickedBlock().getType() == Material.SIGN) || !(e.getClickedBlock().getType() == Material.WALL_SIGN) || !(e.getClickedBlock().getType() == Material.SIGN_POST)) return;
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
	public void onSignChange(SignChangeEvent e) {
		if(e.getPlayer().hasPermission("warp.sign.create")) {
			if(e.getLine(0).equalsIgnoreCase("[ProWarps]")) {
				if (getConfig().getConfigurationSection("warps." + e.getLine(1)) != null) {
					e.setLine(0, ChatColor.GOLD + "ProWarps");
				}
				else {
				onSignChange(e);
				e.getBlock().breakNaturally();
				}
			}
			else {
				onSignChange(e);
				e.getBlock().breakNaturally();
			}
		}
		else {
			Player player = e.getPlayer();
			player.sendMessage(ChatColor.RED + "You don't have enough permissions!");
		}
	}
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
    	Player player = e.getPlayer();
    	if (player.getLocation() == getConfig().getConfigurationSection("Portals." + player.getLocation())) {
    		if (!player.hasPermission("portals.use")) {
        		player.sendMessage(ChatColor.RED + "You don't have enough permissions for using portals!");
        		return;
        	}
    		else {
    			ConfigurationSection warp = getConfig().getConfigurationSection("Portals." + player.getLocation() + ".warp.");
    			World world = Bukkit.getWorld(getConfig().getString("warps." + warp + ".world"));
                double x = getConfig().getDouble("warps." + warp + ".x");
                double y = getConfig().getDouble("warps." + warp + ".y");
                double z = getConfig().getDouble("warps." + warp + ".z");
                float yaw = (float) getConfig().getDouble("warps." + warp + ".yaw");
                float pitch = (float) getConfig().getDouble("warps." + warp + ".pitch");

                player.teleport(new Location(world, x, y, z, yaw, pitch));

                player.sendMessage(ChatColor.GREEN + "You successfully teleported to the warp: " + ChatColor.DARK_GREEN + warp + ChatColor.GREEN+"!");
    		}
    	}
    }
    
    public void onDisable() {
    	
    }
}