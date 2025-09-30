// Siddanth Venkatesh
// P2
// Zero to Hero, to turn in
// 9/19

/*
 * DESCRIPTION: Makes a Cake, and allows methods to modify cake and view cake
 * INPUT: You need the ingredients, cost, weight, name. You can include some base ingredients
 * OUTPUT: You can ouput information regarding the cake, including how much was eaten, cost, name, weight.
 * EDGE CASE: If two ingredients have the same name, then they will be treated as two seperate ingredients
 */

package edu.bhscs;

import java.util.Arrays;

public class Cake {

  // weightPounds is the current weight, WEIGHTOG is the original weight of the cake.
  // cost is the cost of the cake to buy it.
  // name is he name of the cake.
  // ingredients is a list of the ingredients in an array.
  private double weightPounds;
  private final double WEIGHTOG;
  private double cost;
  private String name;
  private String[] ingredients;

  // This is the constructor for a cake, and it makes an instance of a cake with it's ingredients,
  // cost, weight, and name.
  public Cake(String ingredients[], double cost, double weight, String name) {
    this.cost = cost;
    this.name = name;
    this.ingredients = ingredients;
    weightPounds = weight;
    WEIGHTOG = weight;

    String stuff = Arrays.toString(ingredients);
    System.out.println("Baking the cake with... " + stuff);
    System.out.println("The cake will cost: $" + this.cost + "");
  }

  // Returns current weight of cake.
  public double getWeight() {
    return weightPounds;
  }

  public void eat(double percent) {
    if (percent < 0 || percent > 100) {
      System.out.println("You can't eat more than 100 or less than 0 percent of a cake");
      return;
    }
    weightPounds *= (100.00 - percent) / 100.00;
  }

  // Returns name of the cake.
  public String getName() {
    return name;
  }

  // Returns cost of the cake
  public double getCost() {
    return cost;
  }

  // Returns the remaining weight of the cake
  public double amountLeftWeight() {
    return weightPounds;
  }

  // Returns the percent of the cake remaining compared to the original weight.
  public double amountLeftPercent() {
    return 100 * (weightPounds / WEIGHTOG);
  }

  // Displays the info of cake including cost, name, weight, and ingredients.
  public void displayInfo() {
    String stuff = Arrays.toString(ingredients);
    System.out.print(
        name + " Costs: " + cost + "$ Has: " + stuff + " Weighs " + weightPounds + "lb");
    System.out.println();
  }

  // Takes extra ingredients and appends them to a list of base ingredients in a cake
  // PRECONDITION: Do not put ingredients with the same name as a base ingredient
  public static String[] base(String[] ingredients) {
    String[] things = {"Flour", "Sugar", "Water", "Milk", "Egg", "Baking Powder", "Salt"};
    return concatenate(things, ingredients);
  }

  // Takes two arrays and returns a new array where both are combined.
  private static String[] concatenate(String[] first, String[] second) {
    String[] result = new String[first.length + second.length];
    System.arraycopy(first, 0, result, 0, first.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }

  // Main method, used for debugging the cake class
  public static void main(String[] args) {
    String[] ingredients = {
      "Chocolate Chips", "Flour", "Sugar", "Water", "Milk", "Egg", "Cocoa Powder"
    };
    String name = "Chocolate Cake";
    Cake cake = new Cake(ingredients, 100.00, 100, name);
    Customer Bob = new Customer("Bob", 100, 500);
    Bob.eat(cake, 10);
    // System.out.println(cake.amountLeftWeight());
    // cake.displayInfo();
  }
}
