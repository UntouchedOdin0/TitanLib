package me.titan.lib;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerLib {

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

	public static Player getPlayer(UUID from) {
		Player result;

		result = Bukkit.getPlayer(from);

		return result;

	}

}
