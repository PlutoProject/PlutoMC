package ltd.kumo.plutomc.framework.bukkit.holograms.animations.text;

import lombok.NonNull;
import ltd.kumo.plutomc.framework.bukkit.holograms.animations.TextAnimation;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.Common;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.color.IridiumColorAPI;

import java.util.Arrays;

public class TypewriterAnimation extends TextAnimation {

    public TypewriterAnimation() {
        super("typewriter", 3, 20);
    }

    @Override
    public String animate(@NonNull String string, long step, String... args) {
        StringBuilder specialColors = new StringBuilder();
        for (String color : IridiumColorAPI.SPECIAL_COLORS) {
            if (string.contains(color)) {
                specialColors.append(color);
                string = string.replace(color, "");
            }
        }
        String stripped = Common.stripColors(string);
        int currentStep = getCurrentStep(step, stripped.length());
        return specialColors + String.valueOf(Arrays.copyOfRange(stripped.toCharArray(), 0, currentStep));
    }
}
