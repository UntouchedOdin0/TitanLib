package me.titan.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TitanLib implements Listener {

	public static Map<Location, List<Player>> unBreakAbleBlokcs = new HashMap<>();

	@Setter
	@Getter
	public static boolean registerd;
	/**
	 * IF true it will say in every message: "[PluginName] MyMessage".
	 * <h1>Make this false if you don't want it to do that.
	 *
	 *
	 */
	public static boolean showName = true;

	@Setter
	@Getter
	public static JavaPlugin Plugin;

	/**
	 * IF false it will say in every message: "[PluginName] MyMessage".
	 * <h1>Make this true if you don't want it to do that.
	 *
	 *
	 */
	public static void setShowName(boolean showName) {

		TitanLib.showName = showName;

	}

	/**
	 * IF true it will say in every message: "[PluginName] MyMessage".
	 * <h1>Make this false if you don't want it to do that.
	 *
	 *
	 */
	public static boolean isShowName() {

		return showName;
	}
}
