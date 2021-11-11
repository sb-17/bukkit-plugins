package io.github.siminoo.pronickname;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ProNick extends JavaPlugin {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pn")) {
			if(args.length > 1) {
				
			}
		}
		return true;
	}
	
	public void onDisable() {
		
	}
}
