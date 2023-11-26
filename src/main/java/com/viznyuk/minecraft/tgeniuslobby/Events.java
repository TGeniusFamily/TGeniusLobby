package com.viznyuk.minecraft.tgeniuslobby;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.EventListener;

public class Events implements EventListener, Listener {
    TGeniusLobby plugin;
    Events(TGeniusLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("tgeniuslobby.lobby.bypass_autoteleport")) {
            event.getPlayer().sendMessage("§6У вас имунтиет и вас не телепортирует на спавн при заходе на сервер! Используйте /lobby для" +
                    " телепортации в лобби");
            return;
        }
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        assert core != null;
        Location loc = core.getMVWorldManager().getMVWorld(plugin.lobby_world).getSpawnLocation();
        event.getPlayer().teleport(loc);
    }
}
