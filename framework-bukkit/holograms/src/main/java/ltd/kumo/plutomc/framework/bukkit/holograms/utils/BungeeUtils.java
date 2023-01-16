package ltd.kumo.plutomc.framework.bukkit.holograms.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.experimental.UtilityClass;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHolograms;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;

@UtilityClass
public class BungeeUtils {

    private static final PlutoHolograms PLUTO_HOLOGRAMS = PlutoHologramsAPI.get();
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(PLUTO_HOLOGRAMS.getPlugin(), "BungeeCord");
        initialized = true;
    }

    public static void destroy() {
        if (!initialized) return;
        Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.unregisterOutgoingPluginChannel(PLUTO_HOLOGRAMS.getPlugin(), "BungeeCord");
        initialized = false;
    }

    public static void connect(Player player, String server) {
        if (!initialized) init();
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(PLUTO_HOLOGRAMS.getPlugin(), "BungeeCord", out.toByteArray());
        } catch (Exception ignored) {
        }
    }

}
