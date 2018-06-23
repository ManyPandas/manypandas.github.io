package com.essentialitems.command;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;

public final class VanishCommand extends CommandSkeleton {
	
	public static final ArrayList<Player> vanished = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		Player p = (Player) sender;
		if(!vanished.contains(p)) {
			for(Player s : Bukkit.getOnlinePlayers()) {
				s.hidePlayer(p);
			}
			p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2000, 1);
			p.sendMessage("["+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"Vanish"+ChatColor.RESET+"]"+ChatColor.AQUA+" You have disappeared from the server.");
			p.sendMessage("["+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"Vanish"+ChatColor.RESET+"]"+ChatColor.AQUA+" Type /vanish to re-appear.");
			vanished.add(p);
			return 0;
			
		}
		else {
			for(Player s : Bukkit.getOnlinePlayers()) {
				s.showPlayer(p);
				
			}
			p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2000, 1);
			p.sendMessage("["+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"Vanish"+ChatColor.RESET+"]"+ChatColor.AQUA+" You have re-appeared to the server.");
			vanished.remove(p);
			return 0;
			
		}
		
	}

}
