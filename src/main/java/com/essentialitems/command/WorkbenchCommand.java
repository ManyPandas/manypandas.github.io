package com.essentialitems.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;

public class WorkbenchCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		Player p = (Player) sender;
		
		p.openWorkbench(null, true);
		
		return 0;
	}

}
