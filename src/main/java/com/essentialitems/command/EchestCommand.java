package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class EchestCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		Player p = (Player) sender;
		if(args.length>0) {
			//They have specified a player, we need to make sure that they have a view other permission.
			if(!sender.hasPermission("essentialitems.echest.other")) {
				//No permission
				return 5;
			}
			else {
				if(!Util.playerOnline(args[0])) {
					return 4;
					
				}
				Player p1 = Bukkit.getPlayer(args[0]);
				p.openInventory(p1.getEnderChest());
				return 0;
				
			}
		}
		p.openInventory(p.getEnderChest());
		return 0;
	}

}
