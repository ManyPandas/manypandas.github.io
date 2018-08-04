package com.essentialitems.command;

import org.bukkit.command.CommandSender;

import com.essentialitems.Main;

/*
 * VERY Basic Abstract Class
 * Requires every class to provide an implementation of the run method.
 * 
 * I'm not sure why I have these extra fields and methods.
 * I don't really use them (all of the info is already found in the CmdList)
 */
public abstract class CommandSkeleton {
	
	
	
	private String name;
	
	public abstract int run(String[] args, Main mainClass, CommandSender sender);
	
	public String getName() {
		return this.name;
	}

}
