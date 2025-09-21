// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: Makes a Store
 * INPUT: Information regarding what cakes the store sells at what price
 * OUTPUT: No output
 * EDGE CASE: None
 */
package edu.bhscs;

import java.util.HashMap;

public class Store {
  private HashMap<Cake, Integer> inventory = new HashMap<>();
  private String name;
  private double balance = 0.00;

  public Store(Cake[] cakes, int[] amounts, String storeName) {
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

  public double getBalance(){
    return balance;
  }

  public void displayStock() {
    System.out.println("Welcome to our store " + name + ". We sell the following: ");

    for (Cake key : inventory.keySet()) {
      key.displayInfo();
      System.out.println("Stock: " + inventory.get(key));
    }
  }
  public void getMoney(double amount){
    balance += amount;
  }

  public void add(Cake cake, int amount) {
    inventory.put(cake, inventory.get(cake) + amount);
  }
  public void add(String cakeName, int amount){
    Cake cake = getCakeByName(cakeName);
    if (cake == null){
      System.out.println("We don't have cakes with that name");
      return;
    }
    add(cake, amount);
  }

  public int getCakeAmount(String name) {
    for (Cake key : inventory.keySet()) {
      if (key.getName().equals(name)) {
        return inventory.get(key);
      }
    }
    System.out.println("Cake with name " + name + " not found in inventory.");
    return 0;
  }
  public Cake getCakeByName(String name) {
    for (Cake cake : inventory.keySet()) {
      if (cake.getName().equals(name)) {
        return cake;
      }
    }
    return null; // or throw exception / Optional
  }

  public static Store defaultStore(String storeName) {
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

    return new Store(new Cake[] {chocolateCake, vanillaCake, coffeeCake}, amounts, storeName);
  }


}
