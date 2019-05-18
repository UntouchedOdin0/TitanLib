package me.titan.lib.blockLib;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.titan.lib.TitanLib;

public class BlockListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {

		Player p = e.getPlayer();
		Block b = e.getBlock();

		if (TitanLib.unBreakAbleBlokcs != null && !TitanLib.unBreakAbleBlokcs.containsKey(b.getLocation()))
			return;

		List<Player> bp = TitanLib.unBreakAbleBlokcs.get(b.getLocation());

		if (bp != null && !bp.contains(p))
			return;

		e.setCancelled(true);

	}

}
