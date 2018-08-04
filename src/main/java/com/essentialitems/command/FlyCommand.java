package com.essentialitems.command;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;

public class FlyCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		Player p = (Player) sender;
		
		
		if(p.isFlying()) {
			if(p.getGameMode().equals(GameMode.SURVIVAL)||p.getGameMode().equals(GameMode.ADVENTURE)) {
				p.setFlying(false);
				p.setAllowFlight(false);
				p.sendMessage(ChatColor.LIGHT_PURPLE+"You are no longer flying.");
				
				return 0;
				
			}
			else {
				p.setFlying(false);
				p.sendMessage(ChatColor.LIGHT_PURPLE+"You are no longer flying.");
				return 0;
			}
		}
		
		double y = p.getLocation().getY();
		y++;
		
		p.teleport(new Location(p.getWorld(), p.getLocation().getX(), y, p.getLocation().getZ()));
		p.setAllowFlight(true);
		p.setFlying(true);
		p.sendMessage(ChatColor.LIGHT_PURPLE+"You are now flying.");
		
		return 0;
	}

}
