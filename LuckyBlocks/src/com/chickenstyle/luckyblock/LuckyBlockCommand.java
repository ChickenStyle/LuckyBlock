package com.chickenstyle.luckyblock;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class LuckyBlockCommand implements CommandExecutor {
	Main main = Main.getPlugin(Main.class);
	String prefix = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Prefix"));
	String nop = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("No_Permission_Message"));
	
    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
	
    
    ArrayList<String> lore = new ArrayList<String>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//LuckyBlock
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 1) {
				switch (args[0]) {
				// /lb reload
				case "reload":
					player.sendMessage(ChatColor.GREEN + "Config Reloaded Successfully!");
					break;
				
				// /lb help
				case "help":
					player.sendMessage(ChatColor.GRAY + "---------------------");
					player.sendMessage(ChatColor.GRAY + "/lb reload   // used to reload the config.");
					player.sendMessage("");
					player.sendMessage("");
					player.sendMessage(ChatColor.GRAY + "/lb help   // used to look at the commands.");
					player.sendMessage("");
					player.sendMessage(ChatColor.GRAY + "/lb setprize   // used to set the LuckyBlock's prize.");
					player.sendMessage("");
					player.sendMessage(ChatColor.GRAY + "/lb prize   // used to show the LuckyBlock's prize.");
					player.sendMessage("");
					player.sendMessage(ChatColor.GRAY + "/lb give <amount/player> [amount]   // used to give LuckyBlock's to player");
					player.sendMessage("");
					player.sendMessage(ChatColor.GRAY + "---------------------");
				// lb give <player/amount> [amount] (if player was set)
					break;
				case "give":
					// if parameter is number
					if (player.hasPermission("luckyblock.give")) {
					if (args.length != 1 && args.length != 2) {
                    if (isInt(args[1]) == false && args[1] != null  && Bukkit.getServer().getPlayer(args[1]) != null) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target.isOnline()) {
							if (isInt(args[2])) {
								ItemStack luckyblock = new ItemStack(Material.SPONGE);
								ItemMeta lm = luckyblock.getItemMeta();
								lm.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "LuckyBlock");
								lore.clear();
								lore.add(ChatColor.WHITE + "Break the block and get random item!");
								lm.setLore(lore);
								luckyblock.setItemMeta(lm);
								luckyblock.setAmount(Integer.valueOf(args[2]));
								player.getInventory().addItem(luckyblock);
								player.sendMessage(prefix + ChatColor.GREEN + "Successfully gived " +
								ChatColor.GOLD + target.getName() + ChatColor.GREEN + " " + args[2] + " luckyblocks!");
								target.sendMessage(prefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN + 
										" gived you " + args[2] + " luckyblocks!");
							} else {
								player.sendMessage(prefix + ChatColor.GRAY + "Invalid Number!");
							}
						}
					} else {
						player.sendMessage(prefix + ChatColor.GRAY + "The player " + args[1] + " is offline!");
					}
				} else {
					player.sendMessage(prefix + ChatColor.GRAY + "Correct usage /lb give <player> [amount]");
				}
				} else {
					player.sendMessage(prefix + nop);
				}
					break;
					
				// Gui to set the item
				case "setprize":
					if (player.hasPermission("luckyblock.setprize")) {
				    	Inventory gui = Bukkit.createInventory(null, 54, ChatColor.GRAY + "" +ChatColor.BOLD + "Set Items!");
				    	player.openInventory(gui);
					} else {
						player.sendMessage(prefix + nop);
					}
					break;
					
				// Opens gui with the items	
				case "prize":
			    	Inventory open = Bukkit.createInventory(null, 54, ChatColor.GRAY + "" +ChatColor.BOLD + "Items!");
			    	ItemStack[] content = Items.getContent().toArray(new ItemStack[0]);
			        open.setContents(content);
			        player.openInventory(open);
			    	break;
			    	
			    	
			    default:
			    	player.sendMessage(prefix + ChatColor.GRAY + "/lb help");
				}
			} else {
				player.sendMessage(prefix + ChatColor.GRAY + "/lb help");
			}
		}
		return false;
	}

}
