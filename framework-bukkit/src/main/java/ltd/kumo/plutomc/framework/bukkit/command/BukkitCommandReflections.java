package ltd.kumo.plutomc.framework.bukkit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import ltd.kumo.plutomc.framework.bukkit.utilities.reflect.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

import java.util.Collection;

@SuppressWarnings("unchecked")
public final class BukkitCommandReflections {

    private BukkitCommandReflections() {
    }

    public final static RawClass<?> CLASS_VANILLA_COMMAND_WRAPPER = RawClass.of(Ref.obcClass("command.VanillaCommandWrapper"));
    public final static RawClass<?> CLASS_COMMAND_LISTENER_WRAPPER = RawClass.of(Ref.nmsClass("commands.CommandListenerWrapper"));
    public final static RawClass<?> CLASS_COMMAND_DISPATCHER = RawClass.of(Ref.nmsClass("commands.CommandDispatcher"));
    public final static RawClass<?> CLASS_MINECRAFT_SERVER = RawClass.of(Ref.nmsClass("server.MinecraftServer"));
    public final static RawClass<?> CLASS_DEDICATED_SERVER = RawClass.of(Ref.nmsClass("server.dedicated.DedicatedServer"));
    public final static RawClass<?> CLASS_CRAFT_SERVER = RawClass.of(Ref.obcClass("CraftServer"));
    public final static RawClass<?> CLASS_ENTITY_PLAYER = RawClass.of(Ref.nmsClass("server.level.EntityPlayer"));
    public final static RawClass<?> CLASS_CRAFT_PLAYER = RawClass.of(Ref.obcClass("entity.CraftPlayer"));
    public final static RawClass<?> CLASS_I_CHAT_BASE_COMPONENT = RawClass.of(Ref.nmsClass("network.chat.IChatBaseComponent"));
    public final static RawClass<?> CLASS_ARGUMENT_CHAT = RawClass.of(Ref.nmsClass("commands.arguments.ArgumentChat"));
    public final static RawClass<?> CLASS_ARGUMENT_ENTITY = RawClass.of(Ref.nmsClass("commands.arguments.ArgumentEntity"));
    public final static RawClass<?> CLASS_ARGUMENT_DIMENSION = RawClass.of(Ref.nmsClass("commands.arguments.ArgumentDimension"));
    public final static RawClass<?> CLASS_WORLD_SERVER = RawClass.of(Ref.nmsClass("server.level.WorldServer"));
    public final static RawClass<?> CLASS_WORLD = RawClass.of(Ref.nmsClass("world.level.World"));
    public final static RawClass<?> CLASS_CRAFT_WORLD = RawClass.of(Ref.obcClass("CraftWorld"));
    public final static RawClass<?> CLASS_ARGUMENT_VEC3 = RawClass.of(Ref.nmsClass("commands.arguments.coordinates.ArgumentVec3"));
    public final static RawClass<?> CLASS_VEC3D = RawClass.of(Ref.nmsClass("world.phys.Vec3D"));

    public final static RawConstructor<Command> CONSTRUCTOR_VANILLA_COMMAND_WRAPPER;

    public final static RawField FIELD_VANILLA_COMMAND_DISPATCHER;
    public final static RawField FIELD_CHILDREN;
    public final static RawField FIELD_KNOWN_COMMANDS;
    public final static RawField FIELD_DISPATCHER;
    public final static RawField FIELD_VEC3D_X;
    public final static RawField FIELD_VEC3D_Y;
    public final static RawField FIELD_VEC3D_Z;

