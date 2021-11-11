package io.github.siminoo.proslots;

import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProSlots extends JavaPlugin
{
    public void onEnable() {
        this.saveDefaultConfig();
    }
    
    public void onDisable() {
        if (this.getConfig().getBoolean("SaveOnRestart")) {
            try {
				this.updateServerProperties();
			} catch (Throwable e) {
				e.printStackTrace();
			}
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!sender.hasPermission("changeslots.admin")) {
            sender.sendMessage(this.getConfigString("NoPermission"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(this.getConfigString("NoArgument"));
            return true;
        }
        try {
            this.changeSlots(Integer.parseInt(args[0]));
            sender.sendMessage(this.getConfigString("Success").replace("%n", args[0]));
        }
        catch (NumberFormatException e2) {
            sender.sendMessage(this.getConfigString("NoNumber"));
        }
        catch (ReflectiveOperationException e) {
            sender.sendMessage(this.getConfigString("Error"));
            this.getLogger().log(Level.SEVERE, "An error occurred while updating max players", e);
        }
        return true;
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return Collections.emptyList();
    }
    
    private String getConfigString(final String key) {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(key));
    }
    
    private void changeSlots(final int slots) throws ReflectiveOperationException {
        final Method serverGetHandle = this.getServer().getClass().getDeclaredMethod("getHandle", (Class<?>[])new Class[0]);
        final Object playerList = serverGetHandle.invoke(this.getServer(), new Object[0]);
        final Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");
        maxPlayersField.setAccessible(true);
        maxPlayersField.set(playerList, slots);
    }
    
    private void updateServerProperties() throws Throwable {
        final Properties properties = new Properties();
        final File propertiesFile = new File("server.properties");
        try {
            final InputStream is = new FileInputStream(propertiesFile);
            try {
                properties.load(is);
                is.close();
            }
            catch (Throwable t) {
                try {
                    is.close();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw t;
            }
            final String maxPlayers = Integer.toString(this.getServer().getMaxPlayers());
            if (properties.getProperty("max-players").equals(maxPlayers)) {
                return;
            }
            this.getLogger().info("Saving max players to server.properties...");
            properties.setProperty("max-players", maxPlayers);
            final OutputStream os = new FileOutputStream(propertiesFile);
            try {
                properties.store(os, "Minecraft server properties");
                os.close();
            }
            catch (Throwable t2) {
                try {
                    os.close();
                }
                catch (Throwable exception2) {
                    t2.addSuppressed(exception2);
                }
                throw t2;
            }
        }
        catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "Error while saving max players in server properties", e);
        }
    }
}