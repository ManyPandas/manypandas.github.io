package com.essentialitems;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

import com.essentialitems.command.kit.CooldownTicket;
import com.essentialitems.command.kit.Kit;



public final class Util {
	
	public static final String bumper = StringUtils.repeat("\n", 15);
	public static final String punishHeader = "["+ChatColor.RED+ChatColor.BOLD+"Punish"+ChatColor.RESET+"] ";
	public static final String healthHeader ="["+ChatColor.DARK_PURPLE+ChatColor.BOLD+"Health"+ChatColor.RESET+"] ";
	
	
	public static final ArrayList<CooldownTicket> kitCooldowns = new ArrayList<CooldownTicket>();
	
	public static final ArrayList<Player> invulnerablePlayers = new ArrayList<Player>();
	
	public static final HashMap<Player, Long> healCooldowns = new HashMap<Player, Long>();
	public static final HashMap<Player, Long> feedCooldowns = new HashMap<Player, Long>();
	
	public static final HashMap<Player, Integer> warpTasks = new HashMap<Player, Integer>();
	
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
	
	
	public static Inventory createInventory(int rows, String name) throws IllegalArgumentException {
		return Bukkit.createInventory(null, rows*9, name);
		
	}
	public static void permBan(String targetName, String reason,CommandSender sender) {
		
		String bannedMessage = bumper + ChatColor.DARK_RED+ChatColor.BOLD+"Sorry, you have been banned from this server.\n\n"+ChatColor.RESET+ ChatColor.DARK_BLUE+ChatColor.BOLD+"Total Ban duration: Permanent\n\n" +ChatColor.RESET+ChatColor.DARK_GREEN+" Reason: " + reason+bumper;
		Bukkit.getBanList(BanList.Type.NAME).addBan(targetName, bannedMessage, null, null);
		Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+targetName+ChatColor.RESET+ChatColor.RED+" has been permanently banned. ");
		Bukkit.broadcastMessage(punishHeader+ChatColor.DARK_GREEN+"Reason: "+reason);
	}
	
	public static void tempBan(String targetName, String reason, int time, CommandSender sender) {
	    Date timeLeft = new Date(System.currentTimeMillis()+ time*1000*60);
	    String bannedMessage;
	    if(time<60) {
	    	bannedMessage =  bumper+ChatColor.DARK_RED+ChatColor.BOLD+"Sorry, you have been banned from this server.\n\n"+ChatColor.RESET+ChatColor.DARK_BLUE+ChatColor.BOLD+"Total Ban Duration: "+ time+" minute(s). \n\n"
	    +ChatColor.RESET+ChatColor.DARK_GREEN+"Reason: "+reason+bumper;
	    	
	    }
	    else {
	    	bannedMessage =  bumper+ChatColor.RED+ChatColor.BOLD+"Sorry, you have been banned from this server.\n\n"+ChatColor.RESET+ChatColor.DARK_BLUE+ChatColor.BOLD+"Total Ban Duration: "+ time/60+" hour(s). \n\n"+ChatColor.RESET
	    			+ChatColor.DARK_GREEN+"Reason:  "+reason+bumper;
	    }
		
		Bukkit.getBanList(BanList.Type.NAME).addBan(targetName,bannedMessage,timeLeft, null);
		if(time<60) {
			Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+targetName+ChatColor.RESET+ChatColor.RED+" has been banned for a duration of "+time+" minute(s). ");
			Bukkit.broadcastMessage(punishHeader+ChatColor.DARK_GREEN+"Reason: "+reason);
			return;
			
		}
		else {
			Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+targetName+ChatColor.RESET+ChatColor.RED+" has been banned for a duration of "+time/60+" hour(s). ");
			Bukkit.broadcastMessage(punishHeader+ChatColor.DARK_GREEN+"Reason: "+reason);
			return;
		}
		
		
	}
	public static void kick(Player target, String reason, CommandSender sender) {
		target.kickPlayer(bumper+ChatColor.RED+ChatColor.BOLD+"You have been kicked from the server.\n\n" +ChatColor.RESET+ChatColor.DARK_GREEN+"Reason: "+ reason+bumper);
		Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+target.getName()+ChatColor.RESET+ChatColor.RED+" has been kicked from the server. ");
		Bukkit.broadcastMessage(punishHeader+ChatColor.DARK_GREEN+"Reason: "+reason);
	}
	
	
	
	public static void mute(String uuid, String name, String reason, Main mainclass) {
		mainclass.muted.createSection(uuid);
		mainclass.muted.getConfigurationSection(uuid).set("reason", reason);
		mainclass.muted.getConfigurationSection(uuid).set("name", name);
		mainclass.muted.getConfigurationSection(uuid).set("expires", (long)-1);
		mainclass.saveConfig();
		Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+name+ChatColor.RED+ " has been permanently muted.");
		Bukkit.broadcastMessage(punishHeader+ChatColor.DARK_GREEN+"Reason: "+reason);
	}
	public static void mute(String uuid, String name, String reason, boolean verbose, Main mainclass) {
		if(!verbose) {
			mainclass.muted.createSection(uuid);
			mainclass.muted.getConfigurationSection(uuid).set("reason", reason);
			mainclass.muted.getConfigurationSection(uuid).set("name", name);
			mainclass.muted.getConfigurationSection(uuid).set("expires", (long)-1);
			mainclass.saveConfig();
			return;
		}
		else {
			mainclass.muted.createSection(uuid);
			mainclass.muted.getConfigurationSection(uuid).set("reason", reason);
			mainclass.muted.getConfigurationSection(uuid).set("name", name);
			mainclass.muted.getConfigurationSection(uuid).set("expires", (long)-1);
			mainclass.saveConfig();
			Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+name+ChatColor.RED+ " has been permanently muted.");
			Bukkit.broadcastMessage(punishHeader+ChatColor.DARK_GREEN+"Reason: "+reason);
			return;
		}
		
	}
	public static void mute(String uuid, String name, String reason, long expiresmillis, Main mainclass) {
		mainclass.muted.createSection(uuid);
		mainclass.muted.getConfigurationSection(uuid).set("reason", reason);
		mainclass.muted.getConfigurationSection(uuid).set("name", name);
		mainclass.muted.getConfigurationSection(uuid).set("expires", expiresmillis);
		mainclass.saveConfig();
		int time = (int)(expiresmillis/60/1000) -(int)(System.currentTimeMillis()/60/1000);
		if(time>60) {
			Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+name+ChatColor.RED+ " has been muted for "+time/60+" hour(s).");
			Bukkit.broadcastMessage(punishHeader+ChatColor.DARK_GREEN+"Reason: "+reason);
			return;
		}
		Bukkit.broadcastMessage(punishHeader+ChatColor.BLUE+name+ChatColor.RED+ " has been muted for "+time+" minute(s).");
		Bukkit.broadcastMessage(punishHeader+ChatColor.DARK_GREEN+"Reason: "+reason);
		return;
		
	}
	
	
	public static boolean newUnmute(String uuid, Main mainclass) {
		if(mainclass.muted.getConfigurationSection(uuid) == null) {
			return false;
		}
		else {
			mainclass.muted.set(uuid, null);
			mainclass.saveConfig();
			return true;
		}
	}
	
	public static boolean newGetMuted(String uniqueId, Main mainclass) {
		if(mainclass.muted.getConfigurationSection(uniqueId) == null) {
			return false;
		}
		return true;
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
		return bumper + ChatColor.DARK_RED+ChatColor.BOLD+"Sorry, you have been banned from this server.\n\n"+ChatColor.RESET+ ChatColor.DARK_BLUE+ChatColor.BOLD+"Total Ban duration: Permanent\n\n" +ChatColor.RESET
				+ChatColor.DARK_GREEN+"Reason: " + reason+bumper;
		
	}
	public static String generateTempBanMessage(String reason, int time) {
		if(time>60) {
			return bumper+ChatColor.DARK_RED+ChatColor.BOLD+"Sorry, you have been banned from this server.\n\n"+ChatColor.RESET+ChatColor.DARK_BLUE+ChatColor.BOLD+"Total Ban Duration: "+ time/60+" hour(s). \n\n"+ChatColor.RESET+
					ChatColor.DARK_GREEN+"Reason:  "+reason+bumper;
		}
		else {
			return bumper+ChatColor.DARK_RED+ChatColor.BOLD+"Sorry, you have been banned from this server.\n\n"+ChatColor.RESET+ChatColor.DARK_BLUE+ChatColor.BOLD+"Total Ban Duration: "+ time+" minute(s). \n\n"+ChatColor.RESET+
					ChatColor.GREEN+"Reason:  "+reason+bumper;
			
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
	
	public static boolean hasKitCooldown(Player p, Kit kitBeingUsed) {
		if(Util.checkPermission("essentialitems.kit.cooldown.bypass", p)) {
			kitCooldowns.remove(getCooldown(p.getName()));
			return false;
		}
		
		for(CooldownTicket ct : kitCooldowns) {
			if(p.getName().equals(ct.getUser().getName())) {
				//They are listed in the cooldown.  However, are they using the same kit as before?
				if(ct.getKit().getName().equals(kitBeingUsed.getName())) {
					//Yes.  Now let's check if they have a cooldown.
					
					long nextAllowedMillis = (ct.getKit().getCooldown()*1000+ct.getTimeUsedMillis());
					if(System.currentTimeMillis()<=nextAllowedMillis) {
						//Okay, they aren't allowed to use it.
						//We're going to rely on the method caller to figure out how much time left until they can use it.
						return true;
					}
					else {
						//They can use the kit.  We need to remove them from the list, and return false;
						kitCooldowns.remove(ct);
						return false;
						
					}
					
				}
				else {
					//They are using a different kit.  We can just return false.
					//However, we can't remove their entry. The cooldown may still be active.
					return false;
					
				}
			}
			else {
				//Not the right player! (or they aren't listed in the cooldown)
				continue;
			}
		}
		
		/*
		 * The loop has ran itself through.
		 * The player wasn't listed in the cooldown tickets.
		 * We can return false. The cooldown will be applied by the command system.
		 */
		 
		return false;
	}
	public static boolean hasKitCooldown(Player p, String kitName) {
		if(Util.checkPermission("essentialitems.kit.cooldown.bypass", p)) {
			kitCooldowns.remove(getCooldown(p.getName()));
			return false;
		}
		
		for(CooldownTicket ct : kitCooldowns) {
			if(p.getName().equals(ct.getUser().getName())) {
				//They are listed in the cooldown.  However, are they using the same kit as before?
				if(ct.getKit().getName().equals(kitName)) {
					//Yes.  Now let's check if they have a cooldown.
					
					long nextAllowedMillis = (ct.getKit().getCooldown()*1000+ct.getTimeUsedMillis());
					if(System.currentTimeMillis()<=nextAllowedMillis) {
						//Okay, they aren't allowed to use it.
						//We're going to rely on the method caller to figure out how much time left until they can use it.
						return true;
					}
					else {
						//They can use the kit.  We need to remove them from the list, and return false;
						kitCooldowns.remove(ct);
						return false;
						
					}
					
				}
				else {
					//They are using a different kit.  We can just return false.
					//However, we can't remove their entry. The cooldown may still be active.
					return false;
					
				}
			}
			else {
				//Not the right player! (or they aren't listed in the cooldown)
				continue;
			}
		}
		
		/*
		 * The loop has ran itself through.
		 * The player wasn't listed in the cooldown tickets.
		 * We can return false. The cooldown will be applied by the command system.
		 */
		 
		return false;
	}
	
	public static CooldownTicket getCooldown(String playerName) {
		for(CooldownTicket ct : kitCooldowns) {
			if(ct.getUser().getName().equalsIgnoreCase(playerName)) {
				return ct;
			}
			continue;
		}
		return null;
	}
	
	public static boolean hasFeedCooldown(Player p, Main mainclass) {
		
		if(!feedCooldowns.containsKey(p)) {
			return false;
		}
		
		if(Util.checkPermission("essentialitems.feed.cooldown.bypass",p)) {
			feedCooldowns.remove(p);
			return false;
		}
		
		int cooldown = mainclass.getConfig().getInt(Util.configKey.feedCooldown.toString());
		
		if(cooldown ==-1) {
			feedCooldowns.remove(p);
			return false;
		}
		
		long nextallowedmillis = (cooldown*1000)+(long)feedCooldowns.get(p);
		
		if(System.currentTimeMillis()<=nextallowedmillis) {
			return true;
		}
		else {
			feedCooldowns.remove(p);
			return false;
			
		}
		
	}
	public static boolean checkPermission(String permission, CommandSender sender) {
		if(sender.hasPermission("essentialitems.admin")) {
			return true;
		}
		
		return sender.hasPermission(permission);
	}
	public static boolean checkPermission(String permission, Player p) {
		if(p.hasPermission("essentialitems.admin")) {
			return true;
		}
		
		return p.hasPermission(permission);
	}
	public static boolean checkPermission(Permission permission, CommandSender sender) {
		if(sender.hasPermission("essentialitems.admin")) {
			return true;
		}
		
		return sender.hasPermission(permission);
	}public static boolean checkPermission(Permission permission, Player p) {
		if(p.hasPermission("essentialitems.admin")) {
			return true;
		}
		
		return p.hasPermission(permission);
	}
		
	
	
	public static boolean hasHealCooldown(Player p, Main mainclass) {
		
		if(!healCooldowns.containsKey(p)) {
			return false;
		}
		if(Util.checkPermission("essentialitems.heal.cooldown.bypass",p)) {
			healCooldowns.remove(p);
			return false;
		}
		int cooldown = mainclass.getConfig().getInt(Util.configKey.healCooldown.toString());
		
		if(cooldown ==-1) {
			feedCooldowns.remove(p);
			return false;
		}
		
		long nextallowedmillis = cooldown*1000+(long)healCooldowns.get(p);
		
		if(System.currentTimeMillis()<=nextallowedmillis) {
			return true;
		}
		healCooldowns.remove(p);
		return false;
		
	}
	
	
	public static enum configKey {
		
		//All used configurations key are stored here, in an easy-to-access enumeration.
		//These are for use with the main config.yml file only.
		chatslow("chatslow"),
		chatSlowCooldown("chatslow-cooldown"),
		chatSlowReason("chatslow-reason"),
		doMotdChatMessage("doMotdChatMessage"),
		doMotdSubtitle("doMotdSubtitleMessage"),
		doMotdTitle("doMotdTitleMessage"),
		doOjinMessage("doPlayerJoinedMessage"),
		doOleaMessage("doPlayerLeftMessage"),
		feedCooldown("feedcooldown"),
		healCooldown("healcooldown"),
		kickUnauthorizedOnLockdown("kickUnauthorizedOnLockdown"),
		lockdown("lockdown"),
		lockdownReason("lockdown-reason"),
		motdChatMessage("motdChatMessage"),
		motdSubtitleMessage("motdSubtitleMessage"),
		motdTitleMessage("motdTitleMessage"),
		MutedPlayers("MutedPlayers"),
		
		
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
		 bypassLockdown(new Permission("essentialitems.lockdown.bypass")),
		 canBan(new Permission("essentialitems.ban")),
		 canBroadcast(new Permission("essentialitems.broadcast")),
		 canBypassChatStop(new Permission("essentialitems.chatslow.bypass")),
		 canChatStop(new Permission("essentialitems.chatslow.command")),
		 canDamage(new Permission("essentialitems.damage")),
		 canEchest(new Permission("essentialitems.echest")),
		 canFeed(new Permission("essentialitems.feed")),
		 canFly(new Permission("essentialitems.fly")),
		 canGameMode(new Permission("essentialitems.gamemode")),
		 canGetPos(new Permission("essentialitems.getpos")),
		 canGodMode(new Permission("essentialitems.god")),
		 canHeal(new Permission("essentialitems.heal")),
		 canInvsee(new Permission("essentialitems.invsee")),
		 canKick(new Permission("essentialitems.kick")),
		 canKit(new Permission("essentialitems.kit.command")),
		 canMigrate(new Permission("essentialitems.migrate")),
		 canMotd(new Permission("essentialitems.motd")),
		 canMsg(new Permission("essentialitems.msg")),
		 canMute(new Permission("essentialitems.mute")) ,
		 canStartLockdown(new Permission("essentialitems.lockdown.command")),
		 canTempBan(new Permission("essentialitems.tempban")),
		 canTempMute(new Permission("essentialitems.tempmute")),
		 canTrash(new Permission("essentialitems.trash")),
		 canUnban(new Permission("essentialitems.unban")),
		 canUnmute(new Permission("essentialitems.unmute")),
		 canVanish(new Permission("essentialitems.vanish")),
		 canWarn(new Permission("essentialitems.warn")),
		 canWhois(new Permission("essentialitems.whois")),
		 canWorkbench(new Permission("essentialitems.workbench")),
		 
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
