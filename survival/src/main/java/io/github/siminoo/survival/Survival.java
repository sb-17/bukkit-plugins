package io.github.siminoo.survival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Survival extends JavaPlugin implements Listener {
	String prefix = ChatColor.AQUA + "[Survival] -> ";
	String block = "1 block of ";
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        getConfig().set("world", "survival");
        saveConfig();
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("shop")) {
			if(sender instanceof Player) {
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("price")) {
						if(args[1].equalsIgnoreCase("dirt")) {
							sender.sendMessage(prefix + ChatColor.GREEN + block + "dirt costs 2 money");
						}
						else if(args[1].equalsIgnoreCase("stone")) {
							sender.sendMessage(prefix + ChatColor.GREEN + block + "stone costs 4 money");
						}
					}
					else sender.sendMessage(prefix + ChatColor.RED + "Syntax Error!");
				}
				else if(args.length == 3) {
					if(args[0].equalsIgnoreCase("buy")) {
						int count = Integer.parseInt(args[1]);
						if(args[2].equalsIgnoreCase("dirt")) {
							((Player) sender).getInventory().addItem(new ItemStack(Material.DIRT));
							Bukkit.dispatchCommand(sender, "removemoney " + count*2 + " " + sender.getName());
							sender.sendMessage(prefix + "Added " + ChatColor.GREEN + count + " dirt blocks to your inventory");
						}
						else if(args[2].equalsIgnoreCase("stone")) {
							((Player) sender).getInventory().addItem(new ItemStack(Material.STONE));
							Bukkit.dispatchCommand(sender, "removemoney " + count*4 + " " + sender.getName());
							sender.sendMessage(prefix + "Added " + ChatColor.GREEN + count + " stone blocks to your inventory");
						}
					}
					else sender.sendMessage(prefix + ChatColor.RED + "Syntax Error");
				}
				else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("sell")) {
						if(!((Player) sender).getLocation().getWorld().getName().equals(getConfig().getString("world"))) {
							sender.sendMessage(prefix + ChatColor.RED + "You have to be in Survival world!");
						}
						else {
							ItemStack item = ((Player) sender).getItemInHand();
							int count = item.getAmount(); 
							if(item.getType().equals(Material.DIRT)) {
								int i = 0;
								while(i<count) {
									((Player) sender).getInventory().remove(item);
									i++;
								}
								int money = count*2;
								Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "addmoney " + money + " " + sender.getName());
								sender.sendMessage(prefix + ChatColor.GREEN + "You have sold " + count + " dirt blocks for " + money + " money!");
							}
							else if(item.getType().equals(Material.STONE)) {
								int i = 0;
								while(i<count) {
									((Player) sender).getInventory().remove(item);
									i++;
								}
								int money = count*4;
								Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "addmoney " + money + " " + sender.getName());
								sender.sendMessage(prefix + ChatColor.GREEN + "You have sold " + count + " stone blocks for " + money + " money!");
							}
							else if(item.getType().equals(Material.DIAMOND)) {
								int i = 0;
								while(i<count) {
									((Player) sender).getInventory().remove(item);
									i++;
								}
								int money = count*20;
								Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "addmoney " + money + " " + sender.getName());
								sender.sendMessage(prefix + ChatColor.GREEN + "You have sold " + count + " diamonds for " + money + " money!");
							}
							else if(item.getType().equals(Material.GOLD_INGOT)) {
								int i = 0;
								while(i<count) {
									((Player) sender).getInventory().remove(item);
									i++;
								}
								int money = count*10;
								Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "addmoney " + money + " " + sender.getName());
								sender.sendMessage(prefix + ChatColor.GREEN + "You have sold " + count + " gold ingots for " + money + " money!");
							}
							else if(item.getType().equals(Material.IRON_INGOT)) {
								int i = 0;
								while(i<count) {
									((Player) sender).getInventory().remove(item);
									i++;
								}
								int money = count*5;
								Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "addmoney " + money + " " + sender.getName());
								sender.sendMessage(prefix + ChatColor.GREEN + "You have sold " + count + " iron ingots for " + money + " money!");
							}
						}
					}
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setsurvivalworld")) {
			Player p = (Player) sender;
			if(args.length == 0) {
				if(sender.hasPermission("survival.setworld")) {
					getConfig().set("world", p.getLocation().getWorld().getName());
					saveConfig();
				}
			}
			else sender.sendMessage(prefix + ChatColor.RED + "Syntax Error!");
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Location to = e.getTo();
		Location from = e.getFrom();
		Player p = e.getPlayer();
		if(to.getWorld().getName().equals(getConfig().getString("world"))) {
			
		}
		else if(from.getWorld().getName().equals(getConfig().getString("world"))) {
			
		}
	}
	
	public void onDisable() {
		
	}
}
