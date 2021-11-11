package io.github.siminoo.prokp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ProKP extends JavaPlugin implements Listener {
	public Inventory kitMenu = Bukkit.createInventory(null, 27, "KitPvP");
	public ItemStack kitSel = new ItemStack(Material.WOOD_SWORD);
	public ItemStack leaveKP = new ItemStack(Material.BED);
	
	public ItemStack closeInv = new ItemStack(Material.BARRIER);
	public ItemStack bow = new ItemStack(Material.BOW);
	
	public ItemStack defaultKit = new ItemStack(Material.IRON_SWORD);
	public ItemStack archerKit = new ItemStack(Material.BOW);
	public ItemStack speedKit = new ItemStack(Material.LEATHER_BOOTS);
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        
        ItemMeta closeInvMeta = closeInv.getItemMeta();
        closeInvMeta.setDisplayName("Close");
        closeInv.setItemMeta(closeInvMeta);
        kitMenu.setItem(26, closeInv);
        
        ItemMeta defaultKitMeta = defaultKit.getItemMeta();
        defaultKitMeta.setDisplayName("Default kit");
        defaultKit.setItemMeta(defaultKitMeta);
        kitMenu.setItem(0, defaultKit);
        
        ItemMeta archerKitMeta = archerKit.getItemMeta();
        archerKitMeta.setDisplayName("Archer");
        archerKit.setItemMeta(archerKitMeta);
        kitMenu.setItem(1, archerKit);
        
        ItemMeta speedKitMeta = speedKit.getItemMeta();
        speedKitMeta.setDisplayName("Speed Kit");
        speedKit.setItemMeta(speedKitMeta);
        kitMenu.setItem(2, speedKit);
        
        ItemMeta leaveKPMeta = leaveKP.getItemMeta();
    	leaveKPMeta.setDisplayName(ChatColor.RED + "Leave");
    	leaveKP.setItemMeta(leaveKPMeta);
    	
    	ItemMeta kitMeta = kitSel.getItemMeta();
    	kitMeta.setDisplayName(ChatColor.AQUA + "Kit Selector");
    	kitSel.setItemMeta(kitMeta);
        
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.addEnchantment(Enchantment.DURABILITY, 1);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        
        getConfig().set("arena." + ".world", "kitpvp");
		getConfig().set("arena." + ".x", 0.4747855587004777);
		getConfig().set("arena." + ".y", 32.0);
		getConfig().set("arena." + ".z", 0.30179736209607055);
		getConfig().set("arena." + ".yaw", -0.36159256);
		getConfig().set("arena." + ".pitch", 9.318331);
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("kp")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("setspawn")) {
					if (sender.hasPermission("kp.create")) {
						if(sender instanceof Player) {
							getConfig().set("arena." + ".world", p.getWorld().getName());
							getConfig().set("arena." + ".x", p.getLocation().getX());
							getConfig().set("arena." + ".y", p.getLocation().getY());
							getConfig().set("arena." + ".z", p.getLocation().getZ());
							getConfig().set("arena." + ".yaw", p.getLocation().getYaw());
							getConfig().set("arena." + ".pitch", p.getLocation().getPitch());
							saveConfig();
							sender.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.GREEN + "Successfully set spawn!");
						}
					}
					else sender.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.RED + "You don't have enough permissions!");
				}
				else if (args[0].equalsIgnoreCase("join")) {
					if(sender instanceof Player) {
						World world = Bukkit.getWorld(getConfig().getString("arena." + ".world"));
						double x = getConfig().getDouble("arena." + ".x");
						double y = getConfig().getDouble("arena." + ".y");
						double z = getConfig().getDouble("arena." + ".z");
						float yaw = (float) getConfig().getDouble("arena." + ".yaw");
						float pitch = (float) getConfig().getDouble("arena." + ".pitch");
	
						p.teleport(new Location(world, x, y, z, yaw, pitch));
	
						p.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.GREEN + "You have successfully teleported to KitPvP arena!" + ChatColor.RED + " Please use pressureplates to jump into the arena!");
						
						p.getInventory().clear();
						p.getInventory().setItem(0, kitSel);
						p.getInventory().setItem(8, leaveKP);
						
						p.setMetadata("KitpvpLobby", new FixedMetadataValue(this, this));
					}
					else sender.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.RED + "Syntax error!");
				}
			}
			else sender.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.RED + "Syntax error!");
		}
		return true;
	}
	
	public void createScoreboard(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard kitPVPBoard = m.getNewScoreboard();
		Objective stats = kitPVPBoard.registerNewObjective("KitPvP", "");
		stats.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score kills = stats.getScore(ChatColor.GOLD + "Kills:" + ChatColor.RED);
		kills.setScore(getConfig().getInt("players." + player.getName() + ".kills"));	
		Score deaths = stats.getScore(ChatColor.GOLD + "Deaths:" + ChatColor.RED);
		deaths.setScore(getConfig().getInt("players." + player.getName() + ".deaths"));	
		Score KD = stats.getScore(ChatColor.GOLD + "KD:" + ChatColor.RED);
		int kills1 = getConfig().getInt("players." + player.getName() + ".kills");
		int deaths1 = getConfig().getInt("players." + player.getName() + ".deaths");
		if(getConfig().getInt("players." + player.getName() + ".deaths") == 0) {
			KD.setScore(kills1);
		}
		else {
			KD.setScore(kills1/deaths1);
		}
		
		player.setScoreboard(kitPVPBoard);
	}
	
	public void updateScoreboard(Player player) {
		Score kills = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GOLD + "Kills:" + ChatColor.RED);
		kills.setScore(getConfig().getInt("players." + player.getName() + ".kills"));
		
		Score deaths = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GOLD + "Deaths:" + ChatColor.RED);
		deaths.setScore(getConfig().getInt("players." + player.getName() + ".deaths"));
		
		Score KD = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GOLD + "KD:" + ChatColor.RED);
		int kills1 = getConfig().getInt("players." + player.getName() + ".kills");
		int deaths1 = getConfig().getInt("players." + player.getName() + ".deaths");
		
		if(getConfig().getInt("players." + player.getName() + ".deaths") == 0) {
			KD.setScore(kills1);
		}
		else {
			KD.setScore(kills1/deaths1);
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		if(e.getTo().getWorld().getName().equals(getConfig().getString("arena." + ".world"))) {
			p.setMetadata("KitpvpLobby", new FixedMetadataValue(this, this));
			createScoreboard(p);
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(kitMenu.getName())) {
			if(e.getWhoClicked().hasMetadata("KitpvpLobby")) {
				if(e.getCurrentItem().getType() == Material.IRON_SWORD) {
					e.getWhoClicked().getInventory().clear();
					e.getWhoClicked().getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
					e.getWhoClicked().getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
					e.getWhoClicked().getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					e.getWhoClicked().getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					e.getWhoClicked().getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
					e.getWhoClicked().closeInventory();
				}
				else if (e.getCurrentItem().getType() == Material.BOW) {
					if(e.getWhoClicked().hasPermission("kitpvp.vip")) {
						e.getWhoClicked().getInventory().clear();
						e.getWhoClicked().getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
						e.getWhoClicked().getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
						e.getWhoClicked().getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
						e.getWhoClicked().getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
						e.getWhoClicked().getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
						e.getWhoClicked().getInventory().setItem(1, bow);
						e.getWhoClicked().getInventory().setItem(8, new ItemStack(Material.ARROW));
					}
					else {
						e.getWhoClicked().sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.RED + "This is VIP kit! You can buy it here: " + ChatColor.GREEN + "www.scriptcraft.tk");
					}
					e.getWhoClicked().closeInventory();
				}
				else if (e.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
					if(e.getWhoClicked().hasPermission("kitpvp.vip")) {
						e.getWhoClicked().getInventory().clear();
						e.getWhoClicked().getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
						e.getWhoClicked().getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
						e.getWhoClicked().getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
						e.getWhoClicked().getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
						e.getWhoClicked().getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
						e.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0), true);
					}
					else {
						e.getWhoClicked().sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.RED + "This is VIP kit! You can buy it here: " + ChatColor.GREEN + "www.scriptcraft.tk");
					}
					e.getWhoClicked().closeInventory();
				}
				else if(e.getCurrentItem().getType() == Material.BARRIER) {
					e.getWhoClicked().closeInventory();
				}
				e.setCancelled(true);
			}
			else return;
		}
	}
	
	public void death(Player p) {
		World world = Bukkit.getWorld(getConfig().getString("arena." + ".world"));
		double x = getConfig().getDouble("arena." + ".x");
		double y = getConfig().getDouble("arena." + ".y");
		double z = getConfig().getDouble("arena." + ".z");
		float yaw = (float) getConfig().getDouble("arena." + ".yaw");
		float pitch = (float) getConfig().getDouble("arena." + ".pitch");

		p.teleport(new Location(world, x, y, z, yaw, pitch));
		
		p.getInventory().clear();
		p.getInventory().setItem(0, kitSel);
		p.getInventory().setItem(8, leaveKP);
		
		p.removeMetadata("PlayingKitpvp", this);
		p.setMetadata("KitpvpLobby", new FixedMetadataValue(this, this));
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if(e.getPlayer().hasMetadata("KitpvpLobby")) {
			if(item == null) return;
			if (item.getType() == Material.WOOD_SWORD) {
				p.openInventory(kitMenu);
			}
			else if(item.getType() == Material.BED) {
				Bukkit.dispatchCommand(p, "lobby");
			}
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerHeal(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
				if(e.getPlayer().hasMetadata("KitpvpLobby")) {
					if(e.getPlayer().getHealth() == e.getPlayer().getMaxHealth()) {
						e.getPlayer().sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.RED + "You have full HP now!");
					}
					else {
						if(e.getPlayer().getHealth() + 6 > e.getPlayer().getMaxHealth()) {
							e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
						}
						else {
							e.getPlayer().setHealth(e.getPlayer().getHealth() + 6);
						}
					}
				}
				else return;
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(e.getPlayer().getWorld().getName() == getConfig().getString("arena." + ".world")) {
			if(e.getPlayer().hasPermission("kitpvp.sign.create")) {
				if(e.getLine(0).equalsIgnoreCase("[KitPvP]")) {
					if(e.getLine(1).equalsIgnoreCase("heal")) {
						e.setLine(0, ChatColor.DARK_AQUA + "[KitPvP]");
						e.setLine(1, ChatColor.DARK_GREEN + "Click for heal!");
					}
				}
			}
			else e.getPlayer().sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.RED + "You don't have enough permissions!");
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		if(e.getPlayer().hasMetadata("KitpvpLobby") || e.getPlayer().hasMetadata("PlayingKitpvp")) {
			e.setCancelled(true);
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerDeath(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			if (e.getEntity().hasMetadata("KitpvpLobby") || e.getDamager().hasMetadata("KitpvpLobby")) {
				Player killer = (Player) e.getDamager();
				Player killed = (Player) e.getEntity();
				if(killed.getHealth() - e.getDamage() < 1) {
					e.setCancelled(true);
					killer.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.GREEN + killer.getName() + ChatColor.DARK_AQUA + " -> " + ChatColor.RED + killed.getName());
					killed.sendMessage(ChatColor.AQUA + "[KitPvP] -> " + ChatColor.GREEN + killer.getName() + ChatColor.DARK_AQUA + " -> " + ChatColor.RED + killed.getName());
					int killerKills = getConfig().getInt("players." + killer.getName() + ".kills");
					int killedDeaths = getConfig().getInt("players." + killer.getName() + ".deaths");
					getConfig().set("players." + killer.getName() + ".kills", killerKills + 1);
					getConfig().set("players." + killed.getName() + ".deaths", killedDeaths + 1);
					saveConfig();
					updateScoreboard(killer);
					updateScoreboard(killed);
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "addmoney 5 " + killer.getName());
					death(killed);
					killed.getActivePotionEffects().clear();
					killed.setHealth(killed.getMaxHealth());
				}
			}
			else return;
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(getConfig().get("players." + p.getName() + ".kills") == null) {
			int kills = 0;
			getConfig().set("players." + p.getName() + ".kills", kills);
			saveConfig();
		}
		if(getConfig().get("players." + p.getName() + ".deaths") == null) {
			int deaths = 0;
			getConfig().set("players." + p.getName() + ".deaths", deaths);
			saveConfig();
		}
	}
	
	public void onDisable() {
		
	}
}