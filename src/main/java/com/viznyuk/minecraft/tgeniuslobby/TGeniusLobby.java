package com.viznyuk.minecraft.tgeniuslobby;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class TGeniusLobby extends JavaPlugin {

    public String lobby_world;
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
