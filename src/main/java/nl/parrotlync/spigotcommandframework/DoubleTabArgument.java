package nl.parrotlync.spigotcommandframework;

import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DoubleTabArgument extends TabArgument {
    public DoubleTabArgument(String name, boolean required) {
        super(name, required);
    }

    @Override
    public List<String> getSuggestions(CommandSender sender) {
        return new ArrayList<>();
    }

    @Override
    public @Nullable
    String validate(String arg) {
        try {
            Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            return String.format("Error parsing argument %s as double.", getName());
        }
        return null;
    }
}
