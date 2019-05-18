package me.titan.lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigUtils extends YamlConfiguration {

	private static JavaPlugin instance = TitanLib.getPlugin();
	private final File file;

	/**
	 * The default file, can be null
	 */
	private final YamlConfiguration defaults;

	/**
	 * Optionally, you can set the header that will appear when the file
	 * gets edited automatically.
	 *
	 * Call {@link #setHeader(String[])} to use this.
	 */
	private String[] editHeader;

	/**
	 * Path prefix is what appears automatically before the path, when getting keys from the file.
	 *
	 * Example: if you have a PlayerCache class that will always refer to a certain player,
	 * 			you can set his unique id as the pathPrefix to save you time and energy while getting his values,
	 * 			so instead of typing getString(id + ".key") you only need to type getString("key")
	 *
	 * Only works when the default file does not exist! (logically, in the example above you cannot create default
	 * values for each player out there :))
	 */
	private String pathPrefix;

	/**
	 * Makes a new ConfigUtils instance that will manage one configuration file.
	 *
	 * NB: Make sure you created the file with the exact same name and all
	 *     the default values inside of your plugin in the src/main/resources folder!
	 *
	 * @param fileName, the name of the configuration file, e.g. settings.yml
	 */
	public ConfigUtils(final String fileName) {
		this(fileName, true);
	}

	/**
	 * Makes a new instance with an optional default file (see above).
	 *
	 * @param fileName
	 * @param useDefaults, require the default file? see commentaries to the above constructor
	 */
	public ConfigUtils(final String fileName, final boolean useDefaults) {

		// First, set the defaults from which we update your config.
		// The defaults are in your src/main/resources folder in your
		if (useDefaults) {
			// Now we use the file in your plugin .jar as defaults for updating the file on the disk.
			defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(
					ConfigUtils.class.getResourceAsStream("/" + fileName), StandardCharsets.UTF_8));
			Objects.requireNonNull(defaults, "Could not get the default " + fileName
					+ " inside of your plugin, make sure you created the file and that you did not replace the jar on a running server!");

		} else
			defaults = null;

		// Now copy the file from your plugin .jar to the disk (if it doesn't exist)
		file = extract(fileName);

		// Finally, load or update the configuration.
		loadConfig();
	}

	public void addMap(String path, Map<String, Object> map) {

		for (String keys : map.keySet())
			for (Object values : map.values())
				this.set(path + "." + keys, values);
	}

	public Map<String, Object> getMap(String path) {
		Map<String, Object> map = new HashMap<>();

		for (String item : this.getConfigurationSection(path).getKeys(false))
			map.put(item, this.get(path + ".item"));

		return map;

	}

	/**
	 * Set what header will appear when the file is automatically updated.
	 *
	 * Due to the way of how Bukkit stores .yml files, all your # comments are lost
	 * when the file is updated except for this header. You can inform the users
	 * where they can find the default files with documentation.
	 *
	 * @param editHeader, the edit header
	 */
	public void setHeader(final String[] editHeader) {
		this.editHeader = editHeader;
	}

	/**
	 * Gets the header that is applied when the file is updated, or null if not set.
	 *
	 * @return the edit header
	 */
	public String[] getEditHeader() {
		return editHeader;
	}

	/**
	 * Set the new default {@link #pathPrefix}
	 *
	 * @param pathPrefix, the new path prefix, or use null to un-set
	 */
	public void setPathPrefix(final String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	/**
	 * Saves the file on the disk and loads it again.
	 */
	public void reloadConfig() {
		saveConfig();

		loadConfig();
	}

	/**
	 * Writes a key with a value to your file.
	 * Example: write("weather.disable", true)
	 *
	 * @param path, the path, use '.' to split sections
	 * @param value, the value, can be a primitive, a String, HashMap or a Collection (List, or a Set)
	 */
	public void write(final String path, final Object value) {
		set(path, value);

		reloadConfig();
	}

	// Saves the file on the disk and copies the {@link #editHeader} if exists.
	public void saveConfig() {
		try {

			// Copy the header
			if (editHeader != null) {
				options().header(StringUtils.join(editHeader, System.lineSeparator()));
				options().copyHeader(true);
			}

			// Call parent method for saving
			super.save(file);

		} catch (final IOException ex) {
			System.out.println("Failed to save configuration from " + file);

			ex.printStackTrace();
		}
	}

	// Loads the configuration from the disk
	private void loadConfig() {
		try {

			// Call parent method for loading
			super.load(file);

		} catch (final Throwable t) {
			System.out.println("Failed to load configuration from " + file);

			t.printStackTrace();
		}
	}

	/**
	 * Gets an unspecified value from your file, so you must cast it to your desired value (example: (boolean) get("disable.this.feature", true))
	 * The "def" is the default value, must be null since we use default values from your file in your .jar.
	 */
	@Override
	public Object get(String path, final Object def) {
		if (defaults != null) {

			if (def != null && !def.getClass().isPrimitive() && !PrimitiveWrapper.isWrapperType(def.getClass()))
				throw new IllegalArgumentException(
						"The default value must be null since we use defaults from file inside of the plugin! Path: "
								+ path + ", default called: " + def);

			if (super.get(path, null) == null) {
				final Object defaultValue = defaults.get(path);
				Objects.requireNonNull(defaultValue,
						"Default " + file.getName() + " in your .jar lacks a key at '" + path + "' path");

				Common.log("&fUpdating &a" + file.getName() + "&f. Set '&b" + path + "&f' to '" + defaultValue + "'");
				write(path, defaultValue);
			}
		}

		// hacky workaround: prevent infinite loop due to how get works in the parent class
		final String m = new Throwable().getStackTrace()[1].getMethodName();

		// Add path prefix, but only when the default file doesn't exist
		if (defaults == null && pathPrefix != null && !m.equals("getConfigurationSection") && !m.equals("get"))
			path = pathPrefix + "." + path;

		return super.get(path, null);
	}

	@Override
	public void set(String path, final Object value) {
		// hacky workaround: prevent infinite loop due to how get works in the parent class
		final String m = new Throwable().getStackTrace()[1].getMethodName();

		// Add path prefix, but only when the default file doesn't  exist
		if (defaults == null && pathPrefix != null && !m.equals("getConfigurationSection") && !m.equals("get"))
			path = pathPrefix + "." + path;

		super.set(path, value);
	}

	// Extract the file from your jar to the plugins/YourPlugin folder.
	// Does nothing if the file exists
	private File extract(final String path) {
		final JavaPlugin i = instance;
		final File file = new File(i.getDataFolder(), path);

		if (file.exists())
			return file;

		// Create empty file and all necessary directories
		createFileAndDirectory(path);

		if (defaults != null)
			try (InputStream is = i.getResource(path)) {
				Objects.requireNonNull(is, "Inbuilt file not found: " + path);

				// Now copy the content of the default file to there
				Files.copy(is, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);

			} catch (final IOException e) {
				e.printStackTrace();
			}

		return file;
	}

	// Creates YourPlugin folder in plugins/ and the necessary file (e.g. settings.yml)
	private File createFileAndDirectory(final String path) {

		// The data folder is your plugin's folder with your plugin's name inside plugins/ folder.
		final File datafolder = instance.getDataFolder();
		final int lastIndex = path.lastIndexOf('/');
		final File directory = new File(datafolder, path.substring(0, lastIndex >= 0 ? lastIndex : 0));

		// Create all directories if necessary
		directory.mkdirs();

		final File destination = new File(datafolder, path);

		try {
			destination.createNewFile();

		} catch (final IOException ex) {
			System.out.println("Failed to create file " + path);

			ex.printStackTrace();
		}

		return destination;
	}

	// A helper class
	private static final class PrimitiveWrapper {
		private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

		private static boolean isWrapperType(final Class<?> clazz) {
			return WRAPPER_TYPES.contains(clazz);
		}

		private static Set<Class<?>> getWrapperTypes() {
			final Set<Class<?>> ret = new HashSet<>();
			ret.add(Boolean.class);
			ret.add(Character.class);
			ret.add(Byte.class);
			ret.add(Short.class);
			ret.add(Integer.class);
			ret.add(Long.class);
			ret.add(Float.class);
			ret.add(Double.class);
			ret.add(Void.class);
			return ret;
		}
	}

}
