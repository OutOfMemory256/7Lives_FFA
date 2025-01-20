package me.OutOfMemory.plugin;

import me.OutOfMemory.plugin.SevenLifesCore.PlayerListener;
import me.OutOfMemory.plugin.SevenLifesCore.commands.StartGameCMD;
import me.OutOfMemory.plugin.SevenLifesCore.commands.StopGameCMD;
import me.OutOfMemory.plugin.SevenLifesCore.warpSystem.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
    private static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;

        WarpManager.init();

        this.getCommand("startGame").setExecutor(new StartGameCMD());
        this.getCommand("stopGame").setExecutor(new StopGameCMD());

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getInstance() {
        return instance;
    }
}
