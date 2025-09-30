// Siddanth Venkatesh
// P2
// Zero to Hero, to turn in
// 9/19

/*
 * DESCRIPTION: Makes a customer, who can buy and steal cakes or go to jail. If jailed, the customer can't
 * do anything. The customer can donate to PTSA
 * INPUT: Requires the name, weight, and wealth
 * OUTPUT: Either returns a human, or that a customer can't do something because they are either in jail, or don't have enough money
 * EDGE CASE: A jailed customer is restricted in their activities. If two different stores have the same named cake, then the customer
 * can only eat the cake that comes first in their list of cakes.
 */

package edu.bhscs;

import java.util.ArrayList;

public class Customer {
  // cakesOwned is an ArrayList of cakes the customer bought or stolen
  // weight is the weight of the customer
  // balance is the amount of money the customer owns (in dollars)
  // jailed is a boolean telling if a customer is in jail or not
  // name is the name of the customer
  private ArrayList<Cake> cakesOwned = new ArrayList<>();
  private double weight = 0;
  private double balance = 0;
  private boolean jailed = false;
  private String name;

  // This is the constroctor for a customer. It takes in their name, weight, and wealth and makes a
  // customer.
  public Customer(String name, double weight, double wealth) {
    this.name = name;
    this.weight = weight;
    this.balance = wealth;
  }

  // If a customer is in jail, they can try their luck and try to escape. They have a 50% chance.
  public void escape() {
    if (!jailed) {
      System.out.println("Why are you trying to escape when not jailed, " + name + "?");
      return;
    }
    int random = (int) Math.floor(Math.random() * 2);
    if (random == 1) {
      System.out.println("Escape didn't work out");
      return;
    }
    System.out.println("Succesful escape from jail, " + name);
    jailed = false;
  }

  // This takes in a name of cake, and filters through the list of cakes they have and finds the
  // cake with that name
  public Cake getCake(String name) {
    return cakesOwned.stream()
        .filter(
            cake ->
                cake.getName()
                    .equals(name)) // Filters the stream of cakes into a stream only containing ones
        .findFirst() // Could have multiple cakes with the same name, this ensures that only the
        // first one is chosen
        .orElse(
            null); // Exception, if the customer does not have the cake. Did this way to mess around
    // with streams in java
  }

  // You can eat some percent of a cake. Can't eat more than 100 or less than 0.
  public void eat(Cake cake, double percent) {
    if (jailed) {
      System.out.println("You can't eat cake while in jail, " + name);
      return;
    }
    if (!cakesOwned.contains(cake)) {
      System.out.println("Can't eat a cake you don't have");
      return;
    }
    if (percent > 100) {
      System.out.println("Can't eat more than a 100% of the cake");
      return;
    }
    if (percent < 0) {
      System.out.println("Can't eat a negative amount of cake");
      return;
    }
    weight += cake.getWeight() * percent / 100;
    cake.eat(percent);
    System.out.println("You now are " + weight + "lb " + name);
    System.out.println("Do you feel good after eating the " + cake.getName() + " " + name + "?");
  }

  public void steal(Baker store, String cakeName) {
    int random = (int) Math.floor(Math.random() * 2); // Generates 0 or 1
    if (jailed) {
      System.out.println("Your in jail " + name);
      System.out.println("Try to escape before stealing");
      return;
    }
    if (random == 0) {
      jailed = true;
      System.out.println(name + " got jailed for trying to steal a cake");
      return;
    }
    if (store.getCakeAmount(cakeName) < 1) {
      System.out.println("Can't steal something out of stock of " + cakeName);
      return;
    }
    Cake cake = store.getCakeByName(cakeName);
    store.add(cake, -1);
    cakesOwned.add(cake);
    System.out.println("You stole the " + cakeName + " succesfully " + name);
  }

  public void buy(Baker store, String cakeName) {
    if (jailed) {
      System.out.println("Your in jail " + name);
      return;
    }
    if (store.getCakeAmount(cakeName) < 1) {
      System.out.println("Out of stock");
      return;
    }
    Cake cake = store.getCakeByName(cakeName);
    if (balance < cake.getCost()) {
      System.out.println("You are too poor to buy, " + name);
      return;
    }
    store.add(cake, -1);
    cakesOwned.add(cake);
    balance -= cake.getCost();
    store.getMoney(cake.getCost());
  }

  public void buyInFundraiser(Baker store, String cakeName, PTSA fundraiser) {
    if (jailed) {
      System.out.println("Your in jail " + name);
      return;
    }
    if (store.getCakeAmount(cakeName) < 1) {
      System.out.println("Out of stock");
      return;
    }
    Cake cake = store.getCakeByName(cakeName);
    if (balance < cake.getCost()) {
      System.out.println("You are too poor to buy, " + name);
      return;
    }
    store.add(cake, -1);
    cakesOwned.add(cake);
    balance -= cake.getCost();
    store.getMoney(cake.getCost());
  }
}
