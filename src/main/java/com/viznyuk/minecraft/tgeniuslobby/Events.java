package com.viznyuk.minecraft.tgeniuslobby;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.EventListener;

public class Events implements EventListener, Listener {
    TGeniusLobby plugin;
    Events(TGeniusLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
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

    private void HandlePlayerTeleport(Player player) {
        int index = TeleportCounter.getPlayerIndex(player);

        if (index == -1) return;
        TeleportCounter counter = TeleportCounter.teleportCounters.get(index);
        counter.Cancel();
    }
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedBlock()) return;
        HandlePlayerTeleport(event.getPlayer());

    }

    @EventHandler
    public void OnPlayerGetAttacked(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        HandlePlayerTeleport(player);

    }

    @EventHandler
    public void OnPlayerTeleportEvent(PlayerTeleportEvent event) {
        HandlePlayerTeleport(event.getPlayer());

    }
}
