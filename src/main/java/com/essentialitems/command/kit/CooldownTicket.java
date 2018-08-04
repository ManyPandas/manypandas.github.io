package com.essentialitems.command.kit;

import org.bukkit.entity.Player;

public final class CooldownTicket {
	
	
	private Kit kit;
	private Player user;
	private long timeUsedMillis;
	
	
	protected CooldownTicket(Kit kit, Player user, long timeUsedMillis) {
		
		this.kit = kit;
		this.user = user;
		this.timeUsedMillis = timeUsedMillis;
		
	}
	
	public Kit getKit() {
		return this.kit;
		
	}
	
	public Player getUser() {
		return this.user;
	}
	
	public long getTimeUsedMillis() {
		return this.timeUsedMillis;
		
	}

}
