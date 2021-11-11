package io.github.siminoo.prorandomtp;

import io.github.siminoo.prorandomtp.RandomTPCommand;
import org.bukkit.plugin.java.JavaPlugin;
 
public class ProRandomTP extends JavaPlugin {
 
    @Override
    public void onEnable() {
        TeleportUtils yeet = new TeleportUtils(this);
        getCommand("rtp").setExecutor(new RandomTPCommand());
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
 
}
