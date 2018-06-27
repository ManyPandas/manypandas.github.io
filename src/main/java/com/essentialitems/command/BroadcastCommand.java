package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class BroadcastCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		
		String message = Util.buildMessage(args,0);
		Bukkit.broadcastMessage(ChatColor.AQUA+""+ChatColor.BOLD+sender.getName()+"> "+ChatColor.RESET+ChatColor.GOLD+ChatColor.BOLD+message);
		
		for(Player other : Bukkit.getOnlinePlayers()) {
			other.playSound(other.getLocation(), Sound.BLOCK_NOTE_PLING, 200000, 1);
		}
		//This is it.  All it takes is a few simple statements and method calls.
		return 0;
	}

}
