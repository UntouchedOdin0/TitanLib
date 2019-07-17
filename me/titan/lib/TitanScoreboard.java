package me.titan.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * <b>Class Makes scoreboard easier to make!</b> <br>
 * use like this: <br>
 * 1. First make a new instance of TitanSoreboard: <br>
 * <code> TitanScoreboard tsb = new TitanScoreboard("display name") </code> <br>
 * 2. Set lines of thee scoreboard: by using one of the set lines methods. <br>
 * 3. You done! now just display the scoreboard for players: displayTo(players)
 * 
 */
public class TitanScoreboard {

    Scoreboard board;
    private Objective ob;

    Map<Integer, String> lines = new HashMap<>();

    public TitanScoreboard(String displayName) {
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	board = manager.getNewScoreboard();
	ob = board.registerNewObjective("Titan", "dummy");
	ob.setDisplaySlot(DisplaySlot.SIDEBAR);

	ob.setDisplayName(Chat.colorize(displayName));

    }

    private void make() {
	for (int slot : lines.keySet())
	    for (String line : lines.values()) {
		Score score = ob.getScore(Chat.colorize(line));
		score.setScore(slot);
	    }
    }

    public void displayTo(Player p) {
	make();
	p.setScoreboard(board);
    }

    public void displayTo(List<? extends Player> players) {
	for (Player p : players)
	    displayTo(p);
    }

    public void setLines(List<String> list) {
	int slot = -1;
	for (String line : list)
	    lines.put(slot + 1, line);
    }

    public void setLines(String... list) {
	int slot = -1;
	for (String line : list)
	    lines.put(slot + 1, line);
    }

    public void setLine(String line, int slot) {
	lines.put(slot, line);
    }

    public void addLine(String line) {
	lines.put(lines.size() + 1, line);
    }

    public void refresh() {
	make();
    }

}
