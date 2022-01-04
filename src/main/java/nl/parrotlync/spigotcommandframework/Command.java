package nl.parrotlync.spigotcommandframework;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Command implements TabExecutor {
    protected final HashMap<Integer, TabArgument> arguments = new HashMap<>();
    @Getter private final String permission;

    public Command(String permission) {
        this.permission = permission;
    }

    public abstract void executeCommand(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] args) {
        // Permission check
        if (!sender.hasPermission(getPermission())) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command");
            return true;
        }

        // Check if there are missing arguments & call validators
        if (!CommandUtils.checkArgs(sender, arguments, args)) {
            return true;
        }

        // Execute the command
        this.executeCommand(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (getPermission() != null && !sender.hasPermission(getPermission())) { return suggestions; }

        if (arguments.containsKey(args.length - 1)) {
            TabArgument argument = arguments.get(args.length - 1);
            suggestions.addAll(argument.getSuggestions(sender));
            return StringUtil.copyPartialMatches(args[args.length - 1], suggestions, new ArrayList<>());
        }

        return suggestions;
    }
}