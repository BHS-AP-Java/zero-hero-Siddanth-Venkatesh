package edu.bhscs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class PTSA {
  private double balance = 0;
  private String name;
  private double goal;
  private double cut;
  private Map<String, Double> necesities;

  public PTSA(String name, Map<String, Double> necesities) {
    this.name = name;
    this.necesities = necesities;
    this.goal = findGoal();
    System.out.println(
        "Please help our poor school fundraise. We are the " + name + " PTSA and we have no money");
    System.out.println("If you buy cakes for our fundraiser, giving our name, we will be happy");
    System.out.println("Our goal is $" + goal);
  }

  public PTSA(String name, double initialWealth, Map<String, Double> necesities) {
    this.name = name;
    this.goal = findGoal() + initialWealth;
    this.necesities = necesities;
    System.out.println("Please help our poor school fundraise. We are the " + name + " PTSA");
    System.out.println("If you buy cakes for our fundraiser, giving our name, we will be happy");
    displayGoal();
  }

  public double findGoal() {
    double goal = 0;
    for (Double value : necesities.values()) {
      goal += value;
    }
    return goal;
  }

  public void displayGoal() {
    System.out.println("Our goal is $" + goal);
    System.out.println("We are " + ((100 * balance) / goal) + "% to our goal");
    System.out.println("We have $" + balance);
  }

  public void neededItems() {
    necesities.entrySet().stream()
        .sorted(Comparator.comparingDouble(s -> s.getValue()))
        .forEach(s -> System.out.println("We need to buy" + s.getKey() + " that costs " + s.getValue() + "$"));

    System.println("We have enough money to buy the following: ");

    List<Double> Values = necesities.values().stream().sorted().collect(Collectors.toList());
    Double[] valuesArray = Values.toArray(new Double[0]);
    Arrays.parallelPrefix(valuesArray, Double::sum);
    Values.stream().filter(s -> s <= goal).
    System.out.println(Arrays.toString(valuesArray));
  }

  public static void main(String[] args) {
    Map<String, Double> things = new HashMap<>();
    things.put("Computer", 100.00);
    things.put("Sports maintenance", 10.000);
    things.put("Floors", 50.00);
    PTSA BothellPTSA = new PTSA("Bothell", things);
    BothellPTSA.neededItems();
  }
}
