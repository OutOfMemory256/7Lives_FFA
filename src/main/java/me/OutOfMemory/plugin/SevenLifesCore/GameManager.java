package me.OutOfMemory.plugin.SevenLifesCore;

import me.OutOfMemory.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameManager {
    private static GameManager gameManager;
    private final List<Player> inGamePlayers;
    private final List<Player> deadPlayers = new ArrayList<>();
    private final Map<Player, Location> map = new HashMap<>();
    private boolean isGameRunning = false;

    private GameManager(List<Player> players) {
        this.inGamePlayers = players;
        gameManager = this;
    }

    public static void createGame(List<Player> players) {
        if(getInstance() != null && getInstance().isGameRunning())
            return;
        GameManager gameManager = new GameManager(players);

        gameManager.sendBroadcastMessage("Don't move!!!");

        new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> players = GameManager.gameManager.getInGamePlayers();
                for(int i = 3; i >= 0; i--) {
                    for (Player player : players) {
                        System.out.println("niggerman");
                        float progress = (float) i / 3.0f;
                        player.setExp(progress);
                        player.setLevel(i);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        gameManager.init();
                    }
                }.runTaskLater(Plugin.getInstance(), 0);
            }
        }.runTaskLaterAsynchronously(Plugin.getInstance(), 0);
    }

    public void deletePlayer(Player player) {
        inGamePlayers.remove(player);
        deadPlayers.add(player);

        player.setGameMode(GameMode.SPECTATOR);

        if(inGamePlayers.size() == 1 && !deadPlayers.isEmpty())
            stopGame(inGamePlayers.get(0));
        else if (inGamePlayers.isEmpty())
            stopGame();
    }

    public void stopGame() {
        sendBroadcastMessage("Game ended");
        for(Player player: Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(WarpSystem.getIslandLocation(IslandType.LOBBY));
        }

        ScoreboardManager.getScoreboardManager().deleteScoreboard(getInGamePlayers());
        ScoreboardManager.getScoreboardManager().deleteScoreboard(deadPlayers);

        setEndWeather();

        isGameRunning = false;
    }

    public void stopGame(Player player) {
        sendBroadcastMessage(player.getName() + "WINS!!!");
        stopGame();
    }

    private void sendBroadcastMessage(String message) {
        for(Player player: inGamePlayers) {
            player.sendTitle(message, "");
        }

        for(Player player: deadPlayers) {
            player.sendTitle(message, "");
        }
    }

    private void init() {
        try {
            List<IslandType> types = new ArrayList<>(List.of(IslandType.values()));
            Collections.shuffle(types);

            System.out.println("shuffled");

            for (int i = 0, j = 0; j < inGamePlayers.size(); i++) {
                if (types.get(i) == IslandType.LOBBY) {
                    continue;
                }

                if (i == 7) {
                    System.out.println("players have been sent on islands");
                }

                Player player = inGamePlayers.get(j);

                map.put(player, WarpSystem.getIslandLocation(types.get(i)));
                player.teleport(WarpSystem.getIslandLocation(types.get(i)));
                player.setGameMode(GameMode.SURVIVAL);

                System.out.println("teleported");
                j++;
            }

            ScoreboardManager manager = ScoreboardManager.getScoreboardManager();
            manager.init(inGamePlayers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStartWeather();
        isGameRunning = true;
    }

    private void setStartWeather() {
        World world = Bukkit.getWorld("world");
        world.setStorm(true);
        world.setThundering(true);
        world.setThunderDuration(6000);
        world.setTime(18000);
    }

    private void setEndWeather() {
        World world = Bukkit.getWorld("world");
        world.setStorm(false);
        world.setThundering(false);
        world.setTime(1000);
    }

    public static GameManager getInstance() {
        return gameManager;
    }

    public List<Player> getInGamePlayers() {
        return inGamePlayers;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public Map<Player, Location> getMap() {
        return map;
    }
}
