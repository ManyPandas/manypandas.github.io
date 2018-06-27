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
					
					//The unmute for the new system failed.
					//Let's try the old unmute.
					if(Util.getMuted(uuid, mainClass)) {
						//They are muted using the old system.
						Util.unmute(uuid, mainClass);
						sender.sendMessage(Util.punishHeader+ChatColor.RED+"Successfully unmuted "+ChatColor.BLUE+ args[0]);
						return 0;
					}
					else {
						//They aren't muted in any way.
						sender.sendMessage(Util.punishHeader+ChatColor.BLUE+args[0]+ChatColor.RED+" is not muted.");
						return 0;
					}
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
			if(!Util.newGetMuted(uuid, mainClass)) {
				
				//The unmute for the new system failed.
				//Let's try the old unmute.
				if(Util.getMuted(uuid, mainClass)) {
					//They are muted using the old system.
					Util.unmute(uuid, mainClass);
					sender.sendMessage(Util.punishHeader+ChatColor.RED+"Successfully unmuted "+ChatColor.BLUE+ args[0]);
					p.sendMessage(Util.punishHeader+ChatColor.RED+"You have been unmuted by "+ChatColor.BLUE+sender.getName());
					return 0;
				}
				else {
					//They aren't muted in any way.
					sender.sendMessage(Util.punishHeader+ChatColor.BLUE+args[0]+ChatColor.RED+" is not muted.");
					return 0;
				}
			}
			else {
				//Successful unmute with the new system!
				
				//I'm not sure why we need this, but, the un-mute broke for some reason and this fixed it.

				Util.newUnmute(uuid, mainClass);
				sender.sendMessage(Util.punishHeader+ChatColor.RED+"Successfully unmuted "+ChatColor.BLUE+ args[0]);
				p.sendMessage(Util.punishHeader+ChatColor.RED+"You have been unmuted by "+ChatColor.BLUE+sender.getName());
				return 0;
			}
		}
	}
}
