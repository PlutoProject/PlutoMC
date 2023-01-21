package ltd.kumo.plutomc.framework.bukkit.utilities.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RawMethod {

    private final Method method;

    public RawMethod(Method method) {
        this.method = method;
    }

    public Object invokeStatic(Object... parameters) {
        try {
            return this.method.invoke(null, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Object invoke(Object object, Object... parameters) {
        try {
            return this.method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Method original() {
        return this.method;
    }

}
