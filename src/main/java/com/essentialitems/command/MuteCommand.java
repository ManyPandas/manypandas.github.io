package com.essentialitems.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class MuteCommand extends CommandSkeleton {

	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		if(!Util.playerOnline(args[0])) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
			if(!p.hasPlayedBefore()) {
				return 4;
			}
			else {
				String reason = Util.buildMessage(args, 1);
				UUID uuid = p.getUniqueId();
				String name = p.getName();
				Util.mute(uuid.toString(), name, reason, mainClass);
				return 0;
			}
		}
		else {
			Player p = Bukkit.getPlayer(args[0]);
			String reason = Util.buildMessage(args, 1);
			UUID uuid = p.getUniqueId();
			String name = p.getName();
			Util.mute(uuid.toString(), name, reason, mainClass);
			return 0;
			
		}
	}

}
