package ltd.kumo.plutomc.framework.shared.command.arguments;

import ltd.kumo.plutomc.framework.shared.command.Argument;

/**
 * 消息类型参数，这个参数之后的参数都无法被解析，因为该参数会忽略空格
 */
public abstract class ArgumentMessage implements Argument<String> {
}
