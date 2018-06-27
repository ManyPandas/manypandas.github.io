package com.essentialitems.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;
import com.essentialitems.command.invsee.InvSeeGui;

public class InvseeCommand extends CommandSkeleton {
	
	public static HashMap<Player, Player> invseeing = new HashMap<Player, Player>();

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(!Util.playerOnline(args[0])) {
			return 4;
		}
		else {
			Player p = Bukkit.getPlayer(args[0]);
			Player psender = (Player) sender;
			
			invseeing.put(psender, p);
			InvSeeGui.openGui(psender, p);
			psender.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"Now viewing "+ p.getName()+"'s inventory.");
			return 0;
			
			
			
			
			
			
		}
		
	}
	
	

}
