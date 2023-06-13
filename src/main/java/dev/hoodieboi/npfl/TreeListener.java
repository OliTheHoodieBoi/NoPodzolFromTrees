package dev.hoodieboi.npfl;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

public class TreeListener implements Listener {

    @EventHandler
    public void onTreeGrow(StructureGrowEvent event) {
        if (!Plugin.INSTANCE.getShouldRemovePodzol() ||
                event.getSpecies() != TreeType.MEGA_REDWOOD)
            return;
        // Remove podzol
        event.getBlocks().removeIf(block -> block.getType() == Material.PODZOL);
    }
}
