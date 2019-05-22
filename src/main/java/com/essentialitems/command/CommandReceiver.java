package com.essentialitems.command;

import java.util.logging.Level;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.essentialitems.Main;

/*
 * This is the CommandReceiver.  Its job is to receive commands as they come in, find out who and what sent it,
 * and communicate to the sender if there is a problem. 
 * It acts as the CommandExecutor for every command.
 */
public final class CommandReceiver implements org.bukkit.command.CommandExecutor  {
	
	protected static final String errHeader = "["+ChatColor.RED+ChatColor.BOLD+"Error"+ChatColor.RESET+"] ";
	protected static final String permHeader = "["+ChatColor.BLUE+ChatColor.BOLD+"Permissions"+ChatColor.RESET+"] ";
	
	private static final String use= ChatColor.DARK_GREEN+"Usage: ";
	private static final String esheader = "["+ChatColor.GREEN+ChatColor.BOLD+"EssentialItems"+ChatColor.RESET+"] ";
	
	
	
	private CommandInterpreter interpreter;
	private Main mainclass;
	public CommandReceiver(Main mainclass, CommandInterpreter interpreter) {
		this.interpreter = interpreter;
		this.mainclass = mainclass;
	}
	
	/*
	 * Error Codes:
	 * 0: All is well
	 * 1: Error with command sender
	 * 2: Error with the amount of arguments - give the sender the usage
	 * 3: Invalid arguments - give the sender the usage
	 * 4: Error with player specified.
	 * 5: No Permission
	 * Error 6 was deemed not necessary
	 * 7: Custom Command Error Message - Give them the usage
	 * 
	 * 
	 * 
	 * There are also custom error codes for the kit system.
	 * 
	 * 20: Kit Doesn't Exist
	 * 21: No Kit Permission
	 * 22: Not enough arguments to run the subcommand
	 * 23: Cooldown is in effect (say nothing, this message is handled by the KitCommand system as the CommandReceiver doesn't know what is happening behind the scenes.
	 * All it knows is that it returned a certain error code.)
	 * 24: Kit already exists.
	 * 25: Error with specified player (gets the name from args[2] instead of args[0])
	 * 
	 * 
	 * And one error code for the warp system.
	 * 30: Not enough arguments for the subcommand.
	 * 
	 * 
	 * 
	 * 
	 */

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) { 
		
		//Immediate response to /essentialitems (none of that command processing bull-crap)
		if(cmd.getName().equalsIgnoreCase("essentialitems")) {
			sender.sendMessage(esheader+ChatColor.GREEN+ChatColor.BOLD+"Thank you for using Essentialitems!");
			sender.sendMessage("");
			sender.sendMessage(esheader+ChatColor.GREEN+ChatColor.BOLD+"You are running plugin version "+ChatColor.RESET+ChatColor.GREEN+mainclass.getDescription().getVersion());
			sender.sendMessage(esheader+ChatColor.GREEN+ChatColor.BOLD+"Project Page:\n"+ChatColor.RESET+ChatColor.GREEN+"https://dev.bukkit.org/projects/essentialitems");
			sender.sendMessage(esheader+ChatColor.GREEN+ChatColor.BOLD+"GitHub:\n"+ChatColor.RESET+ChatColor.GREEN+"https://github.com/ManyPandas/manypandas.github.io");
			
			return true;
		}
		
		//Just temporarily initialized. It will be changed to a valid CmdList instance and then back into null, after the error code (if any) is processed.
		CmdList finalCmd = null;
		int errcode =6;
		
		try {
			for(CmdList cm : CmdList.values()) { {
				if(cmd.getName().equalsIgnoreCase(cm.getName())) {
					finalCmd =cm;
					//Send off the detected command, its CommandSender, and the Arguments provided.
					errcode = this.sendToInterpreter(cm, args, sender);
					//We have our command, break the loop!
					break;
				}
				else {
					//No command yet!
					errcode = 6;
					continue;
					}
				}
				
			}
			
			if(errcode == 30) {
				sender.sendMessage(ChatColor.RED+"There were not enough arguments to run the sub-command.  Please check /warp help for more information.");
			    return true;
			}
			
			//We know for a FACT that only the kit command will return this error code, so we can just hard-code the message.
			if(errcode == 20) {
				sender.sendMessage(KitCommand.kitHeader+ChatColor.RED+"The kit '"+args[0]+"' does not exist.");
				return true;
			}
			if(errcode == 21) {
				sender.sendMessage(permHeader+ChatColor.BLUE+" You do not have the required permissions to equip this kit.");
				return true;
				
			}
			if(errcode == 22) {
				sender.sendMessage(KitCommand.kitHeader+ChatColor.RED+"There were not enough arguments to perform the subcommand.  Please check /kit help for more information.");
				return true;
			}
			if(errcode == 23) {
				//We can't do much.  We aren't supposed to say anything.
				return true;
			}
			if(errcode == 24) {
				sender.sendMessage(KitCommand.kitHeader+ChatColor.RED+"The kit '"+args[0]+"' already exists.");
				return true;
			}
			if(errcode == 25) {
				sender.sendMessage(errHeader+ChatColor.DARK_RED+"Could not find player by the name of '"+ChatColor.RED+args[2]+ChatColor.DARK_RED+"'.");
				return true;
			}
			
			
			if(errcode == 7) {
				sender.sendMessage(use+finalCmd.getUsage());
				finalCmd=null;
				return true;
			}
			if(errcode == 6) {
				finalCmd=null;
				return true;
			}
			if(errcode == 5) {
				sender.sendMessage(permHeader+ChatColor.BLUE+" You do not have the required permissions to perform this command.");
				finalCmd=null;
				return true;
			}
			if(errcode == 4) { 
				sender.sendMessage(errHeader+ChatColor.DARK_RED+"Could not find player by the name of '"+ChatColor.RED+args[0]+ChatColor.DARK_RED+"'.");
				finalCmd=null;
				return true;
				
			}
			if(errcode == 3) {
				sender.sendMessage(errHeader+ChatColor.DARK_RED+ChatColor.BOLD+"Invalid arguments.  Please check your usage.");
				sender.sendMessage(use+finalCmd.getUsage());
				finalCmd=null;
				return true;
			}
			if(errcode == 2) {
				sender.sendMessage(errHeader+ChatColor.RED+"There were not enough arguments to perform this command. Please check your usage.");
				sender.sendMessage(use+finalCmd.getUsage());
				finalCmd=null;
				return true;
			}
			if(errcode == 1) {
				sender.sendMessage(errHeader+ChatColor.RED+"Players only.");
				finalCmd= null;
				return true;
			}
			if(errcode == 0) {
				//Clean up after the command. (reset variables for the next one)
				finalCmd = null;
				errcode = 6;
				return true;
				
			}
			
			
			mainclass.getLogger().warning("An error occured while executing command '"+finalCmd.getName()+"'.  Invalid Error code "+ errcode);
			mainclass.getLogger().warning("Please consult the plugin developer about this issue.");
			return true;
			
			
			
		
			
		}
		catch(Exception e) {
			mainclass.getLogger().log(Level.WARNING, "An unexpected error occured while executing EssentialItems command '"+finalCmd.getName()+"'");
			mainclass.getLogger().log(Level.WARNING, "Stack trace:");
			mainclass.getLogger().log(Level.WARNING, ExceptionUtils.getStackTrace(e));
			mainclass.getLogger().log(Level.WARNING, "Please help the EssentialItems project by submitting a bug report at:");
			mainclass.getLogger().log(Level.WARNING, "https://github.com/ManyPandas/manypandas.github.io");
			
			sender.sendMessage(ChatColor.RED+"An unexpected error occured while executing EssentialItems command '"+finalCmd.getName()+"'");
			sender.sendMessage(ChatColor.RED+"Please notify the server administrator about this issue.");
			sender.sendMessage(ChatColor.RED+"A stack trace may be obtained in the server logs.");
			
			

			
		}
		finally {
			finalCmd = null;
			errcode = 6;
		}
		return true;
	}
		
	private int sendToInterpreter(CmdList cmd, String[] args, CommandSender sender) {
		return this.interpreter.interpret(sender, cmd, args.length, args);
		
	}
	
	

}
