package com.essentialitems.command.kit;

import org.bukkit.entity.Player;

public class KitInProgress {
	
	
	private String name;
	
	private Player user;
	private int cooldown;
	
	public KitInProgress(String name, int cooldown, Player user) {
		
		this.name = name;
		this.cooldown = cooldown;
		this.user = user;
		
	}
	
	public KitInProgress(String name, Player user) {
		this(name,-1,user);
	}
	
	
	
	public String getName() {
		return this.name;
	}
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public Player getUser() {
		return this.user;
	}

}
