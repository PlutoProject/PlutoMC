package club.plutomc.plutoproject.framework.bukkit.hologram;

import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface HologramGroup extends Iterable<Hologram> {

    @NotNull
    Location getLocation(); // Group的位置是指第一行的位置

    void setLocation(@NotNull Location location);

    void show();

    void hide();

    void refresh(BukkitPlayer player);

    void refreshAll();

    void drop();

    boolean isDropped();

    void addHologram(@NotNull Hologram hologram);

    void addHologramAll(@NotNull HologramGroup group);

    void removeHologram(@NotNull Hologram hologram);

    void removeHologramAll(@NotNull HologramGroup group);

    void addViewer(@NotNull BukkitPlayer player);

    void removeViewer(@NotNull BukkitPlayer player);

    List<Hologram> listHolograms();

}
