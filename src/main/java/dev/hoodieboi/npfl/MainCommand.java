package dev.hoodieboi.npfl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
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
        switch (args[0].toLowerCase()) {
            case "enable" -> onSubcommandEnable(sender, command, label, args);
            case "disable" -> onSubcommandDisable(sender, command, label, args);
            case "reload" -> onSubcommandReload(sender, command, label, args);
            case "info" -> onSubcommandInfo(sender, command, label, args);
            default -> {
                sender.sendMessage(incorrectArguments(label, args, 0));
                return true;
            }
        }
        return true;
    }

    private void onSubcommandEnable(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("npfl.enable")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 1) {
            sender.sendMessage(incorrectArguments(label, args, 1));
            return;
        }
        if (Plugin.INSTANCE.getShouldRemovePodzol()) {
            sender.sendMessage(Component.text("Already enabled").color(NamedTextColor.RED));
            return;
        }
        Plugin.INSTANCE.setShouldRemovePodzol(true);
        // Response
        sender.sendMessage(Component.text("Set current state to: ").color(NamedTextColor.GREEN)
                .append(getCurrentState().color(NamedTextColor.GOLD)));
    }

    private void onSubcommandDisable(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("npfl.disable")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 1) {
            sender.sendMessage(incorrectArguments(label, args, 1));
            return;
        }
        if (!Plugin.INSTANCE.getShouldRemovePodzol()) {
            sender.sendMessage(Component.text("Already disabled").color(NamedTextColor.RED));
            return;
        }
        Plugin.INSTANCE.setShouldRemovePodzol(false);
        // Response
        sender.sendMessage(Component.text("Set current state to: ").color(NamedTextColor.GREEN)
                .append(getCurrentState().color(NamedTextColor.GOLD)));
    }

    private void onSubcommandReload(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("npfl.reload")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 1) {
            sender.sendMessage(incorrectArguments(label, args, 1));
            return;
        }
        // Response
        Plugin.INSTANCE.reloadConfig();
        sender.sendMessage(Component.text("Reloaded config. ").color(NamedTextColor.YELLOW)
                .append(Component.text("Current state: "))
                .append(getCurrentState().color(NamedTextColor.GOLD)));
    }

    private void onSubcommandInfo(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("npfl.info")) {
            sender.sendMessage(command.permissionMessage().color(NamedTextColor.RED));
            return;
        }
        if (args.length > 1) {
            sender.sendMessage(incorrectArguments(label, args, 1));
            return;
        }
        // Response
        sender.sendMessage(Component.text(String.format("--- %s ---\n", Plugin.INSTANCE.getName())).color(NamedTextColor.GOLD)
                .append(Component.text(Plugin.INSTANCE.getPluginMeta().getDescription()).color(NamedTextColor.YELLOW))
                .append(Component.text("\nCurrent state: ").color(NamedTextColor.YELLOW))
                .append(getCurrentState().color(NamedTextColor.GOLD)));
    }
    
    private TextComponent getCurrentState() {
        return Component.text(Plugin.INSTANCE.getShouldRemovePodzol() ? "enabled" : "disabled");
    }

    public static Component incorrectArguments(@NotNull String label, @NotNull String[] args, int validArguments) {
        String validPart = label + " " + String.join(" ", Arrays.copyOfRange(args, 0, validArguments));
        if (validPart.length() > 10)
            validPart = "..." + validPart.substring(validPart.length() - 10);
        String invalidPart = String.join(" ", Arrays.copyOfRange(args, validArguments, args.length));

        return Component.translatable("command.unknown.argument").color(NamedTextColor.RED)
                .append(Component.text("\n" + validPart + (validArguments >= 1 ? " " : "")).color(NamedTextColor.GRAY))
                .append(Component.text(invalidPart).decorate(TextDecoration.UNDERLINED))
                .append(Component.translatable("command.context.here").decorate(TextDecoration.ITALIC));
    }
}
