package com.essentialitems;


import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.essentialitems.command.CmdList;
import com.essentialitems.command.CommandInterpreter;
import com.essentialitems.command.CommandReceiver;
import com.essentialitems.command.invsee.InvSeeGui;
import com.essentialitems.command.motd.MotdGui;
import com.essentialitems.event.ChatListener;
import com.essentialitems.event.EventListener;

import org.bukkit.ChatColor;

public class Main extends JavaPlugin {
	
	File location = new File(this.getDataFolder(), "muted.yml");
	
	public FileConfiguration muted = YamlConfiguration.loadConfiguration(location);
	
	@Override
	public void onEnable() {
		this.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.GOLD+"Enabling......");
		new EventListener(this, new ChatListener(this));
		
		this.getServer().getConsoleSender().sendMessage("[EssentialItems] "+ChatColor.GOLD+"Checking Config...");
		ConfigManager.initCheck(this);
		MotdGui.prepItems();
		InvSeeGui.prepItems();
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
		}
		
		
		
		this.getServer().getConsoleSender().sendMessage("[EssentialItems]"+ChatColor.GREEN+" Enabled.");
		
	}
	
	
	@Override
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage("[EssentialItems]"+ChatColor.GOLD+" Saving configuration...");
		this.saveConfig();
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
	
	
	
	
	

}
