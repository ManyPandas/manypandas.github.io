package com.essentialitems.command.warp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.essentialitems.Main;
import com.essentialitems.Util;

public class Warp {
	
	private String name;
	
	private double x;
	private double y;
	private double z;
	private World world;
	private String permission;
	
	
	public Warp(String name, Location loc) {
		this(name, loc.getWorld(),loc.getX(),loc.getY(),loc.getZ());
		
	
	}
	public Warp(String name, World world,double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.world= world;
		this.name = name;
		this.permission = "essentialitems.warp."+name.toLowerCase();
	}
	
	
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	public String getPermission() {
		return this.permission;
	}
	
	
	
	public World getWorld() {
		return this.world;
	}
	public String getName() {
		return this.name;
	}
	
	
	public void warp(Player player) {
		Location warpLocation = new Location(this.world,this.x,this.y,this.z,player.getLocation().getYaw(),player.getLocation().getPitch());
		
		
		Main mainclass = Main.getInstance();
		
		long delay = mainclass.getConfig().getLong("warpDelay") *20;
		
		player.sendMessage(ChatColor.GOLD+"You will be teleported to "+this.name+" in "+delay/20+" seconds. Do not move in any way.");
		
		
		int taskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) mainclass, new Runnable() {
			public void run() {
				player.teleport(warpLocation);
				//I know this is a BIT risky.  
				//If the server owners configure for no delay then weird things may happen.
				//I'm going to hope not, but we'll see.
				if(Util.warpTasks.containsKey(player)) {
					Util.warpTasks.remove(player);
				}
				player.sendMessage(ChatColor.GREEN+"You have been warped.");
				
			}
		}, delay);
		
		Util.warpTasks.put(player, taskId);
		
		
		
		
		
		
	}
	public void warp(Player player, boolean delay) {
		if(delay) {
			this.warp(player);
		}
		else {
			player.teleport(new Location(this.world,this.x,this.y,this.z,player.getLocation().getYaw(),player.getLocation().getPitch()));
		}
	}
	public void save() {
		Main mainclass = Main.getInstance();
		if(!mainclass.warps.isConfigurationSection(this.name)) {
			//No section
			mainclass.warps.createSection(name);
			
		}
		
		ConfigurationSection section = mainclass.warps.getConfigurationSection(this.name);
		
		section.set("x", this.x);
		section.set("y", this.y);
		section.set("z", this.z);
		section.set("world", this.world.getName());
		mainclass.saveConfig();
			
		
		
		
	}
	public boolean hasPermission(Player player) {
		return player.hasPermission(this.getPermission());
	}
	
	public static Warp getFromConfig(String name) {
		Main mainclass = Main.getInstance();
		
		if(!mainclass.warps.isConfigurationSection(name)) {
			return null;
		}
		
		ConfigurationSection section = mainclass.warps.getConfigurationSection(name);
		
		double x = section.getDouble("x");
		double y = section.getDouble("y");
		double z = section.getDouble("z");
		World world = Bukkit.getServer().getWorld(section.getString("world"));
		if(world == null) {
			return null;
		}
		
		return new Warp(name, world, x, y, z);
		
		
		
		
	}
	
	
	

}
