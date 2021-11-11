package io.github.siminoo.manageinv;

import org.bukkit.plugin.java.JavaPlugin;

public final class ManageInv extends JavaPlugin {
	@Override
	public void onEnable()
	{
		this.getCommand("mnginv").setExecutor(new ManageInvExecutor(this));
	}
	@Override
	public void onDisable()
	{

	}
}

