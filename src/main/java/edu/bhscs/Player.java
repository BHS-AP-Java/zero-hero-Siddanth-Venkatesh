// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: A class that allows the terminal user input to make, destroy, buy, and sell
 * cakes, and control which bakers make it, and PTSA's.
 * Input: Name of player and the types of things that might be created it by them.
 * OUTPUT: Will constantly output all the actions the player can do in loop until the User breaks out of it
 * EDGE CASE: Don't name two different things the same thing, or else things will get messy. ):
 */
package edu.bhscs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Player {
  // PROPERTIES AND FIELDS
  // String name
  private String name;
  // Array List of options Player can do
  private final List<Option<?>> options = new ArrayList<>();
  // Aray List of options without return value that Player can do
  List<noReturnOption> actions = new ArrayList<>();

  // This is a map of all the type of Object to how many there are
  private Map<String, List<Creatable>> lists = new HashMap<>();

  // Originally, Scanner which scans user input was given
  Scanner s;

  // CONSTRUCTOR
  public Player(String name, String[] typesOfInstitutionsOrPeople) {
    for (String thing : typesOfInstitutionsOrPeople) {
      lists.put(thing, new ArrayList<>());
    }
    this.name = name;
    s = new Scanner(System.in);

    // These are the options the Player has
    options.add(new Option<Customer>(("Make Customer"), this::makeCustomer));
    options.add(new Option<Store>(("Make Store"), this::makeStore));
    options.add(new Option<Baker>(("Make Baker"), this::makeBaker));
    options.add(new Option<PTSA>(("Make PTSA"), this::makePTSA));
    actions.add(
        new noReturnOption("View Customers and their actions ", () -> viewCustomerActions()));

  }

  // --------------- This is the wrapper for an "Option" a Player can do --------------------
  // This makes an option class that allows you to make an option the player can do
  private record Option<T>(String label, Maker<T> maker) {}

  // This is an option (or action) the Player can do, that doesn't return anything
  private record noReturnOption(String label, Runnable action) {}

  // Each method for creating people can be abstracted into this Maker class, which
  // allows an Option array to made.
  @FunctionalInterface
  private interface Maker<T> {
    T make(Scanner s);
  }

  // Loops through infinitly, the actions the player can do, until they break out of it
  public void showOptions() {
    loopOptions(options, actions, "Player Options");
  }

  // Loops through the options that need to be done until Player breaks out of it
  public void loopOptions(
      List<Option<?>> options, List<noReturnOption> actions, String whoseOptions) {
    while (true) {
      System.out.println("\n=== " + whoseOptions + " ===");
      for (int i = 0; i < options.size(); i++) {
        System.out.printf("%d. %s%n", i + 1, options.get(i).label);
      }
      for (int j = options.size(); j < options.size() + actions.size(); j++) {
        System.out.printf("%d. %s%n", j + 1, actions.get(j - options.size()).label);
      }
      String input = askQuestion("0. Exit", s);
      if (input.equals("0")) {
        break;
      }
      try {
        int choice = Integer.parseInt(input);
        Option<?> opt;
        noReturnOption action;
        if (choice > options.size() - 1) {
          action = actions.get(choice - 1 - options.size());
          action.action.run();
        } else {
          opt = options.get(choice - 1);
          Object obj = opt.maker.make(s);
          if (obj instanceof Creatable creatable) {
            String type = creatable.getTypeName();
            System.out.println("Created a new " + type);
            lists.get(type).add(creatable);
          }
        }
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  // Asks a question and take in user input from Scanner.
  public String askQuestion(String question, Scanner s) {
    System.out.println(question);
    System.out.print("> ");
    String answer = s.nextLine();
    System.out.println();
    return answer;
  }

  // Asks a question and take in user input from Scanner.
  public String giveAnswer(String question) {
    System.out.println(question);
    System.out.print("> ");
    String answer = s.nextLine();
    System.out.println();
    return answer;
  }

  // -------------------- Defining actions the player can do -----------------------

  // Makes a customer with information about them taken from command line
  private Customer makeCustomer(Scanner s) {
    String name = askQuestion("Enter name: ", s);
    double weight = Double.parseDouble(askQuestion("Enter weight", s));
    double wealth = Double.parseDouble(askQuestion("Enter wealth?", s));
    String race = askQuestion("Enter race: ", s);

    Customer customer = new Customer(name, weight, wealth, race);
    return customer;
  }

  private void viewCustomerActions() {
    Customer customer = chooseEntity("Customer", s);
  }

  // Makes a Baker, with information about them taken from Command line
  private Store makeStore(Scanner s) {
    String storeName = askQuestion("Enter Store name: ", s);
    int numCakes = Integer.parseInt(askQuestion("How many Cakes does store have: ", s));

    Cake[] cakes = new Cake[numCakes];
    int[] amounts = new int[numCakes];

    for (int i = 0; i < numCakes; i++) {
      System.out.printf("\n--- Making Cake %d ---\n", i + 1);
      cakes[i] = makeCake(s);
      amounts[i] = Integer.parseInt(askQuestion("How many of this cake? ", s));
    }
    Store store = new Store(cakes, amounts, storeName);
    System.out.println("Store MADE -------------------");
    // bakers.add(baker);
    return store;
  }
  private Baker makeBaker(Scanner s){
    String name = askQuestion("Name of the baker? ", s);
    double skill = Double.parseDouble(askQuestion("Enter Baker Skill: ", s));
    Store store = chooseEntity("Store", s);
    if (store == null){
      System.out.println("No baker made");
      return null;
    }
    Baker baker = new Baker(this);
    baker.placeOfWork = store;
    baker.giveName(name);
    baker.giveSkill(skill);
    baker.setCash(0);
    return baker;
  }

  // Makes a cake, with information about the cake taken from Command line
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

  // Allows player to pick a Person.
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
    Player player = new Player("Sigma", new String[] {"Customer", "Baker", "PTSA"});
    player.showOptions();
  }
}
