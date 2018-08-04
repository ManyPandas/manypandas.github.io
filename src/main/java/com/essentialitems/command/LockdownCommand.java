package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class LockdownCommand extends CommandSkeleton {
	public static final String lockdownHeader = "["+ChatColor.RED+ChatColor.BOLD+"Lockdown"+ChatColor.RESET+"] ";

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		if(mainClass.getConfig().getBoolean(Util.configKey.lockdown.toString())) {
			mainClass.getConfig().set(Util.configKey.lockdown.toString(), false);
			mainClass.saveConfig();
			Bukkit.broadcastMessage(lockdownHeader+ChatColor.RED+ChatColor.BOLD+"Server lockdown has been disabled by "+ChatColor.RESET+ChatColor.BLUE+ChatColor.BOLD+sender.getName());
			return 0;
		}
		if(args.length == 0) {
			return 2;
		}
		String reason = Util.buildMessage(args, 0);
		
		if(mainClass.getConfig().getBoolean(Util.configKey.kickUnauthorizedOnLockdown.toString())) {
			for(Player other : Bukkit.getOnlinePlayers()) {
				if(Util.checkPermission(Util.permission.bypassLockdown.get(),other)) {
					continue;
				}
				other.kickPlayer(
				Util.bumper+ChatColor.RED+ChatColor.BOLD+"Sorry, but the server is locking down to authorized players only.\n\n"
				+ "Unfortunately, you do not have sufficient permissions to remain connected to the server.\n\n"
				+ ChatColor.RESET+ChatColor.DARK_GREEN+"Reason: "
				+ reason
				+ Util.bumper
				);
			}
			Bukkit.broadcastMessage(lockdownHeader+ChatColor.RED+ChatColor.BOLD+"Server lockdown has been enabled by "+ChatColor.BLUE+ChatColor.BOLD+sender.getName());
			Bukkit.broadcastMessage(lockdownHeader+ChatColor.RED+ChatColor.BOLD+"All unauthorized players have been kicked.");
			Bukkit.broadcastMessage(lockdownHeader+ChatColor.DARK_GREEN+"Reason: "+ reason);
			return 0;
		}
		
		
		Bukkit.broadcastMessage(lockdownHeader+ChatColor.RED+ChatColor.BOLD+"Server lockdown has been enabled by "+ChatColor.BLUE+ChatColor.BOLD+sender.getName());
		Bukkit.broadcastMessage(lockdownHeader+ChatColor.DARK_GREEN+"Reason: "+ reason);
		mainClass.getConfig().set(Util.configKey.lockdown.toString(), true);
		mainClass.getConfig().set(Util.configKey.lockdownReason.toString(), reason);
		mainClass.saveConfig();
		return 0;
	}

}
