package com.essentialitems.command.motd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class MotdGui {
	
	
	private static ItemStack reset;  
	private static ItemStack chatEnabled;  
	private static ItemStack chatDisabled; 
	private static ItemStack titleEnabled; 
	private static ItemStack titleDisabled; 
	private static ItemStack subtitleEnabled; 
	private static ItemStack subtitleDisabled; 
	private static ItemStack joinedEnabled; 
	private static ItemStack joinedDisabled;
	private static ItemStack leftEnabled;
	private static ItemStack leftDisabled;
	private static ItemStack displayChat; 
	private static ItemStack displayTitle; 
	private static ItemStack displaySubtitle; 
	private static ItemStack displayMotd;
	private static ItemStack setChat; 
	private static ItemStack setTitle; 
	private static ItemStack setSubtitle; 
	private static ItemStack resetChat;
	private static ItemStack resetSubtitle;
	private static ItemStack resetTitle;
	
	
	private static String blankLine = " ";
	
	
	
	
	public enum buttons {
		RESET(reset),
		CHATENABLED(chatEnabled),
		CHATDISABLED(chatDisabled),
		TITLEENABLED(titleEnabled),
		TITLEDISABLED(titleDisabled),
		SUBTITLEENABLED(subtitleEnabled),
		SUBTITLEDISABLED(subtitleDisabled),
		JOINEDENABLED(joinedEnabled),
		JOINEDDISABLED(joinedDisabled),
		DISPLAYCHAT(displayChat),
		DISPLAYTITLE(displayTitle),
		DISPLAYSUBTITLE(displaySubtitle),
		DISPLAYMOTD(displayMotd),
		SETCHAT(setChat),
		SETTITLE(setTitle),
		SETSUBTITLE(setSubtitle),
		RESETTITLE(resetTitle),
		RESETSUBTITLE(resetSubtitle),
		RESETCHAT(resetChat),
		LEFTENABLED(leftEnabled),
		LEFTDISABLED(leftDisabled),
		;
		
		static boolean buttonsSet = false;
		private ItemStack item;
		buttons(ItemStack item) {
			this.item=item;
			
			
		}
		public ItemStack get() {
			if(!buttonsSet) {
				prepItems();
			}
			return this.item;
		}
	}
	
	
	public static void openGui(Player p, Main mainclass) {
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN+""+ChatColor.BOLD+"Configure MOTD");
		//Deciding which buttons to use (whether things are enabled or not)
				if(mainclass.getConfig().getBoolean(Util.configKey.doMotdTitle.toString())) {
					//Title is enabled
					inv.setItem(2, titleEnabled);
				}
				else {
					//Title is disabled
					inv.setItem(2, titleDisabled);
				}
				if(mainclass.getConfig().getBoolean(Util.configKey.doMotdSubtitle.toString())) {
					//Subtitle is enabled
					inv.setItem(4, subtitleEnabled);
				}
				else {
					//Subtitle is disabled
					inv.setItem(4, subtitleDisabled);
				}
				if(mainclass.getConfig().getBoolean(Util.configKey.doMotdChatMessage.toString())) {
					//Chat message is enabled
					inv.setItem(6, chatEnabled);
				}
				else {
					//Chat message is disabled
					inv.setItem(6, chatDisabled);
				}
				if(mainclass.getConfig().getBoolean(Util.configKey.doOjinMessage.toString())) {
					//Ojin Message is enabled (for some reason...)
					inv.setItem(53, joinedEnabled);
				}
				else {
					//Ojin Message is disabled
					inv.setItem(53, joinedDisabled);
				}
				if(mainclass.getConfig().getBoolean(Util.configKey.doOleaMessage.toString())) {
					//Olea Message is enabled (for some reason...)
					inv.setItem(44, leftEnabled);
				}
				else {
					inv.setItem(44, leftDisabled);
				}
				
				
				//Buttons that do not toggle.
				inv.setItem(11, displayTitle);
				inv.setItem(13, displaySubtitle);
				inv.setItem(15, displayChat);
				
				inv.setItem(20, setTitle);
				inv.setItem(22, setSubtitle);
				inv.setItem(24, setChat);
				
				inv.setItem(29, resetTitle);
				inv.setItem(31, resetSubtitle);
				inv.setItem(33, resetChat);
				
				inv.setItem(36, displayMotd);
				inv.setItem(45, reset);
				
				
				//Open the GUI
				p.openInventory(inv);
				
	}
	
	
	

	public static void prepItems() {
	
		
		
		//MOTD Reset Button
		{
			reset = new ItemStack(Material.BARRIER,1);
			
			ItemMeta meta = reset.getItemMeta();
			meta.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+"Reset MOTD");
			List<String> lore = new ArrayList<String>();
			String loreLine1 = ChatColor.RED+"Be Careful! This will reset all settings to their defaults!";
			lore.add(blankLine);
			lore.add(loreLine1);
			meta.setLore(lore);
			meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			
			reset.setItemMeta(meta);
		}
		
		//Set MOTD Buttons
		{
			//Title
			{
				setTitle = new ItemStack(Material.MAP,1);
				ItemMeta meta = setTitle.getItemMeta();
				meta.setDisplayName(ChatColor.GOLD+"Set MOTD Title Line");
				
				
				setTitle.setItemMeta(meta);
				
			}
			
			//Subtitle
			{
				setSubtitle = new ItemStack(Material.MAP,1);
				ItemMeta meta = setSubtitle.getItemMeta();
				meta.setDisplayName(ChatColor.GOLD+"Set MOTD Subtitle Line");
				
				
				setSubtitle.setItemMeta(meta);
			}
			
			//Chat message
			{
				setChat = new ItemStack(Material.MAP,1);
				ItemMeta meta = setChat.getItemMeta();
				meta.setDisplayName(ChatColor.GOLD+"Set MOTD Chat Message");
				
				
				setChat.setItemMeta(meta);
				
			}
		}
		
		//Display MOTD Buttons
		{
			//MOTD Title
			{
				displayTitle = new ItemStack(Material.SIGN,1);
				ItemMeta meta = displayTitle.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN+"Display MOTD Title Line");
				
				
				displayTitle.setItemMeta(meta);
				
			}
			//MOTD Chat Message
			{
				displayChat = new ItemStack(Material.SIGN,1);
				ItemMeta meta = displayTitle.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN+"Display MOTD Chat Message");
				
			
				displayChat.setItemMeta(meta);
			}
			//MOTD Subtitle
			{
				displaySubtitle = new ItemStack(Material.SIGN,1);
				ItemMeta meta = displaySubtitle.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN+"Display MOTD Subtitle Line");
				displaySubtitle.setItemMeta(meta);
				
			}
			//Entire MOTD
			{
				displayMotd = new ItemStack(Material.SIGN,1);
				ItemMeta meta = displayMotd.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN+"Display Complete MOTD");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Displays the entire MOTD as currently configured.";
				lore.add(blankLine);
				lore.add(loreLine1);
				meta.setLore(lore);
				meta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				displayMotd.setItemMeta(meta);
			}
			
		}
		
		//Join Message Toggle (e.g. "Notch Joined the game.")
		{
			//Enabled
			{
				short color =5;
				joinedEnabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = joinedEnabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Join Message");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether an on-join message is displayed.";
				String loreLine2 = ChatColor.YELLOW+"Example: Notch joined the game."; 
				String loreLine3 = ChatColor.GREEN+"Enabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(blankLine);
				lore.add(loreLine3);
				meta.setLore(lore);
				
				joinedEnabled.setItemMeta(meta);
				
			}
			
			//Disabled
			{
				short color =14;
				joinedDisabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = joinedDisabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Join Message");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether an on-join message is displayed.";
				String loreLine2 = ChatColor.YELLOW+"Example: Notch joined the game.";
				String loreLine3 = ChatColor.RED+"Disabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(blankLine);
				lore.add(loreLine3);
				meta.setLore(lore);
				
				joinedDisabled.setItemMeta(meta);
				
			}
			
		}
		//Leave Message Toggle (e.g. "Notch Left the Game.")
		{
			//Enabled
			{
				short color = 5;
				leftEnabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = leftEnabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Leave Message");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether an on-leave message is displayed.";
				String loreLine2 = ChatColor.YELLOW+"Example: Notch left the game."; 
				String loreLine3 = ChatColor.GREEN+"Enabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(blankLine);
				lore.add(loreLine3);
				meta.setLore(lore);
				
				leftEnabled.setItemMeta(meta);
			}
			//Disabled 
			{
				short color = 14;
				leftDisabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = leftDisabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Leave Message");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether an on-leave message is displayed.";
				String loreLine2 = ChatColor.YELLOW+"Example: Notch left the game."; 
				String loreLine3 = ChatColor.RED+"Disabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(blankLine);
				lore.add(loreLine3);
				meta.setLore(lore);
				
				leftDisabled.setItemMeta(meta);
			}
			
		}
		
		
		//Chat Toggling Buttons
		
		{
			//Enabled
			{
				short color =5;
				chatEnabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = chatEnabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Chat Message");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether a message";
				String loreLine2 = ChatColor.YELLOW+"is sent to a player when they join.";
				String loreLine3 = ChatColor.GREEN+"Enabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(blankLine);
				lore.add(loreLine3);
				
				meta.setLore(lore);
				chatEnabled.setItemMeta(meta);
			}
			
			//Disabled
			{
				short color =14;
				chatDisabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = chatDisabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Chat Message");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether a message";
				String loreLine2 = ChatColor.YELLOW+"is sent to a player when they join.";
				String loreLine3 = ChatColor.RED+"Disabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(blankLine);
				lore.add(loreLine3);
				
				meta.setLore(lore);
				chatDisabled.setItemMeta(meta);
				
				
			}
			
		}
		
		//Title Toggling Buttons
		
		{
			//Enabled
			{
				short color = 5;
				titleEnabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = chatEnabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Title Line");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether a message on";
				String loreLine2 = ChatColor.YELLOW+"the Title Line is displayed";
				String loreLine3 = ChatColor.YELLOW+"to a player when they join.";
				String loreLine4 = ChatColor.GREEN+"Enabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(loreLine3);
				lore.add(blankLine);
				lore.add(loreLine4);
				meta.setLore(lore);
				
				titleEnabled.setItemMeta(meta);
			}
			
			//Disabled
			{
				short color =14;
				titleDisabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = chatEnabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Title Line");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether a message on";
				String loreLine2 = ChatColor.YELLOW+"the Title Line is displayed";
				String loreLine3 = ChatColor.YELLOW+"to a player when they join.";
				String loreLine4 = ChatColor.RED+"Disabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(loreLine3);
				lore.add(blankLine);
				lore.add(loreLine4);
				meta.setLore(lore);
				
				titleDisabled.setItemMeta(meta);
				
			}
		}
		//Subtitle Toggling Buttons
		
		{
			
			//Enabled 
			{
				short color = 5;
				subtitleEnabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = chatEnabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Subtitle Line");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether a message on the subtitle";
				String loreLine2 = ChatColor.YELLOW+"line is sent when a player joins the server.";
				String loreLine3 = ChatColor.GREEN+"Enabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(blankLine);
				lore.add(loreLine3);
				meta.setLore(lore);
				
				
				subtitleEnabled.setItemMeta(meta);
				
			}
			
			//Disabled
			{
				short color =14;
				subtitleDisabled = new ItemStack(Material.WOOL,1,color);
				ItemMeta meta = chatEnabled.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Toggle Subtitle Line");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Changes whether a message on the subtitle";
				String loreLine2 = ChatColor.YELLOW+"line is sent when a player joins the server.";
				String loreLine3 = ChatColor.RED+"Disabled";
				lore.add(blankLine);
				lore.add(loreLine1);
				lore.add(loreLine2);
				lore.add(blankLine);
				lore.add(loreLine3);
				meta.setLore(lore);
				
				subtitleDisabled.setItemMeta(meta);
				
			}
		}
		
		//Resetting Buttons 
		{
			//Chat 
			{
				resetChat = new ItemStack(Material.BARRIER,1);
				ItemMeta meta = resetChat.getItemMeta();
				meta.setDisplayName(ChatColor.RED+"Reset Chat Message");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Resets all chat message settings.";
				lore.add(blankLine);
				lore.add(loreLine1);
				meta.setLore(lore);
				
				resetChat.setItemMeta(meta);
			}
			//Title
			{
				resetTitle = new ItemStack(Material.BARRIER,1);
				ItemMeta meta = resetTitle.getItemMeta();
				meta.setDisplayName(ChatColor.RED+"Reset Title Line");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Resets all title line settings.";
				lore.add(blankLine);
				lore.add(loreLine1);
				meta.setLore(lore);
				resetTitle.setItemMeta(meta);
				
			}
			//Subtitle
			{
				resetSubtitle = new ItemStack(Material.BARRIER,1);
				ItemMeta meta = resetSubtitle.getItemMeta();
				meta.setDisplayName(ChatColor.RED+"Reset Subtitle Line");
				List<String> lore = new ArrayList<String>();
				String loreLine1 = ChatColor.YELLOW+"Resets all subtitle line settings.";
				lore.add(blankLine);
				lore.add(loreLine1);
				meta.setLore(lore);
				resetSubtitle.setItemMeta(meta);
			}
		}
		buttons.buttonsSet = true;
		
	}
	
}
