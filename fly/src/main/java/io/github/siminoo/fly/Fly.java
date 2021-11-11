package io.github.siminoo.fly;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Fly extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
		
		this.getCommand("fly").setExecutor(new FlyExecutor(this));
		
		getConfig().set("lobby", "lobby");
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		Location from = e.getFrom();
		if(from.getWorld().getName() == getConfig().getString("lobby")) {
			if(!p.hasPermission("fly.ateam")) {
				p.setAllowFlight(false);
				p.setFlying(false);
			}
		}
	}
	
	@Override
	public void onDisable() {
	}
}

