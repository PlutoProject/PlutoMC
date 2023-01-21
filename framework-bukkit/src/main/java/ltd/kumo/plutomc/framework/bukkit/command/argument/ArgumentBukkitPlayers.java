package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandReflections;
import ltd.kumo.plutomc.framework.bukkit.command.MinecraftArgumentType;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
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
