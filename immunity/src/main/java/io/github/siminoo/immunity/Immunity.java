package io.github.siminoo.immunity;

import org.bukkit.plugin.java.JavaPlugin;

public final class Immunity extends JavaPlugin {
	@Override
	public void onEnable()
	{
		this.getCommand("kill").setExecutor(new ImmunityExecutor(this));
	}
	@Override
	public void onDisable()
	{
		
	}
}

