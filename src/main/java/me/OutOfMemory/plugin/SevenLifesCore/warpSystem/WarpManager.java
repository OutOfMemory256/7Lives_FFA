package me.OutOfMemory.plugin.SevenLifesCore.warpSystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class WarpManager {
    private static HashMap<IslandType, IslandWarp> map = new HashMap<>();

    public static void init() {
        try {
            map.put(IslandType.MINI_LAKE, new IslandWarp(createLocation(199.5, 56.5)));
            map.put(IslandType.DEFAULT, new IslandWarp(createLocation(-50, 39)));
            map.put(IslandType.JUNGLES, new IslandWarp(createLocation(60.5, 256.5)));
            map.put(IslandType.OASIS, new IslandWarp(createLocation(-214.5, 37.5)));
            map.put(IslandType.TAIGA, new IslandWarp(createLocation(-177.5, 193.5)));
            map.put(IslandType.TOWN, new IslandWarp(createLocation(132.5, -43.5)));
            map.put(IslandType.VULCAN, new IslandWarp(createLocation(-161.5, -139.5)));

            Location location = new Location(Bukkit.getWorld("world"), -360, 303, 187);
            map.put(IslandType.LOBBY, new IslandWarp(location));
            System.out.println("Warp system have been initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Location createLocation(double x, double z) {
        return new Location(Bukkit.getWorld("world"), x, 1000, z);
    }

    public static Location getIslandLocation(IslandType type) {
        return map.get(type).getLocation();
    }
}
