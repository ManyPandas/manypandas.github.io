package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class ChatSlowCommand extends CommandSkeleton{
	public static final String chatHeader ="["+ChatColor.GRAY+ChatColor.BOLD+"Chat Slow"+ChatColor.RESET+"] ";

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(mainClass.getConfig().getBoolean(Util.configKey.chatslow.toString())) {
			
			//The Chat slow is enabled, we need to disable it.
			mainClass.getConfig().set(Util.configKey.chatslow.toString(), false);
			mainClass.saveConfig();
			Bukkit.broadcastMessage(chatHeader+ChatColor.DARK_AQUA+"The cooldown on player chat has been lifted.");
			return 0;
		}
		else {
			int cooldown;
			try {
				cooldown = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e) {
				return  3;
				
			}
			catch(ArrayIndexOutOfBoundsException e) {
				return 2;
			}
			
			mainClass.getConfig().set(Util.configKey.chatSlowCooldown.toString(), cooldown);
			mainClass.getConfig().set(Util.configKey.chatslow.toString(), true);
			mainClass.saveConfig();
			Bukkit.broadcastMessage(chatHeader+ChatColor.DARK_AQUA+"A cooldown of "+ChatColor.GRAY+cooldown+ChatColor.DARK_AQUA+" second"+(cooldown > 1 ? "s " : " ")+"has been placed on player chat.");
			return 0;
			
			
		}
	}

}
