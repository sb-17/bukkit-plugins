package io.github.siminoo.immunity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;


public class ImmunityExecutor implements CommandExecutor{
	private Immunity immunity;
	public ImmunityExecutor(Immunity immunity)
	{
		
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("kill")) {
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
		return true;
		}
	}


