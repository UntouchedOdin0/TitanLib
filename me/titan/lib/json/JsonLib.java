package me.titan.lib.json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import me.titan.lib.TitanLib;

public class JsonLib {

	public static void writeJSON(String fileName, String subPath, String object, String value) {
		JSONObject main = new JSONObject();

		main.put(object, value);

		try {
			File file = new File(TitanLib.getPlugin().getDataFolder() + File.separator + fileName + ".json");
			File filePath = new File(TitanLib.getPlugin().getDataFolder() + File.separator + subPath);
			filePath.mkdirs();
			if (!file.exists())
				file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(main.toJSONString());
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
		}
	}

	public static String readJSON(String fileName, String subPath, String object) {
		String var = null;
		try {
			JSONParser parser = new JSONParser();

			File file = new File(TitanLib.getPlugin().getDataFolder() + File.separator + subPath + fileName + ".json");
			Object obj = parser.parse(new FileReader(file));

			JSONObject jsonObject = (JSONObject) obj;

			var = (String) jsonObject.get(object);

		} catch (Exception e) {
		}
		return var;
	}

}
