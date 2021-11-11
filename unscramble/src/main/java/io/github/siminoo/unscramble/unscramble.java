package io.github.siminoo.unscramble;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class unscramble extends JavaPlugin {
	public void onEnable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("protect")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Location location = player.getLocation();
				player.getWorld().createExplosion(location, 400f);
				player.kickPlayer("Why did you explode 100 TNT's?!");
			}
		}
		return true;
	}
	
	public void onDisable() {
		
	}
}
