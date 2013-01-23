
package de.wwservices.util;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class MapHelperTest
{

  @Test
  public void testSort()
  {
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
    Set<Entry<Integer, String>> sortedEntrySetValue = MapHelper.sortedEntrySetByValue(mapping);
    Iterator<Entry<Integer, String>> iterator = sortedEntrySetValue.iterator();
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

  @Test
  public void testSortComparator()
  {
    Map<Integer, TestSort> mapping = new TreeMap<Integer, MapHelperTest.TestSort>();
    mapping.put(Integer.valueOf(1), new TestSort(Integer.valueOf(10)));
    mapping.put(Integer.valueOf(2), new TestSort(Integer.valueOf(5)));
    mapping.put(Integer.valueOf(3), new TestSort(Integer.valueOf(8)));
    mapping.put(Integer.valueOf(4), new TestSort(Integer.valueOf(9)));
    Set<Entry<Integer, TestSort>> entrySet = mapping.entrySet();
    Iterator<Entry<Integer, TestSort>> iterator = entrySet.iterator();
    Assert.assertThat(iterator.next().getValue().getSortingnumber(), equalTo(Integer.valueOf(10)));
    Assert.assertThat(iterator.next().getValue().getSortingnumber(), equalTo(Integer.valueOf(5)));
    Assert.assertThat(iterator.next().getValue().getSortingnumber(), equalTo(Integer.valueOf(8)));
    Assert.assertThat(iterator.next().getValue().getSortingnumber(), equalTo(Integer.valueOf(9)));

    entrySet = MapHelper.sortedEntrySetByValue(mapping, new TestComparator());
    iterator = entrySet.iterator();
    Assert.assertThat(iterator.next().getValue().getSortingnumber(), equalTo(Integer.valueOf(5)));
    Assert.assertThat(iterator.next().getValue().getSortingnumber(), equalTo(Integer.valueOf(8)));
    Assert.assertThat(iterator.next().getValue().getSortingnumber(), equalTo(Integer.valueOf(9)));
    Assert.assertThat(iterator.next().getValue().getSortingnumber(), equalTo(Integer.valueOf(10)));
  }

  private static class TestSort
  {
    private Integer sortingnumber = Integer.valueOf(-1);

    TestSort(Integer sorting)
    {
      sortingnumber = sorting;
    }

    public Integer getSortingnumber()
    {
      return sortingnumber;
    }
  }

  private static class TestComparator implements Serializable, java.util.Comparator<TestSort>
  {

    private static final long serialVersionUID = 5202232488208379385L;

    @Override
    public int compare(TestSort o1, TestSort o2)
    {
      return o1.sortingnumber.compareTo(o2.sortingnumber);
    }

  }

}
