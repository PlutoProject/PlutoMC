package ltd.kumo.plutomc.framework.shared.command;

import ltd.kumo.plutomc.framework.shared.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 指令类
 *
 * @param <T> 发送者
 * @param <P> 玩家类型的发送者
 */
public interface Command<T extends CommandSender, P extends Player<?>> {

    /**
     * 获取本指令的名称
     *
     * @return 名称
     */
    String name();

    Command<T, P> suggests(Consumer<Suggestion> provider);

    /**
     * 判断一个发送者是否有权限执行，如果返回值是false，则不会提供对应的指令提示
     *
     * @param requirement 需求
     * @return 自己
     */
    Command<T, P> requires(Predicate<T> requirement);

    /**
     * 设置执行器
     *
     * @param executor 执行器
     * @return 自己
     */
    Command<T, P> executes(BiConsumer<T, CommandContext> executor);

    /**
     * 设置玩家执行器，不是玩家执行不会调用执行器
     *
     * @param executor 执行器
     * @return 自己
     */
    Command<T, P> executesPlayer(BiConsumer<P, CommandContext> executor);

    /**
     * 添加指令子节点
     *
     * @param name 指令名称
     * @return 子节点指令
     */
    Command<T, P> then(String name);

    /**
     * 添加参数子节点
     *
     * @param name 参数名称
     * @param type 参数类型的类
     * @param <E>  参数类型
     * @return 子节点指令
     */
    <E extends Argument<A>, A> Command<T, P> then(String name, Class<E> type);

    /**
     * 添加整型参数子节点
     *
     * @param name 参数名称
     * @param min  最小值
     * @param max  最大值
     * @return 子节点指令
     */
    Command<T, P> thenInteger(String name, int min, int max);

    /**
     * 添加长整型参数子节点
     *
     * @param name 参数名称
     * @param min  最小值
     * @param max  最大值
     * @return 子节点指令
     */
    Command<T, P> thenLong(String name, long min, long max);

    /**
     * 添加单精度浮点型参数子节点
     *
     * @param name 参数名称
     * @param min  最小值
     * @param max  最大值
     * @return 子节点指令
     */
    Command<T, P> thenFloat(String name, float min, float max);

    /**
     * 添加双精度浮点型参数子节点
     *
     * @param name 参数名称
     * @param min  最小值
     * @param max  最大值
     * @return 子节点指令
     */
    Command<T, P> thenDouble(String name, double min, double max);

    /**
     * 复制一个除了名字以外完全相同的指令
     *
     * @param name 新名称
     * @return 新指令
     */
    Command<T, P> clone(String name);

}
