package nl.parrotlync.spigotcommandframework;

import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class IntegerTabArgument extends TabArgument {
    public IntegerTabArgument(String name, boolean required) {
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
            Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return String.format("Error parsing argument %s as integer.", getName());
        }
        return null;
    }
}
