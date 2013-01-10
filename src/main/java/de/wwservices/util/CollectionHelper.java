package de.wwservices.util;

import java.util.Collection;

/**
 * Class to provide common helper methods to cope with {@link Collection}s
 * 
 * @author joergw
 */
public class CollectionHelper {

	/**
	 * Checks whether the given source collection contains any of the items in the find {@link Collection}.
	 * 
	 * @param source
	 * @param find
	 * @return
	 */
	public static <T> boolean containsAny(Collection<T> source,
			Collection<T> find) {
		for (T tofind : find) {
			if (source.contains(tofind)) {
				return true;
			}
		}
		return false;
	}

}
