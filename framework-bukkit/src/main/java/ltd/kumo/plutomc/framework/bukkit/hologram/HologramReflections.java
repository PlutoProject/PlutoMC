package ltd.kumo.plutomc.framework.bukkit.hologram;

import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.*;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public final class HologramReflections {

    // FIXME, Raw reflections broken after repairing the static check

    public final static RawClass<?> CLASS_ENTITY = RawClass.of(Ref.nmsClass("world.entity.Entity"));
    public final static RawClass<?> CLASS_ENTITY_LIVING = RawClass.of(Ref.nmsClass("world.entity.EntityLiving"));
    public final static RawClass<?> CLASS_ENTITY_ARMOR_STAND = RawClass.of(Ref.nmsClass("world.entity.decoration.EntityArmorStand"));
    public final static RawClass<?> CLASS_ENTITY_TURTLE = RawClass.of(Ref.nmsClass("world.entity.animal.EntityTurtle"));
    public final static RawClass<?> CLASS_ENTITY_ITEM = RawClass.of(Ref.nmsClass("world.entity.item.EntityItem"));
    public final static RawClass<?> CLASS_DATA_WATCHER = RawClass.of(Ref.nmsClass("network.syncher.DataWatcher"));
    public final static RawClass<?> CLASS_ENTITY_TYPES = RawClass.of(Ref.nmsClass("world.entity.EntityTypes"));
    public final static RawClass<?> CLASS_I_CHAT_BASE_COMPONENT = RawClass.of(Ref.nmsClass("network.chat.IChatBaseComponent"));
    public final static RawClass<?> CLASS_WORLD = RawClass.of(Ref.nmsClass("world.level.World"));
    public final static RawClass<?> CLASS_WORLD_SERVER = RawClass.of(Ref.nmsClass("server.level.WorldServer"));
    public final static RawClass<?> CLASS_CRAFT_ENTITY = RawClass.of(Ref.obcClass("entity.CraftEntity"));
    public final static RawClass<?> CLASS_CRAFT_ITEM_STACK = RawClass.of(Ref.obcClass("inventory.CraftItemStack"));
    public final static RawClass<?> CLASS_CRAFT_WORLD = RawClass.of(Ref.obcClass("CraftWorld"));
    public final static RawClass<?> CLASS_VEC3D = RawClass.of(Ref.nmsClass("world.phys.Vec3D"));
    public final static RawClass<?> CLASS_ITEM_STACK = RawClass.of(Ref.nmsClass("world.item.ItemStack"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_SPAWN_ENTITY = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutSpawnEntity"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_ENTITY_METADATA = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutEntityMetadata"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_ENTITY_DESTROY = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutEntityDestroy"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_REL_ENTITY_MOVE = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutEntity$PacketPlayOutRelEntityMove"));
    public final static RawClass<?> CLASS_PACKET_PLAY_OUT_ENTITY_TELEPORT = RawClass.of(Ref.nmsClass("network.protocol.game.PacketPlayOutEntityTeleport"));
    public final static RawClass<?> CLASS_PAPER_ADVENTURE = RawClass.of(Ref.classOf("io.papermc.paper.adventure.PaperAdventure"));
    public final static RawClass<?> CLASS_DATA_WATCHER_OBJECT = RawClass.of(Ref.nmsClass("network.syncher.DataWatcherObject"));
    public final static RawClass<?> CLASS_DATA_WATCHER_SERIALIZER = RawClass.of(Ref.nmsClass("network.syncher.DataWatcherSerializer"));
    public final static RawClass<?> CLASS_DATA_WATCHER_ITEM = RawClass.of(Ref.nmsClass("network.syncher.DataWatcher$Item"));

    public final static RawField FIELD_ENTITY_ID;
    public final static RawField FIELD_ENTITY_UUID;
    public final static RawField FIELD_ENTITY_VELOCITY;
    public final static RawField FIELD_EYE_HEIGHT;
    public final static RawField FIELD_DATA_WATCHER;
    public final static RawField FIELD_ENTITY_TYPE_ARMOR_STAND;
    public final static RawField FIELD_ENTITY_TYPE_ITEM;
    public final static RawField FIELD_ENTITY_TYPE_TURTLE;
    public final static RawField FIELD_ITEM_LIFETIME;
    public final static RawField FIELD_ITEM_PICKUP_DELAY;
    public final static RawField FIELD_ITEM_HEALTH;
    public final static RawField FIELD_ITEM_CAN_MOB_PICKUP;

    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_SPAWN_ENTITY;
    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_METADATA;
    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY;
    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_REL_ENTITY_MOVE;
    public final static RawConstructor<?> CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_TELEPORT;
    public final static RawConstructor<?> CONSTRUCTOR_ENTITY_ARMOR_STAND;
    public final static RawConstructor<?> CONSTRUCTOR_ENTITY_ITEM;
    public final static RawConstructor<?> CONSTRUCTOR_ENTITY_TURTLE;

    public final static RawMethod METHOD_GET_HANDLE_ENTITY;
    public final static RawMethod METHOD_GET_HANDLE_WORLD;
    public final static RawMethod METHOD_PACK_ALL;
    public final static RawMethod METHOD_TELEPORT;
    public final static RawMethod METHOD_SET_INVISIBLE;
    public final static RawMethod METHOD_SET_NO_GRAVITY;
    public final static RawMethod METHOD_AS_VANILLA;
    public final static RawMethod METHOD_AS_NMS_COPY;
    public final static RawMethod METHOD_SET_CUSTOM_NAME;
    public final static RawMethod METHOD_SET_CUSTOM_NAME_VISIBLE;
    public final static RawMethod METHOD_GET_HEIGHT;
    public final static RawMethod METHOD_SET_SMALL;
    public final static RawMethod METHOD_SET_ARMS;
    public final static RawMethod METHOD_SET_HIDE_BASE_PLATE;
    public final static RawMethod METHOD_SET_ITEM_STACK;
    public final static RawMethod METHOD_GET_CRAFT_ENTITY;
    public final static RawMethod METHOD_GET_ENTITY_TYPES;

    static {
        FIELD_ENTITY_ID = CLASS_ENTITY.findField(false, false, int.class, 5);
        FIELD_ENTITY_UUID = CLASS_ENTITY.findField(false, false, UUID.class);
        FIELD_ENTITY_VELOCITY = CLASS_ENTITY.findField(false, false, CLASS_VEC3D.original(), 1);
        FIELD_EYE_HEIGHT = CLASS_ENTITY.findField(false, false, float.class, 13);
        FIELD_DATA_WATCHER = CLASS_ENTITY.findField(false, true, CLASS_DATA_WATCHER.original());
        FIELD_ENTITY_TYPE_ARMOR_STAND = CLASS_ENTITY_TYPES.findField(true, true, CLASS_ENTITY_TYPES.original(), 2);
        FIELD_ENTITY_TYPE_ITEM = CLASS_ENTITY_TYPES.findField(true, true, CLASS_ENTITY_TYPES.original(), 45);
        FIELD_ENTITY_TYPE_TURTLE = CLASS_ENTITY_TYPES.findGenericField(true, CLASS_ENTITY_TYPES.original(), CLASS_ENTITY_TURTLE.original());
        FIELD_ITEM_LIFETIME = CLASS_ENTITY_ITEM.findField(false, int.class);
        FIELD_ITEM_PICKUP_DELAY = CLASS_ENTITY_ITEM.findField(false, int.class, 1);
        FIELD_ITEM_HEALTH = CLASS_ENTITY_ITEM.findField(false, int.class, 2);
        FIELD_ITEM_CAN_MOB_PICKUP = CLASS_ENTITY_ITEM.findField(false, boolean.class);

        CONSTRUCTOR_PACKET_PLAY_OUT_SPAWN_ENTITY = CLASS_PACKET_PLAY_OUT_SPAWN_ENTITY.findConstructor(int.class,
                UUID.class, double.class, double.class, double.class, float.class, float.class, CLASS_ENTITY_TYPES.original(), int.class, CLASS_VEC3D.original(), double.class);
        CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_METADATA = CLASS_PACKET_PLAY_OUT_ENTITY_METADATA.findConstructor(int.class, List.class);
        CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_DESTROY = CLASS_PACKET_PLAY_OUT_ENTITY_DESTROY.findConstructor(int[].class);
        CONSTRUCTOR_PACKET_PLAY_OUT_REL_ENTITY_MOVE = CLASS_PACKET_PLAY_OUT_REL_ENTITY_MOVE.findConstructor(int.class, short.class, short.class, short.class, boolean.class);
        CONSTRUCTOR_PACKET_PLAY_OUT_ENTITY_TELEPORT = CLASS_PACKET_PLAY_OUT_ENTITY_TELEPORT.findConstructor(CLASS_ENTITY.original());
        CONSTRUCTOR_ENTITY_ARMOR_STAND = CLASS_ENTITY_ARMOR_STAND.findConstructor(CLASS_WORLD.original(), double.class, double.class, double.class);
        CONSTRUCTOR_ENTITY_ITEM = CLASS_ENTITY_ITEM.findConstructor(CLASS_WORLD.original(), double.class, double.class, double.class, CLASS_ITEM_STACK.original());
        CONSTRUCTOR_ENTITY_TURTLE = CLASS_ENTITY_TURTLE.findConstructor(CLASS_ENTITY_TYPES.original(), CLASS_WORLD.original());

        METHOD_GET_HANDLE_ENTITY = CLASS_CRAFT_ENTITY.findMethod(false, CLASS_ENTITY.original());
        METHOD_GET_HANDLE_WORLD = CLASS_CRAFT_WORLD.findMethod(false, CLASS_WORLD_SERVER.original());
        METHOD_PACK_ALL = CLASS_DATA_WATCHER.findMethod(false, List.class, 2);
        METHOD_TELEPORT = CLASS_ENTITY.findMethod(false, void.class, 1, double.class, double.class, double.class, float.class, float.class);
        METHOD_SET_INVISIBLE = CLASS_ENTITY.findMethod(false, void.class, int.class, boolean.class);
        METHOD_SET_NO_GRAVITY = CLASS_ENTITY.findMethod(false, void.class, 4, boolean.class);
        METHOD_AS_VANILLA = CLASS_PAPER_ADVENTURE.findMethod(true, CLASS_I_CHAT_BASE_COMPONENT.original(), Component.class);
        METHOD_AS_NMS_COPY = CLASS_CRAFT_ITEM_STACK.findMethod(true, CLASS_ITEM_STACK.original(), ItemStack.class);
        METHOD_SET_CUSTOM_NAME = CLASS_ENTITY.findMethod(false, void.class, CLASS_I_CHAT_BASE_COMPONENT.original());
        METHOD_SET_CUSTOM_NAME_VISIBLE = CLASS_ENTITY.findMethod(false, void.class, 3, boolean.class);
        // METHOD_SET_CUSTOM_NAME_VISIBLE = CLASS_ENTITY.findMethod("n", boolean.class);
        METHOD_GET_HEIGHT = CLASS_ENTITY.findMethod(false, float.class, 10);
        METHOD_SET_SMALL = CLASS_ENTITY_ARMOR_STAND.findMethod(false, void.class, 2, boolean.class);
        METHOD_SET_ARMS = CLASS_ENTITY_ARMOR_STAND.findMethod(false, void.class, 3, boolean.class);
        METHOD_SET_HIDE_BASE_PLATE = CLASS_ENTITY_ARMOR_STAND.findMethod(false, void.class, 4, boolean.class);
        METHOD_SET_ITEM_STACK = CLASS_ENTITY_ITEM.findMethod(false, void.class, CLASS_ITEM_STACK.original());
        METHOD_GET_CRAFT_ENTITY = CLASS_ENTITY.findMethod(false, CLASS_CRAFT_ENTITY.original());
        METHOD_GET_ENTITY_TYPES = CLASS_ENTITY.findMethod(false, CLASS_ENTITY_TYPES.original());
    }

    private HologramReflections() {
    }

}
