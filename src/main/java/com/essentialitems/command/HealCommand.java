package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class HealCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		if(args.length<=0) {
			
			if(!(sender instanceof Player)) {
				return 1;
			}
			Player p = (Player) sender;
			
			if(Util.hasHealCooldown(p, mainClass)) {
				int cooldown = mainClass.getConfig().getInt(Util.configKey.healCooldown.toString());
				long nextallowedmillis = (cooldown*1000)+(long)Util.healCooldowns.get(p);
				long timeLeftMillis = nextallowedmillis-System.currentTimeMillis();
				int timeLeftSeconds = (int)timeLeftMillis/1000;
				
				
				sender.sendMessage(Util.healthHeader+ChatColor.RED+"You have "+timeLeftSeconds+" second"+(timeLeftSeconds>1 ? "s" : "")+" before you may use /heal again.");
				return 0;
			}
			
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			p.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been healed.");
			Util.healCooldowns.put(p, System.currentTimeMillis());
			return 0;
		
		}
		else {
			if(!Util.checkPermission("essentialitems.heal.other",sender)) {
				return 5;
			}
			
			if(args[0].equalsIgnoreCase("all")) {
				for(Player p :Bukkit.getOnlinePlayers()) {
					if(p.getHealth()==p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
						continue;
					}
					
					p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					p.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been healed.");
				}
				sender.sendMessage(Util.healthHeader+ChatColor.GREEN+"Successfully healed all online players.");
				return 0;
			}
			else {
				if(!Util.playerOnline(args[0])) {
					return 4;
				}
				else {
					Player p = Bukkit.getPlayer(args[0]);
					p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					p.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been healed.");
					return 0;
					
				}
			}	
		}
		
	}
	
}
