package ltd.kumo.plutomc.framework.bukkit.utilities.reflect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Objects;

public class RawClass<T> {
    private final Class<T> type;

    public RawClass(Class<T> type) {
        this.type = type;
    }

    @Nullable
    public RawConstructor<T> findConstructor(Class<?>... parameters) {
        try {
            return new RawConstructor<>(this.type.getDeclaredConstructor(parameters));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public RawField findField(boolean isStatic, Class<?> type) {
        return this.findField(isStatic, type, 0);
    }

    @Nullable
    public RawField findField(boolean isStatic, boolean isFinal, Class<?> type) {
        return this.findField(isStatic, isFinal, type, 0);
    }

    @Nullable
    public RawField findField(boolean isStatic, Class<?> type, int skipTimes) {
        for (Field field : this.type.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) != isStatic)
                continue;
            if (type != null)
                if (!Objects.equals(type, field.getType()))
                    continue;
            if (skipTimes > 0) {
                skipTimes--;
                continue;
            }
            return new RawField(field);
        }
        return null;
    }

    @Nullable
    public RawField findField(boolean isStatic, boolean isFinal, Class<?> type, int skipTimes) {
        for (Field field : this.type.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) != isStatic)
                continue;
            if (Modifier.isFinal(field.getModifiers()) != isFinal)
                continue;
            if (type != null)
                if (!Objects.equals(type, field.getType()))
                    continue;
            if (skipTimes > 0) {
                skipTimes--;
                continue;
            }
            return new RawField(field);
        }
        return null;
    }

    @NotNull
    public RawField findField(String name) {
        try {
            return new RawField(this.type.getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public RawField findGenericField(boolean isStatic, Class<?> type, Class<?>... generics) {
        return this.findGenericField(isStatic, type, 0, generics);
    }

    public RawField findGenericField(boolean isStatic, Class<?> type, int skipTimes, Class<?>... generics) {
        for (Field field : this.type.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) != isStatic)
                continue;
            if (type != null)
                if (!Objects.equals(type, field.getType()))
                    continue;
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType parameterizedType))
                continue;
            Type[] arguments = parameterizedType.getActualTypeArguments();
            if (generics.length != arguments.length)
                continue;
            boolean shouldContinue = false;
            for (int i = 0; i < arguments.length; i++) {
                Type argument = arguments[i];
                Class<?> generic = generics[i];
                if (argument == null && generic != null) {
                    shouldContinue = true;
                    break;
                }
                if (generic == null)
                    continue;
                if (argument instanceof Class) {
                    if (!Objects.equals(argument, generic)) {
                        shouldContinue = true;
                        break;
                    }
                } else if (argument instanceof ParameterizedType parameterType) {
                    Type rawType = parameterType.getRawType();
                    if (!(rawType instanceof Class)) {
                        shouldContinue = true;
                        break;
                    }
                    if (!Objects.equals(rawType, generic)) {
                        shouldContinue = true;
                        break;
                    }
                }
            }
            if (shouldContinue)
                continue;
            if (skipTimes > 0) {
                skipTimes--;
                continue;
            }
            return new RawField(field);
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
            if (Modifier.isStatic(method.getModifiers()) != isStatic)
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

    @NotNull
    public RawMethod findMethod(String name, Class<?>... parameterTypes) {
        try {
            return new RawMethod(this.type.getDeclaredMethod(name, parameterTypes));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> original() {
        return this.type;
    }

    public static <T> RawClass<T> of(Class<T> type) {
        return new RawClass<>(type);
    }

    private boolean allEquals(Object[] first, Object[] second) {
        if (first.length != second.length)
            return false;
        for (int i = 0; i < first.length; i++)
            if (!Objects.equals(first[i], second[i]))
                return false;
        return true;
    }

    public boolean isInstance(Object packet) {
        return this.type.isInstance(packet);
    }

}