package io.github.siminoo.lobby;

import java.sql.Date;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public final class Lobby extends JavaPlugin implements Listener {
	public Inventory lobbyMenu = Bukkit.createInventory(null, 27, "Lobby Menu");
	ItemStack selector = new ItemStack(Material.BLAZE_ROD);
	ItemStack skywars = new ItemStack(Material.EYE_OF_ENDER);
	ItemStack kitpvp = new ItemStack(Material.IRON_SWORD);
	ItemStack skyblock = new ItemStack(Material.DIRT);
	ItemStack uhcrun = new ItemStack(Material.BOOK);
	ItemStack duels = new ItemStack(Material.DIAMOND_HELMET);
	ItemStack kitSel = new ItemStack(Material.WOOD_SWORD);
	ItemStack leaveKP = new ItemStack(Material.BED);
	ItemStack vip = new ItemStack(Material.EMERALD);
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();

        this.getCommand("v").setExecutor(new Vanish(this));
        this.getCommand("vanish").setExecutor(new Vanish(this));
        this.getCommand("wg").setExecutor(new WorldGen(this));
        this.getCommand("fly").setExecutor(new Fly(this));
        
        ItemMeta skywarsMeta = skywars.getItemMeta();
        ItemMeta kitpvpMeta = kitpvp.getItemMeta();
        ItemMeta skyblockMeta = skyblock.getItemMeta();
        ItemMeta uhcrunMeta = uhcrun.getItemMeta();
        ItemMeta duelsMeta = duels.getItemMeta();
        
        skywarsMeta.setDisplayName(ChatColor.AQUA + "SkyWars");
        kitpvpMeta.setDisplayName(ChatColor.RED + "KitPvP");
        skyblockMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "SkyBlock");
        uhcrunMeta.setDisplayName(ChatColor.GOLD + "UHCRun");
        duelsMeta.setDisplayName(ChatColor.DARK_GREEN + "Duels");
        
        skywars.setItemMeta(skywarsMeta);
        kitpvp.setItemMeta(kitpvpMeta);
        skyblock.setItemMeta(skyblockMeta);
        uhcrun.setItemMeta(uhcrunMeta);
        duels.setItemMeta(duelsMeta);
        
        ItemMeta menuSelMeta = selector.getItemMeta();
        menuSelMeta.setDisplayName("Lobby Menu");
        selector.setItemMeta(menuSelMeta);
        
        ItemMeta kitMeta = kitSel.getItemMeta();
    	kitMeta.setDisplayName(ChatColor.AQUA + "Kit Selector");
    	kitSel.setItemMeta(kitMeta);
    	
    	ItemMeta leaveKPMeta = leaveKP.getItemMeta();
    	leaveKPMeta.setDisplayName(ChatColor.RED + "Leave");
    	leaveKP.setItemMeta(leaveKPMeta);
    	
    	ItemMeta vipMeta = vip.getItemMeta();
    	vipMeta.setDisplayName("VIP");
    	vip.setItemMeta(vipMeta);
        
        lobbyMenu.setItem(0, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(1, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(2, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(3, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(4, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(5, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(6, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(7, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(8, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(9, skywars);
        lobbyMenu.setItem(10, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(11, kitpvp);
        lobbyMenu.setItem(12, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(13, duels);
        lobbyMenu.setItem(14, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(15, uhcrun);
        lobbyMenu.setItem(16, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(17, skyblock);
        lobbyMenu.setItem(18, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(19, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(20, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(21, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(22, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(23, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(24, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(25, new ItemStack(Material.STAINED_GLASS));
        lobbyMenu.setItem(26, new ItemStack(Material.STAINED_GLASS));
        
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setlobby")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("lobby.set")) {
					Player player = (Player) sender;
					getConfig().set("lobby." + ".world", null);
					getConfig().set("lobby." + ".x", null);
					getConfig().set("lobby." + ".y", null);
					getConfig().set("lobby." + ".z", null);
					getConfig().set("lobby." + ".yaw", null);
					getConfig().set("lobby." + ".pitch", null);
					getConfig().set("lobby." + ".world", player.getWorld().getName());
	                getConfig().set("lobby." + ".x", player.getLocation().getX());
	                getConfig().set("lobby." + ".y", player.getLocation().getY());
	                getConfig().set("lobby." + ".z", player.getLocation().getZ());
	                getConfig().set("lobby." + ".yaw", player.getLocation().getYaw());
	                getConfig().set("lobby." + ".pitch", player.getLocation().getPitch());
	                saveConfig();
	                player.sendMessage(ChatColor.AQUA + "[Lobby] -> " + ChatColor.GREEN + "Successfuly set " + ChatColor.DARK_RED + "lobby!");
				}
				else {
					sender.sendMessage(ChatColor.AQUA + "[Lobby] -> " + ChatColor.RED + "You haventn't got enough permissions!");
				}
			}
			else {
				sender.sendMessage(ChatColor.AQUA + "[Lobby] -> " + ChatColor.RED + "You are not a player!");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("spawn") || cmd.getName().equalsIgnoreCase("lobby") || cmd.getName().equalsIgnoreCase("hub")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("lobby.teleport")) {
					if (getConfig().getConfigurationSection("lobby.") != null) {
						Player player = (Player) sender;
						World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
						double x = getConfig().getDouble("lobby." + ".x");
						double y = getConfig().getDouble("lobby." + ".y");
						double z = getConfig().getDouble("lobby." + ".z");
						float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
						float pitch = (float) getConfig().getDouble("lobby." + ".pitch");

						player.teleport(new Location(world, x, y, z, yaw, pitch));

						player.sendMessage(ChatColor.AQUA + "[Lobby] -> " + ChatColor.GREEN+"You have successfully teleported to the Lobby!");
						
						player.getInventory().clear();
						player.getInventory().setItem(0, selector);
						player.getInventory().setItem(4, vip);
						player.getActivePotionEffects().clear();
						
						createScoreboard(player);
						
						player.setMetadata("Lobby", new FixedMetadataValue(this, this));
					}
					else {
						sender.sendMessage(ChatColor.AQUA + "[Lobby] -> " + ChatColor.RED + "Lobby is not set!");
					}
				}
				else {
					sender.sendMessage(ChatColor.AQUA + "[Lobby] -> " + ChatColor.RED + "You haventn't got enough permissions!");
				}
			}
			else {
				sender.sendMessage(ChatColor.AQUA + "[Lobby] -> " + ChatColor.RED + "You are not a player!");
			}
		}
		else if (cmd.getName().equalsIgnoreCase("money")) {
			if(args.length == 0) {
				int money = getConfig().getInt(sender.getName() + ".money");
				sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.GREEN + "You have " + ChatColor.GOLD + money + ChatColor.GREEN + " money now!");
			}
			else if(args.length == 1) {
				if(sender.hasPermission("money.others")) {
					Player target = Bukkit.getPlayer(args[0]);
					int money = getConfig().getInt(target.getName() + ".money");
					sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.GREEN + target.getName() + " has " + ChatColor.GOLD + money + ChatColor.GREEN + " money now!");
				}
				else sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.RED + "You don't have enough permissions!");
			}
			else sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.RED + "Syntax error!");
		}
		else if(cmd.getName().equalsIgnoreCase("sendmoney")) {
			if(args.length == 2) {
				Player target = Bukkit.getPlayer(args[1]);
				int amount = Integer.parseInt(args[0]);
				if(target == null) {
					sender.sendMessage(ChatColor.AQUA + "[Money] ->" + ChatColor.RED + args[1] + " is not online!");
				}
				else {
					int senderMoney = getConfig().getInt(sender.getName() + ".money");
					int targetMoney = getConfig().getInt(target.getName() + ".money");
					if(senderMoney - amount > -1) {
						getConfig().set(target.getName() + ".money", targetMoney + amount);
						getConfig().set(sender.getName() + ".money", senderMoney - amount);
						saveConfig();
						sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.GREEN + "Sent " + ChatColor.GOLD + amount + ChatColor.GREEN + " money to " + target.getName());
						target.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.GREEN + "Recieved " + ChatColor.GOLD + amount + ChatColor.GREEN + " money from " + target.getName());
						
						updateScoreboard(target);
						Player player = (Player) sender;
						updateScoreboard(player);
					}
					else {
						sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.RED + "You don't have enough money! /money for see your balance");
					}
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("addmoney")) {
			if(args.length == 2) {
				Player target = Bukkit.getPlayer(args[1]);
				int amount = Integer.parseInt(args[0]);
				if(target == null) {
					sender.sendMessage(ChatColor.AQUA + "[Money] ->" + ChatColor.RED + args[1] + " is not online!");
				}
				else {
					int targetMoney = getConfig().getInt(target.getName() + ".money");
					if(sender.hasPermission("money.add")) {
						getConfig().set(target.getName() + ".money", targetMoney + amount);
						saveConfig();
						sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.GREEN + "Sent " + ChatColor.GOLD + amount + ChatColor.GREEN + " money to " + target.getName());
						target.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.GREEN + "Recieved " + ChatColor.GOLD + amount + ChatColor.GREEN + " money from " + target.getName());
						
						updateScoreboard(target);
					}
					else sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.RED + "You don't have enough permissions!");
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("removemoney")) {
			if(args.length == 2) {
				Player target = Bukkit.getPlayer(args[1]);
				int amount = Integer.parseInt(args[0]);
				if(target == null) {
					sender.sendMessage(ChatColor.AQUA + "[Money] ->" + ChatColor.RED + args[1] + " is not online!");
				}
				else {
					int targetMoney = getConfig().getInt(target.getName() + ".money");
					if(sender.hasPermission("money.add")) {
						if(getConfig().get(target.getName()) == null) {
							sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.RED + target.getName() + " doesn't have enough money!");
						}
						else {
							if(targetMoney - amount > -1) {
								getConfig().set(target.getName() + ".money", targetMoney - amount);
								saveConfig();
								sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.GREEN + "Removed " + ChatColor.GOLD + amount + ChatColor.GREEN + " money from " + target.getName());
								target.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.GREEN + "Removed " + ChatColor.GOLD + amount + ChatColor.GREEN + " money by " + target.getName());
								
								updateScoreboard(target);
							}
							else sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.RED + target.getName() + " doesn't have enough money!");
						}
					}
					else sender.sendMessage(ChatColor.AQUA + "[Money] -> " + ChatColor.RED + "You don't have enough permissions!");
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("kick")) {
			if(sender.hasPermission("kick")) {
				if(args.length < 2) {
					sender.sendMessage(ChatColor.AQUA + "[Kick] -> " + ChatColor.RED + "Please specify player and reason!");
				}
				else {
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) {
						sender.sendMessage(ChatColor.AQUA + "[Kick] -> " + ChatColor.RED + target.getName() + " is not online!");
					}
					else {
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String allArgs = sb.toString().trim();
						target.kickPlayer(allArgs);
						sender.sendMessage(ChatColor.AQUA + "[Kick] -> " + ChatColor.GREEN + "Successfully kicked " + target.getName());
					}
				}
			}
			else sender.sendMessage(ChatColor.AQUA + "[Kick] -> " + ChatColor.RED + "You don't have enough permissions!");
		}
		else if(cmd.getName().equalsIgnoreCase("permaban")) {
			if(sender.hasPermission("permaban")) {
				if(args.length < 2) {
					sender.sendMessage(ChatColor.AQUA + "[PermaBan] -> " + ChatColor.RED + "Please specify player and reason!");
				}
				else {
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) {
						sender.sendMessage(ChatColor.AQUA + "[PermaBan] -> " + ChatColor.RED + target.getName() + " is not online!");
					}
					else {
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String allArgs = sb.toString().trim();
						BanList banList = Bukkit.getBanList(BanList.Type.NAME);
						banList.addBan(target.getName(), allArgs, null, sender.getName());
						target.kickPlayer(allArgs);
						sender.sendMessage(ChatColor.AQUA + "[PermaBan] -> " + ChatColor.GREEN + "Successfully banned " + target.getName());
					}
				}
			}
			else sender.sendMessage(ChatColor.AQUA + "[PermaBan] -> " + ChatColor.RED + "You don't have enough permissions!");
		}
		else if(cmd.getName().equalsIgnoreCase("ban")) {
			if(sender.hasPermission("ban")) {
				if(args.length < 3) {
					sender.sendMessage(ChatColor.AQUA + "[Ban] -> " + ChatColor.RED + "Please specify player and reason!");
				}
				else {
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) {
						sender.sendMessage(ChatColor.AQUA + "[Ban] -> " + ChatColor.RED + target.getName() + " is not online!");
					}
					else {
						StringBuilder sb = new StringBuilder();
						for (int i = 2; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String allArgs = sb.toString().trim();
						int hours = Integer.parseInt(args[1]);
						if(hours > 0) {
							BanList banList = Bukkit.getBanList(BanList.Type.NAME);
							Date date = new Date(System.currentTimeMillis()+hours*60*1000);
							banList.addBan(target.getName(), allArgs, date, sender.getName());
							target.kickPlayer(ChatColor.RED + "Reason: " + ChatColor.WHITE + allArgs + ChatColor.RED + " Banned for: " + ChatColor.WHITE + hours + "hours");
							sender.sendMessage(ChatColor.AQUA + "[Ban] -> " + ChatColor.GREEN + "Successfully banned " + target.getName());
						}
						else sender.sendMessage(ChatColor.AQUA + "[Ban] -> " + ChatColor.RED + "Can't ban player for less than 1 hour!");
					}
				}
			}
			else sender.sendMessage(ChatColor.AQUA + "[Ban] -> " + ChatColor.RED + "You don't have enough permissions!");
		}
		else if(cmd.getName().equalsIgnoreCase("myworld")) {
			if(sender.hasPermission("myworld")) {
				Player p = (Player) sender;
				sender.sendMessage(ChatColor.AQUA + "[MyWorld] -> " + ChatColor.GREEN + "You are in " + ChatColor.GOLD + p.getWorld().getName() + ChatColor.GREEN + "!");
			}
		}
		else if(cmd.getName().equalsIgnoreCase("freeze")) {
			if(sender.hasPermission("freeze")) {
				if(args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) {
						sender.sendMessage(ChatColor.AQUA + "[Freeze] -> " + ChatColor.RED + target.getName() + " is not online!");
					}
					else {
						target.setMetadata("Freezed", new FixedMetadataValue(this, this));
						sender.sendMessage(ChatColor.AQUA + "[Freeze] -> " + ChatColor.GREEN + "Freezed " + target.getName() + "!");
					}
				}
				else sender.sendMessage(ChatColor.AQUA + "[Freeze] -> " + ChatColor.RED + "Syntax error!");
			}
			else sender.sendMessage(ChatColor.AQUA + "[Freeze] -> " + ChatColor.RED + "You don't have enough permissions!");
		}
		else if(cmd.getName().equalsIgnoreCase("unfreeze")) {
			if(sender.hasPermission("freeze")) {
				if(args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) {
						sender.sendMessage(ChatColor.AQUA + "[Freeze] -> " + ChatColor.RED + target.getName() + " is not online!");
					}
					else {
						target.removeMetadata("Freezed", this);
						sender.sendMessage(ChatColor.AQUA + "[Freeze] -> " + ChatColor.GREEN + "Unfreezed " + target.getName() + "!");
					}
				}
				else sender.sendMessage(ChatColor.AQUA + "[Freeze] -> " + ChatColor.RED + "Syntax error!");
			}
			else sender.sendMessage(ChatColor.AQUA + "[Freeze] -> " + ChatColor.RED + "You don't have enough permissions!");
		}
		return true;
	}

    @SuppressWarnings("deprecation")
	public void createScoreboard(Player player) {
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard lobbyBoard = m.getNewScoreboard();
		Objective server = lobbyBoard.registerNewObjective(ChatColor.AQUA + "ScriptCraft", "");
		server.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score money = server.getScore(ChatColor.GOLD + "Money:" + ChatColor.RED);
		money.setScore(getConfig().getInt(player.getName() + ".money"));
		
		Team admin = lobbyBoard.registerNewTeam("Admin");
        Team owner = lobbyBoard.registerNewTeam("Owner");
        Team builder = lobbyBoard.registerNewTeam("Builder");
        Team helper = lobbyBoard.registerNewTeam("helper");
        Team youtube = lobbyBoard.registerNewTeam("youtube");
        Team playerOnly = lobbyBoard.registerNewTeam("player");
        
        admin.setPrefix(ChatColor.LIGHT_PURPLE + "[Admin] " + ChatColor.WHITE);
        owner.setPrefix(ChatColor.AQUA + "[Owner] " + ChatColor.WHITE);
        builder.setPrefix(ChatColor.YELLOW + "[Builder] " + ChatColor.WHITE);
        helper.setPrefix(ChatColor.GREEN + "[Helper] " + ChatColor.WHITE);
        helper.setPrefix(ChatColor.RED + "[YouTube] " + ChatColor.WHITE);
        playerOnly.setPrefix(ChatColor.GRAY + "[Player] " + ChatColor.WHITE);
        
        if(player.hasPermission("nametag.owner")) {
        	owner.addPlayer(player);
        	player.setDisplayName(ChatColor.AQUA + "[Owner] " + ChatColor.WHITE + player.getName());
        }
        else if(player.hasPermission("nametag.admin")) {
        	admin.addPlayer(player);
        	player.setDisplayName(ChatColor.LIGHT_PURPLE + "[Admin] " + ChatColor.WHITE + player.getName());
        }
        else if(player.hasPermission("nametag.builder")) {
        	builder.addPlayer(player);
        	player.setDisplayName(ChatColor.YELLOW + "[Builder] " + ChatColor.WHITE + player.getName());
        }
        else if(player.hasPermission("nametag.helper")) {
        	helper.addPlayer(player);
        	player.setDisplayName(ChatColor.GREEN + "[Helper] " + ChatColor.WHITE + player.getName());
        }
        else if(player.hasPermission("nametag.youtube")) {
        	youtube.addPlayer(player);
        	player.setDisplayName(ChatColor.RED + "[YouTube] " + ChatColor.WHITE + player.getName());
        }
        else {
        	playerOnly.addPlayer(player);
        	player.setDisplayName(ChatColor.GRAY + "[Player] " + ChatColor.WHITE + player.getName());
        }
		
		player.setScoreboard(lobbyBoard);
	}
	
	public void updateScoreboard(Player player) {
		Score money = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.GOLD + "Money:" + ChatColor.RED);
		money.setScore(getConfig().getInt(player.getName() + ".money"));
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getEntity().getWorld().getName().equalsIgnoreCase("duelArena") || e.getEntity().getWorld().getName().equalsIgnoreCase("duels") || e.getEntity().getWorld().getName().equalsIgnoreCase("kitpvp") || e.getEntity().getWorld().getName().equalsIgnoreCase("lobby") || e.getEntity().getWorld().getName().equalsIgnoreCase("uhcrunlobby") || e.getEntity().getWorld().getName().contains("classicarena")) {
				if (e.getFoodLevel() < 20) {
					e.setFoodLevel(20);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(e.getPlayer().hasMetadata("Freezed")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		if (getConfig().getConfigurationSection("lobby.") != null) {
			Player player = e.getPlayer();
			World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
			double x = getConfig().getDouble("lobby." + ".x");
			double y = getConfig().getDouble("lobby." + ".y");
			double z = getConfig().getDouble("lobby." + ".z");
			float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
			float pitch = (float) getConfig().getDouble("lobby." + ".pitch");

			player.teleport(new Location(world, x, y, z, yaw, pitch));
			
			if(getConfig().get(e.getPlayer().getName() + ".money") == null) {
				getConfig().set(e.getPlayer().getName() + ".money", 10);
				saveConfig();
			}
			player.getInventory().clear();
			player.getInventory().setItem(0, selector);
			player.getInventory().setItem(4, vip);
			
			createScoreboard(player);
			
			player.setMetadata("Lobby", new FixedMetadataValue(this, this));
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if(p.getWorld().getName().equals(getConfig().getString("lobby." + ".world"))) {
			if(item == null) return;
			if(item.getType() == (Material.BLAZE_ROD)) {
				p.openInventory(lobbyMenu);
			}
			else if(item.getType() == (Material.EMERALD)) {
				p.sendMessage(ChatColor.AQUA + "You can buy VIP at www.scriptcraft.tk");
			}
			else {
				return;
			}
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		ItemStack item = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		Inventory i = e.getInventory();
		if(i.getName().equals(lobbyMenu.getName())) {
			if(item.getType() == Material.EYE_OF_ENDER) {
				e.setCancelled(true);
				p.removeMetadata("Lobby", this);
				Bukkit.dispatchCommand(p, "warp skywars");
				p.getInventory().clear();
			}
			else if(item.getType() == Material.IRON_SWORD) {
				e.setCancelled(true);
				p.removeMetadata("Lobby", this);
				Bukkit.dispatchCommand(p, "kp join");
				p.getInventory().clear();
				p.getInventory().setItem(0, kitSel);
				p.getInventory().setItem(8, leaveKP);
				p.getActivePotionEffects().clear();
			}
			else if(item.getType() == Material.BOOK) {
				e.setCancelled(true);
				p.removeMetadata("Lobby", this);
				Bukkit.dispatchCommand(p, "uhcrun lobby");
				p.getInventory().clear();
			}
			else if(item.getType() == Material.DIRT) {
				e.setCancelled(true);
				p.removeMetadata("Lobby", this);
				Bukkit.dispatchCommand(p, "sb tp");
				p.getInventory().clear();
			}
			else if(item.getType() == Material.DIAMOND_HELMET) {
				e.setCancelled(true);
				p.removeMetadata("Lobby", this);
				Bukkit.dispatchCommand(p, "warp duels");
				p.getInventory().clear();
				p.getInventory().setItem(8, leaveKP);
				p.setMetadata("DuelsLobby", new FixedMetadataValue(this, this));
			}
			else return;
			e.setCancelled(true);
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Location to = e.getTo();
		if(to.getWorld().getName() == getConfig().getString("lobby." + ".world")) {
			e.getPlayer().getInventory().clear();
			e.getPlayer().getInventory().setItem(0, selector);
			e.getPlayer().getInventory().setItem(4, vip);
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if(e.getPlayer().getWorld().getName() == getConfig().getString("lobby." + ".world")) {
			e.getPlayer().getInventory().setItem(0, selector);
			e.getPlayer().getInventory().setItem(4, vip);
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		
		if(p.hasMetadata("Lobby")) {
			e.setCancelled(true);
		}
		else return;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
			
	public void onDisable() {
		
	}
}