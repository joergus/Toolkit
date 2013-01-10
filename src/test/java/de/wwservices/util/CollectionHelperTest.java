package de.wwservices.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class CollectionHelperTest {

	@Test
	public void containsAnySuccess() {
		Collection<String> col1 = new ArrayList<String>();
		col1.add("Test 1");
		col1.add("Test 2");

		Assert.assertTrue(CollectionHelper.containsAny(col1,
				Arrays.asList("Test 1")));
		Assert.assertTrue(CollectionHelper.containsAny(col1,
				Arrays.asList("Test 17", "Test 1")));
	}

	@Test
	public void containsAnyFail() {
		Collection<String> col1 = new ArrayList<String>();
		col1.add("Test 1");
		col1.add("Test 2");

		Assert.assertFalse(CollectionHelper.containsAny(col1,
				Arrays.asList("Test 10")));
	}
}
