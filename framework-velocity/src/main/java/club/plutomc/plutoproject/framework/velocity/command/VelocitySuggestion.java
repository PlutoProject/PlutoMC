package club.plutomc.plutoproject.framework.velocity.command;

import club.plutomc.plutoproject.framework.shared.command.Suggestion;

import java.util.ArrayList;
import java.util.List;

public class VelocitySuggestion implements Suggestion {

    private final List<String> stringSuggestions = new ArrayList<>();
    private final List<Integer> intSuggestions = new ArrayList<>();

    @Override
    public Suggestion suggests(String string) {
        this.stringSuggestions.add(string);
        return this;
    }

    @Override
    public Suggestion suggests(int i) {
        this.intSuggestions.add(i);
        return this;
    }

    public List<String> getStringSuggestions() {
        return stringSuggestions;
    }

    public List<Integer> getIntSuggestions() {
        return intSuggestions;
    }

}
