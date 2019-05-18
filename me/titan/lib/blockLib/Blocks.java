package me.titan.lib.blockLib;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.titan.lib.TitanLib;

public class Blocks {

	public void addFlag(Block block) {

		TitanLib.unBreakAbleBlokcs.put(block.getLocation(), null);

	}

	public void addFlag(Block block, List<Player> fors) {

		TitanLib.unBreakAbleBlokcs.put(block.getLocation(), fors);

	}

}
