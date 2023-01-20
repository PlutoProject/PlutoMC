package ltd.kumo.plutomc.modules.whitelist;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import ltd.kumo.plutomc.framework.shared.utilities.colorpattle.Catppuccin;
import ltd.kumo.plutomc.modules.whitelist.utils.ProfileUtil;
import net.kyori.adventure.text.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused"})
public final class WhitelistCommand {

    private final static CommandExceptionType AUTHORIZED_FAILED = new SimpleCommandExceptionType(new LiteralMessage("验证失败。"));

    public static BrigadierCommand brigadier() {
        return new BrigadierCommand(commandNode());
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

        CompletableFuture.supplyAsync(() -> {
            System.out.println(1);
            UUID uuid;

            try {
                System.out.println("1-1");
                uuid = ProfileUtil.getUUID(playerName.toLowerCase());
                System.out.println(uuid);
                return uuid;
            } catch (IOException e) {
                System.out.println("1-2");
                source.sendMessage(Component.text("获取UUID失败。").color(Catppuccin.MOCHA.RED));
                return null;
            }
        }).thenAcceptAsync(uuid -> {
            if (uuid == null) {
                System.out.println("1-3");
                return;
            }

            Objects.requireNonNull(WhitelistModule.getWhitelistManager());
            if (WhitelistModule.getWhitelistManager().hasWhitelist(uuid)) {
                System.out.println("1-4");
                source.sendMessage(Component.text("该用户已有白名单。"));
                return;
            }
            System.out.println("1-5");

            WhitelistModule.getWhitelistManager().createUser(playerName.toLowerCase(), qqNumber, uuid);
            source.sendMessage(Component.text("添加成功。").color(Catppuccin.MOCHA.GREEN));
        });
    }

    private static void executeRemove(CommandSource source, String playerName) {
        CompletableFuture.runAsync(() -> {
            System.out.println(2);
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
        CompletableFuture.supplyAsync(() -> {
            System.out.println(3);
            Objects.requireNonNull(WhitelistModule.getWhitelistManager());

            final int size = WhitelistModule.getWhitelistManager().getAllUser().size();
            source.sendMessage(Component.text("数据库中一共有 " + size + " 条白名单数据。").color(Catppuccin.MOCHA.GREEN));

            return null;
        });
    }
}
