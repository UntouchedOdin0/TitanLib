package me.titan.lib;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

public class PReg {

	public static void registerCommand(final Command command) {
		try {
			final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);

			final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
			commandMap.register(command.getLabel(), command);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
