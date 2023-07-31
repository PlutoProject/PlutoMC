package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandReflections;
import club.plutomc.plutoproject.framework.bukkit.command.MinecraftArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
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
