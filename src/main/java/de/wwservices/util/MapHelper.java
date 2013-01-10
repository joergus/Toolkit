package de.wwservices.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * Helper Class for common Operations on Maps. 
 * @author joergw
 *
 */
public class MapHelper {

	/**
	 * Creates a Set of alle Entries of the given Map Sorted by the Value.
	 * Note that null as Keys are not supported!
	 * 
	 * @param incomingMap
	 * @return
	 */
	public static <K extends Comparable<K>, V extends Comparable<V>> Set<Entry<K, V>> sortedEntrySetByValue(
			Map<K, V> incomingMap) {

		Comparator<Entry<K, V>> entrySetComparator = new EntrySetComparator<K, V>();
		Set<Entry<K, V>> sortedSet = new TreeSet<Entry<K, V>>(
				entrySetComparator);
		sortedSet.addAll(incomingMap.entrySet());
		return sortedSet;

	}

	/**
	 * Comparator to compare to entries of a Map by their values if existent.
	 * 
	 * @author joergw
	 *
	 * @param <K>
	 * @param <V>
	 */
	static class EntrySetComparator<K extends Comparable<K>, V extends Comparable<V>>
			implements Comparator<Entry<K, V>> {

		@Override
		public int compare(Entry<K, V> o1, Entry<K, V> o2) {
			if (o1.getValue() != null && o2.getValue() != null) {
				int compareTo = o1.getValue().compareTo(o2.getValue());
				if (compareTo == 0) {
					return o1.getKey().compareTo(o2.getKey());
				} else {
					return compareTo;
				}
			} else if (o1.getValue() == null && o2.getValue() != null) {
				return 1;
			} else if (o1.getValue() != null && o2.getValue() == null) {
				return -1;
			} else {
				return o1.getKey().compareTo(o2.getKey());
			}

		}

	}

}
