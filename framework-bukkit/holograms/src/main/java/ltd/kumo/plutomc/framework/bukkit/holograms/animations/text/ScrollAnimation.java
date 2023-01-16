package ltd.kumo.plutomc.framework.bukkit.holograms.animations.text;

import lombok.NonNull;
import ltd.kumo.plutomc.framework.bukkit.holograms.animations.TextAnimation;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.Common;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.color.IridiumColorAPI;

public class ScrollAnimation extends TextAnimation {

    public ScrollAnimation() {
        super("scroll", 3, 0);
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
        int length = stripped.length();
        int size = length / 3 * 2;
        int currentStep = getCurrentStep(step, length);
        int index2 = currentStep + size;
        if (index2 > length) {
            return specialColors + stripped.substring(currentStep) + " " + specialColors + stripped.substring(0, index2 - length);
        }
        return specialColors + stripped.substring(currentStep, index2);
    }
}
