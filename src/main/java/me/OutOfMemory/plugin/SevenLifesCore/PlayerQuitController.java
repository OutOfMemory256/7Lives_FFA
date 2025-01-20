package me.OutOfMemory.plugin.SevenLifesCore;

import me.OutOfMemory.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class PlayerQuitController {
    private static PlayerQuitController controller;
    private HashMap<OnlinePlayerState, Integer> map = new HashMap<>();

    private PlayerQuitController(List<Player> players) {
        for(Player player: players)
            map.put(new OnlinePlayerState(this, player), 300);
    }

    public static void createController(List<Player> players) {
        PlayerQuitController currentController = new PlayerQuitController(players);
        PlayerQuitController.controller = currentController;
    }

    public void updateController() {
        for(Map.Entry<OnlinePlayerState, Integer> entry: map.entrySet()) {
            System.out.println(entry.getKey().player.getName() + 1);
            if(!entry.getKey().inGame)
                System.out.println("statee " + entry.getKey().getInGame());

                new BukkitRunnable() {
                OnlinePlayerState state = entry.getKey();
                Integer timer = entry.getValue();
                    @Override
                    public void run() {
                        System.out.println(entry.getKey().player.getName() + 3);
                        while (!state.getInGame()) {
                            if(timer == 0) {
                                GameManager.getInstance().deletePlayer(entry.getKey().player.getUniqueId());
                                return;
                            }
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            timer--;
                            System.out.println(entry.getKey().player.getName() + 4);
                        }
                    }
                }.runTaskAsynchronously(Plugin.getInstance());
        }
    }

    public synchronized void setInGame(Player player, boolean inGame) {
        for(OnlinePlayerState state: map.keySet()) {
            if(state.player.getUniqueId().equals(player.getUniqueId())) {
                System.out.println("state + " + state);
                state.setInGame(inGame);
                System.out.println("state" + state + inGame);
                System.out.println("niggert");
            }
        }
        updateController();
    }

    public static PlayerQuitController getInstance() {
        if(controller == null) {
            System.out.println("Player controller is null!");
            return null;
        }
        return controller;
    }

    public class OnlinePlayerState {
        private final Player player;
        private volatile Boolean inGame = true;

        private final PlayerQuitController controller;

        public OnlinePlayerState(PlayerQuitController controller, Player player) {
            this.player = player;
            this.controller = controller;
        }

        public synchronized Boolean getInGame() {
            return inGame;
        }

        public UUID getUUID() {
            return player.getUniqueId();
        }

        public void setInGame(boolean inGame) {
            this.inGame = inGame;
        }
    }
}
