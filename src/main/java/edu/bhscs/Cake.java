// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: Makes a Cake, and allows methods to modify cake and view cake
 * INPUT: You need the ingredients, cost, weight, name
 * OUTPUT: No output
 * EDGE CASE: None
 */

package edu.bhscs;

import java.util.Arrays;

public class Cake {
  private double weightPounds;
  private double weightOG;
  private double cost;
  private String name;
  private String[] ingredients;

  public Cake(String ingredients[], double cost, double weight, String name) {
    this.cost = cost;
    this.name = name;
    this.ingredients = ingredients;
    weightPounds = weight;
    weightOG = weight;

    String stuff = Arrays.toString(ingredients);
    System.out.println("Baking the cake with... " + stuff);
    System.out.println("The cake will cost: $" + this.cost + "");
  }

  public double getWeight() {
    return weightPounds;
  }
  public void eat(double percent){
    weightPounds *= ((100 - percent) * weightPounds) / 100;
  }
  public String getName(){
    return name;
  }
  public double getCost() {
    return cost;
  }

  public double amountLeftWeight() {
    return weightPounds;
  }

  public double amountLeftPercent() {
    return 100 * (weightPounds / weightOG);
  }

  public void displayInfo() {
    String stuff = Arrays.toString(ingredients);
    System.out.print(
        name + " Costs: " + cost + "$ Has: " + stuff + " Weighs " + weightPounds + "lb");
    System.out.println();
  }

  public static String[] base(String[] ingredients) {
    String[] things = {"Flour", "Sugar", "Water", "Milk", "Egg", "Baking Powder", "Salt"};
    return concatenate(things, ingredients);
  }

  private static String[] concatenate(String[] first, String[] second) {
    String[] result = new String[first.length + second.length];
    System.arraycopy(first, 0, result, 0, first.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }

  public static void main(String[] args) {
    String[] ingredients = {
      "Chocolate Chips", "Flour", "Sugar", "Water", "Milk", "Egg", "Cocoa Powder"
    };
    String name = "Chocolate Cake";
    Cake cake = new Cake(ingredients, 100.00, 100, name);
    Person Bob = new Person("Bob", 100, 500);
    Bob.eat(cake, 10);
    // System.out.println(cake.amountLeftWeight());
    // cake.displayInfo();
  }
}
