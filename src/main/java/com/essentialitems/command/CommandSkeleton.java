package com.essentialitems.command;

import org.bukkit.command.CommandSender;

import com.essentialitems.Main;

public abstract class CommandSkeleton {
	
	private String name;
	
	public abstract int run(String[] args, Main mainClass, CommandSender sender);
	
	public String getName() {
		return this.name;
	}

}
