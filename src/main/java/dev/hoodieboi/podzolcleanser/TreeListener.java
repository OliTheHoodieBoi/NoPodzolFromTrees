package dev.hoodieboi.podzolcleanser;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.List;

public class TreeListener implements Listener {

    @EventHandler
    public void onTreeGrow(StructureGrowEvent event) {
        if (!PodzolCleanser.INSTANCE.getShouldRemovePodzol() ||
                event.getSpecies() != TreeType.MEGA_REDWOOD)
            return;
        // Get all blocks
        List<Location> treeLocations = event.getBlocks().stream()
                .map(BlockState::getLocation).toList();
        List<Material> treeMaterials = event.getBlocks().stream()
                .map(BlockState::getType).toList();
        // Cancel event
        event.setCancelled(true);
        // Reconstruct everything but podzol
        for (int i = 0; i < treeLocations.size(); i++)
            if (treeMaterials.get(i) != Material.PODZOL)
                event.getWorld().getBlockAt(treeLocations.get(i)).setType(treeMaterials.get(i));
    }
}
