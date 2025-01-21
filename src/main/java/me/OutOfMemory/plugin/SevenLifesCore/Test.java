package me.OutOfMemory.plugin.SevenLifesCore;

import me.OutOfMemory.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class Test {
    public static void startTest() {
        ArrayList<Player> players = new ArrayList<>(getOnlinePlayers());

        new BukkitRunnable() {
            @Override
            public void run() {

            }
        }.runTaskAsynchronously(Plugin.getInstance());
    }
}
