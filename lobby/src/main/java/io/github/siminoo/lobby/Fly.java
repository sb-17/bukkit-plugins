package io.github.siminoo.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
	private Lobby lobby;
	public Fly(Lobby lobby) {
		this.lobby = lobby;
	}
	
	public String prefix = ChatColor.AQUA + "[Fly] -> ";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("fly")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission("fly.use")) {
						Player target = (Player) sender;
						if(target.getAllowFlight()) {
							if(target.getWorld().getName() == lobby.getConfig().getString("lobby")) {
								target.setFlying(false);
								target.setAllowFlight(false);
								target.sendMessage(prefix + ChatColor.RED + "OFF");
								return true;
							}
							else {
								if(target.hasPermission("fly.world")) {
									target.setFlying(false);
									target.setAllowFlight(false);
									target.sendMessage(prefix + ChatColor.RED + "OFF");
									return true;
								}
								else {
									sender.sendMessage(prefix + "You can't disable fly in this world!");
								}
							}
						}
						else {
							if(target.getWorld().getName() == lobby.getConfig().getString("lobby")) {
								target.setAllowFlight(true);
								target.sendMessage(prefix + ChatColor.GREEN + "ON");
								return true;
							}
							else {
								if(target.hasPermission("fly.world")) {
									target.setAllowFlight(true);
									target.sendMessage(prefix + ChatColor.GREEN + "ON");
									return true;
								}
								else {
									sender.sendMessage(prefix + "You can't enable fly in this world!");
								}
							}
						}
					}
					else {
						sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
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
							target.sendMessage(prefix + ChatColor.RED + "OFF");
							sender.sendMessage(prefix + ChatColor.RED + target.getName() + ": OFF");
							return true;
						}
						else {
							target.setAllowFlight(true);
							target.sendMessage(prefix + ChatColor.GREEN + "ON");
							sender.sendMessage(prefix + ChatColor.GREEN + target.getName() + ": ON!");
							return true;
						}
					}
					else {
						sender.sendMessage(prefix + ChatColor.RED + args[0] + " is not online!");
					}
				}
				else {
					sender.sendMessage(prefix + ChatColor.RED + "You don't have enough permissions!");
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("flylobbyset")) {
			if(sender.hasPermission("fly.lobby")) {
				Player p = (Player) sender;
				lobby.getConfig().set("lobby", p.getLocation().getWorld());
				p.sendMessage(prefix + ChatColor.GREEN + "Successfully set fly lobby!");
			}
		}
		return true;
	}
}
