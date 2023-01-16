/*
 * This file is part of commodore, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package ltd.kumo.plutomc.framework.bukkit.command.commodore;

import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.function.Function;

/**
 * Factory for obtaining instances of {@link Commodore}.
 */
public final class CommodoreProvider {
    private CommodoreProvider() {
        throw new AssertionError();
    }

    private static final Function<Plugin, Commodore> PROVIDER = checkSupported();

    private static Function<Plugin, Commodore> checkSupported() {
        try {
            Class.forName("com.mojang.brigadier.CommandDispatcher");
        } catch (Throwable e) {
            printDebugInfo(e);
            return null;
        }

        // try the paper impl
        try {
            PaperCommodore.ensureSetup();
            return PaperCommodore::new;
        } catch (Throwable e) {
            printDebugInfo(e);
        }

        // try reflection impl
        try {
            ReflectionCommodore.ensureSetup();
            return ReflectionCommodore::new;
        } catch (Throwable e) {
            printDebugInfo(e);
        }

        return null;
    }

    private static void printDebugInfo(Throwable e) {
        if (System.getProperty("commodore.debug") != null) {
            System.err.println("Exception while initialising commodore:");
            e.printStackTrace(System.err);
        }
    }

    /**
     * Checks to see if the Brigadier command system is supported by the server.
     *
     * @return true if commodore is supported.
     */
    public static boolean isSupported() {
        return PROVIDER != null;
    }

    /**
     * Obtains a {@link Commodore} instance for the given plugin.
     *
     * @param plugin the plugin
     * @return the commodore instance
     * @throws BrigadierUnsupportedException if brigadier is not {@link #isSupported() supported}
     * by the server.
     */
    public static Commodore getCommodore(Plugin plugin) throws BrigadierUnsupportedException {
        Objects.requireNonNull(plugin, "plugin");
        if (PROVIDER == null) {
            throw new BrigadierUnsupportedException(
                    "Brigadier is not supported by the server. " +
                    "Set -Dcommodore.debug=true for debug info."
            );
        }
        return PROVIDER.apply(plugin);
    }
}
