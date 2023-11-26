package com.viznyuk.minecraft.tgeniuslobby;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LobbyTabComplete implements TabCompleter {
    TGeniusLobby plugin;

    LobbyTabComplete(TGeniusLobby p) {
        plugin = p;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if (!command.getName().equalsIgnoreCase("lobby") & !command.getName().equalsIgnoreCase("spawn")) {
            return list;
        }
        if (!(commandSender instanceof Player)) {
            return list;
        }

        Player p = (Player) commandSender;
        if (strings.length == 1) {

                if (p.hasPermission("tgeniuslobby.lobby.others") & ("other".startsWith(strings[0]) | strings[0].isEmpty())) {
                    list.add("other");
                }
                if (p.hasPermission("tgeniuslobby.lobby.set_lobby") & ("set".startsWith(strings[0]) | strings[0].isEmpty())) {
                    list.add("set");
                }

        }
        else if (strings.length == 2) {
            if (p.hasPermission("tgeniuslobby.lobby.others") & strings[0].equalsIgnoreCase("other")) {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    if (strings[1].isEmpty()){
                        list.add(player.getName());
                    } else {
                        if (player.getName().startsWith(strings[1])) {
                            list.add(player.getName());
                        }
                    }

                }
            }
            if (p.hasPermission("tgeniuslobby.lobby.set_lobby") & strings[0].equalsIgnoreCase("set")) {
                MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

                assert core != null;
                for (MultiverseWorld world : core.getMVWorldManager().getMVWorlds()) {
                    list.add(world.getName());
                }
            }

        }

        return list;
    }
}
