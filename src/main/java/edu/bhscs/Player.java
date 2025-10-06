package edu.bhscs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.print.attribute.standard.PrinterState;

public class Player {
  // Properties
  // String name
  private String name;
  // Array List of options Player can do
  private final List<Option<?>> options = new ArrayList<>();

  // This is a map of all the type of Object to how many there are
  private Map<String, List<Creatable>> lists = new HashMap<>();

  // Originally, Scanner which scans user input was given
  // But was changed to have the scanner passed into methods, b/c it is easier to manage that way.

  // Player Constructor
  public Player(String name, String[] typesOfInstitutionsOrPeople) {
    for (String thing : typesOfInstitutionsOrPeople){
      lists.put(thing, new ArrayList<>());
    }
    this.name = name;

    // These are the options the Player has
    options.add(new Option<Customer>(("Make Customer"), this::makeCustomer));
    options.add(new Option<Baker>(("Make Bakery"), this::makeBaker));
    options.add(new Option<PTSA>(("Make PTSA"), this::makePTSA));
    // TODO: options.add(new Option<Void>("View Customer Actions", this::viewCustomerActions));

  }

  // --------------- This is the wrapper for an "Option" a Player can do --------------------
  // This makes an option class that allows you to make an option the player can do
  private record Option<T>(String label, Maker<T> maker) {}

  // Each method for creating people can be abstracted into this Maker class, which
  // allows an Option array to made.
  @FunctionalInterface
  private interface Maker<T> {
    T make(Scanner s);
  }

  public void showOptions(Scanner s) {
    while (true) {
      System.out.println("\n=== Player Options ===");
      for (int i = 0; i < options.size(); i++) {
        System.out.printf("%d. %s%n", i + 1, options.get(i).label);
      }
      String input = askQuestion("0. Exit", s);
      if (input.equals("0")) {
        break;
      }
      try {
        int choice = Integer.parseInt(input);
        Option<?> opt = options.get(choice - 1);
        Object obj = opt.maker.make(s);
        if (obj instanceof Creatable creatable){
          String type =  creatable.getTypeName();
          System.out.println("Created a new " + type);
          lists.get(type).add(creatable);
        }
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  public String askQuestion(String question, Scanner s) {
    System.out.println(question);
    System.out.print("> ");
    String answer = s.nextLine();
    System.out.println();
    return answer;
  }

  // -------------------- Defining actions the player can do -----------------------

  private Customer makeCustomer(Scanner s) {
    String name = askQuestion("Enter name: ", s);
    double weight = Double.parseDouble(askQuestion("Enter weight", s));
    double wealth = Double.parseDouble(askQuestion("Enter wealth?", s));
    String race = askQuestion("Enter race: ", s);

    Customer customer = new Customer(name, weight, wealth, race);
    // customers.add(customer);
    return customer;
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
      amounts[i] = Integer.parseInt(askQuestion("How many of this cake? ", s));
    }
    Baker baker = new Baker(cakes, amounts, storeName, skill);
    // bakers.add(baker);
    return baker;
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
    Flour flour = makeFlour(s);

    return new Cake(ingredients, cost, weight, name, flour, quality);
  }

  // Allows player to make Flour
  private Flour makeFlour(Scanner s) {
    String name = askQuestion("Flour name: ", s);
    double weight = Double.parseDouble(askQuestion("Flour weight", s));
    double price = Double.parseDouble(askQuestion("Flour price ", s));
    int quality =
        Integer.parseInt(askQuestion("Quality of cake if a newbie made it? (probably 0-10) ", s));

    return new Flour(name, weight, price, quality);
  }

  // Allows Player to make PTSA
  private PTSA makePTSA(Scanner s) {
    String name = askQuestion("Name of PTSA? ", s);
    double wealth = Double.parseDouble(askQuestion("Initial wealth? ", s));
    double cut = Double.parseDouble(askQuestion("Cut the PTSA takes froms sales? ", s));
    Map<String, Double> needs = new HashMap<>();
    while (true) {
      System.out.println("Enter something the PTSA needs (or 'done' when finsihed)");
      String item = askQuestion("What is needed? ", s);
      if (item.equalsIgnoreCase("done")) {
        break;
      }
      double amount = Double.parseDouble(askQuestion("How much does this cost? ", s));
      needs.put(item, amount);
    }
    PTSA ptsa = new PTSA(name, needs, wealth, cut);
    // PTSAs.add(ptsa);
    return ptsa;
  }

  private <T extends Creatable> T chooseEntity(String typeName, Scanner s) {
    List<Creatable> list = lists.get(typeName);

    if (list == null || list.isEmpty()) {
      System.out.println("No " + typeName + "s available.");
      return null;
    }

    System.out.println("Choose a " + typeName + ":");
    for (int i = 0; i < list.size(); i++) {
      System.out.println((i + 1) + ". " + list.get(i));
    }

    System.out.print("Enter number: ");
    int choice = Integer.parseInt(s.nextLine());

    if (choice < 1 || choice > list.size()) {
      System.out.println("Invalid choice.");
      return null;
    }

    @SuppressWarnings("unchecked")
    T selected = (T) list.get(choice - 1);
    return selected;
  }


  // For testing purposes
  public static void main() {
    Scanner s = new Scanner(System.in);
    Player player = new Player("Sigma", new String[] {"Customer", "Baker", "PTSA"});
    player.showOptions(s);
  }
}
