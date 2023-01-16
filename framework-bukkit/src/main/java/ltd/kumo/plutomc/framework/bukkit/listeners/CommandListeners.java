package ltd.kumo.plutomc.framework.bukkit.listeners;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.bukkit.utilities.BrigadierUtility;
import ltd.kumo.plutomc.framework.shared.utilities.colorpattle.Catppuccin;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class CommandListeners implements Listener {

    private final BukkitPlatform platform;

    public CommandListeners(BukkitPlatform platform) {
        this.platform = platform;
    }

    @EventHandler
    public void command(PlayerCommandPreprocessEvent event) throws CommandSyntaxException {
        String command = event.getMessage().substring(1);
        BukkitPlayer bukkitPlayer = BukkitPlayer.of(event.getPlayer());
        if (!BrigadierUtility.findValid(command, this.platform.getCommandManager().dispatcher()))
            return;
        event.setCancelled(true);
        try {
            this.platform.getCommandManager().dispatcher().execute(command, bukkitPlayer);
        } catch (CommandSyntaxException e) {
            event.getPlayer().sendMessage(Component.text(e.getMessage()).color(Catppuccin.LATTE.MAROON));
        }
    }

}
