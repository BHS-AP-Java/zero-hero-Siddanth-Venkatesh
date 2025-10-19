// Siddanth Venkatesh
// P2
// Zero to Hero, to turn in
// 9/19

/*
 * DESCRIPTION: Makes a Cake, and allows methods to modify cake and view cake
 * INPUT: You need the ingredients, cost, weight, name. You can include some base ingredients
 * OUTPUT: You can ouput information regarding the cake, including how much was eaten, cost, name, weight.
 * EDGE CASE: If two ingredients have the same name, then they will be treated as two seperate ingredients.
 * The cake has a Flour field and could have flour in it's ingredients list.
 */

package edu.bhscs;

import java.util.Arrays;

public class Cake {
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
  // color of the cake
  private char color = '#';

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
  }

  public Cake() {
    WEIGHTOG = 100;
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
  }

  // METHODS
  // Returns the ingredients in a cake
  public String[] getIngredients() {
    return ingredients;
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

  // Returns the name of the type
  @Override
  public String toString() {
    String stuff = Arrays.toString(ingredients);
    return name + " cake " + " Has: " + stuff + " Weighs " + weightPounds + "lb";
  }

  // Drawing the Cake
  public void draw() {
    // this.ingredients
    // for (int i = 0; i < a; i++){
    //   System.out.prnt(this.color);
    // }
    System.out.println();
    int size = 140;
    char[][] matrix = new char[size][size];
    char[] def = new char[size];
    Arrays.fill(def, ' ');
    for (int i = 0; i < matrix.length; i++) {
      matrix[i] = def.clone();
    }
    float radius = 40.0f;
    float height = 10.0f;
    int slices = 10;
    float thetaStart = (float)((3f / 4f) * Math.PI);
    float dTheta = (float) ((weightPounds / WEIGHTOG) * Math.PI);
    float thetaEnd = thetaStart + dTheta;

    float[][] verts =
        DrawingHelpers.generateCylinderSliceVertices(radius, height, slices, thetaStart, thetaEnd);
    int[][] facesOG = DrawingHelpers.generateCylinderSliceIndices(slices, thetaEnd, thetaStart);
    DrawingHelpers.rotateVertices(verts, (float) (3 * Math.PI / 4), 0.0f, 0.0f);
    int[][] faces = DrawingHelpers.zSortTriangles(facesOG, verts);

    DrawingHelpers.printVertices(verts);
    DrawingHelpers.printIndices(faces);

    int length = faces.length;
    for (int i = 0; i < length; i++) {
      // For now, simple orthographic projection is used
      int shiftx = 80;
      int shifty = 40;

      int x0 = Math.round(verts[faces[i][0]][0]) + shiftx;
      int y0 = Math.round(verts[faces[i][0]][1]) + shifty;
      int z0 = Math.round(verts[faces[i][0]][2]);

      int x1 = Math.round(verts[faces[i][1]][0]) + shiftx;
      int y1 = Math.round(verts[faces[i][1]][1]) + shifty;
      int z1 = Math.round(verts[faces[i][1]][2]);

      int x2 = Math.round(verts[faces[i][2]][0]) + shiftx;
      int y2 = Math.round(verts[faces[i][2]][1]) + shifty;
      int z2 = Math.round(verts[faces[i][2]][2]);

      // Semi accurate shading is used
      char shade = DrawingHelpers.findShading(x0, y0, z0, x1, y1, z1, x2, y2, z2);

      DrawingHelpers.fillTriangle(x0, y0, x1, y1, x2, y2, matrix, shade);
    }

    draw(matrix);
  }

  public void putTriangle(char[][] things) {}

  // Draw a 2d Array
  public void draw(char[][] things) {
    for (int i = 0; i < things[0].length; i++) {
      int length = things.length;
      // Loops a triangle, going by rows then columns to simulate drawing x,y pairs.
      for (int j = 0; j < length; j++) {
        System.out.print(things[j][length - i - 1] + "");
        System.out.flush();
        // System.out.print(j + "jk" + i);
      }
      System.out.println();
    }
  }

  // Main method, used for debugging the cake class
  public static void main(String[] args) {
    Cake cake = new Cake();
    cake.draw();
    // String[] ingredients = {
    //   "Chocolate Chips", "Flour", "Sugar", "Water", "Milk", "Egg", "Cocoa Powder"
    // };
    // String name = "Chocolate Cake";
    // Cake cake = new Cake(ingredients, 100.00, 100, name, 10.00);
    // Customer Bob = new Customer("Bob", 100, 500, "Mexican");
    // Bob.eat(cake, 10);
    // System.out.println(cake.amountLeftWeight());
    // cake.displayInfo();
  }
}
