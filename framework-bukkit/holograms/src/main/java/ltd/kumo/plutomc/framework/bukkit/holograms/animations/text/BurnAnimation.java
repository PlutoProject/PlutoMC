package ltd.kumo.plutomc.framework.bukkit.holograms.animations.text;

import lombok.NonNull;
import ltd.kumo.plutomc.framework.bukkit.holograms.animations.TextAnimation;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.Common;
import ltd.kumo.plutomc.framework.bukkit.holograms.utils.color.IridiumColorAPI;

public class BurnAnimation extends TextAnimation {

    public BurnAnimation() {
        super("burn", 2, 40);
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
        String start = stripped.substring(0, currentStep);
        String end = stripped.substring(currentStep);
        return args[1] + specialColors + start + args[0] + specialColors + end;
    }
}
