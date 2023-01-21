package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.MinecraftArgumentType;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;

import java.util.List;

public class ArgumentBukkitPlayers implements BukkitArgument<List<BukkitPlayer>> {

    @Override
    public List<BukkitPlayer> parse(CommandContext context, String name) {
        // TODO
        return null;
    }

    @Override
    public ArgumentType<?> brigadier() {
        return MinecraftArgumentType.ENTITY.create(false, true);
    }

}
