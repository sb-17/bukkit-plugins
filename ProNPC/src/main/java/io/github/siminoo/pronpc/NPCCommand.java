package io.github.siminoo.pronpc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand implements CommandExecutor {

    private ProNPC plugin = ProNPC.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("npc")) {

                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        String npcName = args[1];
                        plugin.npcManager.createNPC(player, npcName);
                    }
                }
            }
        }
        return true;
    }
}