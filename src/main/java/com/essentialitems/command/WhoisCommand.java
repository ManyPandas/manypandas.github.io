package com.essentialitems.command;

import java.text.DecimalFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

import net.md_5.bungee.api.ChatColor;

public final class WhoisCommand extends CommandSkeleton {

	private String header = "["+ChatColor.DARK_GREEN+ChatColor.BOLD+"Player Info"+ChatColor.RESET+"] ";
	
	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(!Util.playerOnline(args[0])) {
			//Player is offline
			
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
			if(!p.hasPlayedBefore()) {
				return 4;
			}
			else {
				;
				String uuid = p.getUniqueId().toString();
				long lastPlayedMillis = p.getLastPlayed();
				
				
				Date lastPlayed = new Date(lastPlayedMillis);
				
				//Unfortunately, we cannot give the IP address as the player is offline.
				sender.sendMessage(header+ChatColor.DARK_GREEN+"Player info for "+ChatColor.YELLOW+p.getName());
				sender.sendMessage(header+ChatColor.DARK_GREEN+"Online: "+ChatColor.YELLOW+p.isOnline());
				sender.sendMessage(header+ChatColor.DARK_GREEN+"UUID: "+ChatColor.YELLOW+uuid);
				sender.sendMessage(header+ChatColor.DARK_GREEN+"Last Time Played: "+ChatColor.YELLOW+lastPlayed.toString());
				
				return 0;
			}
			
		}
		
		else {
			
			//We need to gather all of the information.
			
			Player p = Bukkit.getPlayer(args[0]);
			
			String uuid = p.getUniqueId().toString();
			String address = p.getAddress().toString();
			Location location = p.getLocation();
			double x = location.getX();
			double y = location.getY();
			double z = location.getZ();
			String worldName = p.getWorld().getName();
			
			DecimalFormat df = new DecimalFormat("#.##");
			
			sender.sendMessage(header+ChatColor.DARK_GREEN+"Player info for "+ChatColor.YELLOW+p.getName());
			sender.sendMessage(header+ChatColor.DARK_GREEN+"Online: "+ChatColor.YELLOW+p.isOnline());
			sender.sendMessage(header+ChatColor.DARK_GREEN+"UUID: "+ChatColor.YELLOW+uuid);
			sender.sendMessage(header+ChatColor.DARK_GREEN+"IP Address: "+ChatColor.YELLOW+address);
			sender.sendMessage(header+ChatColor.DARK_GREEN+"Current Location: In world "+ChatColor.YELLOW+worldName+ChatColor.RESET+ChatColor.DARK_GREEN+" at coordinates "+ChatColor.YELLOW+df.format(x)+" "+df.format(y)+" "+df.format(z));
			return 0;
			
		}
	}

}
