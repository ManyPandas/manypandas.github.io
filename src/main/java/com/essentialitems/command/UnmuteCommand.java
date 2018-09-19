package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class UnmuteCommand extends CommandSkeleton {

	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(!Util.playerOnline(args[0])) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
			
			if(!p.hasPlayedBefore()) {
				return 4;	
			}
			else {
				String uuid = p.getUniqueId().toString();
				if(!Util.newUnmute(uuid, mainClass)) {
					//Not muted
					sender.sendMessage(Util.punishHeader+ChatColor.BLUE+args[0]+ChatColor.RED+" is not muted.");
					return 0;
				}
				else {
					//Successful unmute with the new system!
					sender.sendMessage(Util.punishHeader+ChatColor.RED+"Successfully unmuted "+ChatColor.BLUE+ args[0]);
					return 0;
				}
			}
		}
		else {
			Player p = Bukkit.getPlayer(args[0]);
			String uuid = p.getUniqueId().toString();
			if(!Util.newUnmute(uuid, mainClass)) {
				//Not muted
				sender.sendMessage(Util.punishHeader+ChatColor.BLUE+args[0]+ChatColor.RED+" is not muted.");
				return 0;
				
			}
			else {
				//Successful unmute with the new system!
				sender.sendMessage(Util.punishHeader+ChatColor.RED+"Successfully unmuted "+ChatColor.BLUE+ args[0]);
				return 0;
				
			}
		}
	}
}
