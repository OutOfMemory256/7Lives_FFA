package me.OutOfMemory.plugin.SevenLifesCore;

import me.OutOfMemory.plugin.SevenLifesCore.warpSystem.IslandType;
import me.OutOfMemory.plugin.SevenLifesCore.warpSystem.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(GameManager.getInstance() == null)
            return;

        if(GameManager.getInstance().isGameRunning() &&  GameManager.getInstance().getInGamePlayers().contains(event.getPlayer().getUniqueId())) {
            System.out.println("Player dead when the game is running");
            ScoreboardManager.getScoreboardManager().addDeath(event.getPlayer());
            System.out.println("Player's Name: " + event.getPlayer().getName());
            if (GameManager.getInstance().isGameRunning())
                ScoreboardManager.getScoreboardManager().updateScoreboard(GameManager.getInstance().getInGamePlayers());
            else {
                ArrayList<UUID> uuids = new ArrayList<>();
                for(Player player: Bukkit.getOnlinePlayers())
                    uuids.add(player.getUniqueId());

                ScoreboardManager.getScoreboardManager().deleteScoreboard(uuids);
            }
            System.out.println("Updated scoreboards");
        }
    }

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (GameManager.getInstance().isGameRunning()) {
            if (!GameManager.getInstance().getInGamePlayers().contains(player.getUniqueId())) {
                event.setRespawnLocation(WarpManager.getIslandLocation(IslandType.LOBBY));
                System.out.println("Player spawned when the game is running and current player in game");
                return;
            } else {
                event.setRespawnLocation(GameManager.getInstance().getMap().get(event.getPlayer().getUniqueId()));
                System.out.println("Player spawn when the game is running, but current player is not in game");
                return;
            }
        }
        event.setRespawnLocation(WarpManager.getIslandLocation(IslandType.LOBBY));
        System.out.println("Player's spawn redirected to lobby because the game is not running");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        GameManager manager = GameManager.getInstance();
        Player player = event.getPlayer();

        System.out.println(player.getName() + " joined the world");

        if(manager == null || !manager.isGameRunning()) {
            player.teleport(WarpManager.getIslandLocation(IslandType.LOBBY));
            System.out.print(" and redirected to lobby because the game is not running");
            return;
        }

        System.out.print(" while the game is running");

        if (manager.getInGamePlayers().contains(player.getUniqueId())) {
            PlayerQuitController.getInstance().setInGame(player, true);
            ScoreboardManager.getScoreboardManager().showScoreboard(player);
            System.out.println(". scoreboard was set to current player because he is acting in the game");
        } else {
            player.teleport(WarpManager.getIslandLocation(IslandType.LOBBY));
            System.out.println(" and redirected to lobby because they are not current game players");
        }
    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event) {
        GameManager manager = GameManager.getInstance();

        if(manager == null)
            return;
        if(!(
                manager.isGameRunning() &&
                manager.getInGamePlayers().contains(event.getPlayer().getUniqueId())))
            return;
        PlayerQuitController.getInstance().setInGame(event.getPlayer(), false);
        // GameManager.getInstance().updateGameState();
    }
}