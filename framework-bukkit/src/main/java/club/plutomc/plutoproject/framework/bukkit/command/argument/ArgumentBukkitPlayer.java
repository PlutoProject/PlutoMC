package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandReflections;
import club.plutomc.plutoproject.framework.bukkit.command.MinecraftArgumentType;
import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
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
