package com.chickenstyle.luckyblock;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Events implements Listener {
	
	String prefix = ChatColor.RED + "[" + ChatColor.WHITE + "LuckyBlock" + ChatColor.RED + "] " + ChatColor.GRAY + ">>> ";
    ArrayList<String> lore = new ArrayList<String>();
    //Random Item
    
    // Spawn LuckyBlock Hologram
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		ItemStack luckyblock = new ItemStack(Material.SPONGE);
		ItemMeta lm = luckyblock.getItemMeta();
		lm.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "LuckyBlock");
		lore.add(ChatColor.WHITE + "Break the block and get random item!");
		lm.setLore(lore);
		luckyblock.setItemMeta(lm);
		if (player.getItemInHand().getType().equals(Material.SPONGE)) {
			if (player.getItemInHand().hasItemMeta()) {
			if (player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "" + ChatColor.BOLD + "LuckyBlock")) {
			ArmorStand holo = (ArmorStand) e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation().add(0.5,-0.7,0.5), EntityType.ARMOR_STAND);
			holo.setCustomName(ChatColor.GOLD + "LuckyBlock");
			holo.setCustomNameVisible(true);
			holo.setVisible(false);
			holo.setInvulnerable(true);
			holo.setGravity(false);
			holo.setGliding(false);
			}
		  }
		}
	}
	
	// Save Inventory to config
	@EventHandler
	public void onLeave(InventoryCloseEvent e) {
		if (e.getView().getTitle().equals(ChatColor.GRAY + "" +ChatColor.BOLD + "Set Items!")) {
			Player player = (Player) e.getPlayer();
			Items.setContent(e.getInventory().getContents());
			player.sendMessage(prefix + ChatColor.GREEN + "Items was set!");
		}
	}
	// Disable click in prize inventory
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		 if (e.getView().getTitle().equals(ChatColor.GRAY + "" +ChatColor.BOLD + "Items!")) {
				e.setCancelled(true);
		}

	}
	// Holo remove and give item
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();
		if (block.getType() == Material.SPONGE) {
			if (block.getWorld().getNearbyEntities(block.getLocation(), 1, 1, 1).isEmpty()) {
				return;
			} else {
				ArrayList<ItemStack> list = new ArrayList<ItemStack>();
				for (Entity en: block.getWorld().getNearbyEntities(block.getLocation(), 1, 1, 1)) {
					if (en.getType() == EntityType.ARMOR_STAND) {
						en.remove();
						player.sendMessage(prefix + ChatColor.GOLD + "You just opened LuckyBlock");
					}
					
				}
				for (ItemStack i: Items.getContent()) {
					if (i != null) {
						list.add(i);
					}
				}
				Random rand = new Random();
				int r = rand.nextInt(list.size());
				ItemStack is = list.get(r);
				player.getInventory().addItem(is);
			}
		}
	}
}
