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
import java.util.UUID;

public class ScoreboardManager {
    private static final ScoreboardManager scoreboardManager = new ScoreboardManager();

    private Scoreboard scoreboard;
    private final DisplaySlot slot = DisplaySlot.SIDEBAR;
    private Objective objective;
    private HashMap<UUID, Integer> map;

    public void init(List<UUID> players) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        map  = new HashMap<>();
        objective = scoreboard.registerNewObjective(
                ChatColor.translateAlternateColorCodes('&', "Deaths"),
                ""
        );
        objective.setDisplaySlot(slot);
        for(UUID uuid: players) {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) {
                System.out.println("player is null while scoreboard is initializing!");
                continue;
            }
            map.put(player.getUniqueId(), 0);
            Score score = objective.getScore(player.getName());
            score.setScore(0);
        }
        updateScoreboard(players);
    }

    public void addDeath(Player player) {
        int deathCount = map.get(player.getUniqueId());
        if(deathCount >= 7)
            return;

        map.put(player.getUniqueId(), ++deathCount);

        if(deathCount >= 7) {
            GameManager.getInstance().deletePlayer(player.getUniqueId());
        }
    }

    public void updateScoreboard(List<UUID> players) {
        for(Map.Entry<UUID, Integer> entry: map.entrySet()) {
            if(entry != null) {
                Player player = Bukkit.getPlayer(entry.getKey());
                if(player == null) {
                    System.out.println("Player is null while scoreboard updating!");
                    continue;
                }
                objective.getScore(player).setScore(entry.getValue());
            } else
                System.out.println("null");
        }

        for(UUID uuid: players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                System.out.println("player is null while setting scoreboard to players");
                continue;
            }
            player.setScoreboard(scoreboard);
        }
    }

    public void showScoreboard(Player player) {
        player.setScoreboard(scoreboard);
    }

    public void deleteScoreboard(List<UUID> players) {
        for(UUID uuid: players) {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) {
                System.out.println("player is null while deleting scoreboard!");
                continue;
            }
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
