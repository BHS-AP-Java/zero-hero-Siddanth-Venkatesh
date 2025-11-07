// Siddanth Venkatesh
// P2
// Zero to Hero, to turn in
// 9/19

/*
 * DESCRIPTION: Makes a Cake, and allows methods to modify cake and view cake
 * INPUT: You need the ingredients, cost, weight, name. You can include some base ingredients
 * OUTPUT: You can ouput information regarding the cake, including how much was eaten, cost, name, weight.
 * EDGE CASE: If two ingredients have the same name, then they will be treated as two seperate ingredients.
 * The cake has a Flour field and could have flour in it's ingredients list. When rendering a cake,
 * if the name is not on the Cake, the name will not be printed on the cake
 */

package edu.bhscs;

import java.util.Arrays;

public class Cake implements Offsetable {
  // PROPERTIES AND FIELDS
  // weightPounds is the current weight, WEIGHTOG is the original weight of the cake.
  private double weightPounds;
  // cost is the cost of the cake to buy it.
  private final double WEIGHTOG;
  // name is he name of the cake.
  private double cost;
  // ingredients is a list of the ingredients in an array.
  private String[] ingredients;
  // Name of the cake is name
  private String name;
  // flour is the flour used in the cake
  private Flour flour;
  // quality is the quality of the cake. The price of the cake goes up with quality
  private double quality;

  // Things on the cake
  // Name on the Cake
  private String nameOnTheCake = "no name";
  // Amount of candles on the Cake
  private int candlesOnTheCake = 0;

  // Rendering of the Cake object
  CakeRendering rendering;

  // This is the constructor for a cake, and it makes an instance of a cake with it's ingredients
  // cost, weight, and name.
  // CONSTRUCTOR
  public Cake(String ingredients[], double cost, double weight, String name, double quality) {
    Flour flour = new Flour("All-Purpose Flour", 20, 100, 10);
    this.flour = flour;
    this.quality = quality + flour.getQuality();
    this.cost = cost;
    this.name = name;
    this.ingredients = ingredients;
    weightPounds = weight;
    WEIGHTOG = weight;
    rendering = new CakeRendering(weightPounds, WEIGHTOG);

    String stuff = Arrays.toString(ingredients);
    System.out.println("Baking the cake with... " + stuff);
    System.out.println("The cake will cost: $" + this.cost + "");
    System.out.println("This cake has " + flour.getQuality() + " quality");
  }

  // Makes a default cake, if only the name and flour are given
  public Cake(String name, Flour f, double skill) {
    this.name = name;
    this.flour = f;
    this.ingredients = base();
    this.quality = skill;
    WEIGHTOG = 100;
    weightPounds = WEIGHTOG;
    rendering = new CakeRendering(weightPounds, WEIGHTOG);
  }

  public Cake() {
    WEIGHTOG = 100;
    weightPounds = WEIGHTOG;
    ingredients = base();
    flour = new Flour("ALl purpose flour", 1.0, 10.0, 2);
    rendering = new CakeRendering(weightPounds, WEIGHTOG);
  }

  public Cake(int age, String name) {
    WEIGHTOG = 100;
    weightPounds = WEIGHTOG;
    ingredients = base();
    flour = new Flour("ALl purpose flour", 1.0, 10.0, 2);
    candlesOnTheCake = age;
    nameOnTheCake = name;
    rendering = new CakeRendering(weightPounds, WEIGHTOG);
  }

  // This makes a cake using a specific type of flour
  public Cake(
      String ingredients[], double cost, double weight, String name, Flour flour, double quality) {
    if (flour.getQuality() == 0) {
      System.out.println("Yucky Cake being made");
    }
    this.quality = quality + flour.getQuality();
    this.cost = cost;
    this.name = name;
    this.ingredients = ingredients;
    weightPounds = weight;
    WEIGHTOG = weight;
    rendering = new CakeRendering(weightPounds, WEIGHTOG);

    String stuff = Arrays.toString(ingredients);
    System.out.println("Baking the cake with... " + stuff);
    System.out.println("The cake will cost: $" + this.cost + "");
  }

  // Makes an exact clone of another cake. This is used when you need a new cake value, and not
  // something passed by reference.
  public Cake(Cake other) {
    WEIGHTOG = other.getWeight();
    cost = other.getCost();
    name = other.getName();
    ingredients = other.getIngredients();
    flour = other.getFlour();
    quality = other.getQuality();
    rendering = new CakeRendering(weightPounds, WEIGHTOG);
  }

  // METHODS
  // Getters and Setters:
  // Returns the ingredients in a cake
  public String[] getIngredients() {
    return ingredients;
  }

  // Sets the height of the cake
  public void setHeight(float height) {
    this.rendering.height = height;
  }

  // Returns the flour object in a cake
  public Flour getFlour() {
    return flour;
  }

  // Returns current weight of cake.
  public double getWeight() {
    return weightPounds;
  }

  // Let's you eat a percent of the cake. The percent you eat is based on the amount of cake
  // remaining, not the total cake.
  // Technically not a getter or setter, but I count it because you are setting how much of the cake
  // you eat.
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

  // Returns height of the cake
  public float getHeight() {
    return rendering.height;
  }

  // Returns cost of the cake
  public double getCost() {
    return cost;
  }

  // returns the quality of the cake
  public double getQuality() {
    return quality;
  }

  // Allows the quality of the cake to be modified
  public void setQuality(double newQuality) {
    if (newQuality < quality) {
      System.out.println("The baker " + name + " can't get worse?");
      return;
    }
    quality = newQuality;
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

  // Static methods:
  // Takes extra ingredients and appends them to a list of base ingredients in a cake
  // PRECONDITION: Do not put ingredients with the same name as a base ingredient
  // The cake will contain a Flour object, as well as flour in the ingredients.
  public static String[] base(String[] ingredients) {
    return concatenate(ingredients, base());
  }

  // Same method as above, just not adding any ingredients
  public static String[] base() {
    String[] things = {"Flour", "Sugar", "Water", "Milk", "Egg", "Baking Powder", "Salt"};
    return things;
  }

  // Takes two arrays and returns a new array where both are combined.
  private static String[] concatenate(String[] first, String[] second) {
    String[] result = new String[first.length + second.length];
    System.arraycopy(first, 0, result, 0, first.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }

  // Returns what the Cake should be when printed (not displayed)
  @Override
  public String toString() {
    String stuff = Arrays.toString(ingredients);
    return name + " cake Has: " + stuff + " Weighs " + weightPounds + "lb";
  }

  // Drawing methods
  public void draw() {
    this.draw(nameOnTheCake, "" + candlesOnTheCake);
  }

  public void draw(String name, String ageString) {
    boolean goneBad = flour.quality < 1;
    rendering.draw(nameOnTheCake, "" + candlesOnTheCake, goneBad);
  }

  // returns the length of the cake
  public int getWidth() {
    float[][] verts = rendering.verts;
    return DrawingHelpers.getMax(verts) - DrawingHelpers.getMin(verts);
  }

  // sets the offset of the cake
  public void setOffset(int x) {
    rendering.setShiftX(x);
    return;
  }

  // Main method, used for debugging the cake class
  public static void main(String[] args) {
    Cake cake = new Cake();
    // cake.getFlour().goBad();
    cake.eat(0);
    // cake.draw("Name", "5");
    Table T = new Table(2, 5);
    cake.draw(T);
  }
}
