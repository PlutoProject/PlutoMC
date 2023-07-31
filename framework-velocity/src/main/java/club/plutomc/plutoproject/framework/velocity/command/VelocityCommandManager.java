package club.plutomc.plutoproject.framework.velocity.command;

import club.plutomc.plutoproject.framework.shared.command.arguments.*;
import club.plutomc.plutoproject.framework.velocity.command.argument.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import club.plutomc.plutoproject.framework.shared.command.Argument;
import club.plutomc.plutoproject.framework.velocity.VelocityPlatform;
import ltd.kumo.plutomc.framework.velocity.command.argument.*;

import java.util.HashMap;
import java.util.Map;

public class VelocityCommandManager {

    private final VelocityPlatform platform;
    private final Map<Class<?>, VelocityArgument<?>> argumentImplementers = new HashMap<>();

    public VelocityCommandManager(VelocityPlatform platform) {
        this.platform = platform;

        // Register default command types
        ArgumentVelocityInteger argumentVelocityInteger = new ArgumentVelocityInteger();
        this.argumentImplementers.put(ArgumentInteger.class, argumentVelocityInteger);
        this.argumentImplementers.put(ArgumentVelocityInteger.class, argumentVelocityInteger);

        ArgumentVelocityLong argumentVelocityLong = new ArgumentVelocityLong();
        this.argumentImplementers.put(ArgumentLong.class, argumentVelocityLong);
        this.argumentImplementers.put(ArgumentVelocityLong.class, argumentVelocityLong);

        ArgumentVelocityFloat argumentVelocityFloat = new ArgumentVelocityFloat();
        this.argumentImplementers.put(ArgumentFloat.class, argumentVelocityFloat);
        this.argumentImplementers.put(ArgumentVelocityFloat.class, argumentVelocityFloat);

        ArgumentVelocityDouble argumentVelocityDouble = new ArgumentVelocityDouble();
        this.argumentImplementers.put(ArgumentDouble.class, argumentVelocityDouble);
        this.argumentImplementers.put(ArgumentVelocityDouble.class, argumentVelocityDouble);

        ArgumentVelocityBoolean argumentVelocityBoolean = new ArgumentVelocityBoolean();
        this.argumentImplementers.put(ArgumentBoolean.class, argumentVelocityBoolean);
        this.argumentImplementers.put(ArgumentVelocityBoolean.class, argumentVelocityBoolean);

        ArgumentVelocityString argumentVelocityString = new ArgumentVelocityString();
        this.argumentImplementers.put(ArgumentString.class, argumentVelocityString);
        this.argumentImplementers.put(ArgumentVelocityString.class, argumentVelocityString);

        ArgumentVelocityMessage argumentVelocityMessage = new ArgumentVelocityMessage();
        this.argumentImplementers.put(ArgumentMessage.class, argumentVelocityMessage);
        this.argumentImplementers.put(ArgumentVelocityMessage.class, argumentVelocityMessage);
    }

    @SuppressWarnings("unchecked")
    public void register(String prefix, VelocityCommand command) {
        this.platform.getProxyServer().getCommandManager().register(command.name(), new BrigadierCommand((LiteralArgumentBuilder<CommandSource>) command.toBrigadier()), command.getAliases().toArray(new String[0]));
    }

    @SuppressWarnings("unchecked")
    public <E extends Argument<T>, T> VelocityArgument<T> argument(Class<E> clazz) {
        return (VelocityArgument<T>) this.argumentImplementers.get(clazz);
    }

}
