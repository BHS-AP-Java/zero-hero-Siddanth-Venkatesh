// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: Makes a Baker/Store that sells cakes at the price they were made for. The bakers
 * cakes have better quality the more the baker bakes.
 * INPUT: Information regarding what cakes the store, and the amounts of each cake
 * OUTPUT: Will output information regarding whta the store sells, how much inventory it has, and if
 * something is bought improperly.
 * EDGE CASE: Two cakes should be the same if they have the same name. If they have different names, the store will
 * display two cakes as the same, even though it internally has two different cakes. You will only be able to purchase
 * one of the two cakes.
 */
package edu.bhscs;

import java.util.HashMap;

public class Baker implements Creatable {
  // Properties
  // The inventory represents a map from every cake the baker has to the amount of it they have.
  private HashMap<Cake, Integer> inventory = new HashMap<>();
  // name is the name of the baker.
  private String name;
  // balance is the amount of money the baker has.
  private double balance = 0.00;
  // skill is the skill of the baker, which will determine the quality of the cakes they make
  private double skill;

  // This is the constructor for this class. It takes in the cakes the baker will sell, the amounts
  // of the cakes,
  // and the storeName.
  public Baker(Cake[] cakes, int[] amounts, String storeName, double skill) {
    if (cakes.length != amounts.length) {
      System.out.println("Need amounts for each type of Cake");
      return;
    }
    inventory = new HashMap<>();
    name = storeName;
    for (int i = 0; i < cakes.length; i++) {
      inventory.put(cakes[i], amounts[i]);
    }
    this.skill = skill;
  }

  // Returns the type of the object
  @Override
  public String getTypeName() {
    return "Baker";
  }

  // This sets the default baker, with three types of cakes, each with the same default ingredients,
  // plus one different one.
  public Baker(String storeName, double skill) {
    this(defaultCakeOptions(skill), new int[] {2, 4, 5}, storeName, skill);
  }

  // List of the default cake options a store could sell
  private static Cake[] defaultCakeOptions(double skill) {
    String name = "Chocolate Cake";
    String[] ingredientsChocolate = {"Cocoa Powder"};
    Cake chocolateCake = new Cake(Cake.base(ingredientsChocolate), 150.00, 100, name, skill);

    name = "Vanilla Cake";
    String[] ingredientsVanilla = {"Vanilla Extract"};
    Cake vanillaCake = new Cake(Cake.base(ingredientsVanilla), 75.00, 100, name, skill);

    name = "Coffee Cake";
    String[] ingredientsCoffee = {"Instant Coffee"};
    Cake coffeeCake = new Cake(Cake.base(ingredientsCoffee), 100.00, 100, name, skill);

    return new Cake[] {chocolateCake, vanillaCake, coffeeCake};
  }

  // This gives the balance of the Baker.
  public double getBalance() {
    return balance;
  }

  // This displays all the stock the Baker has.
  public void displayStock() {
    System.out.println("Welcome to our bakery by " + name + ". We sell the following: ");
    for (Cake key : inventory.keySet()) {
      key.displayInfo();
      System.out.println(
          "Stock: " + inventory.get(key) + " and has a quality of " + key.getQuality());
    }
  }

  // This gives the baker some amount of money. It can be negative, meaning the baker loses money.
  public void getMoney(double amount) {
    balance += amount;
  }

  // This adds a cake to bakers inventory. If the cake added has the same name as another cake,
  // but different ingredients, it will still get added.
  // If the baker adds many cakes, the quality of their cakes will go up.
  public void add(Cake cake, int amount) {
    inventory.put(cake, inventory.get(cake) + amount);
    if (amount < 0) {
      System.out.println(
          name
              + " has sold "
              + -1 * amount
              + " cake(s) called "
              + cake.getName()
              + " from their store");
      return;
    }
    System.out.println(
        name
            + " has stocked "
            + amount
            + " extra cake(s) called "
            + cake.getName()
            + " to their store");
    skill += amount * 10;
    cake.setQuality(skill);
    System.out.println(
        "The Baker's skill has increased to "
            + skill
            + " meaning he bakes cakes with better quality");
    System.out.println("-----------------------------");
  }

  // This adds a specific amount of cakes with a certain name into the baker's inventory.
  public void add(String cakeName, int amount) {
    Cake cake = getCakeByName(cakeName);
    if (cake == null) {
      System.out.println(name + " doesn't have cakes with that name ");
      return;
    }
    add(cake, amount);
  }

  /*
   * There could be multiple different cakes with the same name. Because of this, this will only
   * return the amount of cakes with the name given that comes first in the list.
   */
  public int getCakeAmount(String name) {
    for (Cake key : inventory.keySet()) {
      if (key.getName().equals(name)) {
        return inventory.get(key);
      }
    }
    System.out.println("Cake with name " + name + " not found in inventory.");
    return 0;
  }

  /*
   * There could be multiple different cakes with the same name. Because of this, this will only
   * return the first cake it finds with the same name.
   */
  public Cake getCakeByName(String name) {
    for (Cake cake : inventory.keySet()) {
      if (cake.getName().equals(name)) {
        return cake;
      }
    }
    return null; // No cake found with that name
  }
}
