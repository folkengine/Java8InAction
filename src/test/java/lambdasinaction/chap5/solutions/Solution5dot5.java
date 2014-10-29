package lambdasinaction.chap5.solutions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lambdasinaction.chap5.Trader;
import lambdasinaction.chap5.Transaction;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**

 * 
 * 6.  Print all transactions’ values from the traders living in Cambridge.
 * 
 * 7.  What’s the highest value of all the transactions?”
 * 
 * @author r531495
 *
 */
public class Solution5dot5 {

  public static List<Transaction> transactions;

  public static List<Transaction> getTransactions() {
    if (Solution5dot5.transactions == null) {
      Trader raoul = new Trader("Raoul", "Cambridge");
      Trader mario = new Trader("Mario", "Milan");
      Trader alan = new Trader("Alan", "Cambridge");
      Trader brian = new Trader("Brian", "Cambridge");

      Solution5dot5.transactions =
          Arrays.asList(
              new Transaction(brian, 2011, 300), 
              new Transaction(raoul, 2012, 1000),
              new Transaction(raoul, 2011, 400), 
              new Transaction(mario, 2012, 710),
              new Transaction(mario, 2012, 700), 
              new Transaction(alan, 2012, 950));
    }
    return Solution5dot5.transactions;
  }

  /**
   * 1.  Find all transactions in the year 2011 and sort them by value (small to high).
   */
  public static List<Transaction> filterTransactionsByYear(Integer year) {
    List<Transaction> transactions =
        getTransactions().stream().filter(t -> t.getYear() == year)
            .sorted(comparing(Transaction::getValue)).collect(toList());
    // To reverse the order:
    // Collections.reverse(transactions);
    return transactions;
  }

  /**
   * 2.  What are all the unique cities where the traders work?
   */
  public static List<String> filterTransactionsByUniqueCity() {
    List<String> transactions =
        getTransactions().stream().map(t -> t.getTrader().getCity()).distinct().collect(toList());
    return transactions;
  }
  
  public static List<Transaction> filterTransactionsByCity(String city) {
    return getTransactions().stream().filter(p -> p.getTrader().getCity().equalsIgnoreCase(city))
        .collect(toList());
  }

  /**
   * 3.  Find all traders from Cambridge and sort them by name.
   */
  public static List<Trader> filterByCitySortByName(String city) {
    List<Trader> traders =
        filterTransactionsByCity(city).stream()
            .map(t -> t.getTrader()).distinct().sorted(comparing(Trader::getName))
            .collect(toList());
    return traders;
  }

  /**
   * 4.  Return a string of all traders’ names sorted alphabetically.
   */
  public static List<String> filterTradersByName() {
    List<String> names =
        getTransactions().stream().map(t -> t.getTrader().getName()).distinct().sorted()
            .collect(toList());
    return names;
  }
  
  /**
   * 5.  Are any traders based in Milan?
   */
  public static boolean areTradersBasedInCity(String city) {
    return getTransactions().stream().anyMatch(p -> p.getTrader().getCity().equalsIgnoreCase(city));
  }
  
  /**
   * 6.  Print all transactions’ values from the traders living in Cambridge.
   */
  public static void printTransactionValuesByCity(String city) {
    filterTransactionsByCity(city).stream().map(t -> t.getValue())
        .forEach(p -> System.out.println(p.toString()));
  }
  
  /**
   * 7.  What’s the highest value of all the transactions?
   */
  public static Transaction filterHighestTransaction() {
    return getTransactions().stream().sorted(comparing(Transaction::getValue))
        .reduce((previous, current) -> current).get();
  }

  public static void main(String[] args) {
    System.out
        .println("1.  Find all transactions in the year 2011 and sort them by value (small to high).");
    filterTransactionsByYear(2011).stream().forEach(p -> System.out.println("\t" + p));
    System.out.println();

    System.out.println("2.  What are all the unique cities where the traders work?");
    filterTransactionsByUniqueCity().stream().forEach(p -> System.out.println("\t" + p));
    System.out.println();

    System.out.println("3.  Find all traders from Cambridge and sort them by name.");
    filterByCitySortByName("Cambridge").stream().forEach(p -> System.out.println("\t" + p));
    System.out.println();

    System.out.println("4.  Return a string of all traders’ names sorted alphabetically.");
    filterTradersByName().stream().forEach(p -> System.out.println("\t" + p));
    System.out.println();

    System.out.println("5.  Are any traders based in Milan?");
    System.out.println("\t" + areTradersBasedInCity("Milan"));
    System.out.println("5.1  Are any traders based in New York?");
    System.out.println("\t" + areTradersBasedInCity("New York"));
    System.out.println();

    System.out.println("6.  Print all transactions’ values from the traders living in Cambridge.");
    printTransactionValuesByCity("Cambridge");
    System.out.println();

    System.out.println("7.  What’s the highest value of all the transactions?");
    System.out.println(filterHighestTransaction());
  }

}
