package com.essentialitems.command;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.command.warp.Warp;

public class WarpCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		// TODO Auto-generated method stub
		
		Player p = (Player) sender;
		
		String warpname = args[0];
		
		if(args.length == 1) {
			//Okay, there's only one argument, we need to check if the warp exists
			if(!mainClass.warps.isConfigurationSection(warpname)) {
				//The warp does not exist.
				p.sendMessage(ChatColor.RED+"There is no warp named "+warpname);
				return 0;
			}
			else {
				Warp warp = Warp.getFromConfig(warpname);
				
				if(warp == null) {
					//We've already ruled out the possibility of the warp not existing
					//There's a problem with the world.
					//It's null for some reason
					sender.sendMessage(ChatColor.RED+"There is a problem with the warp. Is the world it's warping to loaded? ");
					return 0;
					
				}
				if(!p.hasPermission(warp.getPermission())) {
					sender.sendMessage(ChatColor.RED+"You do not have permission to use that warp.");
				}
				warp.warp(p);
				return 0;
				
			}
		}
		else {
			String subcommand = args[1];
			
			if(subcommand.equalsIgnoreCase("create")) {
				if(mainClass.warps.isConfigurationSection(warpname)) {
					sender.sendMessage(ChatColor.RED+"The warp already exists.");
					return 0;
				}
				Warp warp = new Warp(warpname, p.getLocation());
				warp.save();
				p.sendMessage(ChatColor.GREEN+"Succesfully created new warp '"+warpname+"'. ");
				return 0;
			}
			if(subcommand.equalsIgnoreCase("delete")) {
				mainClass.warps.set(warpname, null);
				sender.sendMessage(ChatColor.GREEN+"Deleted warp '"+warpname+"'.");
				
				return 0;
				
			}
			if(subcommand.equalsIgnoreCase("sethere")) {
				if(Warp.getFromConfig(warpname)== null) {
					sender.sendMessage(ChatColor.RED+"There is no warp named "+warpname);
					return 0;
					
				}
				Warp warp = new Warp(warpname, p.getLocation());
				//the nice thing about the warp system is that it will automatically overwrite the data for us.
				warp.save();
				sender.sendMessage(ChatColor.GREEN+"Warp changed to current location.");
				return 0;
			}
			if(subcommand.equalsIgnoreCase("warp")) {
				if(args.length <3) {
					return 30;
				}
				Player other = Bukkit.getPlayer(args[2]);
				
				if(other == null) {
					return 25;
				}
				Warp warp = Warp.getFromConfig(warpname);
				if(warp == null) {
					sender.sendMessage(ChatColor.RED+"There is no warp named "+warpname);
					return 0;
				}
				warp.warp(other, false);
				other.sendMessage(ChatColor.GOLD+"You have been warped to "+warpname+" by "+sender.getName());
				sender.sendMessage(ChatColor.GREEN+"Warped "+other.getName()+" to "+ warpname);
				return 0;
				
				
			}
			if(subcommand.equalsIgnoreCase("info")) {
				Warp warp = Warp.getFromConfig(warpname);
				if(warp == null) {
					sender.sendMessage(ChatColor.RED+"There is no warp named "+warpname);
					return 0;
				}
				DecimalFormat deciFormat = new DecimalFormat("#.##");
				sender.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+ChatColor.STRIKETHROUGH+"======================");
				sender.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"Warp information for warp "+warp.getName());
				sender.sendMessage(ChatColor.YELLOW+"World: "+warp.getWorld().getName());
				sender.sendMessage(ChatColor.YELLOW+"X: "+deciFormat.format(warp.getX()));
				sender.sendMessage(ChatColor.YELLOW+"Y: "+deciFormat.format(warp.getY()));
				sender.sendMessage(ChatColor.YELLOW+"Z: "+deciFormat.format(warp.getZ()));
				sender.sendMessage(ChatColor.YELLOW+"Permission node: "+ChatColor.BOLD+warp.getPermission());
				sender.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+ChatColor.STRIKETHROUGH+"======================");
				
				return 0;
			    
			}
			else {
				p.sendMessage(ChatColor.RED+"There is no subcommand '"+subcommand+"'.");
				return 0;
			}
		}
	}

}
