package ltd.kumo.plutomc.framework.bukkit.command.argument;

import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class ArgumentBukkitPlayer extends ArgumentPlayer<BukkitPlayer, Player> {

    @Override
    public List<BukkitPlayer> parse(CommandContext context, String name) {
        // TODO
        return null;
    }

}
