package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class TempMuteCommand extends CommandSkeleton {

	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(!Util.playerOnline(args[0])) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
			if(!p.hasPlayedBefore()) {
				return 4;
			}
			else {
				int preTime;
				
				try {
					preTime =Integer.parseInt(args[1]);
				}
				catch(Exception e) {
					return 3;
				}
				
				long time = preTime * 60 *1000 + System.currentTimeMillis();
				String uuid = p.getUniqueId().toString();
				String name = p.getName();
				String reason = Util.buildMessage(args,2);
				Util.mute(uuid, name, reason, time, mainClass);
				return 0;
				
				
				
			}
		}
		else {
			Player p = Bukkit.getPlayer(args[0]);
			int preTime;
			
			try {
				preTime = Integer.parseInt(args[1]);
			}
			catch(Exception e) {
				return 3;
			}
			
			long time = preTime *60 *1000 + System.currentTimeMillis();
			String uuid = p.getUniqueId().toString();
			String name = p.getName();
			String reason = Util.buildMessage(args, 2);
			Util.mute(uuid, name, reason, time, mainClass);
			return 0;
			
		}
	}

}
