package ltd.kumo.plutomc.framework.shared.command;

public interface Argument<T> {

    T parse(CommandContext context, String name);

}
