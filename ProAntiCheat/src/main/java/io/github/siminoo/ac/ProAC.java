package io.github.siminoo.ac;

import java.sql.Date;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ProAC extends JavaPlugin implements Listener {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("vl")) {
			if(args.length == 0) {
				int currentVL = (Integer) getConfig().get("VL." + sender.getName());
				if(getConfig().get("VL." + sender.getName()) == null) {
					sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + "Your violation level is: 0");
				}
				else {
					sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + "Your violation level is: " + currentVL);
				}
			}
			else if(args.length == 1) {
				if(sender.hasPermission("proac.others.vl")) {
					Player target = Bukkit.getPlayer(args[0]);
					int currentVL = (Integer) getConfig().get("VL." + target.getName());
					if(getConfig().get("VL." + target.getName()) == null) {
						sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + target.getName() + "'s violation level is: 0");
					}
					else {
						sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + target.getName() + "'s violation level is: " + currentVL);
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + "You don't have enough permissions!");
				}
			}
			else if(args.length == 2) {
				sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + "Syntax error!");
			}
			else if(args.length == 3) {
				if(sender.hasPermission("proac.others.manage")) {
					Player target = Bukkit.getPlayer(args[0]);
					int currentVL = (Integer) getConfig().get("VL." + target.getName());
					int addrem = Integer.parseInt(args[2]);
					if(getConfig().get("VL." + target.getName()) == null) {
						currentVL = 0;
					}
					if(args[1].equalsIgnoreCase("add")) {
						if(addrem + currentVL == 3 || addrem + currentVL >= 5) {
							sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + "Can't add this amount of VL!");
						}
						else {
							getConfig().set("VL." + target.getName(), addrem + currentVL);
							int finalVL = addrem + currentVL;
							sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + "Added " + addrem + " violation level to " + target.getName() + "! Total VL: " + finalVL);
						}
					}
					else if(args[1].equalsIgnoreCase("remove")) {
						if(addrem > currentVL) {
							sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + "Can't remove this amount of VL!");
						}
						else if(addrem <= currentVL) {
							getConfig().set("VL." + target.getName(), currentVL - addrem);
							int finalVL = addrem - currentVL;
							sender.sendMessage(ChatColor.RED + "[Anticheat] -> " + ChatColor.GREEN + "Removed " + addrem + " violation level from " + target.getName() + "! Total VL: " + finalVL);
						}
					}
				}
			}
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerFly(PlayerMoveEvent e) {
		if(e.getPlayer().isFlying()) {
			if(!e.getPlayer().hasPermission("proac.fly")) {
				e.setCancelled(true);
				int lastVL = (Integer) getConfig().get("VL." + e.getPlayer().getName());
				getConfig().set("VL." + e.getPlayer().getName(), lastVL + 1);
				int currentVL = (Integer) getConfig().get("VL." + e.getPlayer().getName());
				Bukkit.broadcast(ChatColor.RED + "[AntiCheat] -> " + ChatColor.GREEN + e.getPlayer().getName() + " tried to fly!", "proac.admin");
				if(currentVL == 1 || currentVL == 2 || currentVL == 3 || currentVL == 4) {
					e.getPlayer().kickPlayer("Flying is not enabled on this server!");
					Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] -> " + ChatColor.GREEN + e.getPlayer().getName() + " was kicked from the server! Reason: Fly");
				}
				else if(currentVL == 5) {
					BanList Banlist = Bukkit.getBanList(BanList.Type.NAME);
					String source = "Console";
					Date date = new Date(System.currentTimeMillis()+60*60*1000*24*7);
					Player target = e.getPlayer();
					Banlist.addBan(target.getName(), "Banned from server! Reason: Modified client; From: AntiCheat; For: 1 week", date, source);
					e.getPlayer().kickPlayer("Banned from server! Reason: Modified client; From: AntiCheat; For: 1 week");
					Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] -> " + ChatColor.GREEN + e.getPlayer().getName() + " was banned from the server! Reason: Fly");
					getConfig().set("VL." + e.getPlayer().getName(), 0);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			if(e.getEntity() instanceof Player) {
				if(!e.getDamager().hasPermission("proac.killaura")) {
					if(e.getDamager().getName() != e.getEntity().getName()) {
						if(e.getDamager().getLocation().distance(e.getEntity().getLocation()) >= 4.5) {
							e.getDamager().sendMessage(ChatColor.RED + "[Warning] Killaura detect!");
							int lastVL = (Integer) getConfig().get("VL." + e.getDamager().getName());
							getConfig().set("VL." + e.getDamager().getName(), lastVL + 1);
							int currentVL = (Integer) getConfig().get("VL." + e.getDamager().getName());
							Bukkit.broadcast(ChatColor.RED + "[AntiCheat] -> " + ChatColor.GREEN + e.getDamager().getName() + " tried to use KillAura!", "proac.admin");
							if(currentVL == 3) {
								((Player) e.getDamager()).kickPlayer("Killaura is not enabled on this server!");
								Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] -> " + ChatColor.GREEN + e.getDamager().getName() + " was kicked from the server! Reason: KillAura");
							}
							else if(currentVL == 5) {
								BanList Banlist = Bukkit.getBanList(BanList.Type.NAME);
								String source = "Console";
								Date date = new Date(System.currentTimeMillis()+60*60*1000*24*7*4);
								Player target = (Player) e.getDamager();
								Banlist.addBan(target.getName(), "Banned from server! Reason: Modified client; From: AntiCheat; For: 1 month", date, source);
								((Player) e.getDamager()).kickPlayer("Banned from server! Reason: Modified client; From: AntiCheat; For: 1 month");
								Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] -> " + ChatColor.GREEN + e.getDamager().getName() + " was banned from the server! Reason: KillAura");
								getConfig().set("VL." + e.getDamager().getName(), 0);
							}
						}
					}
				}
			}
		}
	}
	
	public void onDisable() {
		
	}
}