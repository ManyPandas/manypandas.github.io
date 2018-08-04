package com.essentialitems.command.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitEditGui {
	
	public static ItemStack no;
	public static ItemStack finish;
	public static ItemStack cancel;
	public static ItemStack kitName;
	
	
	
	public static void openGui(Player p, Kit kit) {
Inventory finalInv = Bukkit.createInventory(null, 54, ChatColor.GOLD+""+ChatColor.BOLD+"Edit Kit");
		
		//We need to set the kitName button every time.  It is not set within the prepItems() method as it is dynamic with the name of the  kit that is being created.
		
		kitName = new ItemStack(Material.SIGN,1);
		ItemMeta meta = kitName.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Editing kit '"+kit.getName()+"'");
		kitName.setItemMeta(meta);
		
		//Now we get to place our buttons and markers in the appropriate places.
		
		finalInv.setContents(kit.getItems());
		
		
		//Barriers
		finalInv.setItem(27, no);
		finalInv.setItem(28, no);
		finalInv.setItem(29, no);
		finalInv.setItem(30, no);
		finalInv.setItem(31, no);
		finalInv.setItem(32, no);
		finalInv.setItem(33, no);
		finalInv.setItem(34, no);
		finalInv.setItem(35, no);
		finalInv.setItem(37, no);
		finalInv.setItem(38, no);
		finalInv.setItem(39, no);
		finalInv.setItem(41, no);
		finalInv.setItem(42, no);
		finalInv.setItem(43, no);
		finalInv.setItem(45, no);
		finalInv.setItem(46, no);
		finalInv.setItem(47, no);
		finalInv.setItem(48, no);
		finalInv.setItem(49, no);
		finalInv.setItem(50, no);
		finalInv.setItem(51, no);
		finalInv.setItem(52, no);
		finalInv.setItem(53, no);

		
		//Set the extra buttons
		finalInv.setItem(36, finish);
		finalInv.setItem(40, kitName);
		finalInv.setItem(44, cancel);
		
		p.openInventory(finalInv);
		
		
		
	}
	
	
	public static void prepItems() {
		
		{
			no = new ItemStack(Material.BARRIER,1);
			ItemMeta meta = no.getItemMeta();
			meta.setDisplayName(" ");
			no.setItemMeta(meta);
			
		}
		
		{
			finish = new ItemStack(Material.WOOL,1,(short) 5);
			ItemMeta meta = finish.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN+"Save Changes and Exit");
			finish.setItemMeta(meta);
			
		}
		{
			cancel = new ItemStack(Material.WOOL,1,(short)14);
			ItemMeta meta = finish.getItemMeta();
			meta.setDisplayName(ChatColor.RED+"Exit Without Saving Changes");
			cancel.setItemMeta(meta);
			
			
		}
		
	}
	
	public enum buttons {
		NO(no),
		FINISH(finish),
		CANCEL(cancel),
		KITNAME(kitName);
		
		;
		
		private ItemStack item;
		static boolean buttonsSet = false;
		buttons(ItemStack item) {
			this.item = item;
		}
		
		public ItemStack get() {
			if(!buttonsSet) {
				prepItems();
				
			}
			return this.item;
		}
		
		
		
	}
	

}
