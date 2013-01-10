package de.wwservices.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * Helper Class for common Operations on Maps.
 * 
 * @author joergw
 * 
 */
public class MapHelper {

	/**
	 * Creates a set of all entries of the given map sorted by the value. Note
	 * that on same values the keys are compared additionally.
	 * 
	 * @param incomingMap
	 *            the map to sort
	 * @return Set conatining sorted entries
	 */
	public static <K extends Comparable<K>, V extends Comparable<V>> Set<Entry<K, V>> sortedEntrySetByValue(
			Map<K, V> incomingMap) {

		Comparator<Entry<K, V>> entrySetComparator = new EntrySetComparator<K, V>();
		Set<Entry<K, V>> sortedSet = new TreeSet<Entry<K, V>>(
				entrySetComparator);
		for (Entry<K, V> entry : incomingMap.entrySet()) {
			sortedSet.add(entry);
		}
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
	private static class EntrySetComparator<K extends Comparable<K>, V extends Comparable<V>>
			implements Serializable, Comparator<Entry<K, V>> {

		private static final long serialVersionUID = 6845808240479551387L;

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

	/**
	 * Creates a set of all entries of the given map sorted by the value. Note
	 * that on same values the keys are compared additionally.
	 * 
	 * @param incomingMap
	 *            the map to sort.
	 * @param comparator
	 *            the Comparator to compare the values
	 * @return sorted Set.
	 */
	public static <K extends Comparable<K>, V> Set<Entry<K, V>> sortedEntrySetByValue(
			Map<K, V> incomingMap, Comparator<V> comparator) {

		Comparator<Entry<K, V>> entrySetComparator = new EntrySetComparator2<K, V>(
				comparator);
		Set<Entry<K, V>> sortedSet = new TreeSet<Entry<K, V>>(
				entrySetComparator);
		for (Entry<K, V> entry : incomingMap.entrySet()) {
			sortedSet.add(entry);
		}
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
	private static class EntrySetComparator2<K extends Comparable<K>, V>
			implements Serializable, Comparator<Entry<K, V>> {

		private static final long serialVersionUID = 5295616358725924256L;
		
		private Comparator<V> comp;

		EntrySetComparator2(Comparator<V> comparator) {
			comp = comparator;
		}

		@Override
		public int compare(Entry<K, V> o1, Entry<K, V> o2) {
			if (o1.getValue() != null && o2.getValue() != null) {
				int compareTo = comp.compare(o1.getValue(), o2.getValue());
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
