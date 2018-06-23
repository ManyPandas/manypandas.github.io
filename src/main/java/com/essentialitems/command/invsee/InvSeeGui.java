package com.essentialitems.command.invsee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class InvSeeGui {
	
	
	private static ItemStack health;
	private static ItemStack hunger;
	private static ItemStack effects;
	private static ItemStack glassPane;
	private static String blankLine = " ";
	
	
	public static void openGui(Player sender, Player seen) {
		
		//Get the final Inventory
		Inventory finalInv = Bukkit.createInventory(null, 54, ChatColor.BLUE+""+ChatColor.BOLD+seen.getName()+"'s Inventory");
		
		//Get the seen Player's inventory
		Inventory seenInv = seen.getInventory();
		
		
		//First lets set all of the seen Inventory contents
		finalInv.setContents(seenInv.getContents());
		
		//Now we can start adding our glass panes.
		
		
		finalInv.setItem(36, glassPane);
		finalInv.setItem(37, glassPane);
		finalInv.setItem(38, glassPane);
		finalInv.setItem(39, glassPane);
		finalInv.setItem(40, glassPane);
		finalInv.setItem(41, glassPane);
		finalInv.setItem(42, glassPane);
		finalInv.setItem(43, glassPane);
		finalInv.setItem(44, glassPane);
		finalInv.setItem(49, glassPane);
		finalInv.setItem(50, glassPane);
		
		//Now we need to put the seen player's armor in the right place...
		finalInv.setItem(45, seen.getInventory().getHelmet());
		finalInv.setItem(46, seen.getInventory().getChestplate());
		finalInv.setItem(47, seen.getInventory().getLeggings());
		finalInv.setItem(48, seen.getInventory().getBoots());
		
		//Next we need to get their health, hunger and active potion effects ready to place them into their respective item's lore
		double healthValue = seen.getHealth();
		int hungerValue = seen.getFoodLevel();
		Collection<PotionEffect> potions = seen.getActivePotionEffects();
		
		
		
		{
			//Set the lore of the health
			ItemMeta meta = health.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add(blankLine);
			lore.add(ChatColor.RED+""+ChatColor.BOLD+healthValue);
			meta.setLore(lore);
			health.setItemMeta(meta);
		}
		
		
		{
			//Set the lore of the hunger
			ItemMeta meta = hunger.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add(blankLine);
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+hungerValue);
			meta.setLore(lore);
			hunger.setItemMeta(meta);
			
		}
		
		{
			//Set the lore of the potion effects
			ItemMeta meta = effects.getItemMeta();
			List<String> lore = new ArrayList<String>();
			
			for(PotionEffect effect : potions) {
				lore.add(ChatColor.GREEN+effect.getType().getName());
				lore.add(blankLine);
			}
			meta.setLore(lore);
			effects.setItemMeta(meta);
		}
		
		//Now all of the buttons are placed, we can put them into their respective slots
		
		
		
		finalInv.setItem(51, health);
		finalInv.setItem(52, hunger);
		finalInv.setItem(53, effects);
		
		
		//The GUI is ready to go...
		
		sender.closeInventory();
		sender.openInventory(finalInv);
		
		return;
		
		
		//Now its up to the inventory click event to handle things for us.
		
		
		
		
		
		
		
	}
	public enum buttons {
		HEALTH(health),
		HUNGER(hunger),
		EFFECTS(effects),
		GLASS_PANE(glassPane),
		;
		
		private ItemStack item;
		static boolean buttonsSet =false;
		
		buttons(ItemStack item) {
			this.item=item;
		}
		
		public ItemStack get() {
			if(!buttonsSet) {
				prepItems();
			}
			return item;
		}
	}
	
	
	
	//Doesn't prepare all items 100%
	//Some values need to be dynamically updated.
	public static void prepItems() {
		
		
		//Place Holder glass pane
		{
			glassPane = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 5);
			ItemMeta meta = glassPane.getItemMeta();
			meta.setDisplayName(" ");
			glassPane.setItemMeta(meta);
			
			
		}
		
		//Heath 
		{
			health = new ItemStack(Material.GOLDEN_APPLE,1);
			ItemMeta meta = health.getItemMeta();
			meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"Health");
			health.setItemMeta(meta);
			
		}
		
		//Hunger
		{
			hunger = new ItemStack(Material.COOKED_BEEF,1);
			ItemMeta meta = hunger.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Hunger");
			hunger.setItemMeta(meta);
			
			
		}
		
		//Any active potion effects.
		{
			effects = new ItemStack(Material.GLASS_BOTTLE, 1);
			ItemMeta meta = effects.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Active Potion Effects");
			effects.setItemMeta(meta);
			
		}
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	

}
