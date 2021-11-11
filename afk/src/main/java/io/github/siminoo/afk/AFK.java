package io.github.siminoo.afk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class AFK extends JavaPlugin implements Listener {
	@Override
	public void onEnable()
	{
		getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("AFK")) {
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
							if (getConfig().getConfigurationSection("AfkRoom.") != null) {
								World world = Bukkit.getWorld(getConfig().getString("AfkRoom." + ".world"));
								double x = getConfig().getDouble("AfkRoom." + ".x");
								double y = getConfig().getDouble("AfkRoom." + ".y");
								double z = getConfig().getDouble("AfkRoom." + ".z");
								float yaw = (float) getConfig().getDouble("AfkRoom." + ".yaw");
								float pitch = (float) getConfig().getDouble("AfkRoom." + ".pitch");

								target.teleport(new Location(world, x, y, z, yaw, pitch));
						}
							}
							return true;
						}
						else
						{
							sender.sendMessage(ChatColor.RED + "You haven't got permission!");
							return true;
						}
					}
				else if (args[0].equalsIgnoreCase("room")) {
					if (sender instanceof Player) {
						if (sender.hasPermission("afk.set")) {
							Player player = (Player) sender;
							getConfig().set("AfkRoom." + ".world", null);
							getConfig().set("AfkRoom." + ".x", null);
							getConfig().set("AfkRoom." + ".y", null);
							getConfig().set("AfkRoom." + ".z", null);
							getConfig().set("AfkRoom." + ".yaw", null);
							getConfig().set("AfkRoom." + ".pitch", null);
							getConfig().set("AfkRoom." + ".world", player.getWorld().getName());
			                getConfig().set("AfkRoom." + ".x", player.getLocation().getX());
			                getConfig().set("AfkRoom." + ".y", player.getLocation().getY());
			                getConfig().set("AfkRoom." + ".z", player.getLocation().getZ());
			                getConfig().set("AfkRoom." + ".yaw", player.getLocation().getYaw());
			                getConfig().set("AfkRoom." + ".pitch", player.getLocation().getPitch());
			                saveConfig();
			                player.sendMessage(ChatColor.GREEN + "Successfuly set " + ChatColor.DARK_RED + "AFK Room!");
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
							if (getConfig().getConfigurationSection("AfkLobby.") != null) {
								World world = Bukkit.getWorld(getConfig().getString("AfkLobby." + ".world"));
								double x = getConfig().getDouble("AfkLobby." + ".x");
								double y = getConfig().getDouble("AfkLobby." + ".y");
								double z = getConfig().getDouble("AfkLobby." + ".z");
								float yaw = (float) getConfig().getDouble("AfkLobby." + ".yaw");
								float pitch = (float) getConfig().getDouble("AfkLobby." + ".pitch");

								target.teleport(new Location(world, x, y, z, yaw, pitch));
								
								target.removeMetadata("afk", this);
							}
							return true;
						}
						else
						{
							sender.sendMessage(ChatColor.RED + "You haven't got permission!");
							return true;
						}
					}
					
				}
				else if (args[0].equalsIgnoreCase("lobby")) {
					if (sender instanceof Player) {
						if (sender.hasPermission("afk.set")) {
							Player player = (Player) sender;
							getConfig().set("AfkLobby." + ".world", null);
							getConfig().set("AfkLobby." + ".x", null);
							getConfig().set("AfkLobby." + ".y", null);
							getConfig().set("AfkLobby." + ".z", null);
							getConfig().set("AfkLobby." + ".yaw", null);
							getConfig().set("AfkLobby." + ".pitch", null);
							getConfig().set("AfkLobby." + ".world", player.getWorld().getName());
			                getConfig().set("AfkLobby." + ".x", player.getLocation().getX());
			                getConfig().set("AfkLobby." + ".y", player.getLocation().getY());
			                getConfig().set("AfkLobby." + ".z", player.getLocation().getZ());
			                getConfig().set("AfkLobby." + ".yaw", player.getLocation().getYaw());
			                getConfig().set("AfkLobby." + ".pitch", player.getLocation().getPitch());
			                saveConfig();
			                player.sendMessage(ChatColor.GREEN + "Successfuly set " + ChatColor.DARK_RED + "AFK Lobby!");
						}
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
	
	@Override
	public void onDisable()
	{
	}
}

