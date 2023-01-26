package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandReflections;
import ltd.kumo.plutomc.framework.bukkit.command.MinecraftArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

public class ArgumentBukkitOfflinePlayers implements BukkitArgument<List<OfflinePlayer>> {

    @Override
    public ArgumentType<?> brigadier() {
        return MinecraftArgumentType.GAME_PROFILE.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OfflinePlayer> parse(CommandContext context, String name) {
        List<Object> gameProfiles = (List<Object>) BukkitCommandReflections.METHOD_GET_GAME_PROFILES.invokeStatic(((BukkitCommandContext) context).brigadier(), name);
        return gameProfiles.stream()
                .map(gameProfile -> BukkitCommandReflections.METHOD_GET_ID.invoke(gameProfile))
                .map(uuid -> (UUID) uuid)
                .map(Bukkit::getOfflinePlayer)
                .toList();
    }

}
