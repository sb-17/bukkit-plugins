package io.github.siminoo.manageinv;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import io.github.siminoo.manageinv.ManageInv;



public class ManageInvExecutor implements CommandExecutor{
	private ManageInv manageinv;
	public ManageInvExecutor(ManageInv manageinv)
	{
		this.manageinv = manageinv;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mnginv")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					if(sender.hasPermission("invmng.use")) {
						Player target = (Player) sender;
						target.sendMessage(ChatColor.GREEN + "You can see someone's inventory!");
					} 
					else {
						sender.sendMessage(ChatColor.RED + "You haven't got permission!");
						return true;
					}
				}
				
			}
			else if(args.length == 1) {
				if(sender.hasPermission("invmng.use")) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						if(sender instanceof Player) {
							Inventory targetInv = target.getInventory();
							((Player) sender).openInventory(targetInv);
						}
					}
					else {
						sender.sendMessage("Player is not online!");
					}
				}
			}
			else {
				sender.sendMessage("Too many arguments!");
			}
			}
		return true;
	}
}

