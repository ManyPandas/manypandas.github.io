package com.essentialitems.command;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class MigrateCommand extends CommandSkeleton {

	@SuppressWarnings("rawtypes")
	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(!mainClass.getConfig().isConfigurationSection(Util.configKey.MutedPlayers.toString())) {
			sender.sendMessage(ChatColor.GREEN+"Successfully migrated all legacy mutes!");
			return 0;
		}
		else {
			//We need to iterate over the Map that the (getValues) method gives us
			Iterator it = mainClass.getConfig().getConfigurationSection("MutedPlayers").getValues(true).entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pair =(Map.Entry)it.next();
				
				//Extract all of the information we need.
				UUID uniqueId = UUID.fromString((String)pair.getKey());
				Player p = Bukkit.getPlayer(uniqueId);
				String name;
				try {
					name = p.getName();
				}
				catch(NullPointerException e) {
					//That player doesn't exist
					//This should work...
					OfflinePlayer p0 = Bukkit.getOfflinePlayer(uniqueId);
					name = p0.getName();
				}
				
				
				String reason = (String) pair.getValue();
				
				Util.mute(uniqueId.toString(), name, reason, mainClass);
				mainClass.getConfig().getConfigurationSection("MutedPlayers").set(uniqueId.toString(), null);
				it.remove(); //prevent ConcurrentModificationException...
			}
			//Now that we have removed all of the old mutes, and written them into the new system, we can remove the old configuration section entirely.
			mainClass.getConfig().set("MutedPlayers", null);
			mainClass.saveConfig();
			sender.sendMessage(ChatColor.GREEN+"Successfully migrated all legacy mutes!");
			return 0;
		}
	}

}
