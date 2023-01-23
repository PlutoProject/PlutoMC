package ltd.kumo.plutomc.framework.shared.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public interface CommandContext {

    /**
     * 通过类型和参数名称获取参数值，如果不存在或参数类型不对应会抛出异常
     *
     * @param type 参数类型的类
     * @param name 参数名称
     * @param <T>  参数类型
     * @return 参数内容
     */
    <T extends Argument<E>, E> E argument(Class<T> type, String name);

    /**
     * 发出错误信息，终止指令执行，给指令执行者提醒错误的内容
     * @param message 信息
     */
    void error(String message) throws CommandSyntaxException;

}
