package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.BanList;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class UnbanCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		if(!Bukkit.getBanList(BanList.Type.NAME).isBanned(args[0])) {
			sender.sendMessage(Util.punishHeader+ChatColor.RED+"That player is not banned.");
			return 0;
		}
		else {
			Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);
			sender.sendMessage(Util.punishHeader+ChatColor.GREEN+"Success! " + args[0]+" has been pardoned!");
			return 0;
			
		}
	}

}
