package com.essentialitems.command;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;

import net.md_5.bungee.api.ChatColor;

public class FlyCommand extends CommandSkeleton {
	
	private static final String flyHeader = "["+ChatColor.DARK_GREEN+ChatColor.BOLD+"Fly"+ChatColor.RESET+"] ";

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		Player p = (Player) sender;
		
		if(p.getGameMode().equals(GameMode.CREATIVE)) {
			p.setFlying(!p.isFlying());
			p.sendMessage(flyHeader+ChatColor.GOLD+"Toggled flight.");
			return 0;
		}
		
		p.setAllowFlight(!p.getAllowFlight());
		p.setFlying(!p.isFlying());
		
		p.sendMessage(flyHeader+ChatColor.GOLD+"Toggled flight.");
		return 0;
	}

}
