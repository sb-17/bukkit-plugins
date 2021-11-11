package io.github.siminoo.changegamemodesign;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class changegamemodesign extends JavaPlugin implements Listener{
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
		if (!(e.getClickedBlock().getType() == Material.OAK_SIGN) || !(e.getClickedBlock().getType() == Material.ACACIA_SIGN) || !(e.getClickedBlock().getType() == Material.BIRCH_SIGN) || !(e.getClickedBlock().getType() == Material.JUNGLE_SIGN) || !(e.getClickedBlock().getType() == Material.DARK_OAK_SIGN) || !(e.getClickedBlock().getType() == Material.SPRUCE_SIGN) || !(e.getClickedBlock().getType() == Material.ACACIA_WALL_SIGN) || !(e.getClickedBlock().getType() == Material.BIRCH_WALL_SIGN) || !(e.getClickedBlock().getType() == Material.DARK_OAK_WALL_SIGN) || !(e.getClickedBlock().getType() == Material.JUNGLE_WALL_SIGN) || !(e.getClickedBlock().getType() == Material.OAK_WALL_SIGN) || !(e.getClickedBlock().getType() == Material.SPRUCE_WALL_SIGN)) return;
		
		Sign s = (Sign) e.getClickedBlock().getState();
		
		
		if (s.getLine(0).equals(ChatColor.GOLD + "Gamemode") && s.getLine(1).equals(ChatColor.GREEN + "Survival")) {
			if (e.getPlayer().hasPermission("gamemode.sign.change")) {
				if (e.getPlayer() instanceof Player) {
					e.getPlayer().setGameMode(GameMode.SURVIVAL);
					e.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode changed to Survival!");
				}
				else {
					e.getPlayer().sendMessage("You are not a player!");
				}
			}
			else {
				e.getPlayer().sendMessage("You have not enough permissions!");
			}
		}
		else if (s.getLine(0).equals(ChatColor.GOLD + "Gamemode") && s.getLine(1).equals(ChatColor.GREEN + "Creative")) {
			if (e.getPlayer().hasPermission("gamemode.sign.change")) {
				if (e.getPlayer() instanceof Player) {
					e.getPlayer().setGameMode(GameMode.CREATIVE);
					e.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode changed to Creative!");
				}
				else {
					e.getPlayer().sendMessage("You are not a player!");
				}
			}
			else {
				e.getPlayer().sendMessage("You have not enough permissions!");
			}
		}
		else if (s.getLine(0).equals(ChatColor.GOLD + "Gamemode") && s.getLine(1).equals(ChatColor.GREEN + "Spectator")) {
			if (e.getPlayer().hasPermission("gamemode.sign.change")) {
				if (e.getPlayer() instanceof Player) {
					e.getPlayer().setGameMode(GameMode.SPECTATOR);
					e.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode changed to Spectator!");
				}
				else {
					e.getPlayer().sendMessage("You are not a player!");
				}
			}
			else {
				e.getPlayer().sendMessage("You have not enough permissions!");
			}
		}
		else if (s.getLine(0).equals(ChatColor.GOLD + "Gamemode") && s.getLine(1).equals(ChatColor.GREEN + "Adventure")) {
			if (e.getPlayer().hasPermission("gamemode.sign.change")) {
				if (e.getPlayer() instanceof Player) {
					e.getPlayer().setGameMode(GameMode.ADVENTURE);
					e.getPlayer().sendMessage(ChatColor.GREEN + "Gamemode changed to Adventure!");
				}
				else {
					e.getPlayer().sendMessage("You are not a player!");
				}
			}
			else {
				e.getPlayer().sendMessage("You have not enough permissions!");
			}
		}
		
	}

	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (e.getLine(0).equals("[Gamemode]"))
		if(e.getPlayer().hasPermission("gamemode.sign.create")) {
			if(e.getLine(1).equalsIgnoreCase("[Survival]")) {
				e.setLine(0, ChatColor.GOLD + "Gamemode");
				e.setLine(1, ChatColor.GREEN + "Survival");
			}
			else if(e.getLine(1).equalsIgnoreCase("[Creative]")) {
				e.setLine(0, ChatColor.GOLD + "Gamemode");
				e.setLine(1, ChatColor.GREEN + "Creative");
			}
			else if(e.getLine(1).equalsIgnoreCase("[Spectator]")) {
				e.setLine(0, ChatColor.GOLD + "Gamemode");
				e.setLine(1, ChatColor.GREEN + "Spectator");
			}
			else if(e.getLine(1).equalsIgnoreCase("[Adventure]")) {
				e.setLine(0, ChatColor.GOLD + "Gamemode");
				e.setLine(1, ChatColor.GREEN + "Adventure");
			}
		}
		else {
			e.getPlayer().sendMessage(ChatColor.RED + "You don't have enough permissions!");
		}
	}
	
	public void onDisable() {
		
	}
	
}
