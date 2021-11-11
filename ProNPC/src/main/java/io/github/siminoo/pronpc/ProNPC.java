package io.github.siminoo.pronpc;

import org.bukkit.plugin.java.JavaPlugin;

public class ProNPC extends JavaPlugin {
	private static ProNPC instance;

    public ProNPCManager npcManager;

    public static ProNPC getInstance() {
        return instance;
    }

    private void setInstance(ProNPC instance) {
        this.instance = instance;
    }

    public void onEnable() {
        setInstance(this);
        this.getCommand("npc").setExecutor(new NPCCommand());
        this.npcManager = new ProNPCManager();
    }
}
