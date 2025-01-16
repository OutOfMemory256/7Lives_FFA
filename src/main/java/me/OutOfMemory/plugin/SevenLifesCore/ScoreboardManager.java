package me.OutOfMemory.plugin.SevenLifesCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardManager {
    private static final ScoreboardManager scoreboardManager = new ScoreboardManager();

    private Scoreboard scoreboard;
    private final DisplaySlot slot = DisplaySlot.SIDEBAR;
    private Objective objective;
    private HashMap<Player, Integer> map;

    public void init(List<Player> players) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        map  = new HashMap<>();
        objective = scoreboard.registerNewObjective(
                ChatColor.translateAlternateColorCodes('&', "Deaths"),
                ""
        );
        objective.setDisplaySlot(slot);
        for(Player player: players) {
            map.put(player, 0);
            Score score = objective.getScore(player.getName());
            score.setScore(0);
        }
        updateScoreboard(players);
    }

    public void addDeath(Player player) {
        int deathCount = map.get(player) + 1;
        map.put(player, deathCount);

        if(deathCount >= 7) {
            GameManager.getInstance().deletePlayer(player);
        }
    }

    public void updateScoreboard(List<Player> players) {
        for(Map.Entry<Player, Integer> entry: map.entrySet()) {
            objective.getScore(entry.getKey()).setScore(entry.getValue());
        }

        for(Player player: players)
            player.setScoreboard(scoreboard);
    }

    public void deleteScoreboard(List<Player> players) {
        for(Player player: players)
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
