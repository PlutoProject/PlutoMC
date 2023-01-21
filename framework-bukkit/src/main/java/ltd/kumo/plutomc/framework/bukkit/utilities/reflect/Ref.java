package ltd.kumo.plutomc.framework.bukkit.utilities.reflect;

import org.bukkit.Bukkit;

public final class Ref {

    private Ref() {
    }

    public static Class<?> classOf(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> nmsClass(String name) {
        return classOf("net.minecraft." + name);
    }

    public static Class<?> obcClass(String name) {
        return classOf("org.bukkit.craftbukkit." + getServerVersion() + "." + name);
    }

    public static String getServerVersion() {
        String version = null;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (Exception e) {
            try {
                version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[1];
            } catch (Exception ignored) {
            }
        }
        return version;
    }

}
