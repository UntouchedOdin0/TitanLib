package me.titan.lib;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *  A Simple Class contains Some methods to get a player from his Name or UUID.
 * 
 * @author TitanDev / JustAGamer
 *
 */

public class PlayerLib {

	/**
	 * 
	 * 
	 * @param from - The Name that you want to get a player from.
	 * @return
	 */

	public static Player getPlayer(String from) {
		Player result;
		try {
			result = Bukkit.getPlayer(from);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

			return null;
		}

		return result;

	}

	/**
	 * 
	 * 
	 * @param from - The UUID that you want to get a player from.
	 * @return
	 */
	public static Player getPlayer(UUID from) {
		Player result;

		result = Bukkit.getPlayer(from);

		return result;

	}

}
