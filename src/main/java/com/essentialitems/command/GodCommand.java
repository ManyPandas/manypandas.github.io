package com.essentialitems.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class GodCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		//We can cast the sender to a player right away...
		Player p = (Player) sender;
		
		if(!Util.invulnerablePlayers.contains(p)) {
			//Toggling on
			Util.invulnerablePlayers.add(p);
			p.sendMessage(ChatColor.DARK_PURPLE+"You are now invulnerable.");
			return 0;
		}
		//Toggling off
		Util.invulnerablePlayers.remove(p);
		p.sendMessage(ChatColor.DARK_PURPLE+"You are no longer invulnerable.");
		return 0;
	}

}
