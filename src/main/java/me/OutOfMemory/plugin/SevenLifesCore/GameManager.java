package me.OutOfMemory.plugin.SevenLifesCore;

import me.OutOfMemory.plugin.Plugin;
import me.OutOfMemory.plugin.SevenLifesCore.warpSystem.IslandType;
import me.OutOfMemory.plugin.SevenLifesCore.warpSystem.WarpManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameManager {
    private static GameManager gameManager;

    private final List<UUID> inGamePlayers = new ArrayList<>();
    private final List<UUID> deadPlayers = new ArrayList<>();

    private final Map<UUID, Location> islandsLocations = new HashMap<>();

    private boolean isGameRunning = false;

    private GameManager(List<Player> players) {
        for(Player player: players) {
            inGamePlayers.add(player.getUniqueId());
        }
        gameManager = this;
    }

    public static void createGame(List<Player> players) {
        if(getInstance() != null && getInstance().isGameRunning())
            return;

        GameManager gameManager = new GameManager(players);
        PlayerQuitController.createController(players);

        for(Player player: players)
            player.teleport(WarpManager.getIslandLocation(IslandType.LOBBY));

        gameManager.sendBroadcastTitle("Не двигайтесь", "во время падения!");

        new BukkitRunnable() {
            @Override
            public void run() {
                List<UUID> players = GameManager.gameManager.getInGamePlayers();

                for(int i = 3; i >= 0; i--) {

                    for (UUID uuid : players) {
                        Player player = Bukkit.getPlayer(uuid);
                        if(player == null) {
                            System.out.println("Player is null while exp scorebar is initing");
                            continue;
                        }
                        float progress = (float) i / 3.0f;

                        player.setExp(progress);
                        player.setLevel(i);

                        if(i != 3)
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }

                    if (i == 0)
                        continue;

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
                }.runTask(Plugin.getInstance());
            }
        }.runTaskLaterAsynchronously(Plugin.getInstance(), 0);
    }

    public void deletePlayer(UUID uuid) {
        inGamePlayers.remove(uuid);
        deadPlayers.add(uuid);

        Player player = Bukkit.getPlayer(uuid);
        if(player == null) {
            System.out.println("Can not delete player because player is null!");
            return;
        }

        player.setGameMode(GameMode.SPECTATOR);

        if(inGamePlayers.size() == 1 && !deadPlayers.isEmpty())
            stopGame();
        else if (inGamePlayers.isEmpty())
            stopGame();
    }

    public void updateGameState() {
        if(inGamePlayers.size() == 1 && !deadPlayers.isEmpty())
            stopGame();
        else if (inGamePlayers.isEmpty())
            stopGame();
    }

    public void stopGame() {
        sendBroadcastTitle("Game ended", "");
        for(Player player: Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20.0);
            player.setFoodLevel(20);
            player.getInventory().clear();
            player.teleport(WarpManager.getIslandLocation(IslandType.LOBBY));
        }

        ScoreboardManager.getScoreboardManager().deleteScoreboard(getInGamePlayers());
        ScoreboardManager.getScoreboardManager().deleteScoreboard(deadPlayers);

        setEndWeather();

        isGameRunning = false;
    }

    public void stopGame(Player player) {
        sendBroadcastTitle(player.getName() + "WINS!!!", "");
        System.out.println("Player wins");
        stopGame();
    }

    private void sendBroadcastTitle(String title, String s1) {
        for(Player player: Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, s1);
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

                Player player = Bukkit.getPlayer(inGamePlayers.get(j));
                if(player == null) {
                    System.out.println("Player is null while game is initializing!");
                    continue;
                }

                islandsLocations.put(inGamePlayers.get(j), WarpManager.getIslandLocation(types.get(i)));
                player.teleport(WarpManager.getIslandLocation(types.get(i)));
                player.setGameMode(GameMode.SURVIVAL);

                player.setHealth(20.0);
                player.setFoodLevel(20);
                player.getInventory().clear();

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

    public List<UUID> getInGamePlayers() {
        return inGamePlayers;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public Map<UUID, Location> getMap() {
        return islandsLocations;
    }
}
