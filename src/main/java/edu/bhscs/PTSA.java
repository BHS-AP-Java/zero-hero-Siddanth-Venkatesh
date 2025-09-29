package edu.bhscs;

import java.util.Map;

public class PTSA {
  private double balance = 0;
  private String name;
  private double goal;
  private double cut;
  private Map<String, Double> necesities;

  public PTSA(String name, double goal, Map<String, Double> necesities) {
    this.name = name;
    this.goal = goal;
    this.necesities = necesities;
    System.out.println("Please help our poor school fundraise. We are the " + name + " PTSA and we have no money");
    System.out.println("If you buy cakes for our fundraiser, giving our name, we will be happy");
    System.out.println("Our goal is $ " + goal);
  }

  public PTSA(String name, double goal, double initialWealth, Map<String, Double> necesities) {
    this.name = name;
    this.goal = findGoal();
    this.necesities = necesities;
    System.out.println("Please help our poor school fundraise. We are the " + name + " PTSA");
    System.out.println("If you buy cakes for our fundraiser, giving our name, we will be happy");
    displayGoal();
  }
  public double findGoal(){
    double goal = 0;
    for(Double value: necesities.values()){
      goal += value;
    }
    return goal;
  }

  public void displayGoal() {
    System.out.println("Our goal is $ " + goal);
    System.out.println("We are " + ((100 * balance) / goal) + "% to our goal");
    System.out.println("We have $" + balance);
  }

  public void neededItems(){
    necesities.entrySet().stream().forEach();
  }
  public static void main(String[] args){
    
  }
}
