package ltd.kumo.plutomc.framework.bukkit.holograms.holograms.objects;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public abstract class UpdatingHologramObject extends HologramObject {

    /*
     *	Fields
     */

    protected int displayRange = 48;
    protected int updateRange = 48;
    protected volatile int updateInterval = 20;

    /*
     *	Constructors
     */

    public UpdatingHologramObject(@NonNull Location location) {
        super(location);
    }

}
