package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.essentialitems.Main;

public class TrashCommand extends CommandSkeleton {

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		// TODO Auto-generated method stub
		
		//Cast the sender to a player, we know it is safe to do so...
		Player p = (Player) sender;
		
		Inventory garbageInv = Bukkit.createInventory(null, 36,ChatColor.DARK_BLUE+""+ChatColor.BOLD+"Trash");
		
		p.closeInventory();
		p.openInventory(garbageInv);
		return 0;
	}

}
