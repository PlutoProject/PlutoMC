package ltd.kumo.plutomc.modules.whitelist;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import ltd.kumo.plutomc.framework.shared.utilities.colorpattle.Catppuccin;
import ltd.kumo.plutomc.modules.whitelist.utils.ProfileUtil;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused"})
public final class WhitelistCommand implements SimpleCommand {
    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length == 0) {
            return;
        }

        if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove") && !args[0].equalsIgnoreCase("list")) {
            return;
        }

        if (args[0].equalsIgnoreCase("add") && args[1] != null && args[2] != null && StringUtils.isNumeric(args[2])) {
            source.sendMessage(Component.text("正在验证，请稍等。").color(Catppuccin.MOCHA.GREEN));

            CompletableFuture.supplyAsync(() -> {
                UUID uuid;

                try {
                    uuid = ProfileUtil.getUUID(args[1].toLowerCase());
                    return uuid;
                } catch (IOException e) {
                    return null;
                }

            }).thenAcceptAsync(uuid -> {
                if (uuid == null) {
                    source.sendMessage(Component.text("用户验证失败。").color(Catppuccin.MOCHA.RED));
                }

                Objects.requireNonNull(WhitelistModule.getWhitelistManager());
                Objects.requireNonNull(uuid);

                if (WhitelistModule.getWhitelistManager().hasWhitelist(uuid)) {
                    source.sendMessage(Component.text("该用户已有白名单。"));
                    return;
                }

                WhitelistModule.getWhitelistManager().createUser(args[1].toLowerCase(), Long.parseLong(args[2]), uuid);
                source.sendMessage(Component.text("添加成功。").color(Catppuccin.MOCHA.GREEN));
            });
        }

        if (args[0].equalsIgnoreCase("remove") && args[1] != null) {
            CompletableFuture.supplyAsync(() -> {
                Objects.requireNonNull(WhitelistModule.getWhitelistManager());

                if (!WhitelistModule.getWhitelistManager().hasWhitelist(args[1].toLowerCase())) {
                    source.sendMessage(Component.text("该用户没有白名单。").color(Catppuccin.MOCHA.RED));
                }

                WhitelistModule.getWhitelistManager().removeUser(args[1].toLowerCase());
                source.sendMessage(Component.text("移除成功。").color(Catppuccin.MOCHA.GREEN));

                return null;
            });
        }

        if (args[0].equalsIgnoreCase("list")) {
            CompletableFuture.supplyAsync(() -> {
                Objects.requireNonNull(WhitelistModule.getWhitelistManager());

                final int size = WhitelistModule.getWhitelistManager().getAllUser().size();
                source.sendMessage(Component.text("数据库中一共有 " + size + " 条白名单数据。").color(Catppuccin.MOCHA.GREEN));

                return null;
            });
        }

        source.sendMessage(Component.text("请检查参数是否完整。").color(Catppuccin.MOCHA.RED));
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("plutomc.whitelist");
    }

    @Override
    public List<String> suggest(final Invocation invocation) {
        return List.of();
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        return CompletableFuture.completedFuture(List.of());
    }
}
