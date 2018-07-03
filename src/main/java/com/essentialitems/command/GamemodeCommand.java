package com.essentialitems.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.essentialitems.Main;
import com.essentialitems.Util;

public final class GamemodeCommand extends CommandSkeleton {
	
	private static String header ="["+ChatColor.GREEN+ChatColor.BOLD+"GameMode"+ChatColor.RESET+"] ";

	@Override
	public int run(String[] args, Main mainClass, CommandSender sender) {
		
		if(args.length>1) {
			
			if(args[1].equalsIgnoreCase("all")) {
				
				if(args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getName().equals(sender.getName())) {
							continue;
						}
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage(header+ChatColor.BLUE+sender.getName()+ChatColor.GREEN+" set your Gamemode to "+ChatColor.BLUE+"CREATIVE"+ChatColor.RESET+ChatColor.GREEN+" mode.");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
						
					}
					sender.sendMessage(header+ChatColor.GREEN+"Set all online players' gamemode to"+ChatColor.BLUE+" CREATIVE "+ChatColor.GREEN+"mode.");
					return 0;
					
				}
				else if(args[0].equalsIgnoreCase("survival")|| args[0].equalsIgnoreCase("0")|| args[0].equalsIgnoreCase("s")) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getName().equals(sender.getName())) {
							continue;
						}
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(header+ChatColor.BLUE+sender.getName()+ChatColor.GREEN+" set your Gamemode to "+ChatColor.BLUE+"SURVIVAL"+ChatColor.RESET+ChatColor.GREEN+" mode.");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
					}
					sender.sendMessage(header+ChatColor.GREEN+"Set all online players' gamemode to"+ChatColor.BLUE+" SURVIVAL "+ChatColor.GREEN+"mode.");
					return 0;
					
				}
				else if(args[0].equalsIgnoreCase("spectator")|| args[0].equalsIgnoreCase("3")|| args[0].equalsIgnoreCase("sp")) {
					for(Player p: Bukkit.getOnlinePlayers()) {
						if(p.getName().equals(sender.getName())) {
							continue;
						}
						p.setGameMode(GameMode.SPECTATOR);
						p.sendMessage(header+ChatColor.BLUE+sender.getName()+ChatColor.GREEN+" set your Gamemode to "+ChatColor.BLUE+"SPECTATOR"+ChatColor.RESET+ChatColor.GREEN+" mode.");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
					}
					sender.sendMessage(header+ChatColor.GREEN+"Set all online players' gamemode to"+ChatColor.BLUE+" SPECTATOR "+ChatColor.GREEN+"mode.");
					return 0;
					
				}
				else if(args[0].equalsIgnoreCase("adventure")|| args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) {
					for(Player p: Bukkit.getOnlinePlayers()) {
						if(p.getName().equals(sender.getName())) {
							continue;
						}
						p.setGameMode(GameMode.ADVENTURE);
						p.sendMessage(header+ChatColor.BLUE+sender.getName()+ChatColor.RESET+ChatColor.GREEN+" set your Gamemode to "+ChatColor.BLUE+"ADVENTURE"+ChatColor.RESET+ChatColor.GREEN+" mode.");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
					}
					sender.sendMessage(header+ChatColor.GREEN+"Set all online players' gamemode to"+ChatColor.BLUE+" ADVENTURE "+ChatColor.GREEN+"mode.");
					return 0;
				}
				else {
					return 3;
				}
				
			}
			else {
				if(!Util.playerOnline(args[1])) {
					return 4;
				}
				else {
					Player p = Bukkit.getPlayer(args[1]);
					if(sender.getName().equals(p.getName())) {
						sender.sendMessage(header+ChatColor.GREEN+"You don't need to specify your own name to set your own game mode.");
						return 7;
					}
 					if(args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) {
						//Creative
 						if(p.getGameMode().equals(GameMode.CREATIVE)) {
 							sender.sendMessage(header+ChatColor.BLUE+args[1]+ChatColor.GREEN+" is already in gamemode "+ChatColor.BLUE+"CREATIVE");
 							return 0;
 							
 						}
						p.setGameMode(GameMode.CREATIVE);
						p.sendMessage(header+ChatColor.BLUE+sender.getName()+ChatColor.GREEN+" set your Gamemode to "+ChatColor.BLUE+"CREATIVE"+ChatColor.RESET+ChatColor.GREEN+" mode.");
						sender.sendMessage(header+ChatColor.GREEN+"Set "+ChatColor.BLUE+args[1]+"'s"+ChatColor.GREEN+" gamemode to "+ ChatColor.BLUE+"CREATIVE"+ChatColor.GREEN+" mode.");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
						return 0;
						
					}
 					else if(args[0].equalsIgnoreCase("survival")|| args[0].equalsIgnoreCase("0")|| args[0].equalsIgnoreCase("s")) {
						if(p.getGameMode().equals(GameMode.SURVIVAL)) {
							sender.sendMessage(header+ChatColor.BLUE+args[1]+ChatColor.GREEN+" is already in gamemode "+ChatColor.BLUE+"SURVIVAL");
 							return 0;
						}
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(header+ChatColor.BLUE+sender.getName()+ChatColor.GREEN+" set your Gamemode to "+ChatColor.BLUE+"SURVIVAL"+ChatColor.RESET+ChatColor.GREEN+" mode.");
						sender.sendMessage(header+ChatColor.GREEN+"Set "+ChatColor.BLUE+args[1]+"'s"+ChatColor.GREEN+" gamemode to "+ ChatColor.BLUE+"SURVIVAL"+ChatColor.GREEN+" mode.");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
						return 0;
					}
 					else if(args[0].equalsIgnoreCase("spectator")|| args[0].equalsIgnoreCase("3")|| args[0].equalsIgnoreCase("sp")) {
						if(p.getGameMode().equals(GameMode.SPECTATOR)) {
							sender.sendMessage(header+ChatColor.BLUE+args[1]+ChatColor.GREEN+" is already in gamemode "+ChatColor.BLUE+"SPECTATOR");
 							return 0;
 							
						}
						p.setGameMode(GameMode.SPECTATOR);
						p.sendMessage(header+ChatColor.BLUE+sender.getName()+ChatColor.GREEN+" set your Gamemode to "+ChatColor.BLUE+"SPECTATOR"+ChatColor.RESET+ChatColor.GREEN+" mode.");
						sender.sendMessage(header+ChatColor.GREEN+"Set "+ChatColor.BLUE+args[1]+"'s"+ChatColor.GREEN+" gamemode to "+ ChatColor.BLUE+"SPECTATOR"+ChatColor.GREEN+" mode.");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
						return 0;
					}
 					else if(args[0].equalsIgnoreCase("adventure")|| args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) {
						if(p.getGameMode().equals(GameMode.ADVENTURE)) {
							sender.sendMessage(header+ChatColor.BLUE+args[1]+ChatColor.GREEN+" is already in gamemode "+ChatColor.BLUE+"ADVENTURE");
 							return 0;
 							
						}
						p.setGameMode(GameMode.ADVENTURE);
						p.sendMessage(header+ChatColor.BLUE+sender.getName()+ChatColor.RESET+ChatColor.GREEN+" set your Gamemode to "+ChatColor.BLUE+"ADVENTURE"+ChatColor.RESET+ChatColor.GREEN+" mode.");
						sender.sendMessage(header+ChatColor.GREEN+"Set "+ChatColor.BLUE+args[1]+"'s"+ChatColor.GREEN+" gamemode to "+ ChatColor.BLUE+"ADVENTURE"+ChatColor.GREEN+" mode.");
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
						return 0;
					}
 					else {
 						return 3;
 					}
				}
			}
			
		}
		else {
			Player p = (Player) sender;
			//Setting game mode of the user.
			if(args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) {
				if(p.getGameMode().equals(GameMode.CREATIVE)) {
					sender.sendMessage(header+ChatColor.GREEN+"You are already in gamemode "+ChatColor.BLUE+"CREATIVE");
					return 0;
						
				}
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage(header+ChatColor.GREEN+"Set own gamemode to "+ChatColor.BLUE+"CREATIVE"+ChatColor.RESET+ChatColor.GREEN+" mode.");
				p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
				return 0;
				
			}
			else if(args[0].equalsIgnoreCase("survival")|| args[0].equalsIgnoreCase("0")|| args[0].equalsIgnoreCase("s")) {
				if(p.getGameMode().equals(GameMode.SURVIVAL)) {
					sender.sendMessage(header+ChatColor.GREEN+"You are already in gamemode "+ChatColor.BLUE+"SURVIVAL");
					return 0;
				}
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(header+ChatColor.GREEN+"Set own gamemode to "+ChatColor.BLUE+"SURVIVAL"+ChatColor.RESET+ChatColor.GREEN+" mode.");
				p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
				return 0;
				//Survival
			}
			else if(args[0].equalsIgnoreCase("spectator")|| args[0].equalsIgnoreCase("3")|| args[0].equalsIgnoreCase("sp")) {
				if(p.getGameMode().equals(GameMode.SPECTATOR)) {
					sender.sendMessage(header+ChatColor.GREEN+"You already in gamemode "+ChatColor.BLUE+"SPECTATOR");
					return 0;
						
				}
				p.setGameMode(GameMode.SPECTATOR);
				p.sendMessage(header+ChatColor.GREEN+"Set own gamemode to "+ChatColor.BLUE+"SPECTATOR"+ChatColor.RESET+ChatColor.GREEN+" mode.");
				p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
				return 0;
			}
			else if(args[0].equalsIgnoreCase("adventure")|| args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) {
				if(p.getGameMode().equals(GameMode.ADVENTURE)) {
					sender.sendMessage(header+ChatColor.GREEN+"You are already in gamemode "+ChatColor.BLUE+"ADVENTURE");
					return 0;
						
				}
				p.setGameMode(GameMode.ADVENTURE);
				p.sendMessage(header+ChatColor.GREEN+"Set own gamemode to "+ChatColor.BLUE+"ADVENTURE"+ChatColor.RESET+ChatColor.GREEN+" mode.");
				p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20000, 1);
				return 0;
				
				
				
			}
			else {
				return 3;
			}
			
		}
	}

}
