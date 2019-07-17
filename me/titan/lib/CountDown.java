package me.titan.lib;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * 
 * Easy Class for making easy countDowns.
 * <br>
 * <br>
 * See {@link ExampleClass} for more information.
 * 
 * @author TitanDev / JustAgamer
 *
 */

public abstract class CountDown {

	final int time;
	int count;
	public int taskID;
	boolean started;
	boolean async;

	public CountDown(int time) {
		this.time = time;
		startTimer();
	}

	public CountDown(int time, boolean async) {
		this.time = time;
		this.async = async;
		startTimer();
	}

	public void startTimer() {
		count = time;
		if (!async) {
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			taskID = scheduler.scheduleSyncRepeatingTask(TitanLib.getPlugin(), () -> {
				if (count == 0) {
					onTimeUp();
					stopTimer(taskID);

					return;
				}
				if (count % 5 == 0)
					doEvery5Seconds(count);
				doEverySecond(count);

				count = count - 1;

			}, 0L, 20L);
		} else {
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			taskID = scheduler.scheduleAsyncRepeatingTask(TitanLib.getPlugin(), () -> {
				if (count == 0) {
					onTimeUp();
					stopTimer(taskID);

					return;
				}
				if (count % 5 == 0)
					doEvery5Seconds(count);
				doEverySecond(count);

				count = count - 1;

			}, 0L, 20L);
		}

	}

	public static void stopTimer(int taskid) {
		Bukkit.getScheduler().cancelTask(taskid);
	}

	public void stopTimerr() {
		Bukkit.getScheduler().cancelTask(taskID);
	}

	/**
	 * Do anything every 5 seconds, like sending messages...
	 * @param currentTime the courent timer amount
	 */
	public abstract void doEvery5Seconds(int currentTime);

	/**
	 * Do anything every seconds, like sending messages...
	 * 
	 * @param currentTime the courent timer amount
	 */
	public abstract void doEverySecond(int currentTime);

	/**
	 * What to do when the time up?
	
	 */
	public abstract void onTimeUp();
}

class exampleClass {
	public void test() {
		new CountDown(10) {

			@Override
			public void doEvery5Seconds(int time) {
				Bukkit.broadcastMessage(Chat.colorize("&4Time Remaining: " + time + " seconds."));

			}

			@Override
			public void onTimeUp() {
				Bukkit.broadcastMessage(Chat.colorize("&4Time is up! &cTeleporting to the game!"));

			}

			@Override
			public void doEverySecond(int currentTime) {
				Bukkit.broadcastMessage(Chat.colorize("&4Time Remaining: " + time + " seconds."));

			}

		};
	}
}
