package de.wwservices.util.string;

import java.util.HashMap;
import java.util.Map;

public class GermanUmlautStrategy extends StringReplacementStrategy {

    private static final Map<String, String> germanUmlautEscapeMap = initGermanUmlautReplacementMap();
    
    private static Map<String, String> initGermanUmlautReplacementMap() {
            Map<String, String> map = new HashMap<String, String>();
            map.put("\u00C4", "Ae");
            map.put("\u00E4", "ae");
            map.put("\u00D6", "Oe");
            map.put("\u00F6", "oe");
            map.put("\u00DC", "Ue");
            map.put("\u00FC", "ue");
            map.put("\u00DF", "ss");
            return map;
    }
    
    @Override
    public Map<String, String> defineReplacementMap() {
        return germanUmlautEscapeMap;
    }

}
