package de.wwservices.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Assert;
import org.junit.Test;

public class MapHelperTest {

	@Test
	public void testSort() {
		Map<Integer, String> mapping = new HashMap<Integer, String>();
		mapping.put(Integer.valueOf(1), "Test");
		mapping.put(Integer.valueOf(2), "Anfang");
		mapping.put(Integer.valueOf(3), "Dummy - Anfang");
		mapping.put(Integer.valueOf(4), "Xylophon");
		mapping.put(Integer.valueOf(5), "Zahlmeister");
		mapping.put(Integer.valueOf(6), "Zahlmeister");
		mapping.put(Integer.valueOf(7), null);
		mapping.put(Integer.valueOf(8), null);
		mapping.put(null, "B-Test");
		Set<Entry<Integer, String>> sortedEntrySetValue = MapHelper
				.sortedEntrySetByValue(mapping);
		Iterator<Entry<Integer, String>> iterator = sortedEntrySetValue
				.iterator();
		Assert.assertThat(iterator.next().getValue(), equalTo("Anfang"));
		Assert.assertThat(iterator.next().getValue(), equalTo("B-Test"));
		Assert.assertThat(iterator.next().getValue(), equalTo("Dummy - Anfang"));
		Assert.assertThat(iterator.next().getValue(), equalTo("Test"));
		Assert.assertThat(iterator.next().getValue(), equalTo("Xylophon"));
		Assert.assertThat(iterator.next().getValue(), equalTo("Zahlmeister"));
		Assert.assertThat(iterator.next().getValue(), equalTo("Zahlmeister"));
		Assert.assertThat(iterator.next().getValue(), nullValue());
		Assert.assertThat(iterator.next().getValue(), nullValue());

	}

}
