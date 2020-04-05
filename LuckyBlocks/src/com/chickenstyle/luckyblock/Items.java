package com.chickenstyle.luckyblock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;



public class Items {
	
	private static File file;
	private static YamlConfiguration config;
    public Items(Main main) {
  	  file = new File(main.getDataFolder(), "Items.yml");
  	 if (!file.exists()) {
  		 try {
				 file.createNewFile();
		    	 config = YamlConfiguration.loadConfiguration(file);
		    	 
		    	 ItemStack dia = new ItemStack(Material.DIAMOND, 8);
		    	 ItemStack iron = new ItemStack (Material.IRON_INGOT, 32);
		    	 ItemStack lapis = new ItemStack (Material.LAPIS_BLOCK, 64);
		    	 
		    	 Inventory open = Bukkit.createInventory(null, 54, ChatColor.GRAY + "" +ChatColor.BOLD + "Prizes!");
		    	 open.addItem(dia);
		    	 open.addItem(iron);
		    	 open.addItem(lapis);
		    	 ItemStack[] items = open.getContents();
		    	  	config.set("items", items);
		    	  	try {
		    				config.save(file);
		    		    	config = YamlConfiguration.loadConfiguration(file);
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    			}
			} catch (IOException e) {
				e.printStackTrace();
			}
  		 
  	 }

   }
    static public void setContent(ItemStack[] itemStacks) {
   	config = YamlConfiguration.loadConfiguration(file);
  	config.set("items", itemStacks);
  	try {
			config.save(file);
	    	config = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
  

	@SuppressWarnings("unchecked")
	static public ArrayList<ItemStack> getContent() {
   	config = YamlConfiguration.loadConfiguration(file);
	ArrayList<ItemStack> items = (ArrayList<ItemStack>) config.getList("items");
  	return items;
  }
	
	static public void configReload() {
	 Main.getPlugin(Main.class).saveConfig();	
	 Main.getPlugin(Main.class).reloadConfig();
   	 config = YamlConfiguration.loadConfiguration(file);
		try {
			config.save(file);
			config = YamlConfiguration.loadConfiguration(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
