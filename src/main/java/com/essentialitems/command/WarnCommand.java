package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class WarnCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(!Bukkit.getPlayer(args[0]).isOnline()) {
			return 4;
		}
		else {
			String reason = Util.buildMessage(args, 1);
			Player p = Bukkit.getPlayer(args[0]);
			Bukkit.broadcastMessage(Util.punishHeader+ChatColor.BLUE+p.getName()+ChatColor.RESET+ChatColor.RED+" has been issued a warning.  "+ChatColor.RESET+ChatColor.DARK_GREEN+"Reason: "+reason);
			p.playSound(p.getLocation(), Sound.ENTITY_CAT_AMBIENT, 200000, 1);
			return 0;
		}
	}

}
