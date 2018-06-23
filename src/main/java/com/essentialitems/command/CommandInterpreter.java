package com.essentialitems.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * This is the CommandInterpreter Class
 * Its job is to check what command was sent, who sent it, and if the amount of arguments is appropriate
 * It also checks for permissions.
 * If all of the checks pass, it hands the command off to the CommandExecutor.
 */
public final class CommandInterpreter {
	public final com.essentialitems.Main main;
	private CommandExecutor executor;
	
	public CommandInterpreter(com.essentialitems.Main main) {
		/*
		 * Only the Main Plug-in class is allowed to instantiate this.
		 * We also need an instance of the main class for certain functions.
		 */
		this.main=main;
		this.executor = new CommandExecutor(this, main);
		
		
	}
	
	
	
	/*
	 * There are several possible return values:
	 * 0: All is well
	 * 1: Error with command sender
	 * 2: Error with the amount of arguments - give the sender the usage
	 * 3: Invalid arguments - give the sender the usage
	 * 4: Error with player specified.
	 * 5: No Permission
	 * 7: Custom Command Error Message
	 * Code 6 does not apply to us.  Nothing will be received if there is a code 6.
	 */
	public int interpret(CommandSender sender, CmdList cmd, int argslength, String[] args) {
		if(!this.checkPermission(cmd,sender)) {
			return 5;
		}
		else if(!this.checkCommandSender(cmd, sender)) {
			return 1;
		}
		else if(argslength < cmd.getMinArgs()) {
			return 2;
		}
		else {
			return this.executor.handOff(args, cmd, sender);
		}
		
		
		
	}
	
	private boolean checkCommandSender(CmdList cmd, CommandSender sender) {
		if(sender instanceof Player) {
			return true;
		}
		else {
			return cmd.getAllowConsole();
		}
			
		
	}
	private boolean checkPermission(CmdList cmd, CommandSender sender) {
		if(!(sender.hasPermission(cmd.getPermission()))) {
			return false;
		}
		return true;
	}
	

}