    public final static RawMethod METHOD_GET_SERVER;
    public final static RawMethod METHOD_GET_COMMAND_DISPATCHER;
    public final static RawMethod METHOD_GET_BRIGADIER_COMMAND_DISPATCHER;
    public final static RawMethod METHOD_HAS_PERMISSION;
    public final static RawMethod METHOD_HAS_PERMISSION_2;
    public final static RawMethod METHOD_GET_BUKKIT_SENDER;
    public final static RawMethod METHOD_GET_BUKKIT_PLAYER;
    public final static RawMethod METHOD_GET_STRING;
    public final static RawMethod METHOD_GET_MESSAGE;
    public final static RawMethod METHOD_GET_ENTITY_PLAYER;
    public final static RawMethod METHOD_GET_ENTITY_PLAYERS;
    public final static RawMethod METHOD_GET_DIMENSION;
    public final static RawMethod METHOD_GET_WORLD;
    public final static RawMethod METHOD_GET_VEC3D;

    static {
        CONSTRUCTOR_VANILLA_COMMAND_WRAPPER = (RawConstructor<Command>) CLASS_VANILLA_COMMAND_WRAPPER.findConstructor(CLASS_COMMAND_DISPATCHER.original(), CommandNode.class);

        FIELD_VANILLA_COMMAND_DISPATCHER = CLASS_MINECRAFT_SERVER.findField(false, CLASS_COMMAND_DISPATCHER.original());
        FIELD_CHILDREN = RawClass.of(CommandNode.class).findField("children");
        FIELD_KNOWN_COMMANDS = RawClass.of(SimpleCommandMap.class).findField("knownCommands");
        FIELD_DISPATCHER = CLASS_VANILLA_COMMAND_WRAPPER.findField(false, CLASS_COMMAND_DISPATCHER.original());
        FIELD_VEC3D_X = CLASS_VEC3D.findField(false, double.class);
        FIELD_VEC3D_Y = CLASS_VEC3D.findField(false, double.class, 1);
        FIELD_VEC3D_Z = CLASS_VEC3D.findField(false, double.class, 2);

        METHOD_GET_SERVER = CLASS_CRAFT_SERVER.findMethod(false, CLASS_DEDICATED_SERVER.original());
        METHOD_GET_COMMAND_DISPATCHER = CLASS_MINECRAFT_SERVER.findMethod(false, CLASS_COMMAND_DISPATCHER.original());
        METHOD_GET_BRIGADIER_COMMAND_DISPATCHER = CLASS_COMMAND_DISPATCHER.findMethod(false, CommandDispatcher.class);
        METHOD_HAS_PERMISSION = CLASS_COMMAND_LISTENER_WRAPPER.findMethod(false, boolean.class, int.class);
        METHOD_HAS_PERMISSION_2 = CLASS_COMMAND_LISTENER_WRAPPER.findMethod(false, boolean.class, int.class, String.class);
        METHOD_GET_BUKKIT_SENDER = CLASS_COMMAND_LISTENER_WRAPPER.findMethod(false, CommandSender.class);
        METHOD_GET_BUKKIT_PLAYER = CLASS_ENTITY_PLAYER.findMethod(false, CLASS_CRAFT_PLAYER.original());
        METHOD_GET_STRING = CLASS_I_CHAT_BASE_COMPONENT.findMethod(false, String.class);
        METHOD_GET_MESSAGE = CLASS_ARGUMENT_CHAT.findMethod(true, CLASS_I_CHAT_BASE_COMPONENT.original(), CommandContext.class, String.class);
        METHOD_GET_ENTITY_PLAYER = CLASS_ARGUMENT_ENTITY.findMethod(true, CLASS_ENTITY_PLAYER.original(), CommandContext.class, String.class);
        METHOD_GET_ENTITY_PLAYERS = CLASS_ARGUMENT_ENTITY.findMethod(true, Collection.class, 2, CommandContext.class, String.class);
        METHOD_GET_DIMENSION = CLASS_ARGUMENT_DIMENSION.findMethod(true, CLASS_WORLD_SERVER.original(), CommandContext.class, String.class);
        METHOD_GET_WORLD = CLASS_WORLD.findMethod(false, CLASS_CRAFT_WORLD.original());
        METHOD_GET_VEC3D = CLASS_ARGUMENT_VEC3.findMethod(true, CLASS_VEC3D.original(), CommandContext.class, String.class);
    }

}
