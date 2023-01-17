package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.bukkit.command.brigadier.MessageArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.commodore.MinecraftArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentMessage;

public class ArgumentBukkitMessage extends ArgumentMessage implements BukkitArgument<String> {

    @Override
    public ArgumentType<?> brigadier() {
        return MessageArgumentType.message();
    }

    @Override
    public ArgumentType<?> commodore() {
        return MinecraftArgumentType.MESSAGE.get();
    }

    @Override
    public String parse(CommandContext context, String name) {
        return MessageArgumentType.getMessage(((BukkitCommandContext) context).brigadier(), name);
    }

}
