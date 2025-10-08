// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: Makes a Store that sells cakes at the price they were made for. The Stores
 * cakes have better quality the more the Store bakes.
 * INPUT: Information regarding what cakes the
 * store, and the amounts of each cake
 * OUTPUT: Will output information regarding whta the store
 * sells, how much inventory it has, and if something is bought improperly.
 * EDGE CASE: Two cakes should be the same if they have the same name. If they have different names, the store will
 * display two cakes as the same, even though it internally has two different cakes. You will only
 * be able to purchase one of the two cakes.
 */
package edu.bhscs;

import java.util.HashMap;

public class Store implements Creatable {
  // PROPERTIES AND FIELDS
  // The inventory represents a map from every cake the Store has to the amount of it they have.
  public HashMap<Cake, Integer> inventory = new HashMap<>();
  // name is the name of the Store.
  private String name;
  // balance is the amount of money the Store has.
  private double balance = 0.00;

  // This is the constructor for this class. It takes in the cakes the Store will sell, the amounts
  // of the cakes,
  // and the storeName.
  // CONSTRUCTOR
  public Store(Cake[] cakes, int[] amounts, String storeName, double skill) {
    if (cakes.length != amounts.length) {
      System.out.println("Need amounts for each type of Cake");
      return;
    }
    inventory = new HashMap<>();
    name = storeName;
    for (int i = 0; i < cakes.length; i++) {
      inventory.put(cakes[i], amounts[i]);
    }
  }

  // METHODS
  // Returns the type of the object
  @Override
  public String getTypeName() {
    return "Store";
  }

  // This sets the default Store, with three types of cakes, each with the same default ingredients,
  // plus one different one.
  public Store(String storeName, double skill) {
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

  // This gives the balance of the Store.
  public double getBalance() {
    return balance;
  }

  // Gives the name of the Store
  public String getName() {
    return name;
  }

  // This displays all the stock the Store has.
  public void displayStock() {
    System.out.println("Welcome to our Store called " + name + ". We sell the following: ");
    for (Cake key : inventory.keySet()) {
      key.displayInfo();
      System.out.println(
          "Stock: " + inventory.get(key) + " and has a quality of " + key.getQuality());
    }
  }

  // This gives the Store some amount of money. It can be negative, meaning the Store loses money.
  public void getMoney(double amount) {
    balance += amount;
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
