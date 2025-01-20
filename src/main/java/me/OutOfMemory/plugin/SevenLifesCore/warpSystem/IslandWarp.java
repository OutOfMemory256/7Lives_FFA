package me.OutOfMemory.plugin.SevenLifesCore.warpSystem;

import org.bukkit.Location;

public class IslandWarp {
    private Location location;

    public IslandWarp(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
