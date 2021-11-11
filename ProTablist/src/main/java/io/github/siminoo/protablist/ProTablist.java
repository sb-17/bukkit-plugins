package io.github.siminoo.protablist;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ProTablist extends JavaPlugin implements Listener{
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	
	
	public void onDisable() {
		
	}
}
