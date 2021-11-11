package io.github.siminoo.proeconomy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ProEconomy extends JavaPlugin {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String[] label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pe")) {
			switch (args[0]) {
			case "bank":
				if (args.length == 4) {
					switch (args[1]) {
					case "send":
						Player target = Bukkit.getPlayer(args[2]);
						if (target == null) {
							sender.sendMessage(ChatColor.DARK_RED + args[2] + ChatColor.RED + " is not online!");
						}
						else {
							int money = Integer.parseInt(args[3]);
							if (money > 0) {
								if (getConfig().get(target.getName() + ".money.") != null) {
									int senderStatus = Integer.parseInt((String) getConfig().get(sender.getName() + ".money."));
									int status = Integer.parseInt((String) getConfig().get(target.getName() + ".money."));
									if (senderStatus > status) {
										getConfig().set(target.getName() + ".money.", status + Integer.parseInt(args[3]));
										getConfig().set(sender.getName() + ".money.", senderStatus - Integer.parseInt(args[3]));
										int finalMoney = status + Integer.parseInt(args[3]);
										sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.DARK_AQUA + finalMoney + ChatColor.GREEN + " money to " + target.getName() + "!");
									}
									else sender.sendMessage(ChatColor.RED + "You don't have enough money!");
								}
								else {
									getConfig().set(target + ".money.", Integer.parseInt(args[3]));
									sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.DARK_AQUA + args[3] + ChatColor.GREEN + " money to " + target.getName() + "!");
								}
							}
							else sender.sendMessage(ChatColor.RED + "Minimum to add is 1!");
						}
						break;
					}
				}
				else sender.sendMessage(ChatColor.RED + "Syntax error!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase( "peadmin")) {
			if (sender.hasPermission("pe.admin")) {
				switch (args[0]) {
				case "bank":
					if (args.length == 4) {
						switch (args[1]) {
						case "send":
							Player target = Bukkit.getPlayer(args[2]);
							if (target == null) {
								sender.sendMessage(ChatColor.DARK_RED + args[2] + ChatColor.RED + " is not online!");
							}
							else {
								int money = Integer.parseInt(args[3]);
								if (money > 0) {
									if (getConfig().get(target.getName() + ".money.") != null) {
									int status = Integer.parseInt((String) getConfig().get(target.getName() + ".money."));
										getConfig().set(target.getName() + ".money.", status + Integer.parseInt(args[3]));
										int finalMoney = status + Integer.parseInt(args[3]);
										sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.DARK_AQUA + finalMoney + ChatColor.GREEN + " money to " + target.getName() + "!");
									}
									else {
										getConfig().set(target + ".money.", Integer.parseInt(args[3]));
										sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.DARK_AQUA + args[3] + ChatColor.GREEN + " money to " + target.getName() + "!");
									}
								}
								else sender.sendMessage(ChatColor.RED + "Minimum to add is 1!");
							}
							break;
							
						case "remove":
							Player target1 = Bukkit.getPlayer(args[2]);
							if (target1 == null) {
								sender.sendMessage(ChatColor.DARK_RED + args[2] + ChatColor.RED + " is not online!");
							}
							else {
								int money = Integer.parseInt(args[3]);
								if (money > 0) {
									int status = Integer.parseInt((String) getConfig().get(target1.getName() + ".money."));
									if (status > Integer.parseInt(args[3])) {
										getConfig().set(target1.getName() + ".money.", status - Integer.parseInt(args[3]));
										int finalMoney = status - Integer.parseInt(args[3]);
										sender.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.DARK_AQUA + finalMoney + ChatColor.GREEN + " money from " + target1.getName() + "!");
									}
									else sender.sendMessage(ChatColor.RED + target1.getName() + "doesn't have enough money!");
								}
								else sender.sendMessage(ChatColor.RED + "Minimum to remove is 1!");
							}
							break;
						}
						break;
					}
					else sender.sendMessage(ChatColor.RED + "Syntax error!");
				}
			}
			else sender.sendMessage(ChatColor.RED + "You don't have enough permissions!");
		}
		else if (cmd.getName().equalsIgnoreCase("status")) {
			int status = Integer.parseInt((String) getConfig().get(sender.getName() + ".money."));
			sender.sendMessage(ChatColor.AQUA + "You have " + status + " money now!");
		}
		return false;
	}
	
	public void onDisable() {
		
	}
}
