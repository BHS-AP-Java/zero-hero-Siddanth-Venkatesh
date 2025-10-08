// Siddanth Venkatesh
// P: 2
// The cake project
// 9/19

/*
 * DESCRIPTION: Makes a customer, who can buy and steal cakes or go to jail.
 * The Customer can donate to the PTSA
 * INPUT: Requires the name, weight, and wealth
 * OUTPUT: Either returns a customer, or that a customer can't do something because they are either in jail, or don't have enough money
 * EDGE CASE: A jailed customer is restricted in their activities. Customer randomly eats
 * one of two cakes if they are named the same. If the race of the customer
 * is not American, they are very limited in the activities they can do.
 */

package edu.bhscs;

import java.util.ArrayList;

public class Customer implements Creatable {
  // PROPERTIES AND FIELDS
  // cakesOwned is an ArrayList of cakes the customer bought or stolen
  private ArrayList<Cake> cakesOwned = new ArrayList<>();
  // weight is the weight of the customer
  private double weight = 0;
  // balance is the amount of money the customer owns (in dollars)
  private double balance = 0;
  // jailed is a boolean telling if a customer is in jail or not
  private boolean jailed = false;
  // name is the name of the customer
  private String name;
  // Is the race of Customer. Be careful if you aren't American.
  private String race;

  // This is the constroctor for a customer. It takes in their name, weight, and wealth and makes a
  // customer.
  public Customer(String name, double weight, double wealth, String race) {
    this.name = name;
    this.weight = weight;
    this.balance = wealth;
    this.race = race;
  }

  // Creatable Methods
  // Returns the name of the type
  @Override
  public String getTypeName() {
    return "Customer";
  }

  // Returns the name of the type
  @Override
  public String toString() {
    return name;
  }

  // The customer can donate some amount of money assuming they have enough balance to a PTSA.
  public void donate(PTSA fundraiser, double amount) {
    System.out.println("----------------------------");
    donateNoMSG(fundraiser, amount);
    System.out.println(
        "Thank you " + name + " for donating " + amount + " to us at " + fundraiser.getName());
    if (jailed) {
      System.out.println(
          "Even though you are in jail "
              + name
              + ", you chose to donate. Even criminals can have hearts it seems");
    }
    System.out.println("----------------------------");
  }

  // Returns an Array List of the Cakes owned
  ArrayList<Cake> getCakesOwned() {
    return cakesOwned;
  }

  // Same thing as the donate above, except there is no message in a succesful donation
  public void donateNoMSG(PTSA fundraiser, double amount) {
    if (balance < amount) {
      System.out.println("Can't donate to " + fundraiser.getName() + " because you are poor");
      return;
    }
    fundraiser.donate(amount);
    balance -= amount;
  }

  // If a customer is in jail, they can try their luck and try to escape. They have a 50% chance.
  public void escape() {
    System.out.println("----------------------------");
    if (!jailed) {
      System.out.println("Why are you trying to escape when not jailed, " + name + "?");
      return;
    }
    if (race != "American") {
      System.out.println("You can't escape from ICE agents " + name);
      System.out.println("------------------------------------");
      return;
    }
    int random = (int) Math.floor(Math.random() * 2);
    if (random == 1) {
      System.out.println("Escape didn't work out");
      return;
    }
    System.out.println("Succesful escape from jail, " + name);
    jailed = false;
    System.out.println("----------------------------");
  }

