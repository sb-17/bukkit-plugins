package io.github.siminoo.speeduhc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public final class speeduhc extends JavaPlugin {
	
	ItemStack wand = new ItemStack(Material.DIAMOND_AXE);
	
	
	
	public int SpeedUhcJoin = 17;
	private SpeedUhcJoin speeduhcjoinclass = new SpeedUhcJoin(this);
	
	@Override
	public void onEnable()
	{
		this.getCommand("speeduhc").setExecutor(new speeduhcCreate(this));
		speeduhcjoinclass.getSpeedUhcJoin();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		getServer().getPluginManager().registerEvents(new SpeedUhcLeave(), this);
		getServer().getPluginManager().registerEvents(new SpeedUhcJoin(this), this);
	}
	@Override
	public void onDisable()
	{
	}
	
	public class speeduhcCreate implements CommandExecutor {
		
		private speeduhc plugin;
		public speeduhcCreate(speeduhc plugin) {
			this.plugin = plugin;
		}
		
		@EventHandler
		public void onPlayerInteract(PlayerInteractEvent event) {
			if (event.getPlayer().hasMetadata("creatingSpeedUhcPos") && event.getPlayer().getItemInHand() == wand) {
				Player player = event.getPlayer();
				getConfig().set("arenas." + player.getMetadata("creatingSpeedUhcArena") + "." + player.getMetadata("creatingSpeedUhcPos") + ".world", player.getWorld().getName());
                getConfig().set("arenas." + player.getMetadata("creatingSpeedUhcArena") + "." + player.getMetadata("creatingSpeedUhcPos") + ".x", player.getLocation().getX());
                getConfig().set("arenas." + player.getMetadata("creatingSpeedUhcArena") + "." + player.getMetadata("creatingSpeedUhcPos") + ".y", player.getLocation().getY());
                getConfig().set("arenas." + player.getMetadata("creatingSpeedUhcArena") + "." + player.getMetadata("creatingSpeedUhcPos") + ".z", player.getLocation().getZ());
                getConfig().set("arenas." + player.getMetadata("creatingSpeedUhcArena") + "." + player.getMetadata("creatingSpeedUhcPos") + ".yaw", player.getLocation().getYaw());
                getConfig().set("arenas." + player.getMetadata("creatingSpeedUhcArena") + "." + player.getMetadata("creatingSpeedUhcPos") + ".pitch", player.getLocation().getPitch());
                saveConfig();
				player.sendMessage(ChatColor.GREEN + "Position " + player.getMetadata("creatingSpeedUhcPos").toString() + " was set successfully in arena " + player.getMetadata("creatingSpeedUhcArena").toString());
			}
		}
		
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (cmd.getName().equalsIgnoreCase("speeduhc")) {
				if (sender instanceof Player) {
					if (args.length == 2) {
						if (args[0].equalsIgnoreCase("create")) {
							if(sender.hasPermission("speeduhc.create") || sender.isOp()) {
								getConfig().set("arenas", args[1]);
								ItemStack itemstack = new ItemStack(Material.DIAMOND_AXE);
								((Player) sender).getInventory().addItem(itemstack);
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos1")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn1"));
									
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn1"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos2")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn2"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn2"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos3")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn3"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn3"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos4")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn4"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn4"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos5")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn5"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn5"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos6")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn6"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn6"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos7")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn7"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn7"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos8")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn8"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn8"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						else if (args[0].equalsIgnoreCase("setpos9")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn9"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn9"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							}else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
							
						}
						else if (args[0].equalsIgnoreCase("setpos10")) {
							if (sender.hasPermission("speeduhc.setpos") || sender.isOp()) {
								Player player = (Player) sender;
								if (player.hasMetadata("creatingSpeedUhcPos")) {
									player.removeMetadata("creatingSpeedUhcPos", plugin);
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn10"));
								}
								else {
									player.setMetadata("creatingSpeedUhcPos", new FixedMetadataValue(plugin, "spawn10"));
								}
								if (player.hasMetadata("creatingSpeedUhcArena")) {
									player.removeMetadata("creatingSpeedUhcArena", plugin);
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
								else {
									player.setMetadata("creatingSpeedUhcArena", new FixedMetadataValue(plugin, args[1]));
								}
							} else {
								sender.sendMessage("DEMENTNY PLUGIN!");
							}
						}
						
					}
					else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("stopcreate")) {
							Player player = (Player) sender;
							player.removeMetadata("creatingSpeedUhcPos", plugin);
							player.removeMetadata("creatingSpeedUhcArena", plugin);
						}
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "More arguments please!");
					return true;
				}
				
			}
			return true;
		}
	}
}
		
	
	

