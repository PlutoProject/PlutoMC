package ltd.kumo.plutomc.framework.bukkit.utilities.reflect;

import java.lang.reflect.Field;

public class RawField {

    private final Field field;

    public RawField(Field field) {
        this.field = field;
        this.field.trySetAccessible();
    }

    @SuppressWarnings("unchecked")
    public Object get(Object object) {
        try {
            return this.field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Object getStatic() {
        try {
            return this.field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Object object, Object t) {
        try {
            this.field.set(object, t);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStatic(Object t) {
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
