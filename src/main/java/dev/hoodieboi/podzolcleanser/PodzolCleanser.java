package dev.hoodieboi.podzolcleanser;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;

public final class PodzolCleanser extends JavaPlugin {

    private static boolean removePodzol;
    private final String enabledPath = "remove-podzol";
    private final String configFileName = "config.yml";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TreeListener(), this);
        saveDefaultConfig();
        reloadConfig();
    }

    @Override
    public void reloadConfig() {
        // Read file
        File configFile = new File(getDataFolder(), configFileName);
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(configFile);
        // Get remove-podzol
        if (!fileConfig.contains(enabledPath)) {
            getLogger().warning(String.format("Missing '%s' in %s!", enabledPath, configFileName));
            loadDefaultConfig();
            return;
        }
        if (!fileConfig.isBoolean(enabledPath)) {
            getLogger().warning(String.format("Expected boolean value for '%s' in %s.", enabledPath, configFileName));
            loadDefaultConfig();
            return;
        }
        removePodzol = fileConfig.getBoolean(enabledPath);
    }

    public void loadDefaultConfig() {
        getLogger().info("Loading default configuration.");
        FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(getResource(configFileName)));
        removePodzol = defaultConfig.getBoolean(enabledPath);
    }

    public void setRemovePodzol(boolean removePodzol) {
        PodzolCleanser.removePodzol = removePodzol;
        // Write to file
        File configFile = new File(getDataFolder(), configFileName);
        if (!configFile.exists())
            saveResource(configFileName, false);
    }

    public static boolean getRemovePodzol() {
        return removePodzol;
    }
}
