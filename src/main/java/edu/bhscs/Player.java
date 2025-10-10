// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: A class that allows the terminal user input to make, destroy, buy, and sell
 * cakes; also allows creation of bakers and ptsas and customers
 * Input: Name of player and the types of things that might be created it by them. Also, will ask for
 * information regarding what is being created when needed.
 * OUTPUT: Will constantly output all the actions the player can do in loop until the User breaks out of it
 * EDGE CASE: Don't name two different things the same thing, or else things will break. If put negatives numbers
 * when prompted for a number, sometimes things will break
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
  // Accomplishments of the player
  int accomplishments;

  // This is a map of all the type of Object to how many there are
  private Map<String, List<Creatable>> lists = new HashMap<>();

  // Originally, Scanner which scans user input was given
  Scanner s;

  // CONSTRUCTOR
  public Player(String name) {
    String[] typesOfInstitutionsOrPeoplePlayerControls = {"Customer", "Baker", "PTSA", "Store"};
    for (String thing : typesOfInstitutionsOrPeoplePlayerControls) {
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
    actions.add(new noReturnOption("View PTSA Goals", () -> viewPTSAGoal()));
    actions.add(new noReturnOption("Baker actions", () -> showBakerActions()));
  }
  // METHODS
  // --------------- This is the wrapper for an "Option" a Player can do --------------------
  // This makes an option class that allows you to make an option the player can do. Each options
  // has a label describing it, and a maker, which is a function.
  private record Option<T>(String label, Maker<T> maker) {}

  // This is an option (or action) the Player can do, that doesn't return anything.
  // Same as above, expcept that a "runner" doesn't return anything.
  private record noReturnOption(String label, Runnable action) {}

  // Each method for creating people can be abstracted into this Maker class, which
  // allows an Option array to made.
  @FunctionalInterface
  private interface Maker<T> {
    T make(Scanner s);
  }

  // Loops through infinitly, the actions the player can do, until they break out of it
  public void showOptions() {
    loopOptions(options, actions, "Player Options", true);
  }

  // Loops through the options that need to be done until Player breaks out of it
  public void loopOptions(
      List<Option<?>> options,
      List<noReturnOption> actions,
      String whoseOptions,
      boolean returnHandling) {
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
        if (choice > options.size()) {
          action = actions.get(choice - 1 - options.size());
          action.action.run();
        } else {
          opt = options.get(choice - 1);
          Object obj = opt.maker.make(s);
          // ONLY executed when outer loop does something.
          if (returnHandling && obj instanceof Creatable creatable) {
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

  public void accomplishments(int amount) {
    accomplishments += amount;
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

  // CUSTOMER ACTIONS:
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
    if (customer == null){
      return;
    }
    showCustomerActions(customer);
  }

  private void showCustomerActions(Customer customer) {
    List<Option<?>> options = List.of(); // The customers actions don't return anything

    List<noReturnOption> actions =
        List.of(
            new noReturnOption(
                "Donate to Fundraiser",
                () -> {
                  PTSA fundraiser = chooseEntity("PTSA", s);
                  if (fundraiser == null) return;
                  double amount = Double.parseDouble(askQuestion("Amount to donate:", s));
                  customer.donate(fundraiser, amount);
                }),
            new noReturnOption(
                "Try to Escape Jail",
                () -> {
                  customer.escape();
                }),
            new noReturnOption(
                "Eat a Cake",
                () -> {
                  System.out.println("Choose the cake you want out of the options");
                  int choice = chooseElement(customer.getCakesOwned(), s);
                  Cake cake = customer.getCakesOwned().get(choice);
                  double percent = Double.parseDouble(askQuestion("Percent to eat:", s));
                  customer.eat(cake, percent);
                }),
            new noReturnOption(
                "View Cakes",
                () -> {
                  ArrayList<Cake> cakes = customer.getCakesOwned();
                  System.out.println(cakes);
                }),
            new noReturnOption(
                "View Balance",
                () -> {
                  double balance = customer.getBalance();
                  System.out.println(balance);
                }),
            new noReturnOption(
                "Steal a Cake (you have to try to guess the name of Cake to steal it)",
                () -> {
                  Baker baker = chooseEntity("Baker", s);
                  if (baker == null) return;
                  String cakeName = askQuestion("Cake name:", s);
                  customer.steal(baker, cakeName);
                }),
            new noReturnOption(
                "Buy a Cake",
                () -> {
                  Baker baker = chooseEntity("Baker", s);
                  if (baker == null) return;
                  baker.placeOfWork.displayStock();
                  String cakeName = askQuestion("Cake name:", s);
                  customer.buy(baker, cakeName);
                }),
            new noReturnOption(
                "Buy a Cake directly from the Baker, with default ingredients, but custom name",
                () -> {
                  Baker baker = chooseEntity("Baker", s);
                  if (baker == null) return;
                  baker.placeOfWork.displayStock();
                  int price = Integer.parseInt(askQuestion("Price:", s));
                  customer.buyCustom(baker, price);
                }),
            new noReturnOption(
                "Buy Cake (Quality Requirement)",
                () -> {
                  Baker baker = chooseEntity("Baker", s);
                  if (baker == null) return;
                  baker.placeOfWork.displayStock();
                  String cakeName = askQuestion("Cake name:", s);
                  double q = Double.parseDouble(askQuestion("Minimum quality:", s));
                  customer.buyQuality(baker, cakeName, q);
                }),
            new noReturnOption(
                "Buy in Fundraiser",
                () -> {
                  Baker baker = chooseEntity("Baker", s);
                  PTSA fundraiser = chooseEntity("PTSA", s);
                  if (baker == null || fundraiser == null) return;
                  baker.placeOfWork.displayStock();
                  String cakeName = askQuestion("Cake name:", s);
                  customer.buyInFundraiser(baker, cakeName, fundraiser);
                }),
            new noReturnOption(
                "Buy in Fundraiser (Quality Requirement)",
                () -> {
                  Baker baker = chooseEntity("Baker", s);
                  PTSA fundraiser = chooseEntity("PTSA", s);
                  if (baker == null || fundraiser == null) return;
                  baker.placeOfWork.displayStock();
                  String cakeName = askQuestion("Cake name:", s);
                  double q = Double.parseDouble(askQuestion("Minimum quality:", s));
                  customer.buyInFundraiserGoodQuality(baker, cakeName, fundraiser, q);
                }));

    loopOptions(options, actions, "Customer Options", false);
  }

  // BAKER/STORE ACTIONS:
  // Makes a Baker, with information about them taken from Command line
  private Store makeStore(Scanner s) {
    String defaultStore =
        askQuestion("Make a default store? (answer yes/y or anything else for no)", s);
    if (defaultStore.equalsIgnoreCase("yes") || defaultStore.equalsIgnoreCase("y")) {
      String name = askQuestion("Name of the default store? ", s);
      return new Store(name);
    }
    ;

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

  private Baker makeBaker(Scanner s) {
    String name = askQuestion("Name of the baker? ", s);
    double skill = Double.parseDouble(askQuestion("Enter Baker Skill: ", s));
    Store store = chooseEntity("Store", s);
    if (store == null) {
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

  private void showBakerActions(){
    Baker baker = chooseEntity("Baker", s);
    if (baker == null){
      return;
    }
    List<Option<?>> options = List.of(); // The bakers actions don't return anything
    List<noReturnOption> actions =
        List.of(new noReturnOption("Add cakes to place of work by name", () -> {
          baker.placeOfWork.displayStock();
          String cakeName = askQuestion("Name of the Cake you want to add", s);
          int amount = Integer.parseInt(askQuestion("How many cakes should they add? ",s));
          baker.add(cakeName, amount);
        }),
        new noReturnOption("Add a new type of cake", () -> {
          Cake cake = makeCake(s);
          int amount = Integer.parseInt(askQuestion("Amount of these new cakes to add? ", s));
          baker.addNewCake(cake, amount);
        }),
        new noReturnOption("View Balance", () -> {
          System.out.println(baker.getBalance());
        }),
        new noReturnOption("Change place of work", () -> {
          System.out.println("Choose the same store if the baker shouldn't change stores");
          Store store = chooseEntity("Store", s);
          baker.takeJob(store);
        }),
        new noReturnOption("View place of Work", () -> {
          System.out.println(baker.placeOfWork);
          baker.placeOfWork.displayStock();
        })

        );

      loopOptions(options, actions, "Baker Actions", false);

  }

  // CAKE ACTIONS:
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

  // PTSA ACTIONS
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

  // View PTSA goal
  private void viewPTSAGoal(){
    PTSA ptsa = chooseEntity("PTSA", s);
    ptsa.displayGoal();
  }

  // HELPER METHODS
  // Allows a Person to choose an Entity
  @SuppressWarnings("unchecked")
  private <T extends Creatable> T chooseEntity(String typeName, Scanner s) {
    List<? extends Creatable> list = lists.get(typeName);

    if (list == null || list.isEmpty()) {
      System.out.println("No " + typeName + "s available.");
      return null;
    }

    System.out.println("\nChoose a " + typeName + ":");
    for (int i = 0; i < list.size(); i++) {
      System.out.println((i + 1) + ". " + list.get(i));
    }

    int choice;
    try {
      choice = Integer.parseInt(askQuestion("Enter number:", s));
    } catch (NumberFormatException e) {
      System.out.println("Invalid input.");
      return null;
    }

    if (choice < 1 || choice > list.size()) {
      System.out.println("Invalid choice.");
      return null;
    }

    return (T) list.get(choice - 1);
  }

  // Returns the index of an element from an Array List.
  public static <T> int chooseElement(ArrayList<T> list, Scanner sc) {
    if (list.isEmpty()) {
      System.out.println("The list is empty.");
      return -1;
    }
    System.out.println("Choose an element:");
    for (int i = 0; i < list.size(); i++) {
      System.out.println((i + 1) + ". " + list.get(i));
    }
    int choice = -1;
    while (true) {
      System.out.print("Enter the number of your choice: ");
      try {
        choice = Integer.parseInt(sc.nextLine()) - 1;
        if (choice >= 0 && choice < list.size()) {
          break;
        } else {
          System.out.println(
              "Invalid choice. Please enter a number between 1 and " + list.size() + ".");
        }
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number.");
      }
    }

    return choice;
  }

  // For testing purposes
  public static void main() {
    Player player = new Player("Sigma");
    player.showOptions();
  }
}
