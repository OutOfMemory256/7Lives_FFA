package me.OutOfMemory.plugin.SevenLifesCore.commands;

import me.OutOfMemory.plugin.SevenLifesCore.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StopGameCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(GameManager.getInstance() != null) {
            GameManager.getInstance().stopGame();
        }

        return false;
    }
}
