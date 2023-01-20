package dev.hoodieboi.podzolcleanser;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MainCommand implements CommandExecutor {
    @Override
    @SuppressWarnings("null")
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            onSubcommandInfo(sender, command, "", args);
            return true;
        }
        String[] subcommandArgs = Arrays.copyOfRange(args, 1, args.length);
        switch (args[0].toLowerCase()) {
            case "enable" -> onSubcommandEnable(sender, command, "enable", subcommandArgs);
            case "disable" -> onSubcommandDisable(sender, command, "disable", subcommandArgs);
            case "reload" -> onSubcommandReload(sender, command, "reload", subcommandArgs);
            case "info" -> onSubcommandInfo(sender, command, "info", subcommandArgs);
            default -> {
                sender.sendMessage(Component.translatable("command.unknown.command", Style.style(NamedTextColor.RED)));
                return true;
            }
        }
        return true;
    }

    private void onSubcommandEnable(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.enable")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 0) {
            trailingArguments(sender, label, args, 1);
            return;
        }
        if (PodzolCleanser.INSTANCE.getShouldRemovePodzol()) {
            sender.sendMessage(Component.text("Already enabled").color(NamedTextColor.RED));
            return;
        }
        PodzolCleanser.INSTANCE.setShouldRemovePodzol(true);
        // Response
        sender.sendMessage(Component.text("Set current state to: ").color(NamedTextColor.GREEN)
                .append(getCurrentState().color(NamedTextColor.GOLD)));
    }

    private void onSubcommandDisable(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.disable")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 0) {
            trailingArguments(sender, label, args, 1);
            return;
        }
        if (!PodzolCleanser.INSTANCE.getShouldRemovePodzol()) {
            sender.sendMessage(Component.text("Already disabled").color(NamedTextColor.RED));
            return;
        }
        PodzolCleanser.INSTANCE.setShouldRemovePodzol(false);
        // Response
        sender.sendMessage(Component.text("Set current state to: ").color(NamedTextColor.GREEN)
                .append(getCurrentState().color(NamedTextColor.GOLD)));
    }

    private void onSubcommandReload(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.reload")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 0) {
            trailingArguments(sender, label, args, 1);
            return;
        }
        // Response
        PodzolCleanser.INSTANCE.reloadConfig();
        sender.sendMessage(Component.text("Reloaded config. ").color(NamedTextColor.YELLOW)
                .append(Component.text("Current state: "))
                .append(getCurrentState().color(NamedTextColor.GOLD)));
    }

    private void onSubcommandInfo(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("podzolcleanser.info")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 0) {
            trailingArguments(sender, label, args, 1);
            return;
        }
        // Response
        sender.sendMessage(Component.text(String.format("--- %s ---\n", PodzolCleanser.INSTANCE.getName())).color(NamedTextColor.GOLD)
                .append(Component.text(PodzolCleanser.INSTANCE.getDescription().getDescription()).color(NamedTextColor.YELLOW))
                .append(Component.text("\nCurrent state: ").color(NamedTextColor.YELLOW))
                .append(getCurrentState().color(NamedTextColor.GOLD)));
    }
    
    private TextComponent getCurrentState() {
        return Component.text(PodzolCleanser.INSTANCE.getShouldRemovePodzol() ? "enabled" : "disabled");
    }

    // TODO: Create tests for this method
    private void trailingArguments(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args, int validArguments) {
        String validPart = label + " " + String.join(" ", Arrays.copyOfRange(args, 0, validArguments));
        validPart = validPart.substring(validPart.length() - 9);
        String invalidPart = String.join(" ", Arrays.copyOfRange(args, validArguments, args.length));

        Component message = Component.translatable("command.unknown.argument").color(NamedTextColor.RED)
                .append(Component.text("\n..." + validPart + " ").color(NamedTextColor.GRAY))
                .append(Component.text(invalidPart).decorate(TextDecoration.UNDERLINED))
                .append(Component.translatable("command.context.here").decorate(TextDecoration.ITALIC));

        sender.sendMessage(message);
    }
}
