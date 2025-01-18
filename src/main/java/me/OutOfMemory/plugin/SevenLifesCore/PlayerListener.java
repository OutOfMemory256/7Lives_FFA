package me.OutOfMemory.plugin.SevenLifesCore;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(GameManager.getInstance().isGameRunning()) {
            ScoreboardManager.getScoreboardManager().addDeath(event.getPlayer());
            ScoreboardManager.getScoreboardManager().updateScoreboard(GameManager.getInstance().getInGamePlayers());
        }
    }

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (GameManager.getInstance().isGameRunning()) {
            if (!GameManager.getInstance().getInGamePlayers().contains(player))
                return;

            Location location = GameManager.getInstance().getMap().get(player);
            if (location == null)
                return;
            event.setRespawnLocation(location);
        } else
            event.setRespawnLocation(WarpSystem.getIslandLocation(IslandType.LOBBY));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(GameManager.getInstance() != null && GameManager.getInstance().isGameRunning())
            event.getPlayer().teleport(WarpSystem.getIslandLocation(IslandType.LOBBY));
    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event) {
        GameManager.getInstance().deletePlayer(event.getPlayer());
    }
}
