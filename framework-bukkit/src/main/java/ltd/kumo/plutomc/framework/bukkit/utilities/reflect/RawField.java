package ltd.kumo.plutomc.framework.bukkit.utilities.reflect;

import java.lang.reflect.Field;

public class RawField<T> {

    private final Field field;

    public RawField(Field field) {
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    public T get(Object object) {
        try {
            return (T) this.field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public T getStatic() {
        try {
            return (T) this.field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Object object, T t) {
        try {
            this.field.set(object, t);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStatic(T t) {
        try {
            this.field.set(null, t);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Field original() {
        return this.field;
    }

}
