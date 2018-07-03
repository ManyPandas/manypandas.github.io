package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class DamageCommand extends CommandSkeleton {
	
	private String dmgheader = "["+ChatColor.DARK_RED+ChatColor.BOLD+"Damage"+ChatColor.RESET+"] ";

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		Player psender = (Player) sender;
		
		//If a player is specified
		if(args.length>1) {
			
			//If the name refers to all online players
			if(args[0].equalsIgnoreCase("all")) {
				
				double damage;
				
				try {
					damage = Double.parseDouble(args[1]);
					
				}
				catch(Exception e) {
					return 3;
				}

				for(Player others : Bukkit.getOnlinePlayers()) {
					
					if(others.getName().equalsIgnoreCase(sender.getName())) {
						continue;
					}
					others.damage(damage);
				}
				sender.sendMessage(dmgheader+ChatColor.DARK_RED+"Did "+ChatColor.RED+damage+ChatColor.DARK_RED+" damage to all online players");
				return 0;
				
			}
			//Make sure that the player specified is online.
			else if(!Util.playerOnline(args[0])) {
				return 4;
			}
			
			//All clear, ready to go.
			else {
				
				Player target = Bukkit.getPlayer(args[0]);
				double damage;
				try {
					damage = Double.parseDouble(args[1]);
				}
				catch(Exception e) {
					return 3;
				}
				
				target.damage(damage);
				psender.sendMessage(dmgheader+ChatColor.DARK_RED+"Did "+ChatColor.RED+damage+ChatColor.DARK_RED+" damage to "+ChatColor.RED+target.getName());
				return 0;
				
			}
			
			
		}
		else {
			double damage;
			try {
				damage = Double.parseDouble(args[0]);
				
			}
			catch(Exception e) {
				return 3;
			}
			psender.damage(damage);
			psender.sendMessage(dmgheader+ChatColor.DARK_RED+"Did "+ChatColor.RED+damage+ChatColor.DARK_RED+" damage to yourself.");
			return 0;
			
		}
	}

}
