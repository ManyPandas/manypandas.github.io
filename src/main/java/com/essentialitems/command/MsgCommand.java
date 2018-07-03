package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class MsgCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		if(!Util.playerOnline(args[0])) {
			return 4; 
			
		}
		else {
			Player p = (Player) sender;
			Player target = Bukkit.getPlayer(args[0]);
			if(target.getName().equals(p.getName())) {
				p.sendMessage(ChatColor.DARK_GREEN+""+ChatColor.BOLD+"You can't message yourself!");
				return 0;
			}
			String message = Util.buildMessage(args, 1);
			target.sendMessage(ChatColor.DARK_GREEN+""+ChatColor.BOLD+"[Private Message]§r§a "+p.getName()+" --> You");
			target.sendMessage(" ");
			target.sendMessage(ChatColor.DARK_GREEN+""+ChatColor.BOLD+message);
			p.sendMessage(ChatColor.DARK_GREEN+""+ChatColor.BOLD+"[Private Message]§r§a You --> "+ target.getName()+" ");
			p.sendMessage(" ");
			p.sendMessage(ChatColor.DARK_GREEN+""+ChatColor.BOLD+message);
			return 0;
			
		}
	}

}
