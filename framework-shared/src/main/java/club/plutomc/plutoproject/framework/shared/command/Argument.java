package club.plutomc.plutoproject.framework.shared.command;

public interface Argument<T> {

    T parse(CommandContext context, String name);

}
