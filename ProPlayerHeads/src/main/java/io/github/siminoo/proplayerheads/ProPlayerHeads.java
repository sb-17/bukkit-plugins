package io.github.siminoo.proplayerheads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ProPlayerHeads extends JavaPlugin {
	String prefix = ChatColor.AQUA + "[PlayerHeads] -> ";
	
	public void onEnable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pph" )) {
			if(args.length == 0) {
				sender.sendMessage(prefix + ChatColor.RED + "Syntax error!");
			}
			else if(args.length == 1) {
				if(sender.hasPermission("pph.head")) {
					if(sender instanceof Player) {
						Player target = Bukkit.getPlayer(args[0]);
						final Player player = (Player) sender;
						ItemStack playerskull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
						SkullMeta meta = (SkullMeta) playerskull.getItemMeta();
						meta.setOwningPlayer(target);
						meta.setDisplayName(args[0] + "'s head");
						playerskull.setItemMeta(meta);
						player.getInventory().addItem(playerskull);
						player.sendMessage(prefix + ChatColor.GREEN + "Added " + args[0] + "'s head into your inventory!");
					}
				}
			}
			else sender.sendMessage(prefix + ChatColor.RED + "Syntax error!");
		}
		return true;
	}
	
	public void onDisable() {
		
	}
}
