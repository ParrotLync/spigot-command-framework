package nl.parrotlync.spigotcommandframework;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandUtils {
    private static String getArgsMessage(List<String> missingArguments) {
        return String.format(ChatColor.RED + "The following arguments are missing: " + ChatColor.GRAY + "%s", missingArguments.toString());
    }

    private static List<String> getMissingArguments(String[] args, Map<Integer, TabArgument> tabArguments) {
        List<String> missingArguments = new ArrayList<>();
        for (TabArgument argument : new ArrayList<>(tabArguments.values()).subList(args.length, tabArguments.size())) {
            if (argument.isRequired()) { missingArguments.add(argument.getName()); }
        }
        return missingArguments;
    }

    public static boolean checkArgs(CommandSender sender, Map<Integer, TabArgument> tabArguments, String[] args) {
        // Check if there are missing arguments
        if (args.length < tabArguments.size()) {
            List<String> missingArguments = getMissingArguments(args, tabArguments);
            if (missingArguments.size() != 0) {
                sender.sendMessage(getArgsMessage(missingArguments));
                return false;
            }
        }

        // Call argument validators
        for (Integer key : tabArguments.keySet()) {
            TabArgument argument = tabArguments.get(key);
            String validateResult = argument.validate(args[key]);
            if (validateResult != null) {
                String message = String.format(ChatColor.DARK_RED + "Validator failed: " + ChatColor.RED + "%s", validateResult);
                sender.sendMessage(message);
                return false;
            }
        }

        return true;
    }
}
