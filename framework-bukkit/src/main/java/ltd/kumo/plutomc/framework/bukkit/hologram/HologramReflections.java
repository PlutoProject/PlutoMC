package ltd.kumo.plutomc.framework.bukkit.hologram;

import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.*;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.UUID;

public final class HologramReflections {

    public final static RawClass<?> CLASS_ENTITY = RawClass.of(Ref.nmsClass("world.entity.Entity"));
    public final static RawClass<?> CLASS_ENTITY_LIVING = RawClass.of(Ref.nmsClass("world.entity.EntityLiving"));
    public final static RawClass<?> CLASS_ENTITY_ARMOR_STAND = RawClass.of(Ref.nmsClass("world.entity.decoration.EntityArmorStand"));
    public final static RawClass<?> CLASS_DATA_WATCHER = RawClass.of(Ref.nmsClass("network.syncher.DataWatcher"));
    public final static RawClass<?> CLASS_ENTITY_TYPES = RawClass.of(Ref.nmsClass("world.entity.EntityTypes"));
    public final static RawClass<?> CLASS_I_CHAT_BASE_COMPONENT = RawClass.of(Ref.nmsClass("network.chat.IChatBaseComponent"));
    public final static RawClass<?> CLASS_WORLD = RawClass.of(Ref.nmsClass("world.level.World"));
    public final static RawClass<?> CLASS_WORLD_SERVER = RawClass.of(Ref.nmsClass("server.level.WorldServer"));
    public final static RawClass<?> CLASS_CRAFT_ENTITY = RawClass.of(Ref.obcClass("entity.CraftEntity"));
    public final static RawClass<?> CLASS_CRAFT_WORLD = RawClass.of(Ref.obcClass("CraftWorld"));
    public final static RawClass<?> CLASS_VEC3D = RawClass.of(Ref.nmsClass("world.phys.Vec3D"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_SPAWN_ENTITY = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutSpawnEntity"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_ENTITY_METADATA = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutEntityMetadata"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_ENTITY_DESTROY = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutEntityDestroy"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_REL_ENTITY_MOVE = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutEntity$PacketPlayOutRelEntityMove"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_ENTITY_TELEPORT = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutEntityTeleport"));
    public final static RawClass<?> CLASS_PAPER_ADVENTURE = RawClass.of(Ref.classOf("io.papermc.paper.adventure.PaperAdventure"));

    public final static RawField FIELD_ENTITY_ID;
    public final static RawField FIELD_ENTITY_UUID;
    public final static RawField FIELD_ENTITY_VELOCITY;
    public final static RawField FIELD_EYE_HEIGHT;
    public final static RawField FIELD_DATA_WATCHER;
    public final static RawField FIELD_ENTITY_TYPE_ARMOR_STAND;

    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_SPAWN_ENTITY;
    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_METADATA;
    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY;
    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_REL_ENTITY_MOVE;
    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_TELEPORT;
    public final static RawConstructor<?> CONSTRUCTOR_ENTITY_ARMOR_STAND;

    public final static RawMethod METHOD_GET_HANDLE_ENTITY;
    public final static RawMethod METHOD_GET_HANDLE_WORLD;
    public final static RawMethod METHOD_PACK_ALL;
    public final static RawMethod METHOD_TELEPORT;
    public final static RawMethod METHOD_SET_INVISIBLE;
    public final static RawMethod METHOD_SET_NO_GRAVITY;
    public final static RawMethod METHOD_AS_VANILLA;
    public final static RawMethod METHOD_SET_CUSTOM_NAME;
    public final static RawMethod METHOD_SET_CUSTOM_NAME_VISIBLE;
    public final static RawMethod METHOD_GET_HEIGHT;
    public final static RawMethod METHOD_SET_SMALL;
    public final static RawMethod METHOD_SET_ARMS;
    public final static RawMethod METHOD_SET_HIDE_BASE_PLATE;

    static {
        FIELD_ENTITY_ID = CLASS_ENTITY.findField(false, false, int.class, 5);
        FIELD_ENTITY_UUID = CLASS_ENTITY.findField(false, false, UUID.class);
        FIELD_ENTITY_VELOCITY = CLASS_ENTITY.findField(false, false, CLASS_VEC3D.original(), 1);
        FIELD_EYE_HEIGHT = CLASS_ENTITY.findField(false, false, float.class, 13);
        FIELD_DATA_WATCHER = CLASS_ENTITY.findField(false, true, CLASS_DATA_WATCHER.original());
        FIELD_ENTITY_TYPE_ARMOR_STAND = CLASS_ENTITY_TYPES.findField(true, true, CLASS_ENTITY_TYPES.original(), 2);;

        CONSTRUCTOR_PACKET_PLAY_OUT_SPAWN_ENTITY = CLASS_PACKET_PLAY_OUT_SPAWN_ENTITY.findConstructor(int.class,
                UUID.class, double.class, double.class, double.class, float.class, float.class, CLASS_ENTITY_TYPES.original(), int.class, CLASS_VEC3D.original(), double.class);
        CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_METADATA = CLASS_PACKET_PLAY_OUT_ENTITY_METADATA.findConstructor(int.class, List.class);
        CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY = CLASS_PACKET_PLAY_OUT_ENTITY_DESTROY.findConstructor(int[].class);
        CONSTRUCTOR_PACKET_PLAY_OUT_REL_ENTITY_MOVE = CLASS_PACKET_PLAY_OUT_REL_ENTITY_MOVE.findConstructor(int.class, short.class, short.class, short.class, boolean.class);
        CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_TELEPORT = CLASS_PACKET_PLAY_OUT_ENTITY_TELEPORT.findConstructor(CLASS_ENTITY.original());
        CONSTRUCTOR_ENTITY_ARMOR_STAND = CLASS_ENTITY_ARMOR_STAND.findConstructor(CLASS_WORLD.original(), double.class, double.class, double.class);

        METHOD_GET_HANDLE_ENTITY = CLASS_CRAFT_ENTITY.findMethod(false, CLASS_ENTITY.original());
        METHOD_GET_HANDLE_WORLD = CLASS_CRAFT_WORLD.findMethod(false, CLASS_WORLD_SERVER.original());
        METHOD_PACK_ALL = CLASS_DATA_WATCHER.findMethod(false, List.class, 2);
        METHOD_TELEPORT = CLASS_ENTITY.findMethod(false, void.class, 1, double.class, double.class, double.class, float.class, float.class);
        METHOD_SET_INVISIBLE = CLASS_ENTITY.findMethod(false, void.class, int.class, boolean.class);
        METHOD_SET_NO_GRAVITY = CLASS_ENTITY.findMethod(false, void.class, 4, boolean.class);
        METHOD_AS_VANILLA = CLASS_PAPER_ADVENTURE.findMethod(true, CLASS_I_CHAT_BASE_COMPONENT.original(), Component.class);
        METHOD_SET_CUSTOM_NAME = CLASS_ENTITY.findMethod(false, void.class, CLASS_I_CHAT_BASE_COMPONENT.original());
        METHOD_SET_CUSTOM_NAME_VISIBLE = CLASS_ENTITY.findMethod(false, void.class, 3, boolean.class);
        // METHOD_SET_CUSTOM_NAME_VISIBLE = CLASS_ENTITY.findMethod("n", boolean.class);
        METHOD_GET_HEIGHT = CLASS_ENTITY.findMethod(false, float.class, 10);
        METHOD_SET_SMALL = CLASS_ENTITY_ARMOR_STAND.findMethod(false, void.class, 2, boolean.class);
        METHOD_SET_ARMS = CLASS_ENTITY_ARMOR_STAND.findMethod(false, void.class, 3, boolean.class);
        METHOD_SET_HIDE_BASE_PLATE = CLASS_ENTITY_ARMOR_STAND.findMethod(false, void.class, 4, boolean.class);
    }

    private HologramReflections() {
    }

}
