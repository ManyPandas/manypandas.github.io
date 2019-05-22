package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.essentialitems.Main;
import com.essentialitems.Util;
import com.essentialitems.util.ActionBar;

public class ActionBarCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		if(!Util.playerOnline(args[0])) {
			return 4;
		}
		else {
			String message = Util.buildMessage(args, 1);
			new ActionBar(message).sendToPlayer(Bukkit.getPlayer(args[0]));
			return 0;
		}
		
	}

}
