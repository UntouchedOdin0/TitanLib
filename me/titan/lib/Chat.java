package me.titan.lib;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;
import me.titan.lib.enums.LogType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

/**
 *
 * This class is to contact with player/console.
 *
 * @author TitanDev
 *
 */

public class Chat {

	private static JavaPlugin instance = TitanLib.getPlugin();
	@Setter
	@Getter
	public static boolean showName = true;

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

	public static void sendBar(final Player pl, final String title) {
		Material m;
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

	public static void log(final List<String> messages, LogType type) {
		List<String> msgs = messages;
		String fmsg = msgs.get(0);
		fmsg = "[" + instance.getName() + "] ";
		msgs.remove(0);
		for (String msg : msgs) {

			Logger l = Logger.getLogger("Minecraft");
			switch (type) {
				case ERROR:
					l.severe(msg);
					break;
				case WARNING:
					l.warning(msg);
					break;
				default:
					tell(Bukkit.getConsoleSender(), msg);
					break;
			}
		}

	}

	public static void log(String fmsg, final List<String> messages, LogType type) {
		Logger l = Logger.getLogger("Minecraft");
		String fmsgf = "[" + instance.getName() + "] " + fmsg;
		switch (type) {
			case ERROR:
				l.severe(fmsgf);
				break;
			case WARNING:
				l.warning(fmsgf);
				break;
			default:
				tell(Bukkit.getConsoleSender(), fmsgf);
				break;
		}

		for (String msg : messages)
			switch (type) {
				case ERROR:
					l.severe(msg);
					break;
				case WARNING:
					l.warning(msg);
					break;
				default:
					tell(Bukkit.getConsoleSender(), msg);
					break;
			}

	}

	public static void log(final List<String> messages) {
		List<String> msgs = messages;
		String fmsg = msgs.get(0);
		fmsg = "[" + instance.getName() + "] ";
		msgs.set(0, fmsg);
		for (String msg : msgs)
			tell(Bukkit.getConsoleSender(), msg);

	}

	public static void log(String fmsg, final List<String> messages) {
		Logger l = Logger.getLogger("Minecraft");
		String fmsgf = "[" + instance.getName() + "] " + fmsg;

		tell(Bukkit.getConsoleSender(), fmsgf);

		for (String msg : messages)
			tell(Bukkit.getConsoleSender(), msg);

	}

	public static void tell(final CommandSender toWhom, final String... messages) {
		for (final String message : messages)
			tell(toWhom, message);
	}

	public static void tell(final CommandSender toWhom, final String message) {
		String prefix;

		prefix = showName ? "&8[&c" + TitanLib.getPlugin().getName() + "&8]&f" : "";
		toWhom.sendMessage(colorize(prefix + message));
	}

	public static String colorize(final String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static void ConvTell(ConversationContext Whom, String message) {
		String prefix;

		prefix = showName ? "&8[&c" + TitanLib.getPlugin().getName() + "&8]&f" : "";
		Whom.getForWhom().sendRawMessage(colorize(prefix + message));

	}

	public static void ConvTell(Conversable Whom, String message) {
		String prefix;

		prefix = showName ? "&8[&c" + TitanLib.getPlugin().getName() + "&8]&f" : "";
		Whom.sendRawMessage(colorize(prefix + message));

	}

}
