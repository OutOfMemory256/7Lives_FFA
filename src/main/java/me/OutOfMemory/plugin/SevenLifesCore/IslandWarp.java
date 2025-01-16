package me.OutOfMemory.plugin.SevenLifesCore;

import org.bukkit.Location;

public class IslandWarp {
    private Location location;
    private IslandType type;

    public IslandWarp(Location location, IslandType type) {
        this.location = location;
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public IslandType getType() {
        return type;
    }
}
