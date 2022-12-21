package dev.hoodieboi.podzolcleanser;

import org.bukkit.plugin.java.JavaPlugin;

public final class PodzolCleanser extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TreeListener(), this);
    }
}
