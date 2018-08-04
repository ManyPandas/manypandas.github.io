package com.essentialitems;

import org.bukkit.ChatColor;

public final class ConfigManager {
	
	//Static class
	private ConfigManager() {}
	
	public static void initCheck(Main mainclass) {
		
		if(!mainclass.getConfig().contains("doPlayerJoinedMessage")) {
			mainclass.getConfig().set("doPlayerJoinedMessage", false);
		}
		if(!mainclass.getConfig().contains("doMotdChatMessage")) {
			mainclass.getConfig().set("doMotdChatMessage", true);
		}
		if(!mainclass.getConfig().contains("doMotdTitleMessage")) {
			mainclass.getConfig().set("doMotdTitleMessage", true);
		}
		if(!mainclass.getConfig().contains("doMotdSubtitleMessage")) {
			mainclass.getConfig().set("doMotdSubtitleMessage", true);
		}
		if(!mainclass.getConfig().contains("motdChatMessage")) {
			mainclass.getConfig().set("motdChatMessage", "All messages are fully configurable");
		}
		if(!mainclass.getConfig().contains("motdSubtitleMessage")) {
			mainclass.getConfig().set("motdSubtitleMessage", "You can control it with /motd");
		}
		if(!mainclass.getConfig().contains("motdTitleMessage")) {
			mainclass.getConfig().set("motdTitleMessage", "EssentialItems MOTD");
		}
		if(!mainclass.getConfig().contains("doPlayerLeftMessage")) {
			mainclass.getConfig().set("doPlayerLeftMessage", false);
		}
		if(!mainclass.getConfig().contains("lockdown")) {
			mainclass.getConfig().set("lockdown", false);
		}
		if(!mainclass.getConfig().contains("lockdown-reason")) {
			mainclass.getConfig().set("lockdown-reason", " .");
		}
		if(!mainclass.getConfig().contains("chatslow-cooldown")) {
			mainclass.getConfig().set("chatslow-cooldown", (int) 0);
		}
		if(!mainclass.getConfig().contains("chatslow")) {
			mainclass.getConfig().set("chatslow", false);
		}
		if(!mainclass.getConfig().contains("kickUnauthorizedOnLockdown")) {
			mainclass.getConfig().set("kickUnauthorizedOnLockdown", false);
		}
		if(!mainclass.getConfig().contains("feedcooldown")) {
			mainclass.getConfig().set("feedcooldown", -1);
			
		}
		if(!mainclass.getConfig().contains("healcooldown")) {
			mainclass.getConfig().set("healcooldown",-1);
		}
		if(mainclass.getConfig().isConfigurationSection("MutedPlayers")) {
			mainclass.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.RED+"Warning! Old storage format found for storing muted players!");
			mainclass.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.RED+"This method of storing mutes is now deprecated.  Please consider running the /mutemigrate command to migrate any mutes to the new system.");
			mainclass.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.RED+"Please note that all new mutes will be stored in a file named 'muted.yml'.");
			mainclass.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.RED+"Support for the deprecated format will continue, however it will be cut with EssentialItems version 3.3");
		}
		mainclass.saveConfig();
	}
		
		
		
	
	
	public static void resetMotd(Main mainclass) {
		mainclass.getConfig().set("motdTitleMessage", "EssentialItems MOTD");
		mainclass.getConfig().set("motdSubtitleMessage", "You can control it with /motd");
		mainclass.getConfig().set("motdChatMessage", "All messages are fully configurable");
		mainclass.getConfig().set("doMotdSubtitleMessage", true);
		mainclass.getConfig().set("doMotdTitleMessage", true);
		mainclass.getConfig().set("doMotdChatMessage", true);
		mainclass.getConfig().set("doPlayerJoinedMessage", false);
		mainclass.getConfig().set("doPlayerLeftMessage", false);
		mainclass.saveConfig();
		return;
		
	}
	public static void resetChat(Main mainclass) {
		mainclass.getConfig().set("motdChatMessage", "All messages are fully configurable");
		mainclass.getConfig().set("doMotdChatMessage", true);
		mainclass.saveConfig();
		return;
		
	}
	public static void resetTitle(Main mainclass) {
		mainclass.getConfig().set("motdTitleMessage", "EssentialItems MOTD");
		mainclass.getConfig().set("doMotdTitleMessage", true);
		mainclass.saveConfig();
		return;
		
	}
	public static void resetSubtitle(Main mainclass) {
		mainclass.getConfig().set("motdSubtitleMessage", "You can control it with /motd");
		mainclass.getConfig().set("doMotdSubtitleMessage", true);
		mainclass.saveConfig();
		return;
	}
	
	


}
