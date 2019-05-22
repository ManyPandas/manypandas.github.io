package com.essentialitems.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ActionBar {
	
	private String text;
	
	public ActionBar(String text) {
		this.text=ChatColor.translateAlternateColorCodes('&', text);
	}
	
	public void sendToPlayer(Player p) {
		if(p == null) {
			throw new IllegalArgumentException("Player Cannot be Null!");
		}
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(this.text).create());
	}
	public void sendToPlayers(Player... players) {
		for(Player p : players) {
			if(p == null) {
				throw new IllegalArgumentException("Player Cannot be Null!");
			}
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(this.text).create());
			
			
			
		}
		
	}
	public void sendToAll() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(this.text).create());
		}
	}
	

}
