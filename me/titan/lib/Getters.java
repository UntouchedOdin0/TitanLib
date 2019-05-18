package me.titan.lib;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class Getters {
	public static int getInt(String from) {
		int i = 0;
		try {
			i = Integer.parseInt(from);
		} catch (IllegalArgumentException e) {
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
