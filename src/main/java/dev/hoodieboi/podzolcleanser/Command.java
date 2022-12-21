package dev.hoodieboi.podzolcleanser;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.control")) {
            sender.sendMessage(command.permissionMessage());
            return true;
        }

        if (args.length == 0)
            onInfo(sender, command, "", args);
        else
            switch (args[0].toLowerCase()) {
                case "set" -> onSet(sender, command, "set", Arrays.copyOfRange(args, 1, args.length));
                case "reload" -> onReload(sender, command, "reload", Arrays.copyOfRange(args, 1, args.length));
                case "info" -> onInfo(sender, command, "info", Arrays.copyOfRange(args, 1, args.length));
                default -> {
                    sender.sendMessage(Component.text("Invalid argument!").color(NamedTextColor.RED));
                    return true;
                }
            }
        return true;
    }

    private void onSet(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PodzolCleanser.INSTANCE.setRemovePodzol(Boolean.parseBoolean(args[0]));
        boolean removePodzol = PodzolCleanser.INSTANCE.getRemovePodzol();
        if (removePodzol)
            sender.sendMessage(Component.text("Podzol grown from large spruce trees will be removed").color(NamedTextColor.GREEN));
        else
            sender.sendMessage(Component.text("Podzol grown from large spruce trees will NOT be removed").color(NamedTextColor.YELLOW));
    }
    private void onReload(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PodzolCleanser.INSTANCE.reloadConfig();
    }
    private void onInfo(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {

    }
}
