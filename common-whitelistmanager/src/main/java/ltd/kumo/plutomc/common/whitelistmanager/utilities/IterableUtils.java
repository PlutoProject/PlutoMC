package ltd.kumo.plutomc.common.whitelistmanager.utilities;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class IterableUtils {
    private IterableUtils() {

    }

    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();

        iterable.forEach(list::add);
        return list;
    }

    public static <T> ImmutableList<T> toImmutableList(Iterable<T> iterable) {
        return ImmutableList.copyOf(toList(iterable));
    }
}
