package ltd.kumo.plutomc.framework.bukkit.command.sender;

import org.bukkit.command.BlockCommandSender;
import org.jetbrains.annotations.NotNull;

public class BukkitBlockCommandSender extends BukkitCommandSender {

    public BukkitBlockCommandSender(BlockCommandSender sender) {
        super(sender);
    }

    @Override
    public @NotNull String name() {
        return asBukkit().getName();
    }

    @Override
    public boolean isBlock() {
        return true;
    }

    @Override
    public org.bukkit.command.BlockCommandSender asBukkit() {
        return (BlockCommandSender) super.asBukkit();
    }

}
