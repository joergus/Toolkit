package de.wwservices.util.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class StringReplacementStrategy {

    private List<StringReplacementStrategy> strategies = new ArrayList<StringReplacementStrategy>();

    protected abstract Map<String, String> defineReplacementMap();

    public StringReplacementStrategy() {
        strategies.add(this);
    }

    public StringReplacementStrategy combineWith(
            StringReplacementStrategy replacementStrategy) {
        strategies.add(replacementStrategy);
        return this;
    }

    public String execute(String source) {
        String result = source;
        for (StringReplacementStrategy strategy : strategies) {
            Map<String, String> defineReplacementMap = strategy
                    .defineReplacementMap();
            if (defineReplacementMap != null && result != null) {
                for (Entry<String, String> entry : defineReplacementMap.entrySet()) {
                    result = result.replace(entry.getKey(), entry.getValue());
                }
            }
        }
        return result;

    }

}
