package edu.bhscs;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Player {
  // Properties
  // String name
  private String name;
  // Scanner s scans user input
  private Scanner s;
  // Array List of options Player can do
  private final List<Option<?>> options = new ArrayList<>();

  // Options is the set of things the Player can do

  public Player(String name) {
    this.name = name;
    this.s = new Scanner(System.in);

    options.add(new Option<>("Make Customer"). this:makeCustomer);
    // This options the Player has

  }

  // -------------------- Defining actions the player can do -----------------------

  public String askQuestion(String question){
    System.out.println(question);
    System.out.print("> ");
    String answer = s.nextLine();
    System.out.println();
    return answer;
  }

  private Customer makeCustomer(){
    String name = askQuestion("Enter name: ");
    double weight = Double.parseDouble(askQuestion("Enter weight"));
    double wealth = Double.parseDouble(askQuestion("Enter wealth?"));
    String race = askQuestion("Enter name: ");
    return new Customer(name, weight, wealth, race);
  }

  private Baker makeBaker() {
    String storeName = askQuestion("Enter Baker name: ");
    double skill = Double.parseDouble(askQuestion("Enter Baker Skill: "));
    int numCakes = Integer.parseInt(askQuestion("How many Cakes does store have: "));

    Cake[] cakes = new Cake[numCakes];
    int[] amounts = new int[numCakes];

    for (int i = 0; i < numCakes; i++) {
      System.out.printf("\n--- Making Cake %d ---\n", i + 1);
      cakes[i] = makeCake();
      amounts[i] = Integer.parseInt(askQuestion("How many of this cake? "));
    }

    return new Baker(cakes, amounts, storeName, skill);
  }

  private Cake makeCake() {
    String name = askQuestion("Cake name: ");
    int count = Integer.parseInt(askQuestion("Enter amounts of ingredients: "));
    String[] ingredients = new String[count];
    for (int i = 0; i < count; i++) {
      System.out.print("Ingredient " + (i + 1) + ": ");
      ingredients[i] = s.nextLine();
    }

    double cost = Double.parseDouble(askQuestion("Cost? "));
    System.out.print("Weight: ");
    double weight = Double.parseDouble(askQuestion("Weight? "));
    System.out.print("Quality (1–10): ");
    double quality = Double.parseDouble(askQuestion("Quality? "));

    System.out.println("Now, create flour for this cake:");
    Flour flour = makeFlour();

    return new Cake(ingredients, cost, weight, name, flour, quality);
  }

  private Flour makeFlour() {
    String name = askQuestion("Flour name: ");
    double weight = Double.parseDouble(askQuestion("Flour weight"));
    int price = Integer.parseInt("Flour price (an int)");
    int quality = 10;

    return new Flour(name, weight, price, quality);
  }

  // This makes an option class that allows you to make an option the player can do
  private record Option<T>(String label, Maker<T> maker) {}
  // Makes it so that each option can have a unique method attached to it
  @FunctionalInterface
  private interface Maker<T> {
    T make(Scanner s);
  }

}
