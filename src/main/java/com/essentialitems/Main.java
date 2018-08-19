package com.essentialitems;


import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.essentialitems.command.CmdList;
import com.essentialitems.command.CommandInterpreter;
import com.essentialitems.command.CommandReceiver;
import com.essentialitems.command.invsee.InvSeeGui;
import com.essentialitems.command.kit.KitCreateGui;
import com.essentialitems.command.kit.KitEditGui;
import com.essentialitems.command.motd.MotdGui;
import com.essentialitems.event.ChatListener;
import com.essentialitems.event.EventListener;
import com.essentialitems.event.InvClickListener;

public class Main extends JavaPlugin {
	
	File location = new File(this.getDataFolder(), "muted.yml");
	
	File kitLocation = new File(this.getDataFolder(), "kits.yml");
	
	public FileConfiguration muted = YamlConfiguration.loadConfiguration(location);
	public FileConfiguration kits = YamlConfiguration.loadConfiguration(kitLocation);
	
	
	@Override
	public void onEnable() {
		this.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.GOLD+"Enabling...");
		new EventListener(this, new ChatListener(this));
		new InvClickListener(this);
		
		this.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.GOLD+"Checking Config...");
		ConfigManager.initCheck(this);
		MotdGui.prepItems();
		InvSeeGui.prepItems();
		KitCreateGui.prepItems();
		KitEditGui.prepItems();
		this.saveConfig();
		
		//Prepare our Three-stage Command Parsing
		CommandReceiver receiver = new CommandReceiver(this, new CommandInterpreter(this));
		this.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.GOLD+"Registering Commands...");
		
		//Register Commands
		{
			//YAY WE DON'T HAVE TO REGISTER IN THE MAIN CLASS ANYMORE!!!
			//WE JUST ADD THE COMMAND TO THE CMDLIST AND PLUGIN YML!!!
			//THIS IS THE BEST DAY OF MY LIFE!!!
			for(CmdList cmd : CmdList.values()) {
				
				this.getCommand(cmd.getName()).setExecutor(receiver);
				
			}
			//Well, we have to manually register this one, as it bypasses the command processing...
			this.getCommand("essentialitems").setExecutor(receiver);
			
		}
		
		
		
		this.getServer().getConsoleSender().sendMessage("[EssentialItems]"+ChatColor.GREEN+" Enabled.");
		
	}
	
	
	@Override
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("[EssentialItems]"+ChatColor.GOLD+"Disabling...");
		this.saveKits();
		this.saveMute();
		//Don't save the config, as we may be /reloading and need to keep all of the changes the user has made (if any).  
		//We don't know what those changes are yet, so we have to wait.
		//However, all of our configuration writes all end with a saveConfig() so this is safe to do.
		
		this.getServer().getConsoleSender().sendMessage("[EssentialItems]"+ChatColor.RED+" Disabled.");
	}
	
	public boolean saveMute() {
		try {
			muted.save(location);
		} catch (IOException e) {
			return false;
		}
		return true;
		
		
	}
	
	public boolean saveKits() {
		try {
			kits.save(kitLocation);
		}
		catch(IOException e) {
			return false;
		}
		return true;
	}
	
	
	
	

}
