package ltd.kumo.plutomc.framework.shared.command.arguments;

import ltd.kumo.plutomc.framework.shared.command.Argument;
import ltd.kumo.plutomc.framework.shared.player.Player;

import java.util.List;

/**
 * 玩家类
 * @param <T>
 */
public abstract class ArgumentPlayer<T extends Player<E>, E> implements Argument<List<T>> {
}
