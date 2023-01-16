package ltd.kumo.plutomc.framework.bukkit.holograms.utils;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class HeadDatabaseUtils {

    public static final HeadDatabaseAPI API = new HeadDatabaseAPI();

    @Nullable
    public static ItemStack getHeadItemStackById(String id) {
        return API.getItemHead(id);
    }
}
