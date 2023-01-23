package ltd.kumo.plutomc.modules.economy.commands;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import ltd.kumo.plutomc.framework.bukkit.BukkitPlatform;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommand;
import ltd.kumo.plutomc.framework.bukkit.command.argument.ArgumentBukkitPlayer;
import ltd.kumo.plutomc.framework.bukkit.economy.BukkitEconomyService;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentDouble;
import ltd.kumo.plutomc.framework.shared.economy.EconomyService;

public class PayCommand {

    private final static SimpleCommandExceptionType MONEY_NOT_ENOUGH = new SimpleCommandExceptionType(new LiteralMessage("您的余额不足"));

    public static BukkitCommand command(BukkitPlatform platform) {
        BukkitCommand payCommand = platform.createCommand("pay");
        payCommand.aliases("支付", "transfer", "转账", "givemoney", "付钱", "给钱");
        payCommand.then("target", ArgumentBukkitPlayer.class)
                .thenDouble("amount", 0.0d, Double.MAX_VALUE)
                .executesPlayer((sender, context) -> {
                    BukkitEconomyService service = (BukkitEconomyService) platform.getService(EconomyService.class);
                    double amount = context.argument(ArgumentDouble.class, "amount");
                    double balance = service.getBalance(sender);
                    if (balance < amount)
                        throw MONEY_NOT_ENOUGH.create();
                    // TODO
                });
        return payCommand;
    }

}
