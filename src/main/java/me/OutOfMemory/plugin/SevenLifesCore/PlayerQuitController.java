package me.OutOfMemory.plugin.SevenLifesCore;

/*
import me.OutOfMemory.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
*/

public class PlayerQuitController {
    /*private static PlayerQuitController controller;
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
            if(!entry.getKey().getInGame())
                System.out.println(entry.getKey().player.getName() + 2);
                new BukkitRunnable() {
                Boolean inGame = entry.getKey().getInGame();
                Integer timer = entry.getValue();
                    @Override
                    public void run() {
                        System.out.println(entry.getKey().player.getName() + 3);
                        while (!inGame) {
                            if(timer == 0)
                                GameManager.getInstance().deletePlayer(entry.getKey().player);
                            try {
                                Thread.sleep(1000);
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

    public void setInGame(Player player, boolean inGame) {
        for(OnlinePlayerState state: map.keySet()) {
            if(state.player.equals(player)) {
                state.inGame = inGame;
            }
        }
        controller.updateController();
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
        private Boolean inGame = true;

        private final PlayerQuitController controller;

        public OnlinePlayerState(PlayerQuitController controller, Player player) {
            this.player = player;
            this.controller = controller;
        }

        public boolean getInGame() {
            return inGame;
        }
    }*/
}
