package de.wwservices.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.wwservices.util.string.GermanUmlautStrategy;
import de.wwservices.util.string.StringReplacementStrategy;

public class StringHelperTest {

    @Test
    public void testExecuteSimpleReplacements() {
        String source ="Neuer Test mit deutschen Umlauten wie Ä Ü Ö ä ü ö";
        String valid ="Neuer Test mit deutschen Umlauten wie Ae Ue Oe ae ue oe";
        String result = StringHelper.executeReplacements(source, new GermanUmlautStrategy());
        assertEquals(valid, result);
    }
    
    @Test
    public void testExecuteCombinedReplacements() {
        String source ="Neuer Test mit deutschen Umlauten wie Ä Ü Ö ä ü ö";
        String valid ="Alter Test mit deutschen Umlauten wie Ae Ue Oe ae ue oe";
        String result = StringHelper.executeReplacements(source, new GermanUmlautStrategy().combineWith(new DummyReplacement()));
        assertEquals(valid, result);
    }
    
    private static class DummyReplacement extends StringReplacementStrategy
    {

        @Override
        protected Map<String, String> defineReplacementMap() {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Neuer", "Alter");
            return map;
        }
        
    }

}
