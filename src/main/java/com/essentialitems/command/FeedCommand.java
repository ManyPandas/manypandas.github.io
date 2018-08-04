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
		
		if(args.length<=0) {
			
			if(!(sender instanceof Player)) {
				return 1;
			}
			Player p = (Player) sender;
			
			if(Util.hasFeedCooldown(p, mainClass)) {
				int cooldown = mainClass.getConfig().getInt(Util.configKey.feedCooldown.toString());
				long nextallowedmillis = (cooldown*1000)+(long)Util.feedCooldowns.get(p);
				long timeLeftMillis = nextallowedmillis-System.currentTimeMillis();
				int timeLeftSeconds = (int)timeLeftMillis/1000;
				
				
				sender.sendMessage(Util.healthHeader+ChatColor.RED+"You have "+timeLeftSeconds+" second"+(timeLeftSeconds>1 ? "s" : "")+" before you may use /feed again.");
				return 0;
			}
			if(p.getFoodLevel()==30) {
				p.sendMessage(Util.healthHeader+ChatColor.RED+"You are already at max food level.");
				return 0;
			}
			
			p.setFoodLevel(30);
			p.setSaturation(10);
			p.setExhaustion(0F);
			p.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been fed.");
			Util.feedCooldowns.put(p, System.currentTimeMillis());
			
			return 0;	
		}
		
		else {
			if(!Util.checkPermission("essentialitems.feed.other",sender)) {
				return 5;
			}
			else if(args[0].equalsIgnoreCase("all")) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					
					if(p.getFoodLevel() ==30) {
						continue;
					}
					p.setFoodLevel(30);
					p.setSaturation(10);
					p.setExhaustion(0F);
					p.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been fed.");
					
				}
				sender.sendMessage(Util.healthHeader+ChatColor.GREEN+"Successfully fed all online players.");
				return 0;
			}
			else {
				if(!Util.playerOnline(args[0])) {
					return 4;
				}
				Player p = Bukkit.getPlayer(args[0]);
				if(p.getFoodLevel() ==30) {
					sender.sendMessage(Util.healthHeader+ChatColor.RED+args[0]+" is already at max food level.");
				}
				p.setFoodLevel(30);
				p.setSaturation(10);
				p.setExhaustion(0F);
				p.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been fed.");
				sender.sendMessage(Util.healthHeader+ChatColor.GREEN+"Successfully fed "+args[0]);
				return 0;
			}
			
		}
		
	}
			
			
		

}
