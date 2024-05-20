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

import java.util.ArrayList;

import static com.viznyuk.minecraft.tgeniuslobby.TeleportCounter.teleportPlayerImmediate;


public class Lobby implements CommandExecutor {
    private final TGeniusLobby plugin;




    Lobby(TGeniusLobby plugin) {
        this.plugin = plugin;
        TeleportCounter.teleportCounters = new ArrayList<>();
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        assert core != null;
        Location loc = core.getMVWorldManager().getMVWorld(plugin.lobby_world).getSpawnLocation();
        if (strings.length == 0 & commandSender instanceof Player){
            Player p = (Player) commandSender;

            if (plugin.teleport_time_seconds != 0 && !commandSender.hasPermission("tgeniuslobby.lobby.bypass_time")) {
                if (TeleportCounter.getPlayerIndex(p) != -1) {
                    commandSender.sendMessage(plugin.lang.getString("error-you-are-in-the-queue"));
                    return true;

                }
                TeleportCounter counter = new TeleportCounter(p, loc);
                counter.Run();
                return true;
            }
            TeleportCounter.teleportPlayerImmediate(p, loc);
            return true;
        }
        else if (strings.length == 2 ) {
            if (strings[0].equalsIgnoreCase("set")) {
                if (!commandSender.hasPermission("tgeniuslobby.lobby.set_lobby")) return false;
                for (MultiverseWorld world : core.getMVWorldManager().getMVWorlds()) {
                    if (world.getName().equalsIgnoreCase(strings[1])) {
                        this.plugin.lobby_world = world.getName();
                        this.plugin.getConfig().set("lobby-world", world.getName());
                        this.plugin.saveConfig();
                        commandSender.sendMessage(String.format(plugin.lang.getString("new-lobby-set"), world.getName()));
                        return true;
                    }
                }
                commandSender.sendMessage(plugin.lang.getString("error-wrong-world"));
                return true;
            }
            if (strings[0].equalsIgnoreCase("other")) {
                if (!commandSender.hasPermission("tgeniuslobby.lobby.others")) return false;

                Player p = this.plugin.getServer().getPlayer(strings[1]);
                if (p == null) {
                    commandSender.sendMessage(plugin.lang.getString("player-was-not-found"));
                    return true;
                }
                teleportPlayerImmediate(p, loc);
                p.sendMessage(plugin.lang.getString("has-been-teleported-by-op"));
                return true;
            }
        }


        return false;
    }
}