  // This takes in a name of cake, and filters through the list of cakes the customer
  // has and finds the cake with that name
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
    System.out.println("----------------------------");
    if (jailed) {
      System.out.println("You can't eat cake while in jail, " + name);
      return;
    }
    if (!cakesOwned.contains(cake)) {
      System.out.println("Can't eat a cake you don't have, " + name);
      return;
    }
    if (percent > 100) {
      System.out.println("Can't eat more than a 100% of the cake, " + name);
      return;
    }
    if (percent < 0) {
      System.out.println("Can't eat a negative amount of cake, " + name);
      return;
    }
    weight += cake.getWeight() * percent / 100;
    cake.eat(percent);
    System.out.println("Do you feel good after eating the " + cake.getName() + " " + name + "?");
    System.out.println("You now are " + weight + "lb " + name + " after eating cake");
    if (cake.getQuality() > 100) {
      System.out.println("That was a pretty high quality cake, so maybe it was worth it");
    }
    System.out.println("----------------------------");
  }

  // Gives a 50% chance to steal a cake from a baker with a certain name. If failed,
  // the customer goes to jail.
  public void steal(Baker baker, String cakeName) {
    int random = (int) Math.floor(Math.random() * 2); // Generates 0 or 1
    System.out.println("----------------------------");
    if (jailed) {
      System.out.println("Your in jail " + name);
      return;
    }
    if (race != "American") {
      System.out.println("Uh oh, ICE agents might get you " + name);
    }
    if (random == 0) {
      jailed = true;
      System.out.println(name + " got jailed for trying to steal a cake");
      return;
    }
    if (baker.placeOfWork.getCakeAmount(cakeName) < 1) {
      System.out.println("Can't steal something out of stock of " + name);
      System.out.println("Wait for the " + cakeName + " to restock");
      return;
    }
    Cake cake = baker.placeOfWork.getCakeByName(cakeName);
    baker.add(cake, -1);
    cakesOwned.add(cake);
    System.out.println("You stole the " + cakeName + " succesfully " + name);
    System.out.println("----------------------------");
  }

  public int pay(int amount) {
    balance -= amount;
    return amount;
  }

  public void takeCake(Cake cake) {
    cakesOwned.add(cake);
  }

  // Allows a costumer to buy a cake from a Baker with a specfic name. The customer must have
  // enough money to buy the cake and not be in jail. They lose money after buying the cake.
  public void buy(Baker baker, String cakeName) {
    if (jailed) {
      System.out.println("Your in jail " + name);
      return;
    }
    if (race != "American") {
      System.out.println("Uh oh, ICE agents got you " + name + ". You are deported");
      System.out.println("-------------------------------------");
      return;
    }
    if (baker.placeOfWork.getCakeAmount(cakeName) < 1) {
      System.out.println(cakeName + " is out of stock");
      System.out.println(name + " please wait for the baker to restock");
      return;
    }
    Cake cake = baker.placeOfWork.getCakeByName(cakeName);
    Cake newCake = new Cake(cake);
    if (balance < cake.getCost()) {
      System.out.println("You are too poor to buy, " + name);
      return;
    }
    System.out.println(name + " bought a " + cakeName);
    baker.add(cake, -1);
    cakesOwned.add(newCake);
    balance -= cake.getCost();
    baker.placeOfWork.getMoney(cake.getCost());
  }

  // This buys a cake directly from the Baker. Since the baker can't use store ingredients
  // it will just be a default cake with a custom name
  public void buyCustom(Baker b, int price) {
    b.takeOrder(price, this);
  }

  // Makes a customer only buy a cake if it meets their quality expectation.
  public void buyQuality(Baker baker, String cakeName, double quality) {
    if (jailed) {
      System.out.println("Your in jail " + name);
      return;
    }
    if (race != "American") {
      System.out.println("Uh oh, ICE agents got you " + name + ". You are deported");
      System.out.println("-------------------------------------");
      return;
    }
    Cake cake = baker.placeOfWork.getCakeByName(cakeName);
    if (cake.getQuality() < quality) {
      System.out.println("The cake " + cakeName + " is too yucky for " + name);
      System.out.println("The quality of the cake needs to go up to atleast " + quality);
      System.out.println("------------------------------------------");
      return;
    }
    buy(baker, cakeName);
  }

  // The customer can buy a cake in a fundraiser with a specific name if they
  // are not in jail and have enough money. A percentage of how much the customer paied
  // will go towards the fundraiser. The baker has no say in how much they lose.
  public void buyInFundraiser(Baker baker, String cakeName, PTSA fundraiser) {
    if (jailed) {
      System.out.println("Your in jail " + name);
      return;
    }
    if (race != "American") {
      System.out.println("Uh oh, ICE agents got you " + name + ". You are deported");
      System.out.println("-------------------------------------");
    }
    if (baker.placeOfWork.getCakeAmount(cakeName) < 1) {
      System.out.println("Out of stock of " + cakeName);
      System.out.println("Please wait for the cake to be restocked, " + name);
      return;
    }
    Cake cake = baker.placeOfWork.getCakeByName(cakeName);
    if (balance < cake.getCost()) {
      System.out.println("You are too poor to buy, " + name);
      return;
    }
    System.out.println("------------------------------------------");
    baker.add(cake, -1);
    cakesOwned.add(cake);
    balance -= cake.getCost();
    double cutDeductedProfit = cake.getCost() * (100 - fundraiser.getCut()) / 100;
    baker.placeOfWork.getMoney(cutDeductedProfit);
    System.out.println(
        "Thank you Bob for participating in the " + fundraiser.getName() + " fundraiser");
    System.out.println("Enjoy the " + cakeName + " that you bought, " + name);
    double amount = (fundraiser.getCut() / 100) * cake.getCost();
    System.out.println(
        "We recieved $" + amount + " from " + name + " because of the cake they bought.");
    donateNoMSG(fundraiser, amount);
    System.out.println("-----------------------------------------");
  }

  // Makes the customer only buy a cake in a fundraiser if it meets their quality expectation.
  public void buyInFundraiserGoodQuality(
      Baker baker, String cakeName, PTSA fundraiser, double quality) {
    if (jailed) {
      System.out.println("Your in jail " + name);
      return;
    }
    if (race != "American") {
      System.out.println("Uh oh, ICE agents got you " + name + ". You are deported");
      System.out.println("-------------------------------------");
    }
    Cake cake = baker.placeOfWork.getCakeByName(cakeName);
    if (cake.getQuality() < quality) {
      System.out.println("The cake " + cakeName + " is too yucky for " + name);
      System.out.println("The quality of the cake needs to go up to atleast " + quality);
      System.out.println("---------------------------------------");
      return;
    }
    buyInFundraiser(baker, cakeName, fundraiser);
  }
}
