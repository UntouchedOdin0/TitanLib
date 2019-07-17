package me.titan.lib;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

/**
 *  Simple Class to get anything from anything(from String Usually).
 * 
 * @author TitanDev / JustAgamer
 *
 */

public class Getters {
	/**
	 * 
	 * Gets an int from a string.
	 * 
	 * @param from
	 * @return int.
	 */
	public static int getInt(String from) {
		int i = 0;
		try {
			i = Integer.parseInt(from);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return i;

	}

	/**
	 * 
	 * Gets an int from a string.
	 * 
	 * @param from 
	 * @param sender - Can be null if you don't want to send message to player if the String isn't a number.
	 * @param failMessage - If null it will send the default message (See Below)
	 * @return
	 */
	public static int getInt(String from, CommandSender sender, String failMessage) {
		int i = 0;
		try {
			i = Integer.parseInt(from);
		} catch (IllegalArgumentException e) {
			if (sender != null && !failMessage.equals("")) {
				Chat.tell(sender, failMessage);
				return 0;
			} else if (sender != null && failMessage.equals("")) {
				// Default message:
				Chat.tell(sender, "&4Error While performing a command, An argument must be a number but it's not.");
				return 0;
			} else
				e.printStackTrace();
		}
		return i;

	}

	public static Material getMaterial(String from, CommandSender sender) {

		Material result;

		try {

			result = Material.getMaterial(from.toUpperCase());

		} catch (IllegalArgumentException ex) {

			Chat.tell(sender, "&4This Material does not exist.");
			return null;
		}

		return result;

	}
}
