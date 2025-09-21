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

  public Store(Cake[] cakes, int[] amounts, String storeName){
    inventory = new HashMap<>();
    if(cakes.length != amounts.length){
      System.out.println("Need amounts for each type of Cake");
      return;
    }
    name = storeName;
    for(int i = 0; i < cakes.length; i++){
      inventory.put(cakes[i], amounts[i]);
    }
  }

  public void displayStock(){
    System.out.println("Welcome to our store " + name + ". We sell the following: ");

    for (Cake key : inventory.keySet()) {
      key.displayInfo();

      System.out.println("Stock: " + inventory.get(key));
    }
  }
  public static void main(String[] args){

    String storeName = "Cake Store";

    String name = "Chocolate Cake";
    String[] ingredientsChocolate = {"Cocoa Powder"};
    Cake chocolateCake = new Cake(Cake.base(ingredientsChocolate), 150.00, 100, name, storeName);

    name = "Vanilla Cake";
    String[] ingredientsVanilla = {"Vanilla Extract"};
    Cake vanillaCake = new Cake(Cake.base(ingredientsVanilla), 75.00, 100, name, storeName);

    name = "Coffee Cake";
    String[] ingredientsCoffee = {"Instant Coffee"};
    Cake coffeeCake = new Cake(Cake.base(ingredientsCoffee), 100.00, 100, name, storeName);

    int[] amounts = {2, 4, 5};

    Store store = new Store(new Cake[]{chocolateCake, vanillaCake, coffeeCake}, amounts, storeName);
    store.displayStock();
  }
}
