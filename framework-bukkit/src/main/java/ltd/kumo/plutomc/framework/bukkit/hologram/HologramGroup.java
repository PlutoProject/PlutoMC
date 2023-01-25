package ltd.kumo.plutomc.framework.bukkit.hologram;

import ltd.kumo.plutomc.framework.bukkit.player.BukkitPlayer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface HologramGroup {

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

    List<Hologram> listHolograms();

}
