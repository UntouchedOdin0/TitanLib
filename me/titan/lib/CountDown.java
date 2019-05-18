package me.titan.lib;

import java.util.function.Consumer;

import org.bukkit.Bukkit;

public abstract class CountDown implements Runnable {
	// Our scheduled task's assigned id, needed for canceling

	private Integer assignedTaskId;

	// Seconds and shiz

	private int seconds;
	private int secondsLeft;

	// Actions to perform while counting down, before and after
	// Construct a timer, you could create multiple so for example if
	// you do not want these "actions"

	/**
	 * Runs the timer once, decrements seconds etc...
	 * Really wish we could make it protected/private so you couldn't access it
	 */
	public abstract void beforeTimer();

	public abstract void afterTimer();

	public abstract Consumer<CountDown> everySecond();

	public CountDown(int seconds) {
		// Initializing fields

		this.seconds = seconds;
		secondsLeft = seconds;

	}

	@Override
	public void run() {
		// Is the timer up?
		if (secondsLeft < 1) {
			// Do what was supposed to happen after the timer
			afterTimer();

			// Cancel timer
			if (assignedTaskId != null)
				Bukkit.getScheduler().cancelTask(assignedTaskId);
			return;
		}

		// Are we just starting?
		if (secondsLeft == seconds)
			beforeTimer();

		// Do what's supposed to happen every second
		if (everySecond() != null)
			everySecond().accept(this);

		// Decrement the seconds left
		secondsLeft--;
	}

	/**
	 * Gets the total seconds this timer was set to run for
	 *
	 * @return Total seconds timer should run
	 */
	public int getTotalSeconds() {
		return seconds;
	}

	/**
	 * Gets the seconds left this timer should run
	 *
	 * @return Seconds left timer should run
	 */
	public int getSecondsLeft() {
		return secondsLeft;
	}

	/**
	 * Schedules this instance to "run" every second
	 */
	public void scheduleTimer() {
		// Initialize our assigned task's id, for later use so we can cancel
		assignedTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(TitanLib.getPlugin(), this, 0L, 20L);
	}

}
