package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class TempBanCommand extends CommandSkeleton {

	
	@Override
	@SuppressWarnings("deprecation")
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(!Util.playerOnline(args[0])) {
			int time;
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
			if(!p.hasPlayedBefore()) {
				return 4;
			}
			else {
				try {
					 time = Integer.parseInt(args[1]);
				}
				catch(Exception e) {
					return 3;
				}
				String reason = Util.buildMessage(args, 2);
				Util.tempBan(p.getName(), reason, time, sender);
				return 0;	
			}
		}
		else {
			int time;
			Player p = Bukkit.getPlayer(args[0]);
			try {
				time = Integer.parseInt(args[1]);
				
			}
			catch(Exception e) {
				return 3;
			}
			String reason = Util.buildMessage(args,2);
			Util.tempBan(p.getName(), reason, time, sender);
			p.kickPlayer(Util.generateTempBanMessage(reason, time));
			return 0;
			
			
		}
		
		
	}

}
