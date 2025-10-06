package edu.bhscs;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
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
    // These are the options the Player has
    options.add(new Option<Customer>(("Make Customer"), this::makeCustomer));

  }

  // -------------------- Defining actions the player can do -----------------------

  public String askQuestion(String question, Scanner s){
    System.out.println(question);
    System.out.print("> ");
    String answer = s.nextLine();
    System.out.println();
    return answer;
  }

  private Customer makeCustomer(Scanner s){
    String name = askQuestion("Enter name: ", s);
    double weight = Double.parseDouble(askQuestion("Enter weight", s));
    double wealth = Double.parseDouble(askQuestion("Enter wealth?", s));
    String race = askQuestion("Enter name: ", s);
    return new Customer(name, weight, wealth, race);
  }

  private Baker makeBaker(Scanner s) {
    String storeName = askQuestion("Enter Baker name: ", s);
    double skill = Double.parseDouble(askQuestion("Enter Baker Skill: ", s));
    int numCakes = Integer.parseInt(askQuestion("How many Cakes does store have: ", s));

    Cake[] cakes = new Cake[numCakes];
    int[] amounts = new int[numCakes];

    for (int i = 0; i < numCakes; i++) {
      System.out.printf("\n--- Making Cake %d ---\n", i + 1);
      cakes[i] = makeCake(s);
      amounts[i] = Integer.parseInt(askQuestion("How many of this cake? "));
    }

    return new Baker(cakes, amounts, storeName, skill);
  }

  private Cake makeCake(Scanner s) {
    String name = askQuestion("Cake name: ", s);
    int count = Integer.parseInt(askQuestion("Enter amounts of ingredients: ", s));
    String[] ingredients = new String[count];
    for (int i = 0; i < count; i++) {
      System.out.print("Ingredient " + (i + 1) + ": ");
      ingredients[i] = s.nextLine();
    }

    double cost = Double.parseDouble(askQuestion("Cost? ", s));
    System.out.print("Weight: ");
    double weight = Double.parseDouble(askQuestion("Weight? ", s));
    System.out.print("Quality (1–10): ");
    double quality = Double.parseDouble(askQuestion("Quality? ", s));

    System.out.println("Now, create flour for this cake:");
    Flour flour = makeFlour();

    return new Cake(ingredients, cost, weight, name, flour, quality);
  }

  private Flour makeFlour(Scanner s) {
    String name = askQuestion("Flour name: ", s);
    double weight = Double.parseDouble(askQuestion("Flour weight", s));
    int price = Integer.parseInt("Flour price (an int)");
    int quality = 10;

    return new Flour(name, weight, price, quality);
  }

  private PTSA makePTSA(Scanner s){
    String name = askQuestion("Name of PTSA? ", s);
    double wealth = Double.parseDouble(askQuestion("Initial wealth? ", s));
    double cut = Double.parseDouble("Cut the PTSA takes froms sales? ");
    Map<String, Double> needs = new HashMap<>();
    while (true){
      System.out.println("Enter something the PTSA needs (or 'done' when finsihed)");
      String item = askQuestion("What is needed? ", s);
      if (item.equalsIgnoreCase("done")){
        break;
      }
      double amount = Double.parseDouble("How much does this cost? ");
      needs.put(item, amount);

    }
    return new PTSA(name, needs, wealth, cut);

  }

  // This makes an option class that allows you to make an option the player can do
  private record Option<T>(String label, Maker<T> maker) {}
  // Makes it so that each option can have a unique method attached to it
  @FunctionalInterface
  private interface Maker<T> {
    T make(Scanner s);
  }

}
