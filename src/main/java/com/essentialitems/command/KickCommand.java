package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class KickCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		String reason = Util.buildMessage(args, 1);
		Player target = Bukkit.getPlayerExact(args[0]);
		if(!Util.playerOnline(args[0])) {
			return 4;
			
		}
		else {
			Util.kick(target, reason, sender);
			return 0;
			
		}
	}
	

}
