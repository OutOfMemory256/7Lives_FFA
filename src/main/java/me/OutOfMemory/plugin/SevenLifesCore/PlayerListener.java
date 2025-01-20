package me.OutOfMemory.plugin.SevenLifesCore;

import me.OutOfMemory.plugin.SevenLifesCore.warpSystem.IslandType;
import me.OutOfMemory.plugin.SevenLifesCore.warpSystem.WarpManager;
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
        if(GameManager.getInstance() == null)
            return;

        if(GameManager.getInstance().isGameRunning() &&  GameManager.getInstance().getInGamePlayers().contains(event.getPlayer().getUniqueId())) {
            System.out.println("negr1");
            ScoreboardManager.getScoreboardManager().addDeath(event.getPlayer());
            System.out.println("negr");
            ScoreboardManager.getScoreboardManager().updateScoreboard(GameManager.getInstance().getInGamePlayers());
            System.out.println("negr");
        }
    }

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (GameManager.getInstance().isGameRunning()) {
            if (!GameManager.getInstance().getInGamePlayers().contains(player.getUniqueId())) {
                event.setRespawnLocation(WarpManager.getIslandLocation(IslandType.LOBBY));
                System.out.println("negr1");
                return;
            }
            else {
                event.setRespawnLocation(GameManager.getInstance().getMap().get(event.getPlayer().getUniqueId()));
                System.out.println("negr2");
                return;
            }
        }
            event.setRespawnLocation(WarpManager.getIslandLocation(IslandType.LOBBY));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        System.out.println("chelen");
        if(GameManager.getInstance() == null) {
            event.getPlayer().teleport(WarpManager.getIslandLocation(IslandType.LOBBY));
            return;
        }
        System.out.println("pen");
        if(!GameManager.getInstance().isGameRunning()) {
            event.getPlayer().teleport(WarpManager.getIslandLocation(IslandType.LOBBY));
            System.out.println("pens1");
        } else if (GameManager.getInstance().getInGamePlayers().contains(event.getPlayer().getUniqueId())) {
            System.out.println("negridos");
            PlayerQuitController.getInstance().setInGame(event.getPlayer(), true);
        } else
            event.getPlayer().teleport(WarpManager.getIslandLocation(IslandType.LOBBY));
    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event) {
        System.out.println("penos");
        if(GameManager.getInstance() == null)
            return;
        System.out.println("pwnus");
        if(!(
                GameManager.getInstance().isGameRunning() &&
                GameManager.getInstance().getInGamePlayers().contains(event.getPlayer().getUniqueId())))
            return;
        System.out.println("nied");
        PlayerQuitController.getInstance().setInGame(event.getPlayer(), false);
        GameManager.getInstance().updateGameState();
    }
}