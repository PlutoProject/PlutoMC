package ltd.kumo.plutomc.framework.bukkit.holograms;

import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlutoHologramsAPI {

    private static PlutoHolograms implementation;

    public static void onLoad(@NonNull JavaPlugin plugin) {
        if (implementation != null)
            return;
        implementation = new PlutoHolograms(plugin);
        implementation.load();
    }

    /**
     * Enable PlutoHologramsAPI.
     */
    public static void onEnable() {
        if (implementation == null)
            return;
        implementation.enable();
    }

    /**
     * Disable PlutoHologramsAPI.
     */
    public static void onDisable() {
        if (implementation == null)
            return;
        implementation.disable();
        implementation = null;
    }

    /**
     * Get the instance of running PlutoHolograms.
     *
     * @return the instance of running PlutoHolograms.
     */
    public static PlutoHolograms get() {
        if (implementation == null)
            throw new IllegalStateException("There is no running instance of PlutoHologramsAPI, enable it first.");
        return implementation;
    }

}
