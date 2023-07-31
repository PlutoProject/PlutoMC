package ltd.kumo.plutomc.modules.economy.commands;

import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommand;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitPlayer;
import ltd.kumo.plutomc.framework.bukkit.economy.BukkitEconomyService;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentDouble;
import ltd.kumo.plutomc.framework.shared.economy.EconomyService;

public class PayCommand {

    public static BukkitCommand command(BukkitPlatform platform) {
        BukkitCommand payCommand = platform.createCommand("pay");
        payCommand.aliases("transfer", "givemoney");
        payCommand.then("target", ArgumentBukkitPlayer.class)
                .thenDouble("amount", 0.0d, Double.MAX_VALUE)
                .executesPlayer((sender, context) -> {
                    BukkitEconomyService service = (BukkitEconomyService) platform.getService(EconomyService.class);
                    double amount = context.argument(ArgumentDouble.class, "amount");
                    double balance = service.getBalance(sender);
                    if (balance < amount)
                        context.error("您的余额不足");
                    // TODO
                });
        return payCommand;
    }

}
