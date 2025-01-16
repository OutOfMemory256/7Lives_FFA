package me.OutOfMemory.plugin.SevenLifesCore;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class WarpSystem {
    private static HashMap<IslandType, IslandWarp> map = new HashMap<>();

    public static void init() {
        try {
            map.put(IslandType.MINI_LAKE, new IslandWarp(createLocation(199.5, 56.5), IslandType.MINI_LAKE));
            map.put(IslandType.DEFAULT, new IslandWarp(createLocation(-50, 39), IslandType.DEFAULT));
            map.put(IslandType.JUNGLES, new IslandWarp(createLocation(60.5, 256.5), IslandType.JUNGLES));
            map.put(IslandType.OASIS, new IslandWarp(createLocation(-214.5, 37.5), IslandType.OASIS));
            map.put(IslandType.TAIGA, new IslandWarp(createLocation(-177.5, 193.5), IslandType.TAIGA));
            map.put(IslandType.TOWN, new IslandWarp(createLocation(132.5, -43.5), IslandType.TOWN));
            map.put(IslandType.VULCAN, new IslandWarp(createLocation(-161.5, -139.5), IslandType.VULCAN));

            Location location = new Location(Bukkit.getWorld("world"), -360, 303, 187);
            map.put(IslandType.LOBBY, new IslandWarp(location, IslandType.LOBBY));
            System.out.println("Warp system have been initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Location createLocation(double x, double z) {
        Location location = new Location(Bukkit.getWorld("world"), x, 1000, z);
        return location;
    }

    public static Location getIslandLocation(IslandType type) {
        return map.get(type).getLocation();
    }
}
