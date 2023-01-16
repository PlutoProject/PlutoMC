package ltd.kumo.plutomc.framework.bukkit.holograms.utils.tick;

import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;

public interface ITicked {

    String getId();

    void tick();

    long getInterval();

    default boolean shouldTick(long tick) {
        return tick % getInterval() == 0;
    }

    default void register() {
        PlutoHologramsAPI.get().getTicker().register(this);
    }

    default void unregister() {
        PlutoHologramsAPI.get().getTicker().unregister(getId());
    }

}
