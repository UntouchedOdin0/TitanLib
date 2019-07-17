package me.titan.lib.locationsLib;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Lever;
import org.bukkit.material.MaterialData;

import com.google.common.base.Preconditions;

public class TitanLoc {

    public static Location add(Location loc, double x, double y, double z) {
	Location result = loc;

	result.setX(loc.getX() + x);
	result.setY(loc.getY() + y);
	result.setZ(loc.getZ() + z);
	return result;

    }

    public static Location remove(Location loc, double x, double y, double z) {
	Location result = loc;

	result.setX(loc.getX() - x);
	result.setY(loc.getY() - y);
	result.setZ(loc.getZ() - z);
	return result;

    }

    public static void setLever(Block b) {
	b.setType(Material.LEVER);
	if (b.getState().getData() instanceof org.bukkit.material.Lever) {
	    System.out.println("Not a lever...");
	    return;

	}
	MaterialData blockState = b.getState().getData();

	Lever l = (Lever) blockState;
	l.setFacingDirection(BlockFace.UP);
	b.getState().update();

    }

    public static Location getCenterLoc(Location loc1, Location loc2) {
	if (!loc1.getWorld().equals(loc2.getWorld()))
	    return null;

	double x1 = loc1.getX();
	double x2 = loc2.getX();

	double z1 = loc1.getZ();
	double z2 = loc2.getZ();

	double y1 = loc1.getY();
	double y2 = loc2.getY();

	double x3 = (x1 + x2 / 2);
	double z3 = (z1 + z2 / 2);
	double y3 = (y1 + y2 / 2);

	return new Location(loc1.getWorld(), x3, y3, z3);

    }

    public static double getSMA(double... num) {

	double result = 0;
	for (double i : num)
	    result += i;

	return result / num.length;

    }

    public static Location getRandomLocation(Location loc1, Location loc2) {
	Preconditions.checkArgument(loc1.getWorld() == loc2.getWorld());
	double minX = Math.min(loc1.getX(), loc2.getX());
	double minY = Math.min(loc1.getY(), loc2.getY());
	double minZ = Math.min(loc1.getZ(), loc2.getZ());

	double maxX = Math.max(loc1.getX(), loc2.getX());
	double maxY = Math.max(loc1.getY(), loc2.getY());
	double maxZ = Math.max(loc1.getZ(), loc2.getZ());

	return new Location(loc1.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY),
		randomDouble(minZ, maxZ));
    }

    public static double randomDouble(double min, double max) {
	return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }

    /**
     * 
     * @param center Center block of this radius
     * @param radius
     * @param sameY  if false, the blocks will have diffrent Y. so it gets a 3D
     *               radius
     * @return
     */
    public static List<Block> getBlocksInRadius(Location center, int radius, boolean sameY) {

	List<Block> blocks = new ArrayList<>();
	if (sameY)
	    for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++)
		for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++)
		    blocks.add(center.getWorld().getBlockAt(x, center.getBlockY(), z));
	else
	    for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++)
		for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++)
		    for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++)
			blocks.add(center.getWorld().getBlockAt(x, y, z));
	return blocks;
    }
}
