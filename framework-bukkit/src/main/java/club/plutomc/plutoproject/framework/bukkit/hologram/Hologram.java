package club.plutomc.plutoproject.framework.bukkit.hologram;

import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface Hologram {

    @NotNull
    Location getLocation();

    void setLocation(@NotNull Location location);

    void show();

    void hide();

    void refreshAll();

    void refresh(@NotNull BukkitPlayer player);

    void drop();

    boolean isDropped();

    void addViewer(@NotNull BukkitPlayer player);

    void removeViewer(@NotNull BukkitPlayer player);

    boolean hasViewer(@NotNull BukkitPlayer player);

    double getHeight(); // 返回高度，方便HologramGroup计算下一行的位置

    @NotNull
    Collection<BukkitPlayer> listViewers();

}
