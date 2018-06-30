package com.essentialitems.command;

import org.bukkit.command.CommandSender;

import com.essentialitems.Main;

public final class CommandExecutor {
	/* This class is the hand off between the Command parsing classes and the Command Classes themselves.
	 * It can be only instantiated by the CommandInterpreter
	 * This is the only class that is allowed to make method calls to command classes
	 * The hand-off will occur once the permissions and argument length have been sorted.  
	 * The Command to be executed has been long known at this point.
	 */
	
	private Main mainClass;
	
	public CommandExecutor (CommandInterpreter interpreter, Main mainClass) {
		//Only the CommandInterpreter may instantiate this.
		//We also need an instance of the Main class for the commands to use.
		this.mainClass = mainClass;
	}
	
	public int handOff(String[] args, CmdList cmd, CommandSender sender) {
		return cmd.getExec().run(args, mainClass, sender);
		
		
		
	}

}