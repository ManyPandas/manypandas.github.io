package com.essentialitems.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import com.essentialitems.command.CommandSkeleton;
import com.essentialitems.command.kit.CooldownTicket;
import com.essentialitems.command.kit.Kit;
import com.essentialitems.command.kit.KitCreateGui;
import com.essentialitems.command.kit.KitEditGui;
import com.essentialitems.command.kit.KitInProgress;
import com.essentialitems.Main;
import com.essentialitems.Util;

public class KitCommand extends CommandSkeleton {
	public static final String kitHeader = "["+ChatColor.GOLD+ChatColor.BOLD+"Kit"+ChatColor.RESET+"] ";
	
	public static final ArrayList<KitInProgress> waitingKits = new ArrayList<KitInProgress>();
	
	public static final HashMap<Player, Kit> awaitingDeletes = new HashMap<Player, Kit>();
	
	public static final HashMap<Player, Kit> editingKits = new HashMap<Player, Kit>();
	
	public static final HashMap<Player, Kit> awaitingRenames = new HashMap<Player, Kit>();
	
	
	
	

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		//We can cast the CommandSender to a player immediately.  We have disabled console usage.
		Player psender = (Player) sender;
		
		//Now, simply typing /kit will send the help messages, so we need to see if there are any arguments at all...
		if(args.length == 0) {
			return subcommandHelp(psender);
		}
		
		String kitName = args[0];
		if(kitName.equalsIgnoreCase("help")) {
			//They're not trying to create a kit, they need help
			return subcommandHelp(psender);
		}
		if(kitName.equalsIgnoreCase("list")) {
			return subcommandList(psender, mainClass);
			
		}
		
		
		String subCommand;
		
