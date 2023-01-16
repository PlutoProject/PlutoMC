package ltd.kumo.plutomc.framework.bukkit.utilities;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandSender;

import java.util.*;

public class BrigadierUtility {

    public static boolean findValid(String input, CommandDispatcher<BukkitCommandSender> dispatcher) {
        for (CommandNode<BukkitCommandSender> node : dispatcher.getRoot().getChildren()) {
            if (node instanceof LiteralCommandNode<BukkitCommandSender> literalCommandNode)
                if (literalCommandNode.isValidInput(input))
                    return true;
        }
        return false;
    }

    public static boolean isExecutable(String string, BukkitCommandSender sender, CommandDispatcher<BukkitCommandSender> dispatcher) throws CommandSyntaxException {
        ParseResults<BukkitCommandSender> parse = dispatcher.parse(new StringReader(string), sender);
        if (parse.getReader().canRead()) {
            return false;
        } else {
            String command = parse.getReader().getString();
            CommandContext<BukkitCommandSender> original = parse.getContext().build(command);
            List<CommandContext<BukkitCommandSender>> contexts = Collections.singletonList(original);

            for(ArrayList<CommandContext<BukkitCommandSender>> next = null; contexts != null; next = null) {
                int size = contexts.size();

                for(int i = 0; i < size; ++i) {
                    CommandContext context = (CommandContext) ((List<?>)contexts).get(i);
                    CommandContext<BukkitCommandSender> child = context.getChild();
                    if (child != null) {
                        if (child.hasNodes())
                            return true;
                    } else if (context.getCommand() != null)
                        return true;
                }

                contexts = next;
            }
            return false;
        }
    }

}
