package me.titan.lib;

import java.util.Random;

public class Chances {

	public static boolean getChance(int percentage) {

		Random rand = new Random();
		// the <= 50 is the percentage
		return rand.nextInt(100) <= percentage;

	}

}
