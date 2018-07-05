package com.essentialitems;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;


/*
 * TODO:
 * Create BukkitScheduler methods to allow for thread-safe access to certain Bukkit API functions.
 * Currently some of my Async events call these methods directly.
 * This isn't thread-safe and will be changed.
 */

public final class Util {
	
	public static final String bumper = StringUtils.repeat("\n", 30);
	public static final String punishHeader = "["+ChatColor.RED+ChatColor.BOLD+"Punish"+ChatColor.RESET+"] ";
	public static final String healthHeader ="["+ChatColor.DARK_PURPLE+ChatColor.BOLD+"Health"+ChatColor.RESET+"] ";
	
	//No instances
	private Util() {}
	
	
	
	public static String buildMessage(String[] material, int start) {
		StringBuilder builder = new StringBuilder();
		for(int i=start; i<material.length; i++) {
			builder.append(material[i]);
			builder.append(" ");
		}
		return builder.toString().trim();
		
	}
	public static void permBan(String targetName, String reason,CommandSender sender) {
		
		String bannedMessage = bumper + "§4§lSorry, you have been banned from this server.\n\n"+ChatColor.RESET+ "§1§lTotal Ban duration: Permanent\n\n" +ChatColor.RESET+"§2 Reason: " + reason+bumper;
		Bukkit.getBanList(BanList.Type.NAME).addBan(targetName, bannedMessage, null, null);
		Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+targetName+ChatColor.RESET+ChatColor.RED+" has been permanently banned. ");
		Bukkit.broadcastMessage(punishHeader+"§2Reason: "+reason);
	}
	
	public static void tempBan(String targetName, String reason, int time, CommandSender sender) {
	    Date timeLeft = new Date(System.currentTimeMillis()+ time*1000*60);
	    String bannedMessage;
	    if(time<60) {
	    	bannedMessage =  bumper+"§4§lSorry, you have been banned from this server.\n\n"+ChatColor.RESET+"§1§l Total Ban Duration: "+ time+" minute(s). \n\n"+ChatColor.RESET+"§2 Reason:  "+reason+bumper;
	    	
	    }
	    else {
	    	bannedMessage =  bumper+"§4§lSorry, you have been banned from this server.\n\n"+ChatColor.RESET+"§1§l Total Ban Duration: "+ time/60+" hour(s). \n\n"+ChatColor.RESET+"§2 Reason:  "+reason+bumper;
	    }
		
		Bukkit.getBanList(BanList.Type.NAME).addBan(targetName,bannedMessage,timeLeft, null);
		if(time<60) {
			Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+targetName+ChatColor.RESET+ChatColor.RED+" has been banned for a duration of "+time+" minute(s). ");
			Bukkit.broadcastMessage(punishHeader+"§2Reason: "+reason);
			return;
			
		}
		else {
			Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+targetName+ChatColor.RESET+ChatColor.RED+" has been banned for a duration of "+time/60+" hour(s). ");
			Bukkit.broadcastMessage(punishHeader+"§2Reason: "+reason);
			return;
		}
		
		
	}
	public static void kick(Player target, String reason, CommandSender sender) {
		target.kickPlayer(bumper+"§c§lYou have been kicked from the server.\n\n" +ChatColor.RESET+"§2 Reason: "+ reason+bumper);
		Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+target.getName()+ChatColor.RESET+ChatColor.RED+" has been kicked from the server. ");
		Bukkit.broadcastMessage(punishHeader+"§2Reason: "+reason);
	}
	
	
	
	public static void mute(String uuid, String name, String reason, Main mainclass) {
		mainclass.muted.createSection(uuid);
		mainclass.muted.getConfigurationSection(uuid).set("reason", reason);
		mainclass.muted.getConfigurationSection(uuid).set("name", name);
		mainclass.muted.getConfigurationSection(uuid).set("expires", (long)-1);
		mainclass.saveMute();
		Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+name+ChatColor.RED+ " has been permanently muted.");
		Bukkit.broadcastMessage(punishHeader+"§2Reason: "+reason);
	}
	public static void mute(String uuid, String name, String reason, boolean verbose, Main mainclass) {
		if(!verbose) {
			mainclass.muted.createSection(uuid);
			mainclass.muted.getConfigurationSection(uuid).set("reason", reason);
			mainclass.muted.getConfigurationSection(uuid).set("name", name);
			mainclass.muted.getConfigurationSection(uuid).set("expires", (long)-1);
			mainclass.saveMute();
			return;
		}
		else {
			mainclass.muted.createSection(uuid);
			mainclass.muted.getConfigurationSection(uuid).set("reason", reason);
			mainclass.muted.getConfigurationSection(uuid).set("name", name);
			mainclass.muted.getConfigurationSection(uuid).set("expires", (long)-1);
			mainclass.saveMute();
			Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+name+ChatColor.RED+ " has been permanently muted.");
			Bukkit.broadcastMessage(punishHeader+"§2Reason: "+reason);
			return;
		}
		
	}
	public static void mute(String uuid, String name, String reason, long expiresmillis, Main mainclass) {
		mainclass.muted.createSection(uuid);
		mainclass.muted.getConfigurationSection(uuid).set("reason", reason);
		mainclass.muted.getConfigurationSection(uuid).set("name", name);
		mainclass.muted.getConfigurationSection(uuid).set("expires", expiresmillis);
		mainclass.saveMute();
		int time = (int)(expiresmillis/60/1000) -(int)(System.currentTimeMillis()/60/1000);
		if(time>60) {
			Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+name+ChatColor.RED+ " has been muted for "+time/60+" hour(s).");
			Bukkit.broadcastMessage(punishHeader+"§2Reason: "+reason);
			return;
		}
		Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+name+ChatColor.RED+ " has been muted for "+time+" minute(s).");
		Bukkit.broadcastMessage(punishHeader+"§2Reason: "+reason);
		return;
		
	}
	//Old unmute method
	@Deprecated
	public static void unmute(String uuid, Main mainclass) {
		mainclass.getConfig().getConfigurationSection(Util.configKey.MutedPlayers.toString()).set(uuid,null);
		mainclass.saveConfig();
		
	}
	
	
	public static boolean newUnmute(String uuid, Main mainclass) {
		if(mainclass.muted.getConfigurationSection(uuid) == null) {
			return false;
		}
		else {
			mainclass.muted.set(uuid, null);
			mainclass.saveMute();
			return true;
		}
	}
	
	public static boolean newGetMuted(String uniqueId, Main mainclass) {
		if(mainclass.muted.getConfigurationSection(uniqueId) == null) {
			return false;
		}
		return true;
	}
	
	//Checks the old mute system.
	@Deprecated
	public static boolean getMuted(String uniqueId, Main mainclass) {
		
		if(!mainclass.getConfig().isConfigurationSection("MutedPlayers")) {
			return false;
		}
		if(!mainclass.getConfig().getConfigurationSection(configKey.MutedPlayers.toString()).contains(uniqueId)) {
			return false;
		}
		return true;
		
		
	} 
	
	//Old Get Reason method
	@Deprecated
	public static String getMuteReason(String uniqueId, Main mainclass) {
		return mainclass.getConfig().getConfigurationSection(configKey.MutedPlayers.toString()).getString(uniqueId);
		
	}
	
	public static String newGetMuteReason(String uuid, Main mainclass) {
		if(mainclass.muted.getConfigurationSection(uuid) == null) {
			return null;
		}
		else {
			return mainclass.muted.getConfigurationSection(uuid).getString("reason");
			
		}
	}
	public static boolean playerOnline(String name) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getName().equals(name)) {
				return true;
			}
			else {
				continue;
			}
		}
		return false;
	}
	public static String generateBanMessage(String reason)  {
		return bumper + "§4§lSorry, you have been banned from this server.\n\n"+ChatColor.RESET+ "§1§lTotal Ban duration: Permanent\n\n" +ChatColor.RESET+"§2 Reason: " + reason+bumper;
		
	}
	public static String generateTempBanMessage(String reason, int time) {
		if(time>60) {
			return bumper+"§4§lSorry, you have been banned from this server.\n\n"+ChatColor.RESET+"§1§l Total Ban Duration: "+ time/60+" hour(s). \n\n"+ChatColor.RESET+"§2 Reason:  "+reason+bumper;
		}
		else {
			return bumper+"§4§lSorry, you have been banned from this server.\n\n"+ChatColor.RESET+"§1§l Total Ban Duration: "+ time+" minute(s). \n\n"+ChatColor.RESET+"§2 Reason:  "+reason+bumper;
			
		}
	}
	
	
	public static void displayMotd(Player p, Main mainclass) {
		String titleLine = mainclass.getConfig().getString(Util.configKey.motdTitleMessage.toString());
		String subtitleLine = mainclass.getConfig().getString(Util.configKey.motdSubtitleMessage.toString());
		
		if(mainclass.getConfig().getBoolean(Util.configKey.doMotdChatMessage.toString())) {
			p.sendMessage(mainclass.getConfig().getString(Util.configKey.motdChatMessage.toString()));
		}
		if(mainclass.getConfig().getBoolean(Util.configKey.doMotdTitle.toString())&& mainclass.getConfig().getBoolean(Util.configKey.doMotdSubtitle.toString())) {
			p.sendTitle(titleLine, subtitleLine, 20,200,20);
			//The MOTD has been displayed as configured.
			return;
			
		}
		else if(mainclass.getConfig().getBoolean(Util.configKey.doMotdTitle.toString())) {
			p.sendTitle(titleLine, " ", 20, 200, 20);
			//The MOTD has been displayed as configured.
			return;
		}
		else if(mainclass.getConfig().getBoolean(Util.configKey.doMotdSubtitle.toString())) {
			
			p.sendTitle(" ", subtitleLine, 20,200,20);
			//The MOTD has been displayed as configured.
			return;
		}
		else {
			//They don't have the Titles enabled! :( 
			return;
		}
	}
	
	
	public static enum configKey {
		
		//All used configurations keys are stored here, in an easy-to-access enumeration.
		//With the exception of the alternative configuration
		//None of its section names are predefined.  
		//UUIDs can vary wildly from one player to the next.
		doMotdSubtitle("doMotdSubtitleMessage"),
		doMotdChatMessage("doMotdChatMessage"),
		doMotdTitle("doMotdTitleMessage"),
		doOjinMessage("doPlayerJoinedMessage"),
		doOleaMessage("doPlayerLeftMessage"),
		motdTitleMessage("motdTitleMessage"),
		motdSubtitleMessage("motdSubtitleMessage"),
		motdChatMessage("motdChatMessage"),
		MutedPlayers("MutedPlayers"),
		lockdown("lockdown"),
		lockdownReason("lockdown-reason"),
		chatSlowReason("chatslow-reason"),
		chatSlowCooldown("chatslow-cooldown"),
		chatslow("chatslow"),
		kickUnauthorizedOnLockdown("kickUnauthorizedOnLockdown"),
		
		
		;
		
		
		String value;
		configKey(String s) {
			this.value=s;
			
		}
		@Override
		public String toString() {
			return value;
		}
		
	}
	public static enum permission {
		 canBan(new Permission("essentialitems.ban")),
		 canWarn(new Permission("essentialitems.warn")),
		 canBroadcast(new Permission("essentialitems.broadcast")),
		 canUnban(new Permission("essentialitems.unban")),
		 canKick(new Permission("essentialitems.kick")),
		 canMsg(new Permission("essentialitems.msg")),
		 canTempBan(new Permission("essentialitems.tempban")),
		 canMute(new Permission("essentialitems.mute")) ,
		 canUnmute(new Permission("essentialitems.unmute")),
		 canMotd(new Permission("essentialitems.motd")),
		 canVanish(new Permission("essentialitems.vanish")),
		 canStartLockdown(new Permission("essentialitems.lockdown.command")),
		 bypassLockdown(new Permission("essentialitems.lockdown.bypass")),
		 canGameMode(new Permission("essentialitems.gamemode")),
		 canFeed(new Permission("essentialitems.feed")),
		 canWorkbench(new Permission("essentialitems.workbench")),
		 canHeal(new Permission("essentialitems.heal")),
		 canChatStop(new Permission("essentialitems.chatslow.command")),
		 canBypassChatStop(new Permission("essentialitems.chatslow.bypass")),
		 canTempMute(new Permission("essentialitems.tempmute")),
		 canInvsee(new Permission("essentialitems.invsee")),
		 canDamage(new Permission("essentialitems.damage")),
		 canMigrate(new Permission("essentialitems.migrate")),
		  ;
		
		
		private Permission permission;
		permission(Permission permission) {
			
			this.permission = permission;  
		  }
		
		public Permission get() {
			return this.permission;
		}
	}
	

}
