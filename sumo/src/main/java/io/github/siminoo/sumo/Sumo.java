package io.github.siminoo.sumo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;

public class Sumo extends JavaPlugin implements Listener {
	String prefix = ChatColor.AQUA + "[Duels] -> ";
	String permErr = ChatColor.RED + "You don't have enough permissions!";
	ItemStack leaveKP = new ItemStack(Material.BED);
	
	public List<Player> queue = new ArrayList<Player>();
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        
        ItemMeta leaveKPMeta = leaveKP.getItemMeta();
    	leaveKPMeta.setDisplayName(ChatColor.RED + "Leave");
    	leaveKP.setItemMeta(leaveKPMeta);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("sumo")) {
			if(sender instanceof Player) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("setspawn1")) {
						if(sender.hasPermission("sumo.setpos1")) {
							getConfig().set("arena." + ".pos1." + ".world", p.getWorld().getName());
							getConfig().set("arena." + ".pos1." + ".x", p.getLocation().getX());
							getConfig().set("arena." + ".pos1." + ".y", p.getLocation().getY());
							getConfig().set("arena." + ".pos1." + ".z", p.getLocation().getZ());
							getConfig().set("arena." + ".pos1." + ".yaw", p.getLocation().getYaw());
							getConfig().set("arena." + ".pos1." + ".pitch", p.getLocation().getPitch());
							saveConfig();
							sender.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.GREEN + "Successfully set SpawnPos1!");
						}
						else sender.sendMessage(prefix + permErr);
					}
					else if(args[0].equalsIgnoreCase("setspawn2")) {
						if(sender.hasPermission("sumo.setpos2")) {
							getConfig().set("arena." + ".pos2." + ".world", p.getWorld().getName());
							getConfig().set("arena." + ".pos2." + ".x", p.getLocation().getX());
							getConfig().set("arena." + ".pos2." + ".y", p.getLocation().getY());
							getConfig().set("arena." + ".pos2." + ".z", p.getLocation().getZ());
							getConfig().set("arena." + ".pos2." + ".yaw", p.getLocation().getYaw());
							getConfig().set("arena." + ".pos2." + ".pitch", p.getLocation().getPitch());
							saveConfig();
							sender.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.GREEN + "Successfully set SpawnPos1!");
						}
						else sender.sendMessage(prefix + permErr);
					}
					else if(args[0].equalsIgnoreCase("join")) {
						if(queue.get(0) == p) {
							p.sendMessage(prefix + ChatColor.RED + "You already are in queue!");
						}
						else {
							queue.add(p);
							p.sendMessage(prefix + ChatColor.GREEN + "Successfully added to queue!");
							if(queue.size() == 2) {
								World arena = Bukkit.getWorld(getConfig().getString("arena." + ".pos1." + ".world"));
								Player p1 = queue.get(0);
								Player p2 = queue.get(1);
								p1.setMetadata("WaitingForDuel", new FixedMetadataValue(this, this));
								p2.setMetadata("WaitingForDuel", new FixedMetadataValue(this, this));
								if(arena.getPlayers().size() == 2) {
									p1.sendMessage(prefix + ChatColor.RED + "Waiting for empty arena...");
									p2.sendMessage(prefix + ChatColor.RED + "Waiting for empty arena...");
									while(arena.getPlayers().size() == 2) {
										
									}
									
									if(p1.hasMetadata("WaitingForDuel")) {
										p1.removeMetadata("WaitingForDuel", this);
										p1.setMetadata("PlayingDuel", new FixedMetadataValue(this, this));
										
										World world = Bukkit.getWorld(getConfig().getString("arena." + ".pos1." + ".world"));
										double x = getConfig().getDouble("arena." + ".pos1." + ".x");
										double y = getConfig().getDouble("arena." + ".pos1." + ".y");
										double z = getConfig().getDouble("arena." + ".pos1." + ".z");
										float yaw = (float) getConfig().getDouble("arena." + ".pos1." + ".yaw");
										float pitch = (float) getConfig().getDouble("arena." + ".pos1." + ".pitch");

										p1.getInventory().clear();
										
										p1.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
										p1.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
										p1.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
										p1.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
										p1.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
										p1.getInventory().setItem(2, new ItemStack(Material.FISHING_ROD));
										p1.getInventory().setItem(4, new ItemStack(Material.GOLDEN_CARROT, 8));
										p1.getInventory().setItem(5, new ItemStack(Material.LAVA_BUCKET));
										p1.getInventory().setItem(6, new ItemStack(Material.WATER_BUCKET));
										
										p1.teleport(new Location(world, x, y, z, yaw, pitch));
										
										p1.sendMessage(prefix + ChatColor.GREEN + "Successfully joined the Duel!");
									}
									else {
										queue.remove(0);
										queue.remove(1);
										
										p2.sendMessage(prefix + ChatColor.RED + "Duel cancelled!");
									}
									
									if(p2.hasMetadata("WaitingForDuel")) {
										p2.removeMetadata("WaitingForDuel", this);
										p2.setMetadata("PlayingDuel", new FixedMetadataValue(this, this));
										
										World world = Bukkit.getWorld(getConfig().getString("arena." + ".pos2." + ".world"));
										double x = getConfig().getDouble("arena." + ".pos2." + ".x");
										double y = getConfig().getDouble("arena." + ".pos2." + ".y");
										double z = getConfig().getDouble("arena." + ".pos2." + ".z");
										float yaw = (float) getConfig().getDouble("arena." + ".pos2." + ".yaw");
										float pitch = (float) getConfig().getDouble("arena." + ".pos2." + ".pitch");

										p2.getInventory().clear();
										
										p2.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
										p2.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
										p2.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
										p2.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
										p2.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
										p2.getInventory().setItem(2, new ItemStack(Material.FISHING_ROD));
										p2.getInventory().setItem(4, new ItemStack(Material.GOLDEN_CARROT, 8));
										p2.getInventory().setItem(5, new ItemStack(Material.LAVA_BUCKET));
										p2.getInventory().setItem(6, new ItemStack(Material.WATER_BUCKET));
										
										p2.teleport(new Location(world, x, y, z, yaw, pitch));
										
										p2.sendMessage(prefix + ChatColor.GREEN + "Successfully joined the Duel!");
									}
									else {
										queue.remove(0);
										queue.remove(1);
										
										p1.sendMessage(prefix + ChatColor.RED + "Duel cancelled!");
									}
								}
							}
							else sender.sendMessage(prefix + ChatColor.GOLD + "Waiting for another player!");
						}
					}
					else if(args[0].equalsIgnoreCase("stop")) {
						if(sender.hasPermission("sumo.stop")) {
							World arena = Bukkit.getWorld(getConfig().getString("arena." + ".pos1." + ".world"));
							if(arena.getPlayers().size() == 0) {
								sender.sendMessage(prefix + ChatColor.RED + "No Duel is currently running!");
							}
							else if(arena.getPlayers().size() == 2) {
								Player p1 = arena.getPlayers().get(0);
								Player p2 = arena.getPlayers().get(1);
								Bukkit.dispatchCommand(p1, "lobby");
								Bukkit.dispatchCommand(p2, "lobby");
								sender.sendMessage(prefix + ChatColor.GREEN + "Successfully stopped Duel!");
							}
							else sender.sendMessage(prefix + ChatColor.RED + "Can't stop Duel!");
						}
					}
					else if(args[0].equalsIgnoreCase("setlobby")) {
						if(sender.hasPermission("sumo.setlobby")) {
							getConfig().set("lobby." + ".world", p.getWorld().getName());
							getConfig().set("lobby." + ".x", p.getLocation().getX());
							getConfig().set("lobby." + ".y", p.getLocation().getY());
							getConfig().set("lobby." + ".z", p.getLocation().getZ());
							getConfig().set("lobby." + ".yaw", p.getLocation().getYaw());
							getConfig().set("lobby." + ".pitch", p.getLocation().getPitch());
							saveConfig();
							
							sender.sendMessage(prefix + ChatColor.GREEN + "Successfully set Sumo lobby!");
						}
						else sender.sendMessage(prefix + permErr);
					}
					else sender.sendMessage(prefix + ChatColor.RED + "Syntax Error!");
				}
				else sender.sendMessage(prefix + ChatColor.RED + "Syntax Error!");
			}
			else sender.sendMessage(prefix + ChatColor.RED + "You are not a player!");
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(getConfig().get("players." + p.getName()) == null) {
			getConfig().set("players." + p.getName() + ".wins", 0);
			getConfig().set("players." + p.getName() + ".loses", 0);
			saveConfig();
		}
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageByEntityEvent e) {
		Player killer = (Player) e.getDamager();
		Player killed = (Player) e.getEntity();
		
		if(killer instanceof Player && killed instanceof Player) {
			if(killer.hasMetadata("PlayingDuel") && killed.hasMetadata("PlayingDuel")) {
				if(killed.getHealth() - e.getDamage() < 1) {
					e.setCancelled(true);
					killer.removeMetadata("PlayingDuel", this);
					int killerWins = getConfig().getInt("players." + killer.getName() + ".wins");
					int killedLoses = getConfig().getInt("players." + killer.getName() + ".loses");
					
					getConfig().set("players." + killer.getName() + ".wins", killerWins + 1);
					getConfig().set("players." + killed.getName() + ".loses", killedLoses + 1);
					
					World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
					double x = getConfig().getDouble("lobby." + ".x");
					double y = getConfig().getDouble("lobby." + ".y");
					double z = getConfig().getDouble("lobby." + ".z");
					float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
					float pitch = (float) getConfig().getDouble("lobby." + ".pitch");
					
					killer.getInventory().clear();
					killed.getInventory().clear();
					
					killer.sendMessage(prefix + ChatColor.GOLD + "Congratulations, you won the Duel! Now teleporting to Sumo lobby.");
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addmoney 5 " + killer.getName());
					
					killer.teleport(new Location(world, x, y, z, yaw, pitch));
					
					killed.removeMetadata("PlayingDuel", this);
					
					world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
					x = getConfig().getDouble("lobby." + ".x");
					y = getConfig().getDouble("lobby." + ".y");
					z = getConfig().getDouble("lobby." + ".z");
					yaw = (float) getConfig().getDouble("lobby." + ".yaw");
					pitch = (float) getConfig().getDouble("lobby." + ".pitch");
					
					killed.sendMessage(prefix + ChatColor.GOLD + "Better luck next time! Now teleporting to Sumo lobby.");
					
					killed.teleport(new Location(world, x, y, z, yaw, pitch));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if(item.getType() == Material.BED) {
			if(e.getPlayer().hasMetadata("DuelsLobby")) {
				Bukkit.dispatchCommand(p, "lobby");
				e.getPlayer().removeMetadata("DuelsLobby", this);
			}
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Location to = e.getTo();
		
		if(to.getWorld().getName() == getConfig().getString("lobby." + ".world")) {
			createScoreboard(e.getPlayer());
			e.getPlayer().getInventory().clear();
			e.getPlayer().setMetadata("DuelsLobby", new FixedMetadataValue(this, this));
			e.getPlayer().getInventory().setItem(8, leaveKP);
		}
	}
	
	public void createScoreboard(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard duelsLobbyBoard = m.getNewScoreboard();
		Objective duels = duelsLobbyBoard.registerNewObjective(ChatColor.AQUA + "Duels", "");
		duels.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score wins = duels.getScore(ChatColor.GOLD + "Wins:" + ChatColor.RED);
		wins.setScore(getConfig().getInt("players." + player.getName() + ".wins"));
		Score loses = duels.getScore(ChatColor.GOLD + "Loses:" + ChatColor.RED);
		loses.setScore(getConfig().getInt("players." + player.getName() + ".loses"));
		
		player.setScoreboard(duelsLobbyBoard);
	}

	@Override
	public void onDisable() {
		
	}
}