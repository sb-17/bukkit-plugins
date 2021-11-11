package io.github.siminoo.bedwars;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Bedwars extends JavaPlugin implements Listener {
	public List<Player> queueSolo;
	public List<Player> queueDuo;
	public List<Player> queueTrio;
	public List<Player> queueSquad;
	public List<String> arenas;
	public int idGB = 0;
	public int idIB = 0;
	public int idDB = 0;
	Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST);
	Inventory blockShop = Bukkit.createInventory(null, InventoryType.CHEST);
	Inventory armourShop = Bukkit.createInventory(null, InventoryType.CHEST);
	Inventory swordShop = Bukkit.createInventory(null, InventoryType.CHEST);
	Inventory bowShop = Bukkit.createInventory(null, InventoryType.CHEST);
	Inventory itemShop = Bukkit.createInventory(null, InventoryType.CHEST);
	ItemStack iron = new ItemStack(Material.IRON_INGOT);
	ItemStack iron2 = new ItemStack(Material.IRON_INGOT, 2);
	ItemStack iron4 = new ItemStack(Material.IRON_INGOT, 4);
	ItemStack iron20 = new ItemStack(Material.IRON_INGOT, 20);
	ItemStack iron64 = new ItemStack(Material.IRON_INGOT, 64);
	ItemStack gold = new ItemStack(Material.GOLD_INGOT);
	ItemStack gold4 = new ItemStack(Material.GOLD_INGOT, 4);
	ItemStack gold20 = new ItemStack(Material.GOLD_INGOT, 20);
	ItemStack dia = new ItemStack(Material.DIAMOND);
	ItemStack dia2 = new ItemStack(Material.DIAMOND, 2);
	ItemStack dia4 = new ItemStack(Material.DIAMOND, 4);
	ItemStack dia5 = new ItemStack(Material.DIAMOND, 5);
	ItemStack dia10 = new ItemStack(Material.DIAMOND, 10);
	ItemStack dia15 = new ItemStack(Material.DIAMOND, 15);
	ItemStack orangeWool = new ItemStack(Material.ORANGE_WOOL, 16);
	ItemStack oakPlanks = new ItemStack(Material.OAK_PLANKS, 16);
	ItemStack endStone = new ItemStack(Material.END_STONE, 16);
	ItemStack chainmailArmourBoots = new ItemStack(Material.CHAINMAIL_BOOTS);
	ItemStack chainmailArmourHelmet = new ItemStack(Material.CHAINMAIL_HELMET);
	ItemStack chainmailArmourChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
	ItemStack chainmailArmourLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
	ItemStack ironArmourBoots = new ItemStack(Material.IRON_BOOTS);
	ItemStack ironArmourHelmet = new ItemStack(Material.IRON_HELMET);
	ItemStack ironArmourChestplate = new ItemStack(Material.IRON_CHESTPLATE);
	ItemStack ironArmourLeggings = new ItemStack(Material.IRON_LEGGINGS);
	ItemStack diaArmourBoots = new ItemStack(Material.DIAMOND_BOOTS);
	ItemStack diaArmourHelmet = new ItemStack(Material.DIAMOND_HELMET);
	ItemStack diaArmourChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
	ItemStack diaArmourLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
	ItemStack bow = new ItemStack(Material.BOW);
	ItemStack arrows = new ItemStack(Material.ARROW, 20);
	ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
	ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
	ItemStack diaSword = new ItemStack(Material.DIAMOND_SWORD);
	ItemStack enderPearl = new ItemStack(Material.ENDER_PEARL, 2);
	ItemStack tnt = new ItemStack(Material.TNT, 2);
	ItemStack flintAndSteel = new ItemStack(Material.FLINT_AND_STEEL);
	ItemStack cookedBeef = new ItemStack(Material.COOKED_BEEF);
	public void onEnable() {
		getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
        inventory.addItem(new ItemStack(Material.IRON_BLOCK));
        inventory.addItem(new ItemStack(Material.BOW));
        inventory.addItem(new ItemStack(Material.WOODEN_SWORD));
        inventory.addItem(new ItemStack(Material.ENDER_EYE));
        inventory.addItem(new ItemStack(Material.LEATHER_HELMET));
        blockShop.addItem(new ItemStack(Material.ORANGE_WOOL));
        blockShop.addItem(new ItemStack(Material.OAK_PLANKS));
        blockShop.addItem(new ItemStack(Material.END_STONE));
        armourShop.addItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        armourShop.addItem(new ItemStack(Material.IRON_CHESTPLATE));
        armourShop.addItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
        bowShop.addItem(new ItemStack(Material.ARROW));
        swordShop.addItem(new ItemStack(Material.STONE_SWORD));
        swordShop.addItem(new ItemStack(Material.IRON_SWORD));
        swordShop.addItem(new ItemStack(Material.DIAMOND_SWORD));
        itemShop.addItem(new ItemStack(Material.ENDER_PEARL));
        itemShop.addItem(new ItemStack(Material.TNT));
        itemShop.addItem(new ItemStack(Material.FLINT_AND_STEEL));
        itemShop.addItem(new ItemStack(Material.COOKED_BEEF));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("bw")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("bw.create")) {
					if (args.length == 2) {
						if (args[0].equalsIgnoreCase("create")) {
							if (sender.hasPermission("bw.create")) {
								Player player = (Player) sender;
								getConfig().set("arenas." + args[1] + ".world", player.getWorld().getName());
								saveConfig();
								player.sendMessage(ChatColor.GREEN + "Successfuly created arena" + args[1] + " !");
								player.setMetadata("CreatingBedWars", new FixedMetadataValue(this, args[3]));
							}
							else {
								sender.sendMessage(ChatColor.RED + "You haven't got enough permissions!");
							}
						}
					}
					else if (args.length == 5) {
						if (args[0].equalsIgnoreCase("spawn")) {
							if (args[1].equalsIgnoreCase("player")) {
								if (args[3].equalsIgnoreCase("1")) {
										if (getConfig().getConfigurationSection("arenas." + args[2]) != null) {
											Player player = (Player) sender;
											getConfig().set("arenas." + args[2] + ".teams." + ".1." + ".PlayerSpawn1." + ".world", player.getWorld().getName());
											getConfig().set("arenas." + args[2] + ".teams." + ".1." + ".PlayerSpawn1." + ".x", player.getLocation().getX());
											getConfig().set("arenas." + args[2] + ".teams." + ".1." + ".PlayerSpawn1." + ".y", player.getLocation().getY());
											getConfig().set("arenas." + args[2] + ".teams." + ".1." + ".PlayerSpawn1." + ".z", player.getLocation().getZ());
											getConfig().set("arenas." + args[2] + ".teams." + ".1." + ".PlayerSpawn1." + ".yaw", player.getLocation().getYaw());
											getConfig().set("arenas." + args[2] + ".teams." + ".1." + ".PlayerSpawn1." + ".pitch", player.getLocation().getPitch());
											saveConfig();
											player.sendMessage(ChatColor.GREEN + "Successfuly added second spawn position!");
										}
										else {
											sender.sendMessage(ChatColor.RED + "That is not a valid arena!");
									}
								}
								else if (args[4].equalsIgnoreCase("2")) {
									if (getConfig().getConfigurationSection("arenas." + args[2]) != null) {
										Player player = (Player) sender;
										getConfig().set("arenas." + args[2] + ".teams." + ".2." + ".PlayerSpawn1." + ".world", player.getWorld().getName());
										getConfig().set("arenas." + args[2] + ".teams." + ".2." + ".PlayerSpawn1." + ".x", player.getLocation().getX());
										getConfig().set("arenas." + args[2] + ".teams." + ".2." + ".PlayerSpawn1." + ".y", player.getLocation().getY());
										getConfig().set("arenas." + args[2] + ".teams." + ".2." + ".PlayerSpawn1." + ".z", player.getLocation().getZ());
										getConfig().set("arenas." + args[2] + ".teams." + ".2." + ".PlayerSpawn1." + ".yaw", player.getLocation().getYaw());
										getConfig().set("arenas." + args[2] + ".teams." + ".2." + ".PlayerSpawn1." + ".pitch", player.getLocation().getPitch());
										saveConfig();
										player.sendMessage(ChatColor.GREEN + "Successfuly added second spawn position!");
										}
										else {
											sender.sendMessage(ChatColor.RED + "That is not a valid arena!");
										}
									}
									else if (args[4].equalsIgnoreCase("3")) {
										if (getConfig().getConfigurationSection("arenas." + args[2]) != null) {
											Player player = (Player) sender;
											getConfig().set("arenas." + args[2] + ".teams." + ".3." + ".PlayerSpawn1." + ".world", player.getWorld().getName());
											getConfig().set("arenas." + args[2] + ".teams." + ".3." + ".PlayerSpawn1." + ".x", player.getLocation().getX());
											getConfig().set("arenas." + args[2] + ".teams." + ".3." + ".PlayerSpawn1." + ".y", player.getLocation().getY());
											getConfig().set("arenas." + args[2] + ".teams." + ".3." + ".PlayerSpawn1." + ".z", player.getLocation().getZ());
											getConfig().set("arenas." + args[2] + ".teams." + ".3." + ".PlayerSpawn1." + ".yaw", player.getLocation().getYaw());
											getConfig().set("arenas." + args[2] + ".teams." + ".3." + ".PlayerSpawn1." + ".pitch", player.getLocation().getPitch());
											saveConfig();
											player.sendMessage(ChatColor.GREEN + "Successfuly added second spawn position!");
										}
										else {
											sender.sendMessage(ChatColor.RED + "That is not a valid arena!");
										}
									}
									else if (args[4].equalsIgnoreCase("2")) {
										if (getConfig().getConfigurationSection("arenas." + args[2]) != null) {
											Player player = (Player) sender;
											getConfig().set("arenas." + args[2] + ".teams." + ".4." + ".PlayerSpawn1." + ".world", player.getWorld().getName());
											getConfig().set("arenas." + args[2] + ".teams." + ".4." + ".PlayerSpawn1." + ".x", player.getLocation().getX());
											getConfig().set("arenas." + args[2] + ".teams." + ".4." + ".PlayerSpawn1." + ".y", player.getLocation().getY());
											getConfig().set("arenas." + args[2] + ".teams." + ".4." + ".PlayerSpawn1." + ".z", player.getLocation().getZ());
											getConfig().set("arenas." + args[2] + ".teams." + ".4." + ".PlayerSpawn1." + ".yaw", player.getLocation().getYaw());
											getConfig().set("arenas." + args[2] + ".teams." + ".4." + ".PlayerSpawn1." + ".pitch", player.getLocation().getPitch());
											saveConfig();
											player.sendMessage(ChatColor.GREEN + "Successfuly added second spawn position!");
										}
										else {
											sender.sendMessage(ChatColor.RED + "That is not a valid arena!");
										}							
										}
										}
									}
									}
									else if (args.length == 1) {
										if (args[0].equalsIgnoreCase("edit")) {
											((Player) sender).setMetadata("EditingBedWars", new FixedMetadataValue(this, args[1]));
										}
										else if (args[0].equalsIgnoreCase("setlobby")) {
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
							                player.sendMessage(ChatColor.GREEN + "Successfuly set " + ChatColor.DARK_RED + "BedWars lobby!");
										}
									}
								}
						}	
					}
					else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("save")) {
							if (((Player) sender).getMetadata("CreatingBedWars") != null) {
								((Player) sender).removeMetadata("CreatingBedWars", this);
							}
						}
					}
		return true;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getPlayer().hasMetadata("CreatingBedWars")) {
			Block bwBlock = e.getBlockPlaced();
			if (bwBlock.equals(Material.RED_BED)) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully placed Bed!");
			}
			else if (bwBlock.equals(Material.DIAMOND_BLOCK)) {
				if (idDB != 1) {
					idDB ++;
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".diamondSpawner." + ".world", e.getBlockPlaced().getWorld().getName());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".diamondSpawner." + ".x", e.getBlockPlaced().getLocation().getX());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".diamondSpawner." + ".y", e.getBlockPlaced().getLocation().getY());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".diamondSpawner." + ".z", e.getBlockPlaced().getLocation().getZ());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".diamondSpawner." + ".yaw", e.getBlockPlaced().getLocation().getYaw());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".diamondSpawner." + ".pitch", e.getBlockPlaced().getLocation().getPitch());
					saveConfig();
					e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully placed diamond spawner!");
				}
			}
			else if (bwBlock.equals(Material.CHEST)) {
				int idShop = 1;
				if (idShop <= 5) {
					e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully placed shop!");
					idShop ++;
				}
			}
			else if (bwBlock.equals(Material.IRON_BLOCK)) {
				if (idIB < 5) {
					idIB ++;
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".ironSpawner." + idIB + ".world", e.getBlockPlaced().getWorld().getName());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".ironSpawner." + idIB + ".x", e.getBlockPlaced().getLocation().getX());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".ironSpawner." + idIB + ".y", e.getBlockPlaced().getLocation().getY());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".ironSpawner." + idIB + ".z", e.getBlockPlaced().getLocation().getZ());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".ironSpawner." + idIB + ".yaw", e.getBlockPlaced().getLocation().getYaw());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".ironSpawner." + idIB + ".pitch", e.getBlockPlaced().getLocation().getPitch());
					saveConfig();
					e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully placed iron spawner!");
				}
			}
			else if (bwBlock.equals(Material.GOLD_BLOCK)) {
				if (idGB < 5) {
					idGB ++;
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".goldSpawner." + idGB + ".world", e.getBlockPlaced().getWorld().getName());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".goldSpawner." + idGB + ".x", e.getBlockPlaced().getLocation().getX());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".goldSpawner." + idGB + ".y", e.getBlockPlaced().getLocation().getY());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".goldSpawner." + idGB + ".z", e.getBlockPlaced().getLocation().getZ());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".goldSpawner." + idGB + ".yaw", e.getBlockPlaced().getLocation().getYaw());
					getConfig().set("arenas." + e.getPlayer().getMetadata("EditingBedWars").get(1) + ".goldSpawner." + idGB + ".pitch", e.getBlockPlaced().getLocation().getPitch());
					saveConfig();
					e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully placed gold spawner!");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Chest) {
				if (e.getPlayer().hasMetadata("PlayingBedWars")) {
					e.getPlayer().openInventory(inventory);
				}
			}
		}
	}
	
	public void InventoryMoveEvent(InventoryMoveItemEvent event) {
		if (((Container) event).getInventory().getType() == InventoryType.CHEST) {
			ItemStack ironBlock = event.getItem();
			if (ironBlock.equals(Material.IRON_BLOCK)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				p.openInventory(blockShop);
			}
			else if (ironBlock.equals(Material.BOW)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				p.openInventory(bowShop);
			}
			else if (ironBlock.equals(Material.WOODEN_SWORD)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				p.openInventory(swordShop);
			}
			else if (ironBlock.equals(Material.ENDER_EYE)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				p.openInventory(itemShop);
			}
			else if (ironBlock.equals(Material.LEATHER_HELMET)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				p.openInventory(armourShop);
			}
			else if (ironBlock.equals(Material.ORANGE_WOOL)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(iron, 4)) {
					p.getInventory().addItem(orangeWool);
					p.getInventory().remove(iron4);
				}
			}
			else if (ironBlock.equals(Material.OAK_PLANKS)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(gold, 4)) {
					p.getInventory().addItem(oakPlanks);
					p.getInventory().remove(gold4);
				}
			}
			else if (ironBlock.equals(Material.END_STONE)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(dia, 4)) {
					p.getInventory().addItem(endStone);
					p.getInventory().remove(dia4);
				}
			}
			else if (ironBlock.equals(Material.CHAINMAIL_CHESTPLATE)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(iron, 40)) {
					p.getInventory().addItem(chainmailArmourHelmet);
					p.getInventory().addItem(chainmailArmourChestplate);
					p.getInventory().addItem(chainmailArmourLeggings);
					p.getInventory().addItem(chainmailArmourBoots);
					p.getInventory().remove(iron64);
				}
			}
			else if (ironBlock.equals(Material.IRON_CHESTPLATE)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(iron, 40)) {
					p.getInventory().addItem(ironArmourHelmet);
					p.getInventory().addItem(ironArmourChestplate);
					p.getInventory().addItem(ironArmourLeggings);
					p.getInventory().addItem(ironArmourBoots);
					p.getInventory().remove(gold20);
				}
			}
			else if (ironBlock.equals(Material.DIAMOND_CHESTPLATE)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(dia, 15)) {
					p.getInventory().addItem(diaArmourHelmet);
					p.getInventory().addItem(diaArmourChestplate);
					p.getInventory().addItem(diaArmourLeggings);
					p.getInventory().addItem(diaArmourBoots);
					p.getInventory().remove(dia15);
				}
			}
			else if (ironBlock.equals(Material.ARROW)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(dia, 2)) {
					p.getInventory().addItem(bow);
					p.getInventory().addItem(arrows);
					p.getInventory().remove(dia2);
				}
			}
			else if (ironBlock.equals(Material.STONE_SWORD)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(iron, 20)) {
					p.getInventory().addItem(stoneSword);
					p.getInventory().remove(iron20);
				}
			}
			else if (ironBlock.equals(Material.IRON_SWORD)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(gold, 20)) {
					p.getInventory().addItem(ironSword);
					p.getInventory().remove(gold20);
				}
			}
			else if (ironBlock.equals(Material.DIAMOND_SWORD)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(dia, 10)) {
					p.getInventory().addItem(diaSword);
					p.getInventory().remove(dia10);
				}
			}
			else if (ironBlock.equals(Material.ENDER_PEARL)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(dia, 5)) {
					p.getInventory().addItem(enderPearl);
					p.getInventory().remove(dia5);
				}
			}
			else if (ironBlock.equals(Material.TNT)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(gold, 20)) {
					p.getInventory().addItem(tnt);
					p.getInventory().remove(gold20);
				}
			}
			else if (ironBlock.equals(Material.FLINT_AND_STEEL)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(dia)) {
					p.getInventory().addItem(flintAndSteel);
					p.getInventory().remove(dia);
				}
			}
			else if (ironBlock.equals(Material.COOKED_BEEF)) {
				Player p = (Player) event.getSource().getHolder();
				event.setCancelled(true);
				if (p.getInventory().contains(iron, 2)) {
					p.getInventory().addItem(cookedBeef);
					p.getInventory().remove(iron2);
				}
			}
		}
	}
	
	public void onPlayerInteract1(PlayerInteractEvent e) throws InterruptedException {
		Action join = e.getAction();
		if (e.getPlayer().hasPermission("bw.join")) {
			if (join == Action.RIGHT_CLICK_BLOCK) {
				Sign sign = (Sign) e.getClickedBlock();
				if (sign.equals(Material.OAK_SIGN)) {
					if (sign.getLine(0).equals(ChatColor.GOLD + "BedWars")) {
						if (sign.getLine(1).equals("Solo")) {
							queueSolo.add(e.getPlayer());
							e.getPlayer().setMetadata("PlayingBedWars", new FixedMetadataValue(this, e.getPlayer()));
							e.getPlayer().sendMessage(ChatColor.GOLD + "Successfully added to Solo queue!");
							if (queueSolo.size() == 4) {
								startGameSolo();
								queueSolo.clear();
							}
						}
						else if (sign.getLine(1).equals("Duo")) {
							queueDuo.add(e.getPlayer());
							e.getPlayer().setMetadata("PlayingBedWars", new FixedMetadataValue(this, e.getPlayer()));
							e.getPlayer().sendMessage(ChatColor.GOLD + "Successfully added to Duo queue!");
							if (queueSolo.size() == 8) {
								startGameDuo();
								queueDuo.clear();
							}
						}
						else if (sign.getLine(1).equals("Trio")) {
							queueTrio.add(e.getPlayer());
							e.getPlayer().setMetadata("PlayingBedWars", new FixedMetadataValue(this, e.getPlayer()));
							e.getPlayer().sendMessage(ChatColor.GOLD + "Successfully added to Trio queue!");
							if (queueSolo.size() == 12) {
								startGameTrio();
								queueTrio.clear();
							}
						}
						else if (sign.getLine(1).equals("Squad")) {
							queueSquad.add(e.getPlayer());
							e.getPlayer().setMetadata("PlayingBedWars", new FixedMetadataValue(this, e.getPlayer()));
							e.getPlayer().sendMessage(ChatColor.GOLD + "Successfully added to Squad queue!");
							if (queueSolo.size() == 16) {
								startGameSquad();
								queueSquad.clear();
							}
						}
					}
				}
			}
		}
	}
	
	public void onSignChange(SignChangeEvent e) {
		if(e.getPlayer().hasPermission("bw.sign.create")) {
			if(e.getLine(0).equalsIgnoreCase("[bw]") || e.getLine(1).equalsIgnoreCase("[Solo]")) {
				e.setLine(0, ChatColor.GOLD + "BedWars");
				e.setLine(1, ChatColor.GREEN + "Solo");
			}
			else if(e.getLine(0).equalsIgnoreCase("[bw]") || e.getLine(1).equalsIgnoreCase("[Duo]")) {
				e.setLine(0, ChatColor.GOLD + "BedWars");
				e.setLine(1, ChatColor.GREEN + "Duo");
			}
			else if(e.getLine(0).equalsIgnoreCase("[bw]") || e.getLine(1).equalsIgnoreCase("[Trio]")) {
				e.setLine(0, ChatColor.GOLD + "BedWars");
				e.setLine(1, ChatColor.GREEN + "Trio");
			}
			else if(e.getLine(0).equalsIgnoreCase("[bw]") || e.getLine(1).equalsIgnoreCase("[Squad]")) {
				e.setLine(0, ChatColor.GOLD + "BedWars");
				e.setLine(1, ChatColor.GREEN + "Squad");
			}
		}
	}
	
	public void startGeneratingDiamond() throws InterruptedException {
		List<String> arenas = getConfig().getStringList("arenas");
		Random rand = new Random();
		int id = rand.nextInt(arenas.size());
		String arena = arenas.get(id);
		long endTime = System.currentTimeMillis() + 1200000;
		while (System.currentTimeMillis() < endTime) {
			World world = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".diamondSpawner." + 1 + ".world"));
			double x = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".x");
			double y = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".y");
			double z = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".z");
			float yaw = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".yaw");
			float pitch = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".pitch");
			Location location = new Location(world, x, y, z, yaw, pitch);
			world.dropItemNaturally(location, new ItemStack(Material.DIAMOND));
			Thread.sleep(10000);
		}
	}
	
	public void startGeneratingIron() throws InterruptedException {
		List<String> arenas = getConfig().getStringList("arenas");
		Random rand = new Random();
		int id = rand.nextInt(arenas.size());
		String arena = arenas.get(id);
		long endTime = System.currentTimeMillis() + 1200000;
		while (System.currentTimeMillis() < endTime) {
			World world = Bukkit.getWorld(getConfig().getString("arenas." + arenas + ".goldSpawner." + 1 + ".world"));
			double x = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 1 + ".x");
			double y = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 1 + ".y");
			double z = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 1 + ".z");
			float yaw = (float) getConfig().getDouble("arenas." + arena + ".goldSpawner." + 1 + ".yaw");
			float pitch = (float) getConfig().getDouble("arenas." + arena + ".goldSpawner." + 1 + ".pitch");
			Location location = new Location(world, x, y, z, yaw, pitch);
			world.dropItemNaturally(location, new ItemStack(Material.IRON_INGOT));
			
			World world1 = Bukkit.getWorld(getConfig().getString("arenas." + arenas + ".goldSpawner." + 2 + ".world"));
			double x1 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 2 + ".x");
			double y1 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 2 + ".y");
			double z1 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 2 + ".z");
			float yaw1 = (float) getConfig().getDouble("arenas." + arena + ".goldSpawner." + 2 + ".yaw");
			float pitch1 = (float) getConfig().getDouble("arenas." + arena + ".goldSpawner." + 2 + ".pitch");
			Location location1 = new Location(world1, x1, y1, z1, yaw1, pitch1);
			world1.dropItemNaturally(location1, new ItemStack(Material.IRON_INGOT));
			
			World world2 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".goldSpawner." + 3 + ".world"));
			double x2 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 3 + ".x");
			double y2 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 3 + ".y");
			double z2 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 3 + ".z");
			float yaw2 = (float) getConfig().getDouble("arenas." + arena + ".goldSpawner." + 3 + ".yaw");
			float pitch2 = (float) getConfig().getDouble("arenas." + arena + ".goldSpawner." + 3 + ".pitch");
			Location location2 = new Location(world2, x2, y2, z2, yaw2, pitch2);
			world2.dropItemNaturally(location2, new ItemStack(Material.IRON_INGOT));
			
			World world3 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".goldSpawner." + 4 + ".world"));
			double x3 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 4 + ".x");
			double y3 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 4 + ".y");
			double z3 = getConfig().getDouble("arenas." + arena + ".goldSpawner." + 4 + ".z");
			float yaw3 = (float) getConfig().getDouble("arenas." + arena + ".goldSpawner." + 4 + ".yaw");
			float pitch3 = (float) getConfig().getDouble("arenas." + arena + ".goldSpawner." + 4 + ".pitch");
			Location location3 = new Location(world3, x3, y3, z3, yaw3, pitch3);
			world3.dropItemNaturally(location3, new ItemStack(Material.IRON_INGOT));
			Thread.sleep(1000);
		}
	}
	
	public void startGeneratingGold() throws InterruptedException {
		List<String> arenas = getConfig().getStringList("arenas");
		Random rand = new Random();
		int id = rand.nextInt(arenas.size());
		String arena = arenas.get(id);
		long endTime = System.currentTimeMillis() + 1200000;
		while (System.currentTimeMillis() < endTime) {
			World world4 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".ironSpawner." + 1 + ".world"));
			double x4 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 1 + ".x");
			double y4 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 1 + ".y");
			double z4 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 1 + ".z");
			float yaw4 = (float) getConfig().getDouble("arenas." + arena + ".ironSpawner." + 1 + ".yaw");
			float pitch4 = (float) getConfig().getDouble("arenas." + arena + ".ironSpawner." + 1 + ".pitch");
			Location location4 = new Location(world4, x4, y4, z4, yaw4, pitch4);
			world4.dropItemNaturally(location4, new ItemStack(Material.GOLD_INGOT));
			
			World world5 = Bukkit.getWorld(getConfig().getString("arenas." + arenas + ".ironSpawner." + 2 + ".world"));
			double x5 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 2 + ".x");
			double y5 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 2 + ".y");
			double z5 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 2 + ".z");
			float yaw5 = (float) getConfig().getDouble("arenas." + arena + ".ironSpawner." + 2 + ".yaw");
			float pitch5 = (float) getConfig().getDouble("arenas." + arena + ".ironSpawner." + 2 + ".pitch");
			Location location5 = new Location(world5, x5, y5, z5, yaw5, pitch5);
			world5.dropItemNaturally(location5, new ItemStack(Material.GOLD_INGOT));
			
			World world6 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".ironSpawner." + 3 + ".world"));
			double x6 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 3 + ".x");
			double y6 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 3 + ".y");
			double z6 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 3 + ".z");
			float yaw6 = (float) getConfig().getDouble("arenas." + arena + ".ironSpawner." + 3 + ".yaw");
			float pitch6 = (float) getConfig().getDouble("arenas." + arena + ".ironSpawner." + 3 + ".pitch");
			Location location6 = new Location(world6, x6, y6, z6, yaw6, pitch6);
			world6.dropItemNaturally(location6, new ItemStack(Material.GOLD_INGOT));
			
			World world7 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".ironSpawner." + 4 + ".world"));
			double x7 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 4 + ".x");
			double y7 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 4 + ".y");
			double z7 = getConfig().getDouble("arenas." + arena + ".ironSpawner." + 4 + ".z");
			float yaw7 = (float) getConfig().getDouble("arenas." + arena + ".ironSpawner." + 4 + ".yaw");
			float pitch7 = (float) getConfig().getDouble("arenas." + arena + ".ironSpawner." + 4 + ".pitch");
			Location location7 = new Location(world7, x7, y7, z7, yaw7, pitch7);
			world7.dropItemNaturally(location7, new ItemStack(Material.GOLD_INGOT));
			Thread.sleep(2500);
		}
	}
	
	public void startGameSolo() throws InterruptedException {
		List<String> arenas = getConfig().getStringList("arenas");
		Random rand = new Random();
		int id = rand.nextInt(arenas.size());
		String arena = arenas.get(id);
		int i = 1;
		if (i == 1) {
			World world = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 1 + ".PlayerSpawn1." + ".world"));
			double x = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".x");
			double y = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".y");
			double z = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".z");
			float yaw = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".yaw");
			float pitch = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".pitch");
			Location location = new Location(world, x, y, z, yaw, pitch);
		    Player p = queueSolo.get(i);
		    p.teleport(location);
		    i ++;
		    if (i == 2) {
		    	World world1 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 2 + ".PlayerSpawn1." + ".world"));
				double x1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".x");
				double y1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".y");
				double z1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".z");
				float yaw1 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".yaw");
				float pitch1 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".pitch");
				Location location1 = new Location(world1, x1, y1, z1, yaw1, pitch1);
			    Player p1 = queueSolo.get(i);
			    p1.teleport(location1);
			    i ++;
			    if (i == 3) {
			    	World world2 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 3 + ".PlayerSpawn1." + ".world"));
					double x2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".x");
					double y2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".y");
					double z2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".z");
					float yaw2 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".yaw");
					float pitch2 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".pitch");
					Location location2 = new Location(world2, x2, y2, z2, yaw2, pitch2);
				    Player p2 = queueSolo.get(i);
				    p2.teleport(location2);
				    i ++;
				    if (i == 4) {
				    	World world3 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 4 + ".PlayerSpawn1." + ".world"));
						double x3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".x");
						double y3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".y");
						double z3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".z");
						float yaw3 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".yaw");
						float pitch3 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".pitch");
						Location location3 = new Location(world3, x3, y3, z3, yaw3, pitch3);
					    Player p3 = queueSolo.get(i);
					    p3.teleport(location3);
					    startGeneratingDiamond();
					    startGeneratingIron();
					    startGeneratingGold();
				    }
			    }
		    }
		}
	}
	
	public void startGameDuo() throws InterruptedException {
		List<String> arenas = getConfig().getStringList("arenas");
		Random rand = new Random();
		int id = rand.nextInt(arenas.size());
		String arena = arenas.get(id);
		int i = 2;
		if (i == 2) {
			World world = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 1 + ".PlayerSpawn1." + ".world"));
			double x = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".x");
			double y = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".y");
			double z = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".z");
			float yaw = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".yaw");
			float pitch = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".pitch");
			Location location = new Location(world, x, y, z, yaw, pitch);
		    Player p = queueDuo.get(i-1);
		    Player p1 = queueDuo.get(i);
		    p.teleport(location);
		    p1.teleport(location);
		    i ++;
		    i ++;
		    if (i == 4) {
		    	World world1 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 2 + ".PlayerSpawn1." + ".world"));
				double x1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".x");
				double y1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".y");
				double z1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".z");
				float yaw1 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".yaw");
				float pitch1 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".pitch");
				Location location1 = new Location(world1, x1, y1, z1, yaw1, pitch1);
				Player p2 = queueDuo.get(i-1);
			    Player p3 = queueDuo.get(i);
			    p2.teleport(location1);
			    p3.teleport(location1);
			    i ++;
			    i ++;
			    if (i == 6) {
			    	World world2 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 3 + ".PlayerSpawn1." + ".world"));
					double x2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".x");
					double y2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".y");
					double z2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".z");
					float yaw2 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".yaw");
					float pitch2 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".pitch");
					Location location2 = new Location(world2, x2, y2, z2, yaw2, pitch2);
					Player p4 = queueDuo.get(i-1);
				    Player p5 = queueDuo.get(i);
				    p4.teleport(location2);
				    p5.teleport(location2);
				    i ++;
				    i ++;
				    if (i == 8) {
				    	World world3 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 4 + ".PlayerSpawn1." + ".world"));
						double x3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".x");
						double y3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".y");
						double z3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".z");
						float yaw3 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".yaw");
						float pitch3 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".pitch");
						Location location3 = new Location(world3, x3, y3, z3, yaw3, pitch3);
						Player p6 = queueDuo.get(i-1);
					    Player p7 = queueDuo.get(i);
					    p6.teleport(location3);
					    p7.teleport(location3);
					    startGeneratingDiamond();
					    startGeneratingIron();
					    startGeneratingGold();
				    }
			    }
		    }
		}
	}
	
	public void startGameTrio() throws InterruptedException {
		List<String> arenas = getConfig().getStringList("arenas");
		Random rand = new Random();
		int id = rand.nextInt(arenas.size());
		String arena = arenas.get(id);
		int i = 3;
		if (i == 3) {
			World world = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 1 + ".PlayerSpawn1." + ".world"));
			double x = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".x");
			double y = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".y");
			double z = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".z");
			float yaw = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".yaw");
			float pitch = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".pitch");
			Location location = new Location(world, x, y, z, yaw, pitch);
			Player p8 = queueDuo.get(i-2);
			Player p = queueDuo.get(i-1);
		    Player p1 = queueDuo.get(i);
		    p.teleport(location);
		    p1.teleport(location);
		    p8.teleport(location);
		    i ++;
		    i ++;
		    i ++;
		    if (i == 6) {
		    	World world1 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 2 + ".PlayerSpawn1." + ".world"));
				double x1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".x");
				double y1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".y");
				double z1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".z");
				float yaw1 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".yaw");
				float pitch1 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".pitch");
				Location location1 = new Location(world1, x1, y1, z1, yaw1, pitch1);
				Player p9 = queueDuo.get(i-2);
				Player p2 = queueDuo.get(i-1);
			    Player p3 = queueDuo.get(i);
			    p2.teleport(location1);
			    p3.teleport(location1);
			    p9.teleport(location1);
			    i ++;
			    i ++;
			    i ++;
			    if (i == 9) {
			    	World world2 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 3 + ".PlayerSpawn1." + ".world"));
					double x2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".x");
					double y2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".y");
					double z2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".z");
					float yaw2 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".yaw");
					float pitch2 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".pitch");
					Location location2 = new Location(world2, x2, y2, z2, yaw2, pitch2);
					Player p10 = queueDuo.get(i-2);
					Player p4 = queueDuo.get(i-1);
				    Player p5 = queueDuo.get(i);
				    p4.teleport(location2);
				    p5.teleport(location2);
				    p10.teleport(location2);
				    i ++;
				    i ++;
				    i ++;
				    if (i == 12) {
				    	World world3 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 4 + ".PlayerSpawn1." + ".world"));
						double x3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".x");
						double y3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".y");
						double z3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".z");
						float yaw3 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".yaw");
						float pitch3 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".pitch");
						Location location3 = new Location(world3, x3, y3, z3, yaw3, pitch3);
						Player p11 = queueDuo.get(i-2);
						Player p6 = queueDuo.get(i-1);
					    Player p7 = queueDuo.get(i);
					    p6.teleport(location3);
					    p7.teleport(location3);
					    p11.teleport(location3);
					    startGeneratingDiamond();
					    startGeneratingIron();
					    startGeneratingGold();
				    }
			    }
		    }
		}
	}
	
	public void startGameSquad() throws InterruptedException {
		List<String> arenas = getConfig().getStringList("arenas");
		Random rand = new Random();
		int id = rand.nextInt(arenas.size());
		String arena = arenas.get(id);
		int i = 4;
		if (i == 4) {
			World world = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 1 + ".PlayerSpawn1." + ".world"));
			double x = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".x");
			double y = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".y");
			double z = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".z");
			float yaw = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".yaw");
			float pitch = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 1 + ".PlayerSpawn1." + ".pitch");
			Location location = new Location(world, x, y, z, yaw, pitch);
			Player p12 = queueDuo.get(i-3);
			Player p8 = queueDuo.get(i-2);
			Player p = queueDuo.get(i-1);
		    Player p1 = queueDuo.get(i);
		    p.teleport(location);
		    p1.teleport(location);
		    p8.teleport(location);
		    p12.teleport(location);
		    i ++;
		    i ++;
		    i ++;
		    i ++;
		    if (i == 8) {
		    	World world1 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 2 + ".PlayerSpawn1." + ".world"));
				double x1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".x");
				double y1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".y");
				double z1 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".z");
				float yaw1 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".yaw");
				float pitch1 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 2 + ".PlayerSpawn1." + ".pitch");
				Location location1 = new Location(world1, x1, y1, z1, yaw1, pitch1);
				Player p13 = queueDuo.get(i-3);
				Player p9 = queueDuo.get(i-2);
				Player p2 = queueDuo.get(i-1);
			    Player p3 = queueDuo.get(i);
			    p2.teleport(location1);
			    p3.teleport(location1);
			    p9.teleport(location1);
			    p13.teleport(location1);
			    i ++;
			    i ++;
			    i ++;
			    i ++;
			    if (i == 12) {
			    	World world2 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 3 + ".PlayerSpawn1." + ".world"));
					double x2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".x");
					double y2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".y");
					double z2 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".z");
					float yaw2 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".yaw");
					float pitch2 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 3 + ".PlayerSpawn1." + ".pitch");
					Location location2 = new Location(world2, x2, y2, z2, yaw2, pitch2);
					Player p14 = queueDuo.get(i-3);
					Player p10 = queueDuo.get(i-2);
					Player p4 = queueDuo.get(i-1);
				    Player p5 = queueDuo.get(i);
				    p4.teleport(location2);
				    p5.teleport(location2);
				    p10.teleport(location2);
				    p14.teleport(location2);
				    i ++;
				    i ++;
				    i ++;
				    i ++;
				    if (i == 16) {
				    	World world3 = Bukkit.getWorld(getConfig().getString("arenas." + arena + ".teams." + 4 + ".PlayerSpawn1." + ".world"));
						double x3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".x");
						double y3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".y");
						double z3 = getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".z");
						float yaw3 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".yaw");
						float pitch3 = (float) getConfig().getDouble("arenas." + arena + ".diamondSpawner." + 4 + ".PlayerSpawn1." + ".pitch");
						Location location3 = new Location(world3, x3, y3, z3, yaw3, pitch3);
						Player p15 = queueDuo.get(i-3);
						Player p11 = queueDuo.get(i-2);
						Player p6 = queueDuo.get(i-1);
					    Player p7 = queueDuo.get(i);
					    p6.teleport(location3);
					    p7.teleport(location3);
					    p11.teleport(location3);
					    p15.teleport(location3);
					    startGeneratingDiamond();
					    startGeneratingIron();
					    startGeneratingGold();
				    }
			    }
		    }
		}
	}
	
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.getEntity().removeMetadata("PlayingBedWars", this);
		World world = Bukkit.getWorld(getConfig().getString("lobby." + ".world"));
		double x = getConfig().getDouble("lobby." + ".x");
		double y = getConfig().getDouble("lobby." + ".y");
		double z = getConfig().getDouble("lobby." + ".z");
		float yaw = (float) getConfig().getDouble("lobby." + ".yaw");
		float pitch = (float) getConfig().getDouble("lobby." + ".pitch");
		Location location = new Location(world, x, y, z, yaw, pitch);
		Player p = event.getEntity();
		if (getConfig().getConfigurationSection("lobby." + ".world") != null) {
			p.teleport(location);
		}
	}
	
	public void onDisable() {
		
	}
}
