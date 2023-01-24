package ltd.kumo.plutomc.modules.whitelist;

import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentLong;
import ltd.kumo.plutomc.framework.shared.command.arguments.ArgumentString;
import ltd.kumo.plutomc.framework.shared.utilities.colorpattle.Catppuccin;
import ltd.kumo.plutomc.framework.velocity.VelocityPlatform;
import ltd.kumo.plutomc.framework.velocity.command.VelocityCommand;
import ltd.kumo.plutomc.framework.velocity.player.VelocityPlayer;
import ltd.kumo.plutomc.modules.whitelist.utils.ProfileUtil;
import net.kyori.adventure.text.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused"})
public final class WhitelistCommand {

    public static BrigadierCommand brigadier() {
        return new BrigadierCommand(commandNode());
    }

    public static VelocityCommand pluto(VelocityPlatform platform) {
        VelocityCommand command = platform.createCommand("whitelist");
        command.then("add")
                .then("playerName", ArgumentString.class)
                .thenLong("qqNumber", 10000, Long.MAX_VALUE)
                .executes((sender, context) -> {
                    executeAdd(((VelocityPlayer)sender).asVelocity(),
                            context.argument(ArgumentString.class, "playerName"),
                            context.argument(ArgumentLong.class, "qqNumber"));
                });
        command.then("remove")
                .then("playerName", ArgumentString.class)
                .executes((sender, context) -> {
                    executeRemove(((VelocityPlayer)sender).asVelocity(),
                            context.argument(ArgumentString.class, "playerName"));
                });
        command.then("list")
                .executes((sender, context) -> {
                    executeList(((VelocityPlayer)sender).asVelocity());
                });
        return command;
    }

    private static LiteralCommandNode<CommandSource> commandNode() {
        return LiteralArgumentBuilder.<CommandSource>literal("whitelist")
                .requires(source -> source.hasPermission("plutomc.whitelist"))
                .then(LiteralArgumentBuilder.<CommandSource>literal("add")
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("playerName", StringArgumentType.string())
                                .then(RequiredArgumentBuilder.<CommandSource, Long>argument("qqNumber", LongArgumentType.longArg(10000))
                                        .executes(context -> {
                                            executeAdd(context.getSource(), StringArgumentType.getString(context, "playerName"), LongArgumentType.getLong(context, "qqNumber"));
                                            return 1;
                                        }))))
                .then(LiteralArgumentBuilder.<CommandSource>literal("remove")
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("playerName", StringArgumentType.string())
                                .executes(context -> {
                                    executeRemove(context.getSource(), StringArgumentType.getString(context, "playerName"));
                                    return 1;
                                })))

                .then(LiteralArgumentBuilder.<CommandSource>literal("list")
                        .executes(context -> {
                            executeList(context.getSource());
                            return 1;
                        }))
                .build();
    }

    private static void executeAdd(CommandSource source, String playerName, long qqNumber) {
        source.sendMessage(Component.text("正在验证，请稍等。").color(Catppuccin.MOCHA.GREEN));

        CompletableFuture.runAsync(() -> {
            UUID uuid;

            try {
                uuid = ProfileUtil.getUUID(playerName.toLowerCase());
            } catch (Exception e) {
                source.sendMessage(Component.text("获取UUID失败。").color(Catppuccin.MOCHA.RED));
                return;
            }

            if (uuid == null) {
                source.sendMessage(Component.text("获取UUID失败。").color(Catppuccin.MOCHA.RED));
                return;
            }

            Objects.requireNonNull(WhitelistModule.getWhitelistManager());

            if (WhitelistModule.getWhitelistManager().hasWhitelist(uuid)) {
                source.sendMessage(Component.text("该用户已有白名单。"));
                return;
            }

            WhitelistModule.getWhitelistManager().createUser(playerName.toLowerCase(), qqNumber, uuid);
            source.sendMessage(Component.text("添加成功。").color(Catppuccin.MOCHA.GREEN));
        });
    }

    private static void executeRemove(CommandSource source, String playerName) {
        CompletableFuture.runAsync(() -> {
            Objects.requireNonNull(WhitelistModule.getWhitelistManager());

            if (!WhitelistModule.getWhitelistManager().hasWhitelist(playerName.toLowerCase())) {
                source.sendMessage(Component.text("该用户没有白名单。").color(Catppuccin.MOCHA.RED));
                return;
            }

            WhitelistModule.getWhitelistManager().removeUser(playerName.toLowerCase());
            source.sendMessage(Component.text("移除成功。").color(Catppuccin.MOCHA.GREEN));
        });
    }

    private static void executeList(CommandSource source) {
        CompletableFuture.runAsync(() -> {
            Objects.requireNonNull(WhitelistModule.getWhitelistManager());

            final int size = WhitelistModule.getWhitelistManager().getAllUser().size();
            source.sendMessage(Component.text("数据库中一共有 " + size + " 条白名单数据。").color(Catppuccin.MOCHA.GREEN));
        });
    }
}
