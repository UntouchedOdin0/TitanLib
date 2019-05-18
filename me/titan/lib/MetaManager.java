package me.titan.lib;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

public class MetaManager {

	public static void changeMetaObject(Metadatable m, String meta, Object newObject) {
		if (!Common.checkForMetaData(meta, m))
			return;
		m.removeMetadata(meta, TitanLib.getPlugin());
		Common.setMetadate(m, meta, newObject);

	}

	public static void setMetaData(Metadatable m, String key, Object value) {

		MetadataValue v = new FixedMetadataValue(TitanLib.getPlugin(), value);
		m.setMetadata(key, v);

	}

	public static Object getMetaObject(String meta, Metadatable m) {

		if (!Common.checkForMetaData(meta, m))
			return null;

		return m.getMetadata(meta).get(0).value();

	}

	public static void changeMetaName(Metadatable m, String oldMeta, String newMeta) {
		if (!Common.checkForMetaData(oldMeta, m))
			return;

		Object o = m.getMetadata(oldMeta).get(0);

		m.removeMetadata(oldMeta, TitanLib.getPlugin());
		Common.setMetadate(m, newMeta, o);

	}

	public static void replaceMeta(Metadatable m, String oldMeta, String newMeta, Object newObject) {
		if (!Common.checkForMetaData(oldMeta, m))
			return;

		//Object o = m.getMetadata(oldMeta).get(0);

		m.removeMetadata(oldMeta, TitanLib.getPlugin());
		Common.setMetadate(m, newMeta, newObject);

	}

}
