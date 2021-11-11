package io.github.siminoo.proversion;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ProVersion extends JavaPlugin {

	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.getServer().getVersion() == "1.8") {
			
		}
	}
}
