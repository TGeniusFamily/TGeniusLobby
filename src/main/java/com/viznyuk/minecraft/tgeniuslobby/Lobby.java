package com.viznyuk.minecraft.tgeniuslobby;


import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class Lobby implements CommandExecutor {
    TGeniusLobby plugin;

    Lobby(TGeniusLobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        assert core != null;
        Location loc = core.getMVWorldManager().getMVWorld(plugin.lobby_world).getSpawnLocation();
        if (strings.length == 0 & commandSender instanceof Player){

            Player p = (Player) commandSender;
            p.teleport(loc);
            commandSender.sendMessage("§6Бум! И вы в лобби!");
            return true;
        }
        else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("set")) {
                for (MultiverseWorld world : core.getMVWorldManager().getMVWorlds()) {
                    if (world.getName().equalsIgnoreCase(strings[1])) {
                        this.plugin.lobby_world = world.getName();
                        this.plugin.getConfig().set("lobby-world", world.getName());
                        this.plugin.saveConfig();
                        commandSender.sendMessage(String.format("§aГотово! /lobby теперь переносит в %s!", world.getName()));
                        return true;
                    }
                }
                commandSender.sendMessage("Ошибка! Неверно указанно имя мира. Убедитесь, что вы добавили его в §aMultiverseCore.");
                return true;
            }
            if (strings[0].equalsIgnoreCase("other")) {
                Player p = this.plugin.getServer().getPlayer(strings[1]);
                if (p == null) {
                    commandSender.sendMessage("Игрок не был найден:(");
                    return true;
                }
                p.teleport(loc);
                commandSender.sendMessage(String.format("§6Бум! И %s в лобби!", p.getName()));
                return true;
            }
        }


        return false;
    }
}
