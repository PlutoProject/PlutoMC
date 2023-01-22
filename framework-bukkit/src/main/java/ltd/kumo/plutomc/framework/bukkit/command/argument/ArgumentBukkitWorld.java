package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandReflections;
import ltd.kumo.plutomc.framework.bukkit.command.MinecraftArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import org.bukkit.World;

public class ArgumentBukkitWorld implements BukkitArgument<World> {

    @Override
    public ArgumentType<?> brigadier() {
        return MinecraftArgumentType.DIMENSION.get();
    }

    @Override
    public World parse(CommandContext context, String name) {
        Object worldServer = BukkitCommandReflections.METHOD_GET_DIMENSION.invoke(null, ((BukkitCommandContext) context).brigadier(), name);
        return (World) BukkitCommandReflections.METHOD_GET_WORLD.invoke(worldServer);
    }

}
