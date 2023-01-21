package ltd.kumo.plutomc.framework.bukkit.utilities.reflect;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class RawClass {
    private final Class<?> type;

    public RawClass(Class<?> type) {
        this.type = type;
    }

    @Nullable
    public <T> RawField<T> findField(boolean isStatic, Class<T> type) {
        return this.findField(isStatic, type, 0);
    }

    @Nullable
    public <T> RawField<T> findField(boolean isStatic, Class<T> type, int skipTimes) {
        for (Field field : this.type.getDeclaredFields()) {
            if (isStatic && !Modifier.isStatic(field.getModifiers()))
                continue;
            if (type != null)
                if (!Objects.equals(type, field.getType()))
                    continue;
            if (skipTimes > 0) {
                skipTimes--;
                continue;
            }
            return new RawField<>(field);
        }
        return null;
    }

    @Nullable
    public RawMethod findMethod(boolean isStatic, Class<?> returnType, Class<?>... parameterTypes) {
        return findMethod(isStatic, returnType, 0, parameterTypes);
    }

    @Nullable
    public RawMethod findMethod(boolean isStatic, Class<?> returnType, int skipTimes, Class<?>... parameterTypes) {
        for (Method method : this.type.getDeclaredMethods()) {
            if (isStatic && !Modifier.isStatic(method.getModifiers()))
                continue;
            if (!Objects.equals(returnType, method.getReturnType()))
                continue;
            Class<?>[] arguments = method.getParameterTypes();
            if (arguments.length != parameterTypes.length)
                continue;
            if (!allEquals(parameterTypes, arguments))
                continue;
            if (skipTimes > 0) {
                skipTimes--;
                continue;
            }
            return new RawMethod(method);
        }
        return null;
    }

    public Class<?> original() {
        return this.type;
    }

    public static RawClass of(Class<?> type) {
        return new RawClass(type);
    }

    private boolean allEquals(Object[] first, Object[] second) {
        if (first.length != second.length)
            return false;
        for (int i = 0; i < first.length; i++)
            if (!Objects.equals(first[i], second[i]))
                return false;
        return true;
    }

}