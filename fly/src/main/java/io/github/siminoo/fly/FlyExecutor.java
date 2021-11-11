package io.github.siminoo.fly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;


public class FlyExecutor implements CommandExecutor{
	private Fly fly;
	public FlyExecutor(Fly fly)
	{
		this.fly = fly;
	}
	
	public String prefix = ChatColor.AQUA + "[Fly] -> ";
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Fly")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission("fly.use")) {
						Player target = (Player) sender;
						if(target.getAllowFlight()) {
							if(target.getWorld().getName() == fly.getConfig().getString("lobby")) {
								target.setFlying(false);
								target.setAllowFlight(false);
								target.sendMessage(prefix + ChatColor.RED + "Fly disabled!");
								return true;
							}
							else {
								if(target.hasPermission("fly.world")) {
									target.setFlying(false);
									target.setAllowFlight(false);
									target.sendMessage(prefix + ChatColor.RED + "Fly disabled!");
									return true;
								}
								else {
									sender.sendMessage(prefix + "You can't disable fly in this world!");
								}
							}
						}
						else {
							if(target.getWorld().getName() == fly.getConfig().getString("lobby")) {
								target.setAllowFlight(true);
								target.sendMessage(prefix + ChatColor.GREEN + "Fly enabled!");
								return true;
							}
							else {
								if(target.hasPermission("fly.world")) {
									target.setAllowFlight(true);
									target.sendMessage(prefix + ChatColor.GREEN + "Fly enabled!");
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
							target.sendMessage(prefix + ChatColor.RED + "Fly disabled!");
							sender.sendMessage(prefix + ChatColor.RED + target.getName() + ": fly disabled!");
							return true;
						}
						else {
							target.setAllowFlight(true);
							target.sendMessage(prefix + ChatColor.GREEN + "Fly enabled!");
							sender.sendMessage(prefix + ChatColor.RED + target.getName() + ": fly enabled!");
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
				fly.getConfig().set("lobby", p.getLocation().getWorld());
				p.sendMessage(prefix + ChatColor.GREEN + "Successfully set fly lobby!");
			}
		}
		return true;
	}
}

