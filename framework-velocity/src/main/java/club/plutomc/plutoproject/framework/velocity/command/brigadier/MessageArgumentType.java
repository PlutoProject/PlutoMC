package club.plutomc.plutoproject.framework.velocity.command.brigadier;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Arrays;
import java.util.Collection;

public class MessageArgumentType implements ArgumentType<Message> {

    private static final Collection<String> EXAMPLES = Arrays.asList("Hello worlds!", "foo", "Aaa");

    private MessageArgumentType() {
    }

    public static String getMessage(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Message.class).content();
    }

    public static MessageArgumentType message() {
        return new MessageArgumentType();
    }

    @Override
    public Message parse(StringReader reader) throws CommandSyntaxException {
        Message message = new Message(reader.getString().substring(reader.getCursor(), reader.getTotalLength()));
        reader.setCursor(reader.getTotalLength());
        return message;
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

}
