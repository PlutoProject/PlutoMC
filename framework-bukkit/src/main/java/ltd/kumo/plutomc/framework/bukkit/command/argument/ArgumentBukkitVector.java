package ltd.kumo.plutomc.framework.bukkit.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitArgument;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandContext;
import ltd.kumo.plutomc.framework.bukkit.command.BukkitCommandReflections;
import ltd.kumo.plutomc.framework.bukkit.command.MinecraftArgumentType;
import ltd.kumo.plutomc.framework.shared.command.CommandContext;
import org.bukkit.util.Vector;

public class ArgumentBukkitVector implements BukkitArgument<Vector> {

    @Override
    public Vector parse(CommandContext context, String name) {
        Object vec3d = BukkitCommandReflections.METHOD_GET_VEC3D.invoke(null, ((BukkitCommandContext) context).brigadier(), name);
        double x = (double) BukkitCommandReflections.FIELD_VEC3D_X.get(vec3d);
        double y = (double) BukkitCommandReflections.FIELD_VEC3D_Y.get(vec3d);
        double z = (double) BukkitCommandReflections.FIELD_VEC3D_Z.get(vec3d);
        return new Vector(x, y, z);
    }

    @Override
    public ArgumentType<?> brigadier() {
        return MinecraftArgumentType.VECTOR_3.create(false);
    }

}
