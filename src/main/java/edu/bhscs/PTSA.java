// Siddanth Venkatesh
// P: 2
// The cake project
// 9/19

/*
 * DESCRIPTION: Makes a PTSA that does a fundraiser to buy things for a school. All bakers are
 * automatically entered into a fundraiser. They lose a "cut" of their profits whenever someone buys from them for their fundraiser.
 * The PTSA will try to buy the cheapest things first.
 * INPUT: Requires the name of the school, the things he PTSA plans to buy, and how much those things cost,
 * as well as the cut the PTSA will take from every cake purchase from the baker.
 * OUTPUT: It will output all the things the PTSA is trying to buy, the amount of money they are trying to raise. It will also confirm
 * what stuff the PTSA can buy, assuming they buy the cheapest things up.
 * EDGE CASE: If the fundraiser tries to fundraise twice for the same thing, it will be counted twice.
 * If the fundraiser has equal value items, the PTSA will prioritize buying one randomly if they can only buy some of them.
 * The things the PTSA fundraises for should have articles attached to them, like "a computer", or "some new floors" or else
 * when it prints out those things, grammar errors will occur.
 */

package edu.bhscs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PTSA {
  // balance is the amount of money the PTSA has fundraised
  // name is the name of the school the PTSA is for
  // cut is the percentage of revenue the PTSA takes from cake sales
  // necesities is a HashMap representing the things and the cost of those things the PTSA
  // is trying to fundraise for. The String things is what the PTSA needs.
  // They strings in them should have articles attached to them.
  private double balance = 0;
  private String name;
  private double goal;
  private double cut;
  private Map<String, Double> necesities;

  // This is the PTSA contructor. It takes in the name of the school and
  // what it needs to fundraise for at what price using a HashMap.
  public PTSA(String name, Map<String, Double> necesities, double cut) {
    System.out.println("----------------------------");
    if (cut < 0 || cut > 100) {
      System.out.println("Can't take a negative cut, or can't take a cut above 100 percent");
      return;
    }
    this.name = name;
    this.cut = cut;
    this.necesities = necesities;
    this.goal = findGoal();
    displayGoal();
  }

  // This is the same PTSA constructor as above, except that it has some initial wealth
  public PTSA(String name, Map<String, Double> necesities, double initialWealth, double cut) {
    if (cut < 0 || cut > 100) {
      System.out.println("Can't take a negative cut, or can't take a cut above 100 percent");
      return;
    }
    this.name = name;
    this.cut = cut;
    this.necesities = necesities;
    this.goal = findGoal();
    this.balance = initialWealth;
    displayGoal();
  }

  // Returns the name of the school the PTSA represents.
  public String getName() {
    return name;
  }

  // This finds the total the PTSA is fundraising for.
  public double findGoal() {
    double goal = 0;
    for (Double value : necesities.values()) {
      goal += value;
    }
    return goal;
  }

  // This displays the amount of money the PTSA is fundraising for, and the things it is wanting to
  // buy.
  // Also shows the price of things it wants to buy and the things it can buy.
  public void displayGoal() {
    System.out.println("----------------------------");
    System.out.println("Please help our poor school fundraise. We are the " + name + " PTSA");
    System.out.println("If you buy cakes for our fundraiser, giving our name, we will be happy");
    goal = findGoal();
    System.out.println("Our goal is $" + goal);
    FundraiserItems();
    System.out.print("We are " + ((100 * balance) / goal) + "% to our goal. ");
    System.out.println("We have $" + balance);
    if (balance < goal) {
      System.out.println("----------------------------");
      return;
    }
    System.out.println("We have reached our goal for " + name + " for now. YAYAYAYYAYAYAYAY");
    System.out.println("----------------------------");
  }

  // This returns a list of items that are needed, and their prices, as well as what can already be
  // affored, assuming that we buy the cheapest things first.
  public void FundraiserItems() {
    // Again, using my favorite streams.
    necesities.entrySet().stream()
        .sorted(Comparator.comparingDouble(s -> s.getValue()))
        .forEach(
            s ->
                System.out.println(
                    "We need to buy "
                        + s.getKey()
                        + " that costs "
                        + s.getValue()
                        + "$")); // Sorts the necesities by
    // the value of the thing in the HashMap, then prints the information regading it.

    System.out.println("We have enough money to buy the following: ");

    // Sorts the HashMap values, and then puts it into a list
    List<Double> Values = necesities.values().stream().sorted().collect(Collectors.toList());
    Double[] valuesArray = Values.toArray(new Double[0]);
    // Gives the cumalative sum to buy the first n things in an array
    Arrays.parallelPrefix(valuesArray, Double::sum);
    // Finds the index of the most expensive element that can be bought
    long count = Arrays.stream(valuesArray).filter(sum -> sum <= balance).count();
    // Prints out all elements that cheaper or equal to the most expensive one that can be bought.
    necesities.entrySet().stream()
        .sorted(Comparator.comparingDouble(s -> s.getValue()))
        .limit(count)
        .forEach(
            s ->
                System.out.println(
                    "We can buy " + s.getKey() + " which costs us $" + s.getValue()));
  }

  // This adds a new thing that is being fundraised for
  public void add(String thing, double price) {
    necesities.put(thing, price);
    System.out.println("Our " + name + " PTSA now needs to buy " + thing);
    System.out.println("It costs $" + price);
  }

  // Donate some wealth
  public void donate(double amount) {
    if (amount <= 0) {
      System.out.println("Please donate a positive amount");
      return;
    }
    balance += amount;
  }

  // Returns the percentage that the PTSA gets from a cake sale.
  public double getCut() {
    return cut;
  }

  // Main method used for debugging this class
  public static void main(String[] args) {
    Map<String, Double> things = new HashMap<>();
    things.put("a Computer", 100.00);
    things.put("Sports maintenance", 10.000);
    things.put("some new floors", 50.00);
    PTSA BothellPTSA = new PTSA("Bothell", things, 160, 90);
    BothellPTSA.add("Chairs", 50);
    BothellPTSA.displayGoal();
  }
}
