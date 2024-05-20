package com.viznyuk.minecraft.tgeniuslobby;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

public final class TGeniusLobby extends JavaPlugin {

    public String lobby_world;
    public int teleport_time_seconds;
    public FileConfiguration lang;




    @Override
    public void onEnable() {
        // Plugin startup logic

        ArrayList<String> aliases = new ArrayList<>();
        if (!new File(getDataFolder(), "lang.yml").exists()) {
            saveResource("lang.yml", true);
        }
        File f = new File(getDataFolder(), "lang.yml");

        lang = new YamlConfiguration();
        try {
            lang.load(f);
        } catch (InvalidConfigurationException e) {
            getLogger().log(Level.SEVERE, "lang.yml file corrupted. Created new.");
            saveResource("lang.yml", true);
            lang = YamlConfiguration.loadConfiguration(f);

        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "unable to get lang file " + e.getMessage());
        }
        aliases.add("spawn");
        this.getCommand("lobby").setAliases((aliases));
        this.getCommand("lobby").setExecutor(new Lobby(this));
        this.getCommand("lobby").setTabCompleter(new LobbyTabComplete(this));

        FileConfiguration cfg = this.getConfig();

        lobby_world = cfg.getString("lobby-world");
        teleport_time_seconds = cfg.getInt("teleport-time-seconds");
        getServer().getPluginManager().registerEvents(new Events(this), this);
        TeleportCounter.InitLang(this);
    }

}
