package com.essentialitems.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.essentialitems.Main;
import com.essentialitems.command.InvseeCommand;
import com.essentialitems.command.KitCommand;
import com.essentialitems.command.MotdCommand;

public class InvClickListener implements Listener {
	
	private Main mainclass;
	
	public InvClickListener(Main plugin) {
		this.mainclass = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onInvClick(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(ChatColor.GREEN+""+ChatColor.BOLD+"Configure MOTD")) {
			
			e.setCancelled(true);
			Player p =(Player) e.getWhoClicked();
			
			MotdCommand.inventoryClick(p, e.getCurrentItem(), e.getInventory(), e.getSlot(), mainclass);
			return;
		}
		else if(InvseeCommand.invseeing.containsKey((Player) e.getWhoClicked()) && 
				e.getInventory().getName().equalsIgnoreCase(ChatColor.BLUE+""+ChatColor.BOLD+InvseeCommand.invseeing.get((Player) e.getWhoClicked()).getName()+"'s Inventory") ) {
			e.setCancelled(true);
			return;
		}
		else {
			InvseeCommand.invseeing.remove((Player) e.getWhoClicked());
			//We can't return quite yet.  We need to check if somebody is creating or editing a kit.
		}
		
		if(e.getInventory().getName().equalsIgnoreCase(ChatColor.GOLD+""+ChatColor.BOLD+"Create Kit")) {
			//A kit is being created...
			e.setCancelled(
					KitCommand.inventoryClick((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), mainclass)
					);
			
			
			
		}
		if(e.getInventory().getName().equalsIgnoreCase(ChatColor.GOLD+""+ChatColor.BOLD+"Edit Kit")) {
			//A kit is being edited...
			e.setCancelled(
					KitCommand.inventoryClick((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory(), mainclass)
					);
		}
		
		
		
	}

}
