package dev.hoodieboi.podzolcleanser;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.control")) {
            sender.sendMessage(command.permissionMessage());
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "true", "false" -> PodzolCleanser.INSTANCE.setRemovePodzol(Boolean.parseBoolean(args[0]));
            case "reload" -> PodzolCleanser.INSTANCE.reloadConfig();
            default -> {
                sender.sendMessage(Component.text("Invalid argument! Must be true or false").color(NamedTextColor.RED));
                return true;
            }
        }
        boolean removePodzol = PodzolCleanser.INSTANCE.getRemovePodzol();
        if (removePodzol)
            sender.sendMessage(Component.text("Podzol grown from large spruce trees will be removed").color(NamedTextColor.GREEN));
        else
            sender.sendMessage(Component.text("Podzol grown from large spruce trees will NOT be removed").color(NamedTextColor.YELLOW));
        return true;
    }
}
