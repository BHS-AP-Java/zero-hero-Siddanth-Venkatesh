// Siddanth Venkatesh
// P2
// Part of Cake project
// 9/19

/*
 * DESCRIPTION: Makes a person, who can buy and steal cakes or go to jail. If jailed, the person can't
 * do anything
 * INPUT: Requires the name, weight, and wealth
 * OUTPUT: No output
 * EDGE CASE: A jailed person is restricted in their activities
 */

package edu.bhscs;

import java.util.ArrayList;

public class Customer {
  private ArrayList<Cake> cakesOwned = new ArrayList<>();
  private double weight = 0;
  private double balance = 0;
  private boolean jailed = false;
  private String name;

  public Customer(String name, double weight, double wealth) {
    this.name = name;
    this.weight = weight;
    this.balance = wealth;
  }

  public void escape() {
    if (!jailed) {
      System.out.println("Why are you trying to escape when not jailed, " + name + "?");
      return;
    }
    int random = (int) Math.floor(Math.random() * 2);
    if (random == 1) {
      System.out.println("Escape didn't wrk out");
      return;
    }
    System.out.println("Succesful escape from jail, " + name);
    jailed = false;
  }

  public Cake getCake(String name) {
    return cakesOwned.stream()
        .filter(cake -> cake.getName().equals(name))
        .findFirst()
        .orElse(null); // exception
  }

  public void eat(Cake cake, double percent) {
    if (jailed) {
      System.out.println("You can't eat cake while in jail, " + name);
      return;
    }
    if (!cakesOwned.contains(cake)) {
      System.out.println("Can't eat a cake you don't have");
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

  public void buyInFundraiser(Baker store, String cakeName) {
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
