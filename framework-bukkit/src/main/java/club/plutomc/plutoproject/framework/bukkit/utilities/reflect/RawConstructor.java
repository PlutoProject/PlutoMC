package club.plutomc.plutoproject.framework.bukkit.utilities.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RawConstructor<T> {

    private final Constructor<T> constructor;

    public RawConstructor(Constructor<T> constructor) {
        this.constructor = constructor;
        this.constructor.trySetAccessible();
    }

    public T newInstance(Object... parameters) {
        try {
            return this.constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Constructor<T> original() {
        return this.constructor;
    }

}
