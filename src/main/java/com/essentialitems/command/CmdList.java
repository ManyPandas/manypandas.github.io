package com.essentialitems.command;

import org.bukkit.permissions.Permission;
import com.essentialitems.Util;

public enum CmdList {
	
	/*
	 * STEPS TO ADDING A NEW COMMAND:
	 * 
	 * 1. Register in the CmdList
	 * 2. Register in plugin.yml
	 * 3. Create Command Class
	 * 3a. Make sure the new class extends CommandSkeleton
	 * 4. Write all necessary code for the command.  If multiple classes are needed (for instance the Kit command) keep the...
	 *    ...command's point of entry in the main command package, then create a new package for the necessary additional classes.
	 *    
	 * If not all of the steps are followed, the command will not work.
	 * 
	 */
	
	
	ACTIONBAR("actionbar","/actionbar <Player> <Message>",2,true,new Permission("essentialitems.actionbar"),new ActionBarCommand()),
	BANPLAYER("ban", "/ban <Player> <Reason>",2,true,Util.permission.canBan.get(), new BanCommand()),
	BROADCAST("broadcast","/broadcast <Message>",1,true,Util.permission.canBroadcast.get(), new BroadcastCommand()),
	CHATSLOW("chatslow","/chatslow <Cooldown in Seconds>",0,true,Util.permission.canChatStop.get(), new ChatSlowCommand()),
	DAMAGE("damage","/damage [Player] <Damage>",1,false,Util.permission.canDamage.get(), new DamageCommand()),
	ECHEST("echest","/echest [Player]",0,false,Util.permission.canEchest.get(),new EchestCommand()),
	FEED("feed", "/feed [Player]",0,true,Util.permission.canFeed.get(),new FeedCommand()),
	FLY("fly","/fly",0,false,Util.permission.canFly.get(),new FlyCommand()),
	GAMEMODE("gamemode","/gamemode <Mode> [Player]",1,false,Util.permission.canGameMode.get(),new GamemodeCommand()),
	GETPOS("getpos","/getpos [Player]",0,true,Util.permission.canGetPos.get(),new GetposCommand()),
	GODMODE("godmode", "/godmode",0,false,Util.permission.canGodMode.get(),new GodCommand()),
	HEAL("heal", "/heal [Player]",0,true,Util.permission.canHeal.get(),new HealCommand()),
	INVSEE("invsee","/invsee <Player>",1,false,Util.permission.canInvsee.get(), new InvseeCommand()),
	KICKPLAYER("kick","/kick <Player> <Reason>",2,true,Util.permission.canKick.get(), new KickCommand()),
	KIT("kit","/kit <Kit> [SubCommand]",0,false,Util.permission.canKit.get(),new KitCommand()),
	LOCKDOWN("lockdown","/lockdown <Reason>",0,true,Util.permission.canStartLockdown.get(),new LockdownCommand()),
	MOTD("motd","/motd",0,false,Util.permission.canMotd.get(), new MotdCommand()),
	MSG("msg","/msg <Player> <Message>",2,true,Util.permission.canMsg.get(),new MsgCommand()),
	MUTE("mute","/mute <Player> <Reason>",2,true,Util.permission.canMute.get(),new MuteCommand()),
	MUTEMIGRATE("mutemigrate","/mutemigrate",0,true,Util.permission.canMigrate.get(),new MigrateCommand()),
	TEMPBAN("tempban","/tempban <Player> <Time in Minutes> <Reason>",3,true,Util.permission.canTempBan.get(), new TempBanCommand()),
	TEMPMUTE("tempmute","/tempmute <Player> <Time in Minutes> <Reason>",3,true,Util.permission.canTempMute.get(),new TempMuteCommand()),
	TRASH("trash","/trash",0,false,Util.permission.canTrash.get(),new TrashCommand()),
	UNBANPLAYER("unban","/unban <Player>",1,true,Util.permission.canUnban.get(), new UnbanCommand()),
	UNMUTE("unmute","/unmute <Player>", 1,true,Util.permission.canUnmute.get(), new UnmuteCommand()),
	VANISH("vanish","/vanish",0,false,Util.permission.canVanish.get(),new VanishCommand()),
	WARN("warn", "/warn <Player> <Reason>",2,true,Util.permission.canWarn.get(),new WarnCommand()),
	WARP("warp", "/warp <Warp Name>",1,false,new Permission("essentialitems.warp"), new WarpCommand()),
	WHOIS("whois","/whois <Player>",1,true,Util.permission.canWhois.get(),new WhoisCommand()),
	WORKBENCH("workbench","/workbench",0,false,Util.permission.canWorkbench.get(),new WorkbenchCommand()),
	
	
	
	
	
	
	
	
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

