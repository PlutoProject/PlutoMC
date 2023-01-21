package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandReflections;
import ltd.kumo.plutomc.framework.bukkit.command.MinecraftArgumentType;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import org.bukkit.entity.Player;

public class ArgumentBukkitPlayer implements BukkitArgument<BukkitPlayer> {

    @Override
    public BukkitPlayer parse(CommandContext context, String name) {
        Object entityPlayer = BukkitCommandReflections.METHOD_GET_ENTITY_PLAYER.invokeStatic(((BukkitCommandContext) context).brigadier(), name);
        Player player = (Player) BukkitCommandReflections.METHOD_GET_BUKKIT_PLAYER.invoke(entityPlayer);
        return BukkitPlayer.of(player);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return MinecraftArgumentType.ENTITY.create(true, true);
    }

}
