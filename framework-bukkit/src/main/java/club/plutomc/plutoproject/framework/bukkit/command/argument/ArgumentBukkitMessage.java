package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandReflections;
import club.plutomc.plutoproject.framework.bukkit.command.MinecraftArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import club.plutomc.plutoproject.framework.shared.command.arguments.ArgumentMessage;

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
