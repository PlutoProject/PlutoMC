package ltd.kumo.plutomc.framework.bukkit.holograms.holograms;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.enums.EnumFlag;
import ltd.kumo.plutomc.framework.bukkit.holograms.holograms.objects.FlagHolder;
import ltd.kumo.plutomc.framework.bukkit.holograms.nms.NMS;
import org.bukkit.Location;

import java.util.*;

@Getter
@Setter
public class HologramPage extends FlagHolder {

    /*
     *	Fields
     */

    private final @NonNull Hologram parent;
    private final @NonNull List<Integer> clickableEntityIds;
    private final @NonNull List<HologramLine> lines;
    protected boolean alwaysFacePlayer;
    private int index;

    /*
     *	Constructors
     */

    public HologramPage(@NonNull Hologram parent, int index) {
        this.parent = parent;
        this.index = index;
        this.clickableEntityIds = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.alwaysFacePlayer = parent.isAlwaysFacePlayer();
    }

    /*
     *	General Methods
     */

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasOffsets() {
        return true; // Not used, just ignore.
    }

    /**
     * Get the current parent hologram of this page.
     *
     * @return the current parent hologram of this page.
     */
    @NonNull
    public Hologram getParent() {
        return parent;
    }

    /**
     * Get height of this hologram in blocks.
     *
     * @return height of this hologram in blocks.
     */
    public double getHeight() {
        double height = 0.0D;
        for (HologramLine hologramLine : lines) {
            height += hologramLine.getHeight();
        }
        return height;
    }

    public int getClickableEntityId(int index) {
        if (index >= clickableEntityIds.size()) {
            clickableEntityIds.add(NMS.getInstance().getFreeEntityId());
        }
        return clickableEntityIds.get(index);
    }

    @NonNull
    public Location getCenter() {
        Location center = parent.getLocation().clone();
        if (parent.isDownOrigin()) {
            center.add(0, getHeight() / 2, 0);
        } else {
            center.subtract(0, getHeight() / 2, 0);
        }
        return center;
    }

    /**
     * Get hologram size. (Number of lines)
     *
     * @return Number of lines in this hologram.
     */
    public int size() {
        return this.lines.size();
    }

    @NonNull
    public Map<String, Object> serializeToMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        List<Map<String, Object>> linesMap = new ArrayList<>();
        for (int i = 1; i <= this.lines.size(); i++) {
            HologramLine line = this.lines.get(i - 1);
            linesMap.add(line.serializeToMap());
        }
        map.put("lines", linesMap);
        return map;
    }

    @NonNull
    public HologramPage clone(@NonNull Hologram parent, int index) {
        HologramPage page = new HologramPage(parent, index);
        for (HologramLine line : getLines()) {
            page.addLine(line.clone(page, page.getNextLineLocation()));
        }
        page.addFlags(this.getFlags().toArray(new EnumFlag[0]));
//        page.setAlwaysFacePlayer(isAlwaysFacePlayer());
        return page;
    }

    /*
     *	Lines Methods
     */

    /**
     * Re-Align the lines in this hologram page putting them to the right place.
     * <p>
     * This method is good to use after teleporting the hologram page.
     */
    public void realignLines() {
        Location currentLocation = parent.getLocation().clone();
        if (parent.isDownOrigin()) {
            currentLocation.add(0, getHeight(), 0);
        }

        for (HologramLine line : lines) {
            Location lineLocation = line.getLocation();
            lineLocation.setX(currentLocation.getX() + line.getOffsetX());
            lineLocation.setY(currentLocation.getY() + line.getOffsetY());
            lineLocation.setZ(currentLocation.getZ() + line.getOffsetZ());

            line.setLocation(lineLocation);
            line.updateLocation(true);
            currentLocation.subtract(0, line.getHeight(), 0);
        }
    }

    /**
     * Add a new line to the bottom of this hologram page.
     *
     * @param line New line.
     * @return Boolean whether the operation was successful.
     */
    public boolean addLine(@NonNull HologramLine line) {
        lines.add(line);
        parent.getViewerPlayers(index).forEach(line::show);
        realignLines();
        return true;
    }

    /**
     * Insert a new line into this hologram page.
     *
     * @param index Index of the new line.
     * @param line  New line.
     * @return Boolean whether the operation was successful.
     */
    public boolean insertLine(int index, @NonNull HologramLine line) {
        if (index < 0 || index >= size()) {
            return false;
        }
        lines.add(index, line);
        parent.getViewerPlayers(this.index).forEach(line::show);
        realignLines();
        return true;
    }

    /**
     * Set new content of a line in this hologram page.
     *
     * @param index   Index of the line.
     * @param content Line's new content.
     * @return Boolean whether the operation was successful.
     */
    public boolean setLine(int index, @NonNull String content) {
        if (index < 0 || index >= size()) {
            return false;
        }
        HologramLine line = getLine(index);
        line.hide();
        line.setContent(content);
        line.show();
        realignLines();
        return true;
    }

    /**
     * Get line on a specified index in this hologram page.
     *
     * @param index Index of the line.
     * @return The HologramLine or null if it wasn't found.
     */
    public HologramLine getLine(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }
        return lines.get(index);
    }

    /**
     * Remove a line from this hologram page.
     *
     * @param index Index of the line.
     * @return The removed line or null if it wasn't found.
     */
    public HologramLine removeLine(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }
        HologramLine line = lines.remove(index);
        if (line != null) {
            line.destroy();
            realignLines();
        }
        return line;
    }

    /**
     * Swap two lines in this hologram page.
     *
     * @param index1 First line.
     * @param index2 Second line.
     * @return Boolean whether the operation was successful.
     */
    public boolean swapLines(int index1, int index2) {
        if (index1 < 0 || index1 >= size() || index2 < 0 || index2 >= size()) {
            return false;
        }
        Collections.swap(this.lines, index1, index2);
        realignLines();
        return true;
    }

    /**
     * Get the Location at the bottom of this hologram page that's available for a new line.
     *
     * @return the Location at the bottom of this hologram page that's available for a new line.
     */
    @NonNull
    public Location getNextLineLocation() {
        if (size() == 0) {
            return parent.getLocation().clone();
        }
        HologramLine line = lines.get(lines.size() - 1);
        return line.getLocation().clone().subtract(0, line.getHeight(), 0);
    }

    /**
     * Get the List of all lines in this hologram page.
     *
     * @return List of all lines in this hologram page.
     */
    @NonNull
    public List<HologramLine> getLines() {
        return ImmutableList.copyOf(lines);
    }

    public boolean hasEntity(int eid) {
        return clickableEntityIds.contains(eid) || lines.stream().anyMatch(line -> line.getEntityIds()[0] == eid || line.getEntityIds()[1] == eid);
    }

}
