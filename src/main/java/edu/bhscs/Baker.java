// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: Makes a Baker/Store that sells cakes at the price they were made for
 * INPUT: Information regarding what cakes the store, and the amounts of each cake
 * OUTPUT: Will output information regarding whta the store sells, how much inventory it has, and if
 * something is bought improperly.
 * EDGE CASE: Two cakes should be the same if they have the same name. If they have different names, the store will
 * display two cakes as the same, even though it internally has two different cakes. You will only be able to purchase
 * one of the two cakes.
 */
package edu.bhscs;

import java.util.HashMap;

public class Baker {
  // The inventory represents a map from every cake the baker has to the amount of it they have.
  // name is the name of the bake.
  // balance is the amount of money the baker has.
  private HashMap<Cake, Integer> inventory = new HashMap<>();
  private String name;
  private double balance = 0.00;

  // This is the constructor for this class. It takes in the cakes the baker will sell, the amounts of the cakes,
  // and the storeName.
  public Baker(Cake[] cakes, int[] amounts, String storeName) {
    inventory = new HashMap<>();
    if (cakes.length != amounts.length) {
      System.out.println("Need amounts for each type of Cake");
      return;
    }
    name = storeName;
    for (int i = 0; i < cakes.length; i++) {
      inventory.put(cakes[i], amounts[i]);
    }
  }

  // This gives the balance of the Baker.
  public double getBalance() {
    return balance;
  }

  // This displays all the stock the Baker has.
  public void displayStock() {
    System.out.println("Welcome to our store " + name + ". We sell the following: ");

    for (Cake key : inventory.keySet()) {
      key.displayInfo();
      System.out.println("Stock: " + inventory.get(key));
    }
  }

  // This gives the baker some amount of money. It can be negative, meaning the baker loses money.
  public void getMoney(double amount) {
    balance += amount;
  }

  // This adds a cake to bakers inventory. If the cake added has the same name as another cake,
  // but different ingredients, it will still get added.
  public void add(Cake cake, int amount) {
    inventory.put(cake, inventory.get(cake) + amount);
    if (amount < 0){
      System.out.println("We have sold "+ -1 * amount + " cakes called " + cake.getName() + " from our store");
      return;
    }
    System.out.println("We have stocked " + amount + " extra cakes called " + cake.getName() + " to our store");
  }

  // This adds a specific amount of cakes with a certain name into the baker's inventory.
  public void add(String cakeName, int amount) {
    Cake cake = getCakeByName(cakeName);
    if (cake == null) {
      System.out.println("We don't have cakes with that name");
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

  // This sets the default baker, with three types of cakes, each with the same default ingredients,
  // plus one different one.
  public static Baker defaultBaker(String storeName) {
    String name = "Chocolate Cake";
    String[] ingredientsChocolate = {"Cocoa Powder"};
    Cake chocolateCake = new Cake(Cake.base(ingredientsChocolate), 150.00, 100, name);

    name = "Vanilla Cake";
    String[] ingredientsVanilla = {"Vanilla Extract"};
    Cake vanillaCake = new Cake(Cake.base(ingredientsVanilla), 75.00, 100, name);

    name = "Coffee Cake";
    String[] ingredientsCoffee = {"Instant Coffee"};
    Cake coffeeCake = new Cake(Cake.base(ingredientsCoffee), 100.00, 100, name);

    int[] amounts = {2, 4, 5};

    return new Baker(new Cake[] {chocolateCake, vanillaCake, coffeeCake}, amounts, storeName);
  }
}
