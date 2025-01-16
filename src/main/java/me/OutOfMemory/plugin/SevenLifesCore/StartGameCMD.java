package me.OutOfMemory.plugin.SevenLifesCore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class StartGameCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        System.out.println("Trying to start 7Lives: FFA");

        ArrayList<Player> players = new ArrayList<>(getOnlinePlayers());
        System.out.println("Players added");

        GameManager.createGame(players);

        System.out.println("Game has been started successfully!");

        return false;
    }
}
