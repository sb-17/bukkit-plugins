package io.github.siminoo.voidspawn;

import java.util.List;

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
import org.bukkit.plugin.java.JavaPlugin;

public class VoidSpawn extends JavaPlugin implements Listener {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("vs")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("addworld")) {
					if(p.hasPermission("vs.addworld")) {
						if(getConfig().get("worlds." + p.getWorld().getName()) == null) {
							getConfig().set("worlds." + p.getWorld().getName() + ".x", p.getLocation().getX());
							getConfig().set("worlds." + p.getWorld().getName() + ".y", p.getLocation().getY());
							getConfig().set("worlds." + p.getWorld().getName() + ".z", p.getLocation().getZ());
							getConfig().set("worlds." + p.getWorld().getName() + ".yaw", p.getLocation().getYaw());
							getConfig().set("worlds." + p.getWorld().getName() + ".pitch", p.getLocation().getPitch());
							saveConfig();
							p.sendMessage(ChatColor.GREEN + "Successfully added this world and spawn!");
						}
						else p.sendMessage(ChatColor.RED + "This world is already added!");
					}
					else p.sendMessage(ChatColor.RED + "You don't have enough permissions!");
				}
			}
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		List<String>addedWorlds = this.getConfig().getStringList("worlds.");
		if(addedWorlds.contains(e.getPlayer().getWorld().getName())) {
			World world = e.getPlayer().getWorld();
			if(e.getPlayer().getLocation().getY() <= 0) {
				double x = getConfig().getDouble("worlds." + e.getPlayer().getWorld().getName() + ".x");
				double y = getConfig().getDouble("worlds." + e.getPlayer().getWorld().getName() + ".y");
				double z = getConfig().getDouble("worlds." + e.getPlayer().getWorld().getName() + ".z");
				float yaw = (float) getConfig().get("worlds." + e.getPlayer().getWorld().getName() + ".yaw");
				float pitch = (float) getConfig().get("worlds." + e.getPlayer().getWorld().getName() + ".pitch");
				
				e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), x, y, z, yaw, pitch));
			}
		}
	}
	
	public void onDisable() {
		
	}
}
