package ltd.kumo.plutomc.framework.shared.command;

import org.jetbrains.annotations.NotNull;

public interface CommandSender {

    /**
     * 获取指令发送者的名称
     *
     * @return 获取指令发送者的名称
     */
    @NotNull
    String name();

    /**
     * 向指令发送者发送消息
     *
     * @param message 消息文本
     */
    void send(String message);

    /**
     * 判断发送者是否是玩家
     *
     * @return 状态
     */
    boolean isPlayer();

    /**
     * 判断发送者是否是控制台
     *
     * @return 状态
     */
    boolean isConsole();

    /**
     * 判断发送者是否是方块如命令方块
     *
     * @return 状态
     */
    boolean isBlock();

}
