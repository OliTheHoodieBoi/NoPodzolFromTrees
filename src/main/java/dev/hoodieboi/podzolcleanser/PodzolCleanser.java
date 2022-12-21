package dev.hoodieboi.podzolcleanser;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

public final class PodzolCleanser extends JavaPlugin {

    public static PodzolCleanser INSTANCE;
    private boolean removePodzol = true;
    private final String enabledPath = "remove-podzol";
    private final String configFileName = "config.yml";

    @Override
    public void onEnable() {
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(new TreeListener(), this);

        saveDefaultConfig();
        reloadConfig();

        registerCommand();
    }

    @Override
    public void reloadConfig() {
        // Read file
        FileConfiguration fileConfig = getFileConfig();
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

    private FileConfiguration getFileConfig() {
        File configFile = new File(getDataFolder(), configFileName);
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void loadDefaultConfig() {
        getLogger().info("Loading default configuration.");
        InputStream defaultConfigFile = getResource(configFileName);
        if (defaultConfigFile == null) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not find default config");
            return;
        }
        FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigFile));
        removePodzol = defaultConfig.getBoolean(enabledPath);
    }

    public void setRemovePodzol(boolean removePodzol) {
        this.removePodzol = removePodzol;
        // Save to config
        FileConfiguration fileConfig = getFileConfig();
        fileConfig.set(enabledPath, removePodzol);
        try {
            File configFile = new File(getDataFolder(), configFileName);
            fileConfig.save(configFile);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Could not save config.", e);
        }
    }

    public boolean getRemovePodzol() {
        return removePodzol;
    }

    private void registerCommand() {
        PluginCommand command = getServer().getPluginCommand("removepodzol");
        if (command == null) {
            Bukkit.getLogger().warning("Could not load command 'removepodzol'");
            return;
        }
        command.setExecutor(new Command());
        if (CommodoreProvider.isSupported()) {
            LiteralCommandNode<?> completion = literal("removepodzol")
                    .then(literal("set").then(literal("true")).then(literal("false")))
                    .then(literal("reload"))
                    .then(literal("info"))
                    .build();

            Commodore commodore = CommodoreProvider.getCommodore(this);
            commodore.register(command, completion);
        }
    }
}
