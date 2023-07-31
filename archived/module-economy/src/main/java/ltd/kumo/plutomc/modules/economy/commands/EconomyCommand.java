package ltd.kumo.plutomc.modules.economy.commands;

import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommand;
import ltd.kumo.plutomc.framework.bukkit.economy.BukkitEconomyService;
import ltd.kumo.plutomc.framework.shared.economy.EconomyService;
import ltd.kumo.plutomc.framework.shared.utilities.colorpattle.Catppuccin;
import net.kyori.adventure.text.Component;

public class EconomyCommand {

    public static BukkitCommand command(BukkitPlatform platform) {
        BukkitCommand economyCommand = platform.createCommand("economy");
        economyCommand.aliases("eco", "balance", "coin", "money");
        economyCommand.executesPlayer((sender, context) -> {
            BukkitEconomyService service = (BukkitEconomyService) platform.getService(EconomyService.class);
            double economy = service.getBalance(sender);
            sender.send(Component.text("您的余额为 ")
                    .color(Catppuccin.FRAPPE.GREEN)
                    .append(Component.text(economy)
                            .color(Catppuccin.FRAPPE.TEXT)));
        });
        return economyCommand;
    }

}
