package me.OutOfMemory.plugin.SevenLifesCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {
    private static final ScoreboardManager scoreboardManager = new ScoreboardManager();

    private Scoreboard scoreboard;
    private final DisplaySlot slot = DisplaySlot.SIDEBAR;
    private Objective objective;
    private HashMap<Player, Integer> map;

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
            map.put(player, 0);
            Score score = objective.getScore(player.getName());
            score.setScore(0);
        }
        updateScoreboard(players);
    }

    public void addDeath(Player player) {
        int deathCount = map.get(player);
        if(deathCount >= 7)
            return;

        map.put(player, ++deathCount);

        if(deathCount >= 7) {
            GameManager.getInstance().deletePlayer(player.getUniqueId());
        }
    }

    public void updateScoreboard(List<UUID> players) {
        for(Map.Entry<Player, Integer> entry: map.entrySet()) {
            if(entry != null)
                objective.getScore(entry.getKey()).setScore(entry.getValue());
            else
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
