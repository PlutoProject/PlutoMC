package ltd.kumo.plutomc.mainsurvival.commands;

import ltd.kumo.plutomc.framework.player.BukkitPlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TempCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            var bukkitPlayer = BukkitPlayer.of(player);

            var metaContainer = bukkitPlayer.metaContainer();

            metaContainer.set("main_survival_server.temp_broadcast", "true");
            metaContainer.apply();

            player.closeInventory();
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0F);
        }

        return true;
    }
}
