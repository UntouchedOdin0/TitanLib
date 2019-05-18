package me.titan.lib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.titan.lib.enums.LogType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

// This is the common library
public class Common {

	private static JavaPlugin instance = TitanLib.getPlugin();

	/**
	
	
	/**
	 * sends message that display in the middle of the screen.
	 * <p>
	 *
	 *
	 * @param pl What do you think? ITS THE PLAYER!!!
	 * @param title TITLE
	 * @param subtitle SUBTITLE
	 *
	 */

	public static void sendTitle(final Player pl, final String title, final String subtitle) {
		pl.sendTitle(colorize(title), colorize(subtitle), 20, 3 * 20, 10);
	}

	public static String getRandomMessage(final String... messages) {
		final Random rand = new Random();
		return messages[rand.nextInt(messages.length)];

	}

	public static void setMetadate(Metadatable m, String key, Object o) {
		MetadataValue result = new FixedMetadataValue(TitanLib.getPlugin(), o);

		m.setMetadata(key, result);

	}

	public static boolean checkForMetaData(String meta, Metadatable e) {
		if (!e.hasMetadata(meta))
			return false;

		for (MetadataValue v : e.getMetadata(meta))
			return v.getOwningPlugin() == TitanLib.getPlugin();

		return false;

	}

	public static boolean checkPerm(Player pl, String perm) {
		return pl.hasPermission(perm);

	}

	public static void regTitanEvents(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, TitanLib.getPlugin());

	}

	public static void sendBar(final Player pl, final String title) {
		try {
			pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(colorize(title)));

		} catch (final Throwable t) {
			tell(pl, title);
		}
	}

	public static void log(final String... messages) {
		for (final String message : messages)
			log(message);
	}

	public static void log(final String messages) {
		tell(Bukkit.getConsoleSender(), "[" + instance.getName() + "] " + messages);
	}

	public static void log(final String messages, LogType type) {
		String msg = "[" + instance.getName() + "] " + messages;
		Logger l = Logger.getLogger("Minecraft");
		switch (type) {
			case ERROR:
				l.severe(msg);
				break;
			case WARNING:
				l.warning(msg);
				break;
			default:
				tell(Bukkit.getConsoleSender(), "[" + instance.getName() + "] " + messages);
				break;
		}

	}

	public static void tell(final CommandSender toWhom, final String... messages) {
		for (final String message : messages)
			tell(toWhom, message);
	}

	public static Entity getNearestEntityInSight(Player player, int range) {
		List<Entity> entities = player.getNearbyEntities(range, range, range);
		List<Block> sightBlock = player.getLineOfSight(null, range);
		List<Location> sight = new ArrayList<>();
		for (int i = 0; i < sightBlock.size(); i++)
			sight.add(sightBlock.get(i).getLocation());
		for (int i = 0; i < sight.size(); i++)
			for (int k = 0; k < entities.size(); k++)
				if (Math.abs(entities.get(k).getLocation().getX() - sight.get(i).getX()) < 1.3)
					if (Math.abs(entities.get(k).getLocation().getY() - sight.get(i).getY()) < 1.5)
						if (Math.abs(entities.get(k).getLocation().getZ() - sight.get(i).getZ()) < 1.3)
							return entities.get(k);
		return null;
	}

	public static void tell(final CommandSender toWhom, final String message) {

		String prefix = "&8[&c" + TitanLib.getPlugin().getName() + "&8]";
		if (!TitanLib.isShowName())
			toWhom.sendMessage(colorize(prefix + message));
		else
			toWhom.sendMessage(colorize(message));
	}

	public static String colorize(final String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

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

	public static <T> T getOrDefault(final T nullable, final T def) {
		return nullable != null ? nullable : def;
	}

	public static void runLater(final int delay, final BukkitRunnable task) {
		runLater(delay, task);
	}

	public static void runLater(final int delay, final Runnable task) {
		Bukkit.getScheduler().runTaskLater(instance, task, delay);
	}

	public static int getInt(String from) {
		int i = 0;
		try {
			i = Integer.parseInt(from);
		} catch (NumberFormatException e) {
			throw e;
		}
		return i;

	}

	//	public static int getInt(String from, String message, CommandSender sender) {
	//		int i = 0;
	//		try {
	//			i = Integer.parseInt(from);
	//		} catch (IllegalArgumentException e) {
	//			e.printStackTrace();
	//			tell(sender, message);
	//		}
	//		return i;
	//
	//	}

	public static void WaitThenDo(int ford, BukkitRunnable run) {

		new BukkitRunnable() {

			@Override
			public void run() {
				run();

			}
		}.runTaskLater(TitanLib.getPlugin(), ford);

	}

	public static void respawnAfter(int after, Player p) {
		for (int i = 0; i < after; i++) {
			int ff = i - after;
			if (i == after)
				p.spigot().respawn();
			WaitThenDo(i, new BukkitRunnable() {

				@Override
				public void run() {

					sendTitle(p, "&c&lRespawning in &b" + ff + " &c&lseconds", "");
				}
			});
		}

	}

}
