package ltd.kumo.plutomc.framework.bukkit.injector;

import io.netty.channel.Channel;
import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.RawClass;
import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.RawField;
import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.RawMethod;
import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.Ref;

import java.util.List;
import java.util.Queue;
import java.util.UUID;

public final class ProtocolReflections {

    public final static RawClass<?> CLASS_DEDICATED_SERVER = RawClass.of(Ref.nmsClass("server.dedicated.DedicatedServer"));
    public final static RawClass<?> CLASS_MINECRAFT_SERVER = RawClass.of(Ref.nmsClass("server.MinecraftServer"));
    public final static RawClass<?> CLASS_CRAFT_SERVER = RawClass.of(Ref.obcClass("CraftServer"));
    public final static RawClass<?> CLASS_CRAFT_PLAYER = RawClass.of(Ref.obcClass("entity.CraftPlayer"));
    public final static RawClass<?> CLASS_PLAYER_CONNECTION = RawClass.of(Ref.nmsClass("server.network.PlayerConnection"));
    public final static RawClass<?> CLASS_SERVER_CONNECTION = RawClass.of(Ref.nmsClass("server.network.ServerConnection"));
    public final static RawClass<?> CLASS_NETWORK_MANAGER = RawClass.of(Ref.nmsClass("network.NetworkManager"));
    public final static RawClass<?> CLASS_ENTITY_PLAYER = RawClass.of(Ref.nmsClass("server.level.EntityPlayer"));
    public final static RawClass<?> CLASS_PACKET_LOGIN_OUT_SUCCESS = RawClass.of(Ref.nmsClass("network.protocol.login.PacketLoginOutSuccess"));
    public final static RawClass<?> CLASS_GAME_PROFILE = RawClass.of(Ref.classOf("com.mojang.authlib.GameProfile"));

    public static final RawField FIELD_SERVER_CONNECTION;
    public static final RawField FIELD_NETWORK_MANAGERS_LIST;
    public static final RawField FIELD_PENDING_NETWORK_MANAGERS;
    public static final RawField FIELD_CHANNEL;
    public static final RawField FIELD_GAME_PROFILE;
    public static final RawField FIELD_PLAYER_CONNECTION;
    public static final RawField FIELD_NETWORK_MANAGER;

    public final static RawMethod METHOD_GET_SERVER;
    public final static RawMethod METHOD_GET_HANDLE;
    public final static RawMethod METHOD_GET_ID;


    static {
        FIELD_SERVER_CONNECTION = CLASS_MINECRAFT_SERVER.findField(false, CLASS_SERVER_CONNECTION.original());
        FIELD_NETWORK_MANAGERS_LIST = CLASS_SERVER_CONNECTION.findField(false, List.class, 1);
        FIELD_PENDING_NETWORK_MANAGERS = CLASS_SERVER_CONNECTION.findField(false, Queue.class);
        FIELD_CHANNEL = CLASS_NETWORK_MANAGER.findField(false, Channel.class);
        FIELD_GAME_PROFILE = CLASS_PACKET_LOGIN_OUT_SUCCESS.findField(false, CLASS_GAME_PROFILE.original());
        FIELD_PLAYER_CONNECTION = CLASS_ENTITY_PLAYER.findField(false, CLASS_PLAYER_CONNECTION.original());
        FIELD_NETWORK_MANAGER = CLASS_PLAYER_CONNECTION.findField(false, CLASS_NETWORK_MANAGER.original());

        METHOD_GET_SERVER = CLASS_CRAFT_SERVER.findMethod(false, CLASS_DEDICATED_SERVER.original());
        METHOD_GET_HANDLE = CLASS_CRAFT_PLAYER.findMethod(false, CLASS_ENTITY_PLAYER.original());
        METHOD_GET_ID = CLASS_GAME_PROFILE.findMethod(false, UUID.class);
    }

}
