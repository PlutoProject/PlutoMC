package ltd.kumo.plutomc.mainsurvival.listeners;

import com.google.common.collect.ImmutableList;
import ltd.kumo.plutomc.framework.player.BukkitPlayer;
import ltd.kumo.plutomc.framework.utility.colorpattle.Catppuccin;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 玩家监听器
 */
@SuppressWarnings("unused")
public class PlayerListeners implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var bukkitPlayer = BukkitPlayer.of(player);

        var metaContainer = bukkitPlayer.metaContainer();

        if (!Boolean.parseBoolean(metaContainer.getWithDefault("main_survival_server.temp_broadcast", "false"))) {
            Book book = Book.book(Component.text(" "), Component.text(" "), ImmutableList.of(Component.text("！重要信息！").color(Catppuccin.LATTE.TEXT)
                    .appendNewline()
                    .appendNewline()
                    .append(Component.text("服务器目前仍在开发，暂时没有很多扩展性内容。").color(Catppuccin.LATTE.PEACH))
                    .append(Component.text("如果你遇到了问题或有什么好的建议请及时向我们反馈。").color(Catppuccin.LATTE.PEACH))
                    .appendNewline()
                    .appendNewline()));

            player.openBook(book);
        }
    }
}
