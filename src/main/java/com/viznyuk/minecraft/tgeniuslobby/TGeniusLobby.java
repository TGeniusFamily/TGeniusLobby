package com.viznyuk.minecraft.tgeniuslobby;

import com.github.sirblobman.combatlogx.api.ICombatLogX;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class TGeniusLobby extends JavaPlugin {

    public String lobby_world;

    public ICombatLogX getICombatLogXAPI() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin plugin = pluginManager.getPlugin("CombatLogX");
        if (plugin == null) return null;
        return (ICombatLogX) plugin;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic

        FileConfiguration config = this.getConfig();

//
        ArrayList<String> aliaces = new ArrayList<>();


        aliaces.add("hub");
        aliaces.add("spawn");
        this.getCommand("lobby").setAliases((aliaces));
        this.getCommand("lobby").setExecutor(new Lobby(this));
        this.getCommand("lobby").setTabCompleter(new LobbyTabComplete(this));


        config.addDefault("lobby-world", "Spawn-Nature-Geist789");
        config.options().copyDefaults(true);
        lobby_world = config.getString("lobby-world");
        saveConfig();
        getServer().getPluginManager().registerEvents(new Events(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
