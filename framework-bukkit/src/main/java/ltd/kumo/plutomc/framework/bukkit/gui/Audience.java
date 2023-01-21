package ltd.kumo.plutomc.framework.bukkit.gui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Audience {

    @Nullable
    Scene getScene();

    @NotNull
    UUID uuid();

    void removeScene();

}
