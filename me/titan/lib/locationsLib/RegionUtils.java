package me.titan.lib.locationsLib;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import lombok.NonNull;

public class RegionUtils {
	public static boolean isWithinCuboid(Location loc, Location primary, Location secondary) {
		final Vector primVector = primary.toVector(), secVector = secondary.toVector();

		final double locX = loc.getX();
		final double locY = loc.getY();
		final double locZ = loc.getZ();

		final int x = primVector.getBlockX();
		final int y = primVector.getBlockY();
		final int z = primVector.getBlockZ();

		final int x1 = secVector.getBlockX();
		final int y1 = secVector.getBlockY();
		final int z1 = secVector.getBlockZ();

		if ((locX >= x && locX <= x1) || (locX <= x && locX >= x1))
			if ((locZ >= z && locZ <= z1) || (locZ <= z && locZ >= z1))
				if ((locY >= y && locY <= y1) || (locY <= y && locY >= y1))
					return true;

		return false;
	}

	public static Block[] getBlocks(@NonNull final Location primary, @NonNull final Location secondary) {
		final List<Block> blocks = new ArrayList<>();

		final int topBlockX = (primary.getBlockX() < secondary.getBlockX() ? secondary.getBlockX()
				: primary.getBlockX());
		final int bottomBlockX = (primary.getBlockX() > secondary.getBlockX() ? secondary.getBlockX()
				: primary.getBlockX());

		final int topBlockY = (primary.getBlockY() < secondary.getBlockY() ? secondary.getBlockY()
				: primary.getBlockY());
		final int bottomBlockY = (primary.getBlockY() > secondary.getBlockY() ? secondary.getBlockY()
				: primary.getBlockY());

		final int topBlockZ = (primary.getBlockZ() < secondary.getBlockZ() ? secondary.getBlockZ()
				: primary.getBlockZ());
		final int bottomBlockZ = (primary.getBlockZ() > secondary.getBlockZ() ? secondary.getBlockZ()
				: primary.getBlockZ());

		for (int x = bottomBlockX; x <= topBlockX; x++)
			for (int z = bottomBlockZ; z <= topBlockZ; z++)
				for (int y = bottomBlockY; y <= topBlockY; y++) {
					final Block block = primary.getWorld().getBlockAt(x, y, z);

					if (block != null)
						blocks.add(block);
				}

		return blocks.toArray(new Block[blocks.size()]);
	}
}
