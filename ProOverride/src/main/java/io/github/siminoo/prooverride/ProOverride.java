package io.github.siminoo.prooverride;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class ProOverride extends JavaPlugin {

	String MOTD = Bukkit.getServer().getMotd();
	
	@EventHandler
	public void on(ServerListPingEvent e) {
		
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
}
