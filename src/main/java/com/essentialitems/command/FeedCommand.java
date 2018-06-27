package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class FeedCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		Player p = (Player) sender;
		
		if(args.length>0) {
			if(args[0].equalsIgnoreCase("all")) {
				for(Player other : Bukkit.getOnlinePlayers()) {
					
					if(other.getName().equals(p.getName())) {
						continue;
					}
					if(other.getFoodLevel()>= 30) {
						continue;
					}
					other.setFoodLevel(30);
					other.setSaturation(10);
					other.setExhaustion(0F);
					other.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been fed.");
				}
				p.sendMessage(Util.healthHeader+ChatColor.GREEN+"Sucessfully fed all online players.");
				return 0;
			}
			else {
				if(!Util.playerOnline(args[0])) {
					return 4;
				}
				else {
					Player other = Bukkit.getPlayer(args[0]);
					if(other.getFoodLevel()>= 30) {
						p.sendMessage(Util.healthHeader+ChatColor.BLUE+args[0]+ChatColor.RED+" is already at max hunger.");
						return 0;
					}
					other.setFoodLevel(30);
					other.setSaturation(10);
					other.setExhaustion(0F);
					other.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been fed.");
					p.sendMessage(Util.healthHeader+ChatColor.GREEN+"Sucessfully fed "+ args[0]);
					return 0;
				}
			}
		}
		else {
			if(p.getFoodLevel()>= 30) {
				p.sendMessage(Util.healthHeader+ChatColor.RED+"You are already at max hunger.");
				return 0;
			}
			p.setFoodLevel(30);
			p.setSaturation(10);
			p.setExhaustion(0F);
			p.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been fed.");
			return 0;
		}
	}
	

}
