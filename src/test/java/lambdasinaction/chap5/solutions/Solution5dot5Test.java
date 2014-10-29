package lambdasinaction.chap5.solutions;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lambdasinaction.chap5.Trader;
import lambdasinaction.chap5.Transaction;

import org.junit.Test;

import com.google.common.collect.Ordering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Solution5dot5Test {

  static final Logger LOG = LoggerFactory.getLogger(Solution5dot5Test.class);
  
  @Test
  public void filterTransactionsByYear_2011() {
    LOG.debug("filterTransactionsByYear_2011()");
    List<Transaction> year2011 = Solution5dot5.filterTransactionsByYear(2011);

    year2011.stream().forEach(p -> LOG.debug(p.toString()));
    
    // All should be of the year 2011
    assertTrue(year2011.stream().allMatch(t -> t.getYear() == 2011));
    assertFalse(year2011.stream().allMatch(t -> t.getYear() == 2010));

    // Should be in the order of lowest to highest
    // Use Guava's Ordering to do the validation
    Ordering<Transaction> byValue = new Ordering<Transaction>() {
      @Override
      public int compare(Transaction t1, Transaction t2) {
        return Integer.compare(t1.getValue(), t2.getValue());
      }
    };

    assertTrue(byValue.isOrdered(year2011));
  }
  
  @Test
  public void filterTransactionsByUniqueCity() {
    LOG.debug("filterTransactionsByUniqueCity()");
    // Create a Set of the cities for testing.
    // This avoids brittle tests by being flexible to the data structures
    Set<String> testCities = new HashSet<>();
    for (Transaction t : Solution5dot5.getTransactions()) {
      testCities.add(t.getTrader().getCity());
    }
    
    // Make sure that the 
    List<String> cities = Solution5dot5.filterTransactionsByUniqueCity();
    cities.stream().forEach(p -> LOG.debug(p.toString()));
    assertEquals(testCities.size(), cities.size());
    
    // Use Hamcrest Matchers to verify the contents
    assertThat(cities, containsInAnyOrder(testCities.toArray()));
  }
  


  @Test
  public void filterByCitySortByName_cambridge() {
    LOG.debug("filterByCitySortByName_cambridge()");
    
//    TreeSet<Trader> traders = new TreeSet<Trader>(new StringComp());
//    
//    //Collections.sort(toSort, Ordering.usingToString());
//
//
//    Ordering<Trader> byValue = new Ordering<Trader>() {
//      @Override
//      public int compare(Trader t1, Trader t2) {
//        return String.compare(t1.getName(), t2.getName());
//      }
//    };
  }


}

class StringComp implements Comparator<String> {
  @Override
  public int compare(String str1, String str2) {
    return str1.compareTo(str2);
  }
}
