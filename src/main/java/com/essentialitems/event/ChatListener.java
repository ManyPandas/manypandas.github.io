package com.essentialitems.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.essentialitems.Main;
import com.essentialitems.Util;
import com.essentialitems.command.ChatSlowCommand;
import com.essentialitems.command.KitCommand;
import com.essentialitems.command.MotdCommand;

public class ChatListener implements Listener {
	
	protected  final Map<String, Long> coolDownTime = new HashMap<String, Long>();
	
	private Main mainclass;
	
	public ChatListener(Main plugin) {
		this.mainclass = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	
	
	
		@EventHandler(priority = EventPriority.HIGH)
		
		public void onPlayerChat(AsyncPlayerChatEvent e) {
			
			//Check new mute system first
			String uuid = e.getPlayer().getUniqueId().toString();
			if(Util.newGetMuted(uuid, mainclass)) {
				//They are muted via the new system!
				//However, we cannot cancel the event right this second (well, we will considering how fast this thing runs...)
				//Let's first  check to see if the mute is permanent:
				if(mainclass.muted.getConfigurationSection(uuid).getLong("expires") == (long)-1) {
					//It's permanent!
					//Cancel the event.
					e.setCancelled(true);
					
					//Get the reason.
					String reason = Util.newGetMuteReason(uuid, mainclass);
					
					//Send the message.
					e.getPlayer().sendMessage(Util.punishHeader+ChatColor.RED+"You cannot speak.  You are permanently muted.");
					e.getPlayer().sendMessage(Util.punishHeader+ChatColor.DARK_GREEN+"Reason: "+ reason);
					return;
				}
				//The mute is not permanent!
				//Is it expired though?
				if(mainclass.muted.getConfigurationSection(uuid).getLong("expires")<System.currentTimeMillis()) {
					//The mute is expired...
					//We can't really do much but remove it from the file...
					Util.newUnmute(uuid, mainclass);
					
					//We can't return out either.  They could (for some reason) be changing the MOTD.
				}
				else {
					//It's not expired!
					e.setCancelled(true);
					
					//Let's send the player a message reminding them why they are muted, and how much time they have to go.
					
					//Get the time remaining in minutes.
					int timeLeftMinutes = (int)( (mainclass.muted.getConfigurationSection(uuid).getLong("expires")/1000/60) -(int) (System.currentTimeMillis()/1000/60) );
					//Get the reason.
					String reason = Util.newGetMuteReason(uuid, mainclass);
					
					if(timeLeftMinutes>60) {
						//Send the message.
						e.getPlayer().sendMessage(Util.punishHeader+ChatColor.RED+"You are muted for another "+ChatColor.BLUE+timeLeftMinutes/60+ChatColor.RED+" hour(s).");
						e.getPlayer().sendMessage(Util.punishHeader+ChatColor.DARK_GREEN+"Reason: "+ reason);
						
						//We're done here. Let's return out.
						return;
					}
					else if(timeLeftMinutes>1) {
						//Send the message.
						e.getPlayer().sendMessage(Util.punishHeader+ChatColor.RED+"You are muted for another "+ChatColor.BLUE+timeLeftMinutes+ChatColor.RED+" minute(s).");
						e.getPlayer().sendMessage(Util.punishHeader+ChatColor.DARK_GREEN+"Reason: "+ reason);
						
						//We're done here. Let's return out.
						return;	
					}
					else {
						e.getPlayer().sendMessage(Util.punishHeader+ChatColor.RED+"You are muted for another "+ChatColor.BLUE+"1"+ChatColor.RED+" minute(s).");
						e.getPlayer().sendMessage(Util.punishHeader+ChatColor.DARK_GREEN+"Reason: "+ reason);
						
					}
					
				}
			}	
			
			//Now let's send the message to the MOTD event, somebody may be setting it.
			if(MotdCommand.chatMessage(e.getPlayer(), e.getMessage(), mainclass)) {
				//Somebody was setting it, and they gave a valid input.
				e.setCancelled(true);
				//They can bypass the chat slow for now that's fine...
				return;
			}
			
			//Nobody was changing the MOTD, lets see if they are working with kits...
			if(KitCommand.chatMessage(e.getPlayer(), e.getMessage(), mainclass)) {
				//Valid input.
				e.setCancelled(true);
				return;
				
				//They can bypass the chat slow for right now that's fine...
			}
			else {
				if(mainclass.getConfig().getBoolean(Util.configKey.chatslow.toString())) {
					//Looks like a chat slow is in effect!
					
					
					
					/* 
					 * I know, I know!  Permission checks aren't thread safe...
					 * However, since I'm not changing anything with the world, and Spigot doesn't seem to be complaining, I'm not going to change it.
					 */
					if(Util.checkPermission(Util.permission.canBypassChatStop.get(),e.getPlayer())) {
						//They have BYPASS permission?!? 
						return;
					}
					else {
						long currentTime = System.currentTimeMillis();
						String name = e.getPlayer().getName();
						Long lastChat = coolDownTime.get(name);
						if(lastChat != null) {
							long allowNext = lastChat.longValue() + mainclass.getConfig().getInt(Util.configKey.chatSlowCooldown.toString()) *1000;
							if(currentTime <= allowNext) {
								e.setCancelled(true);
								int timeRemaining = (int)((allowNext - currentTime) / 1000L) + 1;
								e.getPlayer().sendMessage(ChatSlowCommand.chatHeader+ChatColor.DARK_AQUA+"You cannot speak at the moment.  Please wait another "+ChatColor.GRAY+timeRemaining+ChatColor.DARK_AQUA+" second"+
								(timeRemaining > 1 ? "s " : " ")+ "before sending a message again.");
								return;
							}
							else {
								coolDownTime.put(name, currentTime);
								return;
							}
						}
						else {
							coolDownTime.put(name, currentTime);
							return;
						}
					}
				}
			}	
			
		}
		//End of listener

}
//EOF