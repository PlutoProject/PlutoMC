package ltd.kumo.plutomc.framework.bukkit.holograms.holograms.objects;

import lombok.Getter;
import lombok.NonNull;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.enums.EnumFlag;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class FlagHolder {

    protected final @NonNull Set<EnumFlag> flags = Collections.synchronizedSet(new HashSet<>());

    public void addFlags(EnumFlag @NonNull ... flags) {
        this.flags.addAll(Arrays.asList(flags));
    }

    public void removeFlags(EnumFlag @NonNull ... flags) {
        for (EnumFlag flag : flags) {
            this.flags.remove(flag);
        }
    }

    public boolean hasFlag(@NonNull EnumFlag flag) {
        return flags.contains(flag);
    }

}
