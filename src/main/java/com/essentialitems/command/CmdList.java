package com.essentialitems.command;

import org.bukkit.permissions.Permission;
import com.essentialitems.Util;

public enum CmdList {
	
	/*
	 * STEPS TO ADDING A NEW COMMAND:
	 * 
	 * 1. Register in the CmdList
	 * 2. Register in plugin.yml
	 * 3. Register in the Main class
	 * 4. Create Command Class
	 * 4a. Make Sure the new class extends CommandSkeleton
	 * 5. Write all necessary code for the command.  If multiple classes are needed (for instance, the MOTD command) keep the.., 
	 *    ...command's point of entry in the main command package, then create a new package for the necessary additional classes.
	 *    
	 * If not all of the steps are followed, the command will not work.
	 * 
	 */
	
	
	BANPLAYER("ban", "/ban <Player> <Reason>",2,true,Util.permission.canBan.get(), new BanCommand()),
	KICKPLAYER("kick","/kick <Player> <Reason>",2,true,Util.permission.canKick.get(), new KickCommand()),
	UNBANPLAYER("unban","/unban <Player>",1,true,Util.permission.canUnban.get(), new UnbanCommand()),
	TEMPBAN("tempban","/tempban <Player> <Time in Minutes> <Reason>",3,true,Util.permission.canTempBan.get(), new TempBanCommand()),
	BROADCAST("broadcast","/broadcast <Message>",1,true,Util.permission.canBroadcast.get(), new BroadcastCommand()),
	MOTD("motd","/motd",0,false,Util.permission.canMotd.get(), new MotdCommand()),
	WARN("warn", "/warn <Player> <Reason>",2,true,Util.permission.canWarn.get(),new WarnCommand()),
	MSG("msg","/msg <Player> <Message>",2,true,Util.permission.canMsg.get(),new MsgCommand()),
	MUTE("mute","/mute <Player> <Reason>",2,true,Util.permission.canMute.get(),new MuteCommand()),
	TEMPMUTE("tempmute","/tempmute <Player> <Time in Minutes> <Reason>",3,true,Util.permission.canTempMute.get(),new TempMuteCommand()),
	UNMUTE("unmute","/unmute <Player>", 1,true,Util.permission.canUnmute.get(), new UnmuteCommand()),
	VANISH("vanish","/vanish",0,false,Util.permission.canVanish.get(),new VanishCommand()),
	LOCKDOWN("lockdown","/lockdown [Reason]",0,true,Util.permission.canStartLockdown.get(),new LockdownCommand()),
	GAMEMODE("gamemode","/gamemode <Mode> [Player]",1,false,Util.permission.canGameMode.get(),new GamemodeCommand()),
	FEED("feed", "/feed [Player]",0,false,Util.permission.canFeed.get(),new FeedCommand()),
	HEAL("heal", "/heal [Player]",0,false,Util.permission.canHeal.get(),new HealCommand()),
	WORKBENCH("workbench","/workbench",0,false,Util.permission.canWorkbench.get(),new WorkbenchCommand()),
	CHATSLOW("chatslow","/chatslow [Cooldown in Seconds]",0,true,Util.permission.canChatStop.get(), new ChatSlowCommand()),
	INVSEE("invsee","/invsee <Player>",1,false,Util.permission.canInvsee.get(), new InvseeCommand()),
	FLY("fly","/fly",0,false,Util.permission.canFly.get(), new FlyCommand())
	
	
	
	
	
	;
	
	
	
	
	
	
	
	
	private String name;
	private String usage;
	private int minArgs;
	private boolean allowConsole;
	private Permission permission;
	private CommandSkeleton exec;
	
	CmdList(String name, String usage, int minArgs, boolean allowConsole, Permission permission, CommandSkeleton exec) {
		this.name = name;
		this.usage = usage;
		this.minArgs = minArgs;
		this.allowConsole = allowConsole;
		this.permission = permission;
		this.exec = exec;
		
		
	}
	public Permission getPermission() {
		return this.permission;
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	public boolean getAllowConsole() {
		return this.allowConsole;
	}
	public int getMinArgs() {
		return this.minArgs;
	}
	public String getName() {
		return this.name;
		
	}
	public String getUsage() {
		return this.usage;
	}
	public CommandSkeleton getExec() {
		return this.exec;
	}

}

