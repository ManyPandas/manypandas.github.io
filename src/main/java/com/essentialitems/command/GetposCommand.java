package com.essentialitems.command;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class GetposCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		//Check arguments
		if(args.length == 0) {
			//We're getting the position of the sender
			
			//We need to also check if the sender is a player, if not, we don't have the data they are requesting.
			if(!(sender instanceof Player)) {
				//Not a player
				return 1;
			}
			//Sender is a player
			//Cast
			Player p = (Player) sender;
			DecimalFormat format = new DecimalFormat("#.##");
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			String xs = format.format(x);
			String ys = format.format(y);
			String zs = format.format(z);
			String worldname = p.getLocation().getWorld().getName();
			
			p.sendMessage(ChatColor.GREEN+"You are currently in the world "+worldname);
			p.sendMessage(ChatColor.GREEN+"At coordinates "+ xs+" "+ys+" "+zs);
			
			return 0;
			
			
			
			
		}
		else {
			//Check if the specified player is online
			if(!Util.playerOnline(args[0])) {
				//Not online
				return 4;
				
			}
			//Online, get the player
			Player p = Bukkit.getPlayer(args[0]);
			DecimalFormat format = new DecimalFormat("#.##");
			//Get the data
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getZ();
			String xs = format.format(x);
			String ys = format.format(y);
			String zs = format.format(z);
			String worldName = p.getLocation().getWorld().getName();
			
			sender.sendMessage(ChatColor.GREEN+args[0]+" is currently in the world "+worldName);
			sender.sendMessage(ChatColor.GREEN+"At coordinate "+xs+" "+ys+" "+zs);
			
			return 0;
			
		}
		
	}

}
