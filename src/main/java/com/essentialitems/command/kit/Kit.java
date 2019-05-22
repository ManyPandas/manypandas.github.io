package com.essentialitems.command.kit;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import com.essentialitems.Main;
import com.essentialitems.Util;
import com.essentialitems.command.KitCommand;

public final class Kit {
	
	private String name;
	private ItemStack[] items;
	private Permission permission;
	private int cooldown;
	
	public Kit(String name, ItemStack[] items, int cooldown ) {
		
		this.name = name;
		this.items = items;
		this.cooldown = cooldown;
		
		permission = this.createPermission();
		
		
	}
	//Create a kit with no cooldown
	public Kit(String name, ItemStack[] items) {
		this(name, items, -1);
	}
	
	private Permission createPermission() {
		
		Permission perm = new Permission("essentialitems.kit."+this.getName().toLowerCase());
		return perm;
		
	}
	
	public void use(Player p) {
		
		Inventory pInv = p.getInventory();
		
		pInv.addItem(items);
		
		p.sendMessage(KitCommand.kitHeader+ChatColor.GOLD+"You have been equipped with the '"+this.name+"' kit.");
		
		if(this.cooldown ==-1) {
			//We don't even need to add a cooldown ticket.  There is no cooldown for this kit.
			return;
		}
		
		CooldownTicket ticket = new CooldownTicket(this,p,System.currentTimeMillis());
		Util.kitCooldowns.add(ticket);
		
		
	}
	public void use(Player p, boolean equipOther) {
		//If equipOther is true, then we know that the 'equip' subcommand is being run and that the cooldown should be bypassed.
		if(equipOther) {
			Inventory pInv = p.getInventory();
			
			pInv.addItem(items);
			
			p.sendMessage(KitCommand.kitHeader+ChatColor.GOLD+"You have been equipped with the '"+this.name+"' kit.");
			return;
		}
		Inventory pInv = p.getInventory();
		
		pInv.addItem(items);
		
		p.sendMessage(KitCommand.kitHeader+ChatColor.GOLD+"You have been equipped with the '"+this.name+"' kit.");
		
		if(this.cooldown ==-1) {
			//We don't even need to add a cooldown ticket.  There is no cooldown for this kit.
			return;
		}
		
		CooldownTicket ticket = new CooldownTicket(this,p,System.currentTimeMillis());
		Util.kitCooldowns.add(ticket);
		return;
		
	}
	
	public void saveKit(Main mainclass) {
		
		if(!mainclass.kits.isConfigurationSection(this.name)) {
			mainclass.kits.createSection(this.name);
			mainclass.saveConfig();
			
		}
		
		if(!mainclass.kits.getConfigurationSection(this.name).isConfigurationSection("items")) {
			mainclass.kits.getConfigurationSection(this.name).createSection("items");
		}
		mainclass.kits.getConfigurationSection(this.name).set("name", this.name);
		mainclass.kits.getConfigurationSection(this.name).set("cooldown", this.cooldown);
		
		List<ItemStack> itemsList = (List<ItemStack>) Arrays.asList(items);
		
		//We can write an arraylist to the configuration because it is serializeable and ItemStacks are serializeable as well
		mainclass.kits.getConfigurationSection(this.name).set("items", itemsList);
		
		mainclass.saveConfig();
		
		
	}
	
	public void rename(String name, Main mainclass) {
		
		//Before we rename ourselves, we need to remove our old reference in the kit storage system.
		this.delete(mainclass);
		//We're still alive!  Now we need to change our name...
		this.name = name;
		//Then save...
		this.saveKit(mainclass);
		//Done!
		
		
	}
	public void delete(Main mainclass) {
		mainclass.kits.set(this.name, null);
		mainclass.saveConfig();
	}
	
	
	public String getName() {
		
		return this.name;
	}
	
	public int getCooldown() {
		
		return this.cooldown;
		
	}
	
	public ItemStack[] getItems() {
		
		return this.items;
		
	}
	
	public Permission getPermission() {
		return this.permission;
	}
	
	public static Kit getKitFromConfig(String kitName, Main mainclass) {
		if(!mainclass.kits.isConfigurationSection(kitName)) {
			return null;
		}
		
		ConfigurationSection kitInfo  = mainclass.kits.getConfigurationSection(kitName);
		
		String name = kitInfo.getString("name");
		int cooldown = kitInfo.getInt("cooldown");
		
		@SuppressWarnings("unchecked")
		List<ItemStack> itemsList = (List<ItemStack>) kitInfo.get("items");
		
		Object[] itemsPre =itemsList.toArray();
		
		ItemStack[] items = new ItemStack[itemsPre.length];
		
		int arrIndex =0;
		for(int i=0; i<itemsPre.length; i++) {
			if(itemsPre[i]== null) {
				continue;
			}
			items[arrIndex]=(ItemStack)itemsPre[i];
			arrIndex++;
		}
			
		
		return new Kit(name, items, cooldown);
	}
	
	
	
		
		
	

	
		
		
}
	
	
	
	
	
	
	
	


