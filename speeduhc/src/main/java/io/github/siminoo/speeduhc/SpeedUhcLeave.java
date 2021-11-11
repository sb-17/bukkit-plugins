package io.github.siminoo.speeduhc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class SpeedUhcLeave implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if (!(e.getClickedBlock().getType() == Material.OAK_SIGN) && !(e.getClickedBlock().getType() == Material.LEGACY_SIGN_POST)) return;
		
		Sign s = (Sign) e.getClickedBlock().getState();
		
		if (s.getLine(0).equals(ChatColor.GOLD + "SpeedUHC") && s.getLine(1).equals(ChatColor.GREEN + "Leave")) {
			int id = 1;
			try {
				id = Integer.parseInt(s.getLine(2));
			}catch(Exception ex) {
				e.getPlayer().sendMessage(id + " is not a valid arena!");
				return;
			}
			e.getPlayer().performCommand("speeduhc leave " + id);
		}
	
	}
	
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(e.getPlayer().hasPermission("speeduhc.join")) {
			if(e.getLine(0).equalsIgnoreCase("[SpeedUHC]") && e.getLine(1).equalsIgnoreCase("[Leave]")) {
				e.setLine(0, ChatColor.GOLD + "SpeedUHC");
				e.setLine(1, ChatColor.GREEN + "Leave");
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