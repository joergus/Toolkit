
package de.wwservices.util.string;


/**
 * Hilfsklasse für den Umgang mit Strings.
 * 
 * @author joergw
 * 
 */
public final class StringHelper
{
    
    public static final String executeReplacements(String source, StringReplacementStrategy strategy){
        return strategy.execute(source);
    }

}
