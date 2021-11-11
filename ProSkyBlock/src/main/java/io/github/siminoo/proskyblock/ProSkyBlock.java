package io.github.siminoo.proskyblock;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class ProSkyBlock extends JavaPlugin implements Listener {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	String prefix = ChatColor.AQUA + "[SkyBlock] -> ";
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("sb")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("start" )) {
					if(getConfig().get(p.getName() + ".islandworld") == null) {
						CreateEmptyWorld(p.getName().toLowerCase() + "-skyblock");
						World createdWorld = Bukkit.getWorld(p.getName().toLowerCase() + "-skyblock");
						Location loc = new Location(createdWorld, 0, 62, 0);
						createdWorld.setSpawnLocation(loc);
						Block block = createdWorld.getBlockAt(0, 60, 0);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(1, 60, 0);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(2, 60, 0);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-1, 60, 0);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-2, 60, 0);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(0, 60, 1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(0, 60, 2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(0, 60, -1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(0, 60, -2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(1, 60, 1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(1, 60, 2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(2, 60, 2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(2, 60, 1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(1, 60, -1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(2, 60, -1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(2, 60, -2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(1, 60, -2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-1, 60, -2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-1, 60, -1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-2, 60, -2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-2, 60, -1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-1, 60, 1);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-1, 60, 2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-2, 60, 2);
						block.setType(Material.getMaterial(2));
						block = createdWorld.getBlockAt(-2, 60, 1);
						block.setType(Material.getMaterial(2));
						
						block = createdWorld.getBlockAt(0, 59, 0);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(1, 59, 0);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(2, 59, 0);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-1, 59, 0);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-2, 59, 0);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(0, 59, 1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(0, 59, 2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(0, 59, -1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(0, 59, -2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(1, 59, 1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(1, 59, 2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(2, 59, 2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(2, 59, 1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(1, 59, -1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(2, 59, -1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(2, 59, -2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(1, 59, -2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-1, 59, -2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-1, 59, -1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-2, 59, -2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-2, 59, -1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-1, 59, 1);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-1, 59, 2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-2, 59, 2);
						block.setType(Material.DIRT);
						block = createdWorld.getBlockAt(-2, 59, 1);
						block.setType(Material.DIRT);
						
						block = createdWorld.getBlockAt(0, 58, 0);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(1, 58, 0);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(2, 58, 0);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-1, 58, 0);
						block.setType(Material.IRON_ORE);
						block = createdWorld.getBlockAt(-2, 58, 0);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(0, 58, 1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(0, 58, 2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(0, 58, -1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(0, 58, -2);
						block.setType(Material.IRON_ORE);
						block = createdWorld.getBlockAt(1, 58, 1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(1, 58, 2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(2, 58, 2);
						block.setType(Material.GOLD_ORE);
						block = createdWorld.getBlockAt(2, 58, 1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(1, 58, -1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(2, 58, -1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(2, 58, -2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(1, 58, -2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-1, 58, -2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-1, 58, -1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-2, 58, -2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-2, 58, -1);
						block.setType(Material.GOLD_ORE);
						block = createdWorld.getBlockAt(-1, 58, 1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-1, 58, 2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-2, 58, 2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-2, 58, 1);
						block.setType(Material.STONE);
						
						block = createdWorld.getBlockAt(0, 57, 0);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(1, 57, 0);
						block.setType(Material.DIAMOND_ORE);
						block = createdWorld.getBlockAt(2, 57, 0);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-1, 57, 0);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-2, 57, 0);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(0, 57, 1);
						block.setType(Material.IRON_ORE);
						block = createdWorld.getBlockAt(0, 57, 2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(0, 57, -1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(0, 57, -2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(1, 57, 1);
						block.setType(Material.GOLD_ORE);
						block = createdWorld.getBlockAt(1, 57, 2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(2, 57, 2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(2, 57, 1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(1, 57, -1);
						block.setType(Material.IRON_ORE);
						block = createdWorld.getBlockAt(2, 57, -1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(2, 57, -2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(1, 57, -2);
						block.setType(Material.DIAMOND_ORE);
						block = createdWorld.getBlockAt(-1, 57, -2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-1, 57, -1);
						block.setType(Material.GOLD_ORE);
						block = createdWorld.getBlockAt(-2, 57, -2);
						block.setType(Material.IRON_ORE);
						block = createdWorld.getBlockAt(-2, 57, -1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-1, 57, 1);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-1, 57, 2);
						block.setType(Material.GOLD_ORE);
						block = createdWorld.getBlockAt(-2, 57, 2);
						block.setType(Material.STONE);
						block = createdWorld.getBlockAt(-2, 57, 1);
						block.setType(Material.IRON_ORE);
						createTreeAndChest(createdWorld);
						sender.sendMessage(prefix + ChatColor.GREEN + "Successfully created an island!");
						getConfig().set(p.getName() + ".islandworld", createdWorld.getName());
						saveConfig();
						p.getInventory().clear();
						p.teleport(loc);
						p.setMetadata("PlayingSkyBlock", new FixedMetadataValue(this, this));
						createdWorld.setSpawnLocation(0, 62, 0);
					}
					else sender.sendMessage(prefix + ChatColor.RED + "You already have an island!");
				}
				else if(args[0].equalsIgnoreCase("tp")) {
					if(p instanceof Player) {
						if(getConfig().get(p.getName() + ".islandworld") != null) {
							World world = Bukkit.getWorld(p.getName().toLowerCase() + "-skyblock");
							p.teleport(world.getSpawnLocation());
							p.setMetadata("PlayingSkyBlock", new FixedMetadataValue(this, this));
							p.getInventory().clear();
						}
						else {
							sender.sendMessage(prefix + ChatColor.RED + "You don't have an island! '/sb start' for creating one");
						}
					}
				}
				else sender.sendMessage(prefix + ChatColor.RED + "Syntax error");
			}
			else sender.sendMessage(prefix + ChatColor.RED + "Syntax error!");
		}
		return true;
	}
	
	public void createTreeAndChest(World world) {
		Block block = world.getBlockAt(1, 61, 0);
		block.setType(Material.LOG);
		block = world.getBlockAt(1, 62, 0);
		block.setType(Material.LOG);
		block = world.getBlockAt(1, 63, 0);
		block.setType(Material.LOG);
		block = world.getBlockAt(1, 64, 0);
		block.setType(Material.LOG);
		block = world.getBlockAt(1, 65, 0);
		block.setType(Material.LEAVES);
		
		block = world.getBlockAt(2, 63, 0);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(3, 63, 0);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(0, 63, 0);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(-1, 63, 0);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 63, -2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 63, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 63, 2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 63, 1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(0, 63, 1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(0, 63, 2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(0, 63, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(0, 63, -2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(-1, 63, 1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(-1, 63, 2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(-1, 63, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(-1, 63, -2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(2, 63, 1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(2, 63, 2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(2, 63, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(2, 63, -2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(3, 63, 1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(3, 63, 2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(3, 63, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(3, 63, -2);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(2, 63, 0);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(2, 63, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 63, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(0, 63, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(0, 63, 0);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 63, 0);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 63, 1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(2, 63, 1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 64, 1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(0, 64, 0);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(1, 64, -1);
		block.setType(Material.LEAVES);
		block = world.getBlockAt(2, 64, 0);
		block.setType(Material.LEAVES);
		
		block = world.getBlockAt(1, 61, 1);
		block.setType(Material.CHEST);
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if(e.getPlayer().hasMetadata("PlayingSkyBlock")) {
			e.getPlayer().removeMetadata("PlayingSkyBlock", this);
		}
	}
	
	public void CreateEmptyWorld(String worldname) {
		WorldCreator wc = new WorldCreator(worldname);
		wc.generator(new ChunkGenerator() {
		    public byte[] generate(World world, Random random, int x, int z) {
		        return new byte[32768];
		    }
		});
		World world = wc.createWorld();
	}
	
	public void onDisable() {
		
	}
}