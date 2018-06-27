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
		Player p = (Player) sender;
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("all")) {
				for(Player other : Bukkit.getOnlinePlayers()) {
					if(other.getName().equals(p.getName())) {
						continue;
					}
					if(other.getHealth() == other.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
						continue;
					}
					
					other.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					other.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been healed.");
					
				}
				p.sendMessage(Util.healthHeader+ChatColor.GREEN+"Healed all online players to full health.");
				return 0;
				
			}
			else {
				if(!Util.playerOnline(args[0])) {
					return 4;
				}
				else {
					Player other = Bukkit.getPlayer(args[0]);
					if(other.getHealth() == other.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
						p.sendMessage(Util.healthHeader+ChatColor.BLUE+args[0]+ChatColor.RED+" is already at max health.");
						return 0;
					}
					other.setHealth(other.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					other.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been healed.");
					p.sendMessage(Util.healthHeader+ChatColor.GREEN+"Sucessfully healed "+ChatColor.BLUE+args[0]);
					return 0;
				}
			}
		}
		else {
			if(p.getHealth() == p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
				p.sendMessage(Util.healthHeader+ChatColor.RED+"You are already at max health.");
				return 0;
			}
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			p.sendMessage(Util.healthHeader+ChatColor.GREEN+"You have been healed.");
			return 0;
			
		}
		
	}

}
