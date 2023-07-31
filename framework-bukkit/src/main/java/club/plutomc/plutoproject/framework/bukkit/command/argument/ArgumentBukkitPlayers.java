package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandReflections;
import club.plutomc.plutoproject.framework.bukkit.command.MinecraftArgumentType;
import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
import org.bukkit.entity.Player;

import java.util.List;

public class ArgumentBukkitPlayers implements BukkitArgument<List<BukkitPlayer>> {

    @Override
    public List<BukkitPlayer> parse(CommandContext context, String name) {
        List<?> entityPlayers = (List<?>) BukkitCommandReflections.METHOD_GET_ENTITY_PLAYERS.invokeStatic(((BukkitCommandContext) context).brigadier(), name);
        return entityPlayers.stream()
                .map(entityPlayer -> BukkitCommandReflections.METHOD_GET_BUKKIT_PLAYER.invoke(entityPlayer))
                .map(entityPlayer -> (Player) entityPlayer)
                .map(BukkitPlayer::of)
                .toList();
    }

    @Override
    public ArgumentType<?> brigadier() {
        return MinecraftArgumentType.ENTITY.create(false, true);
    }

}