		try {
			subCommand = args[1];
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			//Crap! There was no sub command provided... we need to equip the kit! (that is, if they have permission)
			
			return noSubCommand(kitName, psender, mainClass);
		}
		//Okay, we have a subcommand, but what is it?
		
		
		//I know, I have methods for the sub commands, however I wrote this code 
		if(subCommand.equalsIgnoreCase("create")) {
			//We get to create a kit!
			
			//Check for permissions.
			
			if(!this.checkPermission("essentialitems.kit.command.create", psender)) {
				psender.sendMessage(CommandReceiver.permHeader+ChatColor.BLUE+"You do not have permission to use this subcommand.");
				return 0;
			}
			//But does it already exist?
			if(kitExists(kitName, mainClass)) {
				//It does...
				return 24;
			}
			else {
				//It sure doesn't!  
				//We need to check for arguments first though.  If we don't have enough info, we can't create the kit.
				if(args.length == 2) {
					//No cooldown provided
					
					
					
					//We don't have to parse an integer so we can open the creation GUI right away and create a KitInProgress
					KitCreateGui.openGui(psender, kitName);
					KitInProgress work = new KitInProgress(kitName, psender);
					waitingKits.add(work);
					return 0;
					
					
					
				}
				if(args.length == 3) {
					//There is a cooldown provided.
					int cooldown;
					
					try {
						
						cooldown = Integer.parseInt(args[2]);
					}
					catch(Exception e) {
						//Invalid integer
						return 3;
					}
					//Next we need to open up the kit creation GUI
					KitCreateGui.openGui(psender, args[0]);
					//Since we can't tell when the button is clicked, we can simply create a KitInProgress object, storing the previously specified data, place it in an ArrayList, then reference it when we get an inventory click.
					//tl;dr: just 2 more things we can do then we can return 0;
					KitInProgress work = new KitInProgress(kitName, cooldown, psender);
					waitingKits.add(work);
					return 0;
					
				}
			}
		}
		if(subCommand.equalsIgnoreCase("delete")) {
			//We get to delete a kit.
			
			//Check permission first...
			if(!this.checkPermission("essentialitems.kit.command.delete", psender)) {
				psender.sendMessage(CommandReceiver.permHeader+ChatColor.BLUE+"You do not have permission to use this subcommand.");
				return 0;
				
			}
			
			//Does it even exist?
			if(!kitExists(kitName, mainClass)) {
				return 20;
			}
			
			//It exists...
			//Run the delete subcommand!
			Kit kit = Kit.getKitFromConfig(kitName, mainClass);
			return subcommandDelete(psender, kit);
		}
		if(subCommand.equalsIgnoreCase("edit")) {
			//We are editing an existing kit.
			
			
			//Check permission
			if(!this.checkPermission("essentialitems.kit.command.edit", psender)) {
				psender.sendMessage(CommandReceiver.permHeader+ChatColor.BLUE+"You do not have permission to use this subcommand.");
				return 0;
			}
			//Check existence
			if(!kitExists(kitName,mainClass)) {
				return 20;
			}
			
			//It exists, run the subcommand...
			Kit kit = Kit.getKitFromConfig(kitName, mainClass);
			
			return subcommandEdit(kit, psender, mainClass);
			
		}
		if(subCommand.equalsIgnoreCase("rename")) {
			//We are renaming an existing kit.
			
			//Check permission
			//Check permission
			if(!this.checkPermission("essentialitems.kit.command.rename", psender)) {
				psender.sendMessage(CommandReceiver.permHeader+ChatColor.BLUE+"You do not have permission to use this subcommand.");
				return 0;
			}
			
			//Check existence
			if(!kitExists(kitName,mainClass)) {
				return 20;
			}
			
			//Our kit exists, run the subcommand
			
			Kit kit = Kit.getKitFromConfig(kitName, mainClass);
			return subcommandRename(psender, kit);
		}
		if(subCommand.equalsIgnoreCase("equip")) {
			//Check argument length.  We need a player argument.
			if(args.length<2) {
				return 22;
			}
			if(!Util.playerOnline(args[2])) {
				return 25;
			}
			else {
				Player p = Bukkit.getPlayer(args[2]);
				Kit kit = Kit.getKitFromConfig(kitName, mainClass);
				
				return subcommandEquip(psender, p, kit);
				
			}
		}
			
		
		
		
		sender.sendMessage(kitHeader+ChatColor.RED+"Unknown sub-command.  Type /kit help for help.");
		return 0;
	}
	
	
	
	private int subcommandHelp(Player p) {
		String header = "["+ChatColor.GREEN+ChatColor.BOLD+"Kit Help"+ChatColor.RESET+"] ";
		p.sendMessage(header+ChatColor.GREEN+ChatColor.BOLD+"EssentialItems Kit Help");
		p.sendMessage(ChatColor.GREEN+"Main Command Usage:  /kit <Name> [Sub Command]");
		p.sendMessage(header+ChatColor.GREEN+ChatColor.BOLD+"Sub Commands:");
		p.sendMessage(ChatColor.GREEN+"If a sub-command is not specified, the system will default to equipping the specified kit if it exists.");
		p.sendMessage(ChatColor.GREEN+"'create' - Create a kit with the specified name if it does not exist already.");
		p.sendMessage(ChatColor.GREEN+"'delete' - Delete an existing kit.");
		p.sendMessage(ChatColor.GREEN+"'edit' - Edit an Existing kit.");
		p.sendMessage(ChatColor.GREEN+"'rename' - Rename an Existing kit.");
		p.sendMessage(ChatColor.GREEN+"'list' - List all usable kits.");
		p.sendMessage(ChatColor.GREEN+"'equip - This sub-command requires an additional player name argument to equip the specified kit on that player.");
		p.sendMessage(header+ChatColor.GREEN+ChatColor.BOLD+"Type '/kit help' to view this message again.");
		
		
		
		
		
		return 0;
	}
	private int subcommandList(Player p, Main mainclass) {
		int kitsListed =0;
		p.sendMessage(kitHeader+ChatColor.GOLD+"All available kits:");
		for(String name : mainclass.kits.getKeys(false)) {
			if(!this.checkPermission("essentialitems.kit."+name, p)) {
				continue;
			}
			if(Util.hasKitCooldown(p,name)) {
				continue;
				
			}
			p.sendMessage(ChatColor.GOLD+name);
			kitsListed++;
			
		}
		if(kitsListed ==0) {
			p.sendMessage(ChatColor.GOLD+"No kits are currently available for use.");
		}
		
		
		
		return 0;
		
	}
	private int subcommandEdit(Kit kit, Player p, Main mainclass) {
		//They want to edit a kit!
		//Open the GUI...
		KitEditGui.openGui(p, kit);
		
		//Now we add them to the HashMap
		editingKits.put(p, kit);
		return 0;
	}
	
	
	private int subcommandDelete(Player p, Kit kit) {
		//We need to first put the player on the HashMap and ask if they really want it deleted.
		
		awaitingDeletes.put(p, kit);
		p.sendMessage(kitHeader+ChatColor.RED+ChatColor.BOLD+"Are you sure you want to delete this kit?  This action cannot be undone. (Y/N)");
		//That's it.  Now we wait for the event listener.
		
		return 0;
		
	}
	private int subcommandRename(Player p, Kit kit) {
		//We need to list the player in the renaming HashMap and ask what they want for the new kit name.
		awaitingRenames.put(p, kit);
		
		p.sendMessage(kitHeader+ChatColor.GOLD+"Please type your new name into chat.  Type '!cancel' to cancel.");
		
		return 0;
		
	}
	private int subcommandEquip(Player p, Player other, Kit kit) {
		
		kit.use(other, true);
		p.sendMessage(kitHeader+ChatColor.GREEN+"Successfully equipped "+other.getName()+" with the '"+kit.getName()+"' kit.");
		return 0;
	}
	
	
	//No sub command is provided.  We'll default to simply equipping the kit.
	private int noSubCommand(String kitName, Player p, Main mainclass) {

		
		//We need to first check if the kit exists at all.
		if(!kitExists(kitName, mainclass)) {
			return 20;
			
		}
		
		//Okay, now we need to retrieve the kit from the storage file.  I have just the thing...
		Kit kit = Kit.getKitFromConfig(kitName, mainclass);
		
		//Now we must check for kit permissions.
		if(!this.checkPermission(kit.getPermission(), p)) {
			//They don't...
			return 21;
		}
		//Next, we must check for kit cooldowns.
		
		if(Util.hasKitCooldown(p, kit)) {
			//They have a cooldown on the selected kit...
			CooldownTicket ct = Util.getCooldown(p.getName());
			
			long nextallowedmillis = kit.getCooldown()*1000+ct.getTimeUsedMillis();
			
			long timeleftmillis = (nextallowedmillis-System.currentTimeMillis());
			
			int timeleftseconds = (int) timeleftmillis/1000;
			
			p.sendMessage(kitHeader+ChatColor.GOLD+"You have "+timeleftseconds+" second"+ (timeleftseconds>1?"s":"")+" before you may use the '"+kit.getName()+"' kit again.");
			return 23;
		}
		
		
		//Now we equip the kit on the player.
		kit.use(p);
		
		
		
		
		
		return 0;
	}
	
	private boolean checkPermission(String permission, Player p) {
		if(p.hasPermission("essentialitems.kit.admin")|| p.hasPermission("essentialitems.admin")) {
			return true;
		}
		return p.hasPermission(permission);
		
	}
	private boolean checkPermission(Permission permission, Player p) {
		if(p.hasPermission("essentialitems.kit.admin")|| p.hasPermission("essentialitems.admin")) {
			return true;
		}
		return p.hasPermission(permission);
		
	}
	
	
	
	
	
	//Returns true if the event must be cancelled
	public static boolean inventoryClick(Player clicker, int slotClicked, ItemStack itemClicked, Inventory gui, Main mainclass) {
		if(gui.getName().equals(ChatColor.GOLD+""+ChatColor.BOLD+"Create Kit")) {
			//Get our KitInProgress (there has to be one with the clickers name in it or we wouldn't be here...)
			KitInProgress waitingKit = getInProgress(clicker);
			
			//However, just in case...
			if(waitingKit == null) {
				//What?  its null... return out... this is an interesting error...
				clicker.closeInventory();
				clicker.sendMessage(CommandReceiver.errHeader+ChatColor.RED+"An error occurred.  The circumstances of this error are, unfortunately, unexplainable.  "
						+ "Please let the plugin developer know about this error message, and let them know it was found by 'KitCommand.java'");
				return true;
			}
			
			//Check what ItemStack was clicked
			
			if(itemClicked.equals(KitCreateGui.buttons.NO.get())) {
				return true;
			}
			if(itemClicked.equals(KitCreateGui.buttons.KITNAME.get())) {
				return true;
			}
			if(itemClicked.equals(KitCreateGui.buttons.CANCEL.get())) {
				//They want to cancel the kit creation:
				
				//Delete the kit in progress with their name on it.
				waitingKits.remove(waitingKit);
				
				//Close the gui.
				clicker.closeInventory();
				//Cancel the event
				return true;
			}
			if(itemClicked.equals(KitCreateGui.buttons.FINISH.get())) {
				
				boolean noItems = true;
				for(int i=0; i<26; i++) {
					if(gui.getItem(i)==null) {
						continue;
						
					}
					else {
						noItems = false;
						break;
					}
					
					
				}
				if(noItems) {
					//They don't have anything inside of the kit item slots.  They have to, or else they can't finish the kit.
					clicker.sendMessage(ChatColor.RED+"You cannot create a kit without any items!");
					return true;
					
					
				}
				//Okay, we can construct a kit from the information we have.  We just have to create an ItemStack array to construct the new kit.
				ArrayList<ItemStack> pre = new ArrayList<ItemStack>();
				
				for(int i=0; i<26; i++) {
					ItemStack item = gui.getItem(i);
					if(item==null) {
						continue;
					}
					pre.add(item);
					
				}
				
				//Convert the ArrayList into an ItemStack array
				ItemStack[] items = pre.toArray(new ItemStack[pre.size()]);
				
				
				
				Kit finalKit = new Kit(waitingKit.getName(), items, waitingKit.getCooldown());
				//The kit (after LOTS of prerequisities) is finally constructed.
				//Now, we get to save the kit and pray that it works.
				finalKit.saveKit(mainclass);
				clicker.closeInventory();
				clicker.sendMessage(kitHeader+ChatColor.GREEN+"Success!  Kit '"+finalKit.getName()+"' has successfully been created! You may equip it with /kit "+finalKit.getName() +". "
						+ "Make sure you and any possible users have the correct permission node!");
				return true;
				
				
			}
			return false;
			
		}
		if(gui.getName().equals(ChatColor.GOLD+""+ChatColor.BOLD+"Edit Kit")) {
			
			if(itemClicked.equals(KitEditGui.buttons.NO.get())) {
				return true;
			}
			
			if(itemClicked.equals(KitEditGui.buttons.CANCEL.get())) {	
				//They don't want to save their changes. (if any)
				editingKits.remove(clicker);
				clicker.closeInventory();
				return true;
				
			}
			
			if(itemClicked.equals(KitEditGui.buttons.KITNAME.get())) {
				return true;
			}
			
			if(itemClicked.equals(KitEditGui.buttons.FINISH.get())) {
				
				//They want to save the kit.
				
				//Get new ItemStack
				boolean noItems = true;
				for(int i=0; i<26; i++) {
					if(gui.getItem(i)==null) {
						continue;
						
					}
					else {
						noItems = false;
						break;
					}
					
					
				}
				if(noItems) {
					//They don't have anything inside of the kit item slots.  They have to, or else they can't finish the kit.
					clicker.sendMessage(ChatColor.RED+"You cannot save a kit without any items!");
					return true;
					
					
				}
				//Okay, we can construct a kit from the information we have.  We just have to create an ItemStack array to construct the new kit.
				ArrayList<ItemStack> pre = new ArrayList<ItemStack>();
				
				for(int i=0; i<26; i++) {
					ItemStack item = gui.getItem(i);
					if(item==null) {
						continue;
					}
					pre.add(item);
					
				}
				
				//Okay, we have an arraylist we can use, we just need to cast it to a List and then write it to the configuration.
				
				List<ItemStack> items = (List<ItemStack>) pre;
				
				//Now we need to get the configuration section the kit resides in.
				
				Kit kit = editingKits.get(clicker);
				
				mainclass.kits.getConfigurationSection(kit.getName()).set("items", items);
				
				mainclass.saveKits();
				
				clicker.closeInventory();
				clicker.sendMessage(ChatColor.GREEN+"Success.  Your changes have been saved.");
				return true;
				
				
				
				
			}
			return false;
			
		}
		return false;
	
		
		
		
		
		
	}
	
	
	public static boolean chatMessage(Player p, String message, Main mainclass) {
		
		
		if(awaitingDeletes.containsKey(p)) {
			if(message.equalsIgnoreCase("y")) {
				Kit kit = awaitingDeletes.get(p);
				kit.delete(mainclass);
				p.sendMessage(kitHeader+ChatColor.GREEN+"Success.  The kit '"+kit.getName()+"' has been deleted.");
				return true;
			}
			if(message.equalsIgnoreCase("n")) {
				p.sendMessage(kitHeader+ChatColor.GREEN+"Cancelled.  The kit has not been deleted.");
				awaitingDeletes.remove(p);
				return true;
			}
			//Not a valid input; don't cancel the event.
			return false;
			
		}
		//Not in the hashmap don't cancel the event.
		//Don't return either, they may be renaming...
		if(awaitingRenames.containsKey(p)) {
			if(message.equalsIgnoreCase("!cancel")) {
				//They want to cancel.  Send them a message saying it was cancelled, then cancel the chat message.
				//Get the kit as we want to tell them what kit they didn't rename...
				p.sendMessage(kitHeader+ChatColor.RED+"Cancelled. "+awaitingRenames.get(p).getName()+" has not been renamed.");
				awaitingRenames.remove(p);
				return true;
				
			}
			else {
				//Get the kit name first...
				String kitName = awaitingRenames.get(p).getName();
				Kit kit = awaitingRenames.get(p);
				kit.rename(message, mainclass);
				p.sendMessage(kitHeader+ChatColor.GREEN+"Successfully renamed "+ kitName+" to "+kit.getName());
				return true;
				
			}
		}
		//They aren't doing anything with the kits... return false
		return false;
	}

	public static KitInProgress getInProgress(Player user) {
		for(KitInProgress kit : waitingKits) {
			if(kit.getUser().equals(user)) {
				return kit;
			}
			continue;
			
		}
		return null;
	}
	
	private boolean kitExists(String name, Main mainclass) {
		if(Kit.getKitFromConfig(name, mainclass) == null) {
			return false;
		}
		return true;
		
	}

}
