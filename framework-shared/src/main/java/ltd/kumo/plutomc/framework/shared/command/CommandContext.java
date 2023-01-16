package ltd.kumo.plutomc.framework.shared.command;

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

}
