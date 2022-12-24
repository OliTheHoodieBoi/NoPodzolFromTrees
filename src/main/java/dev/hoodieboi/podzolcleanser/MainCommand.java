package dev.hoodieboi.podzolcleanser;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MainCommand implements CommandExecutor {
    @Override
    @SuppressWarnings("null")
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0)
            onInfo(sender, command, "", args);
        else
            switch (args[0].toLowerCase()) {
                case "set" -> onSet(sender, command, "set", Arrays.copyOfRange(args, 1, args.length));
                case "reload" -> onReload(sender, command, "reload", Arrays.copyOfRange(args, 1, args.length));
                case "info" -> onInfo(sender, command, "info", Arrays.copyOfRange(args, 1, args.length));
                default -> {
                    sender.sendMessage(Component.translatable("command.unknown.command", Style.style(NamedTextColor.RED)));
                    return true;
                }
            }
        return true;
    }

    // set subcommand
    private void onSet(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.set")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 2) {
            trailingArguments(sender, label, args, 1);
            return;
        }

        if (args[0] == "enabled")
            PodzolCleanser.INSTANCE.setRemovePodzol(true);
        else if (args[0] == "disabled")
            PodzolCleanser.INSTANCE.setRemovePodzol(false);
        else
            sender.sendMessage(Component.translatable("").color(NamedTextColor.RED));
        // Response
        sender.sendMessage(Component.text("Set current state to: ").color(NamedTextColor.GREEN)
                .append(getCurrentState().color(NamedTextColor.AQUA)));
    }

    // reload subcommand
    private void onReload(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.reload")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 1) {
            trailingArguments(sender, label, args, 1);
            return;
        }

        sender.sendMessage(Component.text("Reloading config...").color(NamedTextColor.YELLOW));
        PodzolCleanser.INSTANCE.reloadConfig();
        sender.sendMessage(Component.text("Current state: ").color(NamedTextColor.YELLOW)
                .append(getCurrentState().color(NamedTextColor.AQUA)));
    }

    // info subcommand
    private void onInfo(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.info")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 1) {
            trailingArguments(sender, label, args, 1);
            return;
        }

        sender.sendMessage(Component.text(String.format("--- %s ---\n", PodzolCleanser.INSTANCE.getName())).color(NamedTextColor.AQUA)
                .append(Component.text(PodzolCleanser.INSTANCE.getDescription().getDescription()).color(NamedTextColor.YELLOW))
                .append(Component.text("\nCurrent state: ").color(NamedTextColor.YELLOW))
                .append(getCurrentState().color(NamedTextColor.AQUA)));
    }
    
    private TextComponent getCurrentState() {
        return Component.text(PodzolCleanser.INSTANCE.getRemovePodzol() ? "enabled" : "disabled");
    }

    private void trailingArguments(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args, int validArguments) {
        String validPart = label + " " + String.join(" ", Arrays.copyOfRange(args, 0, validArguments));
        validPart = validPart.substring(validPart.length() - 9);
        String invalidPart = String.join(" ", Arrays.copyOfRange(args, validArguments, args.length));

        Component message = Component.translatable("command.unknown.argument").color(NamedTextColor.RED)
                .append(Component.text("\n..." + validPart).color(NamedTextColor.GRAY))
                .append(Component.text(invalidPart))
                .append(Component.translatable("command.unknown.here"));

        sender.sendMessage(message);
    }
}
