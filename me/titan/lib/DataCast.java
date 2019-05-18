package me.titan.lib;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

public class DataCast {

	public static int toInt(Number nums) {
		int result = 0;
		try {
			result = (int) nums;
		} catch (ClassCastException e) {

			e.printStackTrace();

		}
		return result;

	}

	/**
	 *
	 *
	 * X = "x"; <br>
	 * Y = "y";<br>
	 * Z = "z";<br>
	 * YAW = "yaw";<br>
	 * PITCH = "p";<br>
	 *
	 * @param loc
	 * @return
	 */

	public static Map<String, Integer> intLocation(Location loc) {
		Map<String, Integer> result = new HashMap<>();
		result.put("x", (int) loc.getX());
		result.put("y", (int) loc.getY());
		result.put("z", (int) loc.getZ());
		result.put("yaw", (int) loc.getYaw());
		result.put("p", (int) loc.getPitch());

		return result;

	}

}
