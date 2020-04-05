package com.chickenstyle.luckyblock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		getCommand("luckyblock").setExecutor(new LuckyBlockCommand());
	    this.getConfig().options().copyDefaults();
	    saveDefaultConfig();
		new Items(this);
	}
}
