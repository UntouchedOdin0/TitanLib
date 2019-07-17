package me.titan.lib.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @deprecated - May not work perfectly
 * 
 * @author TitanDev / JustAgamer
 *
 */
@Deprecated
public class Items {

	public String Name;
	public Material mat;
	public int data;

	public ItemStack newItem(Material mat, String Name, String... lore) {
		ItemStack result = new ItemStack(mat, 1);
		ItemMeta m = result.getItemMeta();
		m.setDisplayName(Name);
		return null;
	}

}
