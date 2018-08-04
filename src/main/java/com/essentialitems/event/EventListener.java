package com.essentialitems.event;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.essentialitems.Main;
import com.essentialitems.Util;
import com.essentialitems.command.InvseeCommand;
import com.essentialitems.command.KitCommand;
import com.essentialitems.command.MotdCommand;
import com.essentialitems.command.VanishCommand;

public final class EventListener implements Listener {
	private Main mainclass;
	private ChatListener chat;
	
	
	public EventListener(Main plugin, ChatListener chat) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.mainclass = plugin;
		this.chat = chat;
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void ojin(PlayerJoinEvent e) {
		
		//HOLD UP!! We can't display the MOTD yet!  What if the server is locked down?
		if(!Util.checkPermission(Util.permission.bypassLockdown.get(), e.getPlayer())&&mainclass.getConfig().getBoolean(Util.configKey.lockdown.toString())) {
			e.getPlayer().kickPlayer(ChatColor.RED+""+ChatColor.BOLD+"Sorry, the server is currently in a locked-down state. You do not have permission to log into the server at this time.\n\n"+ChatColor.RESET+ChatColor.BLUE+" Lockdown Reason: "+mainclass.getConfig().getString
					(Util.configKey.lockdownReason.toString()));
			//They don't even have access to the server.  Return out.
			return;
		}
		
		for(Player p : VanishCommand.vanished) {
			e.getPlayer().hidePlayer(p);
		}
		if(!mainclass.getConfig().getBoolean(Util.configKey.doOjinMessage.toString())) {
			e.setJoinMessage(null);
		}
		
		Util.displayMotd(e.getPlayer(),mainclass);
		
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		Player p1 = e.getPlayer();
		chat.coolDownTime.remove(p1.getName());
		if(!mainclass.getConfig().getBoolean(Util.configKey.doOleaMessage.toString())) {
			e.setQuitMessage(null);
		}
		if(VanishCommand.vanished.contains(e.getPlayer())) {
			VanishCommand.vanished.remove(e.getPlayer());
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.showPlayer(e.getPlayer());
			}
			return;
		}
		return;
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
	@EventHandler(priority = EventPriority.LOW)
	public void onInvClose(InventoryCloseEvent e) {
		
		Player p = (Player) e.getPlayer();
		
		if(e.getInventory().getName().equalsIgnoreCase(ChatColor.GOLD+""+ChatColor.BOLD+"Create Kit")) {
			//There is a kit in progress with this player's name on it.  We need to get rid of it or else it will cause errors.
			if(KitCommand.getInProgress(p)!= null) {
				//Remove the entry.
				KitCommand.waitingKits.remove(KitCommand.getInProgress(p));
				return;
			}
			//Hmm... I think the event must have fired twice...
			//I'm not sure, I'm just going to return out.
			return;
			
		}
		//Not the inventory we're looking for, do nothing
		
	}
	

}
