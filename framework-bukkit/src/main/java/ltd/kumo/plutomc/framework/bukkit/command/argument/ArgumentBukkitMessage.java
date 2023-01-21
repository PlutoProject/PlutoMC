package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandReflections;
import ltd.kumo.plutomc.framework.bukkit.command.MinecraftArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentMessage;

public class ArgumentBukkitMessage extends ArgumentMessage implements BukkitArgument<String> {

    @Override
    public ArgumentType<?> brigadier() {
        return MinecraftArgumentType.MESSAGE.get();
    }

    @Override
    public String parse(CommandContext context, String name) {
        Object message = BukkitCommandReflections.METHOD_GET_MESSAGE.invokeStatic(((BukkitCommandContext) context).brigadier(), name);
        return (String) BukkitCommandReflections.METHOD_GET_STRING.invoke(message);
    }

}
