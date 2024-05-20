package com.viznyuk.minecraft.tgeniuslobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TeleportCounter {

    private static TGeniusLobby plugin;
    private int taskId;
    private final Player player;
    private final Location loc;
    private boolean isCanceled;

    public static List<TeleportCounter> teleportCounters;


    private static String langDoNotMoveWhileTeleporting = null;
    private static String langTeleportCounterColor = null;
    private static String langOnTeleport = null;
    private static String langTeleportCanceled = null;
    private static String onTeleportedTitle = null;

    @Override
    public String toString() {
        return "TeleportCounter{" + (player.getName() + ", " + loc.toString()) + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return p.identity().uuid() == player.identity().uuid();
        }
        return super.equals(obj);
    }

    public static int getPlayerIndex(Player player) {
        for (int i = 0; i < teleportCounters.size(); i++) {
            if (player.identity().uuid() == TeleportCounter.teleportCounters.get(i).player.identity().uuid()) {
                return i;
            }
        }
        return -1;
    }

    public TeleportCounter(Player p, Location loc) {
        this.player = p;
        this.loc = loc;


    }

    public static void InitLang(TGeniusLobby plugin) {
        if (langDoNotMoveWhileTeleporting != null) return;
        TeleportCounter.plugin = plugin;
        langDoNotMoveWhileTeleporting = plugin.lang.getString("do-not-move-while-teleporting");
        langTeleportCounterColor = plugin.lang.getString("teleport-counter-color");
        langOnTeleport = plugin.lang.getString("on-teleport");
        langTeleportCanceled = plugin.lang.getString("teleport-cancelled");
        onTeleportedTitle = plugin.lang.getString("on-teleport-title");
    }
    private void StopTask() {
        isCanceled = true;
        Bukkit.getScheduler().cancelTask(taskId);
        teleportCounters.remove(this);

    }
    public void Cancel() {
        StopTask();
        player.showTitle(Title.title(Component.text(langTeleportCanceled), Component.empty()));
    }
    public static void teleportPlayerImmediate(Player p, Location loc) {
        p.teleport(loc);
        p.sendMessage(langOnTeleport);
        p.showTitle(Title.title(Component.text(onTeleportedTitle),  Component.empty()));

    }
    private void teleportPlayer() {
        teleportPlayerImmediate(player, loc);
        StopTask();
    }

    public void Run() {
        teleportCounters.add(this);
        AtomicInteger counter = new AtomicInteger(plugin.teleport_time_seconds);

        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (counter.decrementAndGet() <= 0 && !isCanceled) {
                teleportPlayer();
                StopTask();
            }
            else {

        player.showTitle(Title.title(Component.text(langTeleportCounterColor + counter.get()),
                Component.text(langDoNotMoveWhileTeleporting)));
            }

        }, 0, 20);
    }

}
