package com.essentialitems.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.essentialitems.ConfigManager;
import com.essentialitems.Main;
import com.essentialitems.Util;
import com.essentialitems.command.motd.MotdGui;

public final class MotdCommand extends CommandSkeleton {
	
	public static final ArrayList<Player> playersSettingTitle = new ArrayList<Player>();
	public static final ArrayList<Player> playersSettingSubtitle = new ArrayList<Player>();
	public static final ArrayList<Player> playersSettingChatMessage = new ArrayList<Player>();
	public static final ArrayList<Player> playersResettingMotd = new ArrayList<Player>();
	public static final ArrayList<Player> playersResettingChat = new ArrayList<Player>(); 
	public static final ArrayList<Player> playersResettingTitle = new ArrayList<Player>(); 
	public static final ArrayList<Player> playersResettingSubtitle = new ArrayList<Player>();
    
	
	private static final String motdHeader = "["+ChatColor.GOLD+ChatColor.BOLD+"MOTD"+ChatColor.RESET+"] ";

	@Override
	public int run(String[] args, Main mainclass, CommandSender sender) {
		
		
		//This is all that is needed.  All of the other functions are handled by event listeners.
		Player p = (Player) sender;
		
		if(playersSettingTitle.contains(p)) {
			p.sendMessage(motdHeader+ChatColor.GOLD+"Please complete your previous action before opening the GUI again.");
			return 0;
		}
		if(playersSettingSubtitle.contains(p)) {
			p.sendMessage(motdHeader+ChatColor.GOLD+"Please complete your previous action before opening the GUI again.");
			return 0;
		}
		if(playersSettingChatMessage.contains(p)) {
			p.sendMessage(motdHeader+ChatColor.GOLD+"Please complete your previous action before opening the GUI again.");
			return 0;
		}
		if(playersResettingMotd.contains(p)) {
			p.sendMessage(motdHeader+ChatColor.GOLD+"Please complete your previous action before opening the GUI again.");
			return 0;
		}
		if(playersResettingChat.contains(p)) {
			p.sendMessage(motdHeader+ChatColor.GOLD+"Please complete your previous action before opening the GUI again.");
			return 0;
		}
		if(playersResettingTitle.contains(p)) {
			p.sendMessage(motdHeader+ChatColor.GOLD+"Please complete your previous action before opening the GUI again.");
			return 0;
		}
		if(playersResettingSubtitle.contains(p)) {
			p.sendMessage(motdHeader+ChatColor.GOLD+"Please complete your previous action before opening the GUI again.");
			return 0;
		}
		
		MotdGui.openGui(p, mainclass);
		return 0;
	}
	
	
	public static void inventoryClick(Player p, ItemStack itemClicked, Inventory inv, int slotNumber, Main mainclass) {
		
		
		//Buttons that toggle things
		if(itemClicked.equals(MotdGui.buttons.TITLEENABLED.get())) {
			//We are toggling the title line OFF
			inv.setItem(slotNumber, MotdGui.buttons.TITLEDISABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doMotdTitle.toString(), false);
			mainclass.saveConfig();
			return;
			
		}
		if(itemClicked.equals(MotdGui.buttons.TITLEDISABLED.get())) {
			//We are toggling the title line ON
			inv.setItem(slotNumber, MotdGui.buttons.TITLEENABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doMotdTitle.toString(), true);
			mainclass.saveConfig();
			return;
			
		}
		if(itemClicked.equals(MotdGui.buttons.CHATENABLED.get())) {
			//We are toggling the chat message OFF
			inv.setItem(slotNumber, MotdGui.buttons.CHATDISABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doMotdChatMessage.toString(), false);
			mainclass.saveConfig();
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.CHATDISABLED.get())) {
			//We are toggling the chat message ON
			inv.setItem(slotNumber, MotdGui.buttons.CHATENABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doMotdChatMessage.toString(), true);
			mainclass.saveConfig();
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.SUBTITLEENABLED.get())) {
			inv.setItem(slotNumber, MotdGui.buttons.SUBTITLEDISABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doMotdSubtitle.toString(), false);
			mainclass.saveConfig();
			return;
			//We are toggling the subtitle line OFF
		}
		if(itemClicked.equals(MotdGui.buttons.SUBTITLEDISABLED.get())) {
			//We are toggling the subtitle line ON
			inv.setItem(slotNumber,  MotdGui.buttons.SUBTITLEENABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doMotdSubtitle.toString(), true);
			mainclass.saveConfig();
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.JOINEDENABLED.get())) {
			//We are toggling the ojin message OFF 
			inv.setItem(slotNumber, MotdGui.buttons.JOINEDDISABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doOjinMessage.toString(), false);
			mainclass.saveConfig();
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.JOINEDDISABLED.get())) {
			//We are toggling the ojin message ON (for some reason...)
			inv.setItem(slotNumber, MotdGui.buttons.JOINEDENABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doOjinMessage.toString(), true);
			mainclass.saveConfig();
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.LEFTENABLED.get())) {
			//We are toggling the olea message OFF 
			inv.setItem(slotNumber, MotdGui.buttons.LEFTDISABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doOleaMessage.toString(),false);
			mainclass.saveConfig();
			return;
			
		}
		if(itemClicked.equals(MotdGui.buttons.LEFTDISABLED.get())) {
			//W are toggling the olea message ON (for some reason...)
			inv.setItem(slotNumber, MotdGui.buttons.LEFTENABLED.get());
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1000, (float) 2.0);
			mainclass.getConfig().set(Util.configKey.doOleaMessage.toString(),false);
			mainclass.saveConfig();
			return;
			
		}
		
		//Buttons that don't toggle.
		if(itemClicked.equals(MotdGui.buttons.DISPLAYCHAT.get())) {
			p.closeInventory();
			p.sendMessage(mainclass.getConfig().getString(Util.configKey.motdChatMessage.toString()));
			return;
			//Display the chat message
		}
		if(itemClicked.equals(MotdGui.buttons.DISPLAYTITLE.get())) {
			p.closeInventory();
			p.sendTitle(mainclass.getConfig().getString(Util.configKey.motdTitleMessage.toString()), " ", 20, 200, 20);
			return;
			//Display Title
		}
		if(itemClicked.equals(MotdGui.buttons.DISPLAYSUBTITLE.get())) {
			p.closeInventory();
			p.sendTitle(" ",mainclass.getConfig().getString(Util.configKey.motdSubtitleMessage.toString()), 20, 200, 20);
			return;
			//Display Subtitle
		}
		if(itemClicked.equals(MotdGui.buttons.DISPLAYMOTD.get())) {
			//Display currently configured MOTD
			p.closeInventory();
			Util.displayMotd(p, mainclass);
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.SETCHAT.get())) {
			//We are setting the chat message
			MotdCommand.startSetChatMessage(p, mainclass);
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.SETTITLE.get())) {
			//We are setting the title line
			MotdCommand.startSetTitle(p, mainclass);
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.SETSUBTITLE.get())) {
			MotdCommand.startSetSubtitle(p, mainclass);
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.RESETCHAT.get())) {
			//We are resetting the chat message!
			MotdCommand.resetChat(p);
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.RESETSUBTITLE.get())) {
			MotdCommand.resetSubtitle(p);
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.RESETTITLE.get())) {
			//We are resetting the title line!
			MotdCommand.resetTitle(p);
			return;
		}
		if(itemClicked.equals(MotdGui.buttons.RESET.get())) {
			//We are RESETTING THE MOTD!!!
			MotdCommand.resetMotd(p);
			return;
		}
		
		
		
		
	}
	
	public static boolean chatMessage(Player p, String message, Main mainclass) {
		
		if(playersSettingTitle.contains(p)) {
			if(message.equalsIgnoreCase("!cancel")) {
				p.sendMessage(motdHeader+ChatColor.GOLD+"Cancelled.  The MOTD Title line has not been changed.");
				playersSettingTitle.remove(p);
				
				return true;
			}
			else {
				message = message.trim();
				message = message.replace('&', '§');
				mainclass.getConfig().set(Util.configKey.motdTitleMessage.toString(), message);
				mainclass.saveConfig();
				p.sendMessage(motdHeader+ChatColor.GOLD+"Success! The title line has been set. It now looks like: ");
				p.sendMessage(message);
				playersSettingTitle.remove(p);
				
				return true;
			}
		}
		if(playersSettingSubtitle.contains(p)) {
			if(message.equalsIgnoreCase("!cancel")) {
				p.sendMessage(motdHeader+ChatColor.GOLD+"Cancelled.  The MOTD Subtitle line has not been changed.");
				playersSettingSubtitle.remove(p);
				
				return true;
			}
			else {
				message = message.trim();
				message = message.replace('&', '§');
				mainclass.getConfig().set(Util.configKey.motdSubtitleMessage.toString(), message);
				mainclass.saveConfig();
				p.sendMessage(motdHeader+ChatColor.GOLD+"Success! The subtitle line has been set. It now looks like: ");
				p.sendMessage(message);
				playersSettingSubtitle.remove(p);
				
				return true;
				
			}
			
		}
		if(playersSettingChatMessage.contains(p)) {
			if(message.equalsIgnoreCase("!cancel")) {
				p.sendMessage(motdHeader+ChatColor.GOLD+"Cancelled.  The MOTD Chat message has not been changed.");
				playersSettingChatMessage.remove(p);
				
				return true;
			}
			else {
				message = message.trim();
				message = message.replace('&', '§');
				mainclass.getConfig().set(Util.configKey.motdChatMessage.toString(), message);
				mainclass.saveConfig();
				p.sendMessage(motdHeader+ChatColor.GOLD+"Success! The chat message has been set. It now looks like: ");
				p.sendMessage(message);
				playersSettingChatMessage.remove(p);
				
				return true;
			}
			
		}
		if(playersResettingMotd.contains(p)) {
			if(message.equalsIgnoreCase("n")||message.equalsIgnoreCase("no")) {
				p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"Cancelled.  The MOTD has not been changed.");
				playersResettingMotd.remove(p);
				
				return true;
				
			}
			else if(message.equalsIgnoreCase("y")||message.equalsIgnoreCase("yes")) {
				ConfigManager.resetMotd(mainclass);
				p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"The MOTD has been successfully reset.");
				playersResettingMotd.remove(p);
				
				return true;
			}
			else {
				//Not a valid input...
				return true;
			}
		}
		if(playersResettingChat.contains(p)) {
			if(message.equalsIgnoreCase("n")|| message.equalsIgnoreCase("no")) {
				p.sendMessage(motdHeader+ChatColor.GOLD+"Cancelled.  The MOTD Chat message has not been changed.");
				playersResettingChat.remove(p);
				
				return true;
				
			}
			else if(message.equalsIgnoreCase("y")||message.equalsIgnoreCase("yes")) {
				ConfigManager.resetChat(mainclass);
				p.sendMessage(motdHeader+ChatColor.GOLD+"The MOTD Chat message has been reset.");
				playersResettingChat.remove(p);
				
				return true;
			}
			else {
				//Not a valid input...
				return false;
			}
			
		}
		if(playersResettingTitle.contains(p)) {
			if(message.equalsIgnoreCase("n")|| message.equalsIgnoreCase("no")) {
				p.sendMessage(motdHeader+ChatColor.GOLD+"Cancelled.  The MOTD Title line has not been changed.");
				playersResettingTitle.remove(p);
				
				return true;
			}
			else if(message.equalsIgnoreCase("y")||message.equalsIgnoreCase("yes")) {
				ConfigManager.resetTitle(mainclass);
				p.sendMessage(motdHeader+ChatColor.GOLD+"The MOTD Title line has been reset.");
				playersResettingTitle.remove(p);
				
				return true;
			}
			else {
				//Not a valid input...
				return false;
			}
		}
		if(playersResettingSubtitle.contains(p)) {
			if(message.equalsIgnoreCase("n")|| message.equalsIgnoreCase("no")) {
				p.sendMessage(motdHeader+ChatColor.GOLD+"Cancelled.  The MOTD Subtitle line has not been changed.");
				playersResettingSubtitle.remove(p);
				
				return true;
			}
			else if(message.equalsIgnoreCase("y")||message.equalsIgnoreCase("yes")) {
				ConfigManager.resetSubtitle(mainclass);
				p.sendMessage(motdHeader+ChatColor.GOLD+"The MOTD Subtitle line has been reset.");
				playersResettingSubtitle.remove(p);
				
				return true;
			}
			else {
				//Not a valid input...
				return false;
				
			}
		}
		//Welp, false alarm
		
		return false;
		
	}
	
	private static void startSetTitle(Player p, Main mainclass) {
		//Setting the title line
		playersSettingTitle.add(p);
		p.closeInventory();
		p.sendMessage(motdHeader+ChatColor.GOLD+"You have chosen to change the MOTD title line.");
		p.sendMessage(motdHeader+ChatColor.GOLD+"The current MOTD title line is: \n\n"+ChatColor.RESET+mainclass.getConfig().get(Util.configKey.motdTitleMessage.toString())+"\n\n");
		p.sendMessage(motdHeader+ChatColor.GOLD+"Please type your new message into chat.  Color codes using the & symbol are supported. Type '!cancel' to cancel.");
	}
	private static void startSetSubtitle(Player p, Main mainclass) {
		//Setting the subtitle line
		playersSettingSubtitle.add(p);
		p.closeInventory();
		p.sendMessage(motdHeader+ChatColor.GOLD+"You have chosen to change the MOTD subtitle line.");
		p.sendMessage(motdHeader+ChatColor.GOLD+"The current MOTD subtitle line is: \n\n"+ChatColor.RESET+mainclass.getConfig().get(Util.configKey.motdSubtitleMessage.toString())+"\n\n");
		p.sendMessage(motdHeader+ChatColor.GOLD+"Please type your new message into chat.  Color codes using the & symbol are supported. Type '!cancel' to cancel.");
		
		
	}
	private static void startSetChatMessage(Player p, Main mainclass) {
		playersSettingChatMessage.add(p);
		p.closeInventory();
		p.sendMessage(motdHeader+ChatColor.GOLD+"You have chosen to change the MOTD chat message line.");
		p.sendMessage(motdHeader+ChatColor.GOLD+"The current MOTD chat message is: \n\n"+ChatColor.RESET+mainclass.getConfig().get(Util.configKey.motdChatMessage.toString())+"\n\n");
		p.sendMessage(motdHeader+ChatColor.GOLD+"Please type your new message into chat.  Color codes using the & symbol are supported. Type '!cancel' to cancel.");
		
	}
	private static void resetMotd(Player p) {
		playersResettingMotd.add(p);
		p.closeInventory();
		p.sendMessage(motdHeader+ChatColor.DARK_RED+""+ChatColor.BOLD+"Warning!!! You are about to reset all MOTD settings to their default states!!!");
		p.sendMessage(motdHeader+ChatColor.DARK_RED+""+ChatColor.BOLD+"Are you SURE you want to continue? (Y/N)");

		
	}
	private static void resetChat(Player p) {
		playersResettingChat.add(p);
		p.closeInventory();
		p.sendMessage(motdHeader+ChatColor.RED+ChatColor.BOLD+"Warning!  You are about to reset the chat message to its default state!");
		p.sendMessage(motdHeader+ChatColor.RED+ChatColor.BOLD+"Are you sure you want to continue? (Y/N)");
		
	}
	private static void resetTitle(Player p) {
		playersResettingTitle.add(p);
		p.closeInventory();
		p.sendMessage(motdHeader+ChatColor.RED+ChatColor.BOLD+"Warning!  You are about to reset the Title line to its default state!");
		p.sendMessage(motdHeader+ChatColor.RED+ChatColor.BOLD+"Are you sure you want to continue? (Y/N)");
	}
	private static void resetSubtitle(Player p) {
		playersResettingSubtitle.add(p);
		p.closeInventory();
		p.sendMessage(motdHeader+ChatColor.RED+ChatColor.BOLD+"Warning!  You are about to reset the Subtitle line to its default state!");
		p.sendMessage(motdHeader+ChatColor.RED+ChatColor.BOLD+"Are you sure you want to continue? (Y/N)");
	}

}
