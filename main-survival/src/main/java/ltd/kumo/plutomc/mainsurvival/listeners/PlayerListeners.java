package ltd.kumo.plutomc.mainsurvival.listeners;

import ltd.kumo.plutomc.framework.player.AbstractPlayer;
import ltd.kumo.plutomc.framework.utility.colorpattle.Catppuccin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.incendo.interfaces.paper.type.BookInterface;

/**
 * 玩家监听器
 */
@SuppressWarnings("unused")
public class PlayerListeners implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var bukkitPlayer = AbstractPlayer.bukkit(player);

        var metaContainer = bukkitPlayer.metaContainer();

        if (!Boolean.parseBoolean(metaContainer.getWithDefault("main_survival_server.temp_broadcast", "false"))) {
            BookInterface bookInterface = BookInterface.builder()
                    .title(Component.text("！重要信息！").color(Catppuccin.LATTE.TEXT)
                            .appendNewline()
                            .append(Component.text("服务器目前仍在开发，暂时没有很多扩展性内容。").color(Catppuccin.LATTE.PEACH))
                            .append(Component.text("如果你遇到了问题或有什么好的建议请及时向我们反馈。").color(Catppuccin.LATTE.PEACH))
                            .appendNewline()
                            .append(Component.text("[点击此处以后不再提示]")
                                    .color(Catppuccin.LATTE.GREEN)
                                    .clickEvent(ClickEvent.runCommand("temp_broadcast_finish"))))
                    .build();
        }
    }
}
