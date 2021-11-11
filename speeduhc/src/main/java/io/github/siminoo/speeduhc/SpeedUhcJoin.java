package io.github.siminoo.speeduhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpeedUhcJoin implements Listener {
		
	speeduhc plugin;
	public SpeedUhcJoin(speeduhc plugin) {
		this.plugin = plugin;
	}
	
	public void getSpeedUhcJoin() {
	}
	
	
		@EventHandler
		public void onPlayerInteract(PlayerInteractEvent e) {
			if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
			if (!(e.getClickedBlock().getType() == Material.OAK_SIGN) && !(e.getClickedBlock().getType() == Material.LEGACY_SIGN_POST)) return;
			
			Sign s = (Sign) e.getClickedBlock().getState();
			
			
			if (s.getLine(0).equals(ChatColor.GOLD + "SpeedUHC") && s.getLine(1).equals(ChatColor.GREEN + "Join")) {
				int id = 1;
				try {
					id = Integer.parseInt(s.getLine(2));
				}catch(Exception ex) {
					e.getPlayer().sendMessage(id + " is not a valid arena!");
					return;
				}
				World world = Bukkit.getWorld(plugin.getConfig().getString("arenas."+ s.getLine(2) +".world"));
                double x = plugin.getConfig().getDouble("arenas."+ s.getLine(2) +".x");
                double y = plugin.getConfig().getDouble("arenas."+ s.getLine(2) +".y");
                double z = plugin.getConfig().getDouble("arenas."+ s.getLine(2) +".z");
                float yaw = (float) plugin.getConfig().getDouble("arenas."+ s.getLine(2) +".yaw");
                float pitch = (float) plugin.getConfig().getDouble("arenas."+ s.getLine(2) +".pitch");
                plugin.saveConfig();
                
                
                
			}
		
		}
		
		
		@EventHandler
		public void onSignChange(SignChangeEvent e) {
			if(e.getPlayer().hasPermission("speeduhc.sign.create")) {
				if(e.getLine(0).equalsIgnoreCase("[SpeedUHC]") && e.getLine(1).equalsIgnoreCase("[Join]")) {
					e.setLine(0, ChatColor.GOLD + "SpeedUHC");
					e.setLine(1, ChatColor.GREEN + "Join");
				}
				try {
					Integer.parseInt(e.getLine(2));
				}catch (Exception ex) {
					e.getPlayer().sendMessage(ChatColor.RED + " " + e.getLine(2) + "is not a valid arena!");
					return;
				}
			}
		}
	}