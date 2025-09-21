// Siddanth Venkatesh
// P2
// Varriables: Making a Cake
// 9/12

/*
 * DESCRIPTION: Allows you to make people and a store where people can buy or steal from the store
 * INPUT: Require the people and the store
 * OUTPUT: Shows weight of people and the store inventory
 * EDGE CASE: None
 */

package edu.bhscs;

public class Main {

  public static void main(String[] args) {
    String storeName = "Cake Store";
    Store store = Store.defaultStore(storeName);
    store.displayStock();
    Person Bob = new Person("Bob", 100, 1000);
    Bob.buy(store, "Chocolate Cake");
    Bob.buy(store, "Chocolate Cake");
    Bob.buy(store, "Chocolate Cake");
    Bob.eat(Bob.getCake("Chocolate Cake"), 50);
    Bob.steal(store, "Coffee Cake");
    store.add("Chocolate Cake", 2);
    store.displayStock();
  }
}
