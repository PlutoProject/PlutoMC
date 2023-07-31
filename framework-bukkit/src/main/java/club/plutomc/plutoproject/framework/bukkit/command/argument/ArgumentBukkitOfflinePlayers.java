package club.plutomc.plutoproject.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitArgument;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandContext;
import club.plutomc.plutoproject.framework.bukkit.command.BukkitCommandReflections;
import club.plutomc.plutoproject.framework.bukkit.command.MinecraftArgumentType;
import club.plutomc.plutoproject.framework.shared.command.CommandContext;
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
