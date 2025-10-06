// Siddanth Venkatesh
// P: 2
// The cake project
// 9/19

/*
 * DESCRIPTION: Allows you to make people and a baker where people can buy or steal from the baker. Additionally,
 * their is a fundraiser going on by a PTSA that is looking to make money through donations and by people buying cakes for the fundraiser.
 * INPUT: Requires the people, the store, and the PTSA to all be made in the main function. Then actions can be written in main
 * such as buying, selling, stealing, and donating. No command line arguments.
 * OUTPUT: Shows the prices and wealth of the store, how succesful the fundraiser was, and how the person changes from buying a cake.
 * EDGE CASE: Don't name two different cakes the same thing. Don't give two cakes the same two ingredients. Don't make
 * the PTSA have to buy two of the sames things. All of these will result in doubled up behavior, where the actual thing that gets chosen
 * would be random. For example, if two cakes were named "Vanilla", then buying a cake named "Vanilla" would randomly pick one of them
 */

package edu.bhscs;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    // String bakerName = "Mr. Reiber";
    // Baker baker = new Baker(bakerName, 100.0);
    // baker.displayStock();
    // Customer Bob = new Customer("Bob", 100, 1000, "American");
    // Bob.buy(baker, "Chocolate Cake");
    // Bob.buy(baker, "Chocolate Cake");
    // Bob.buy(baker, "Chocolate Cake");
    // Bob.eat(Bob.getCake("Chocolate Cake"), 50);
    // Bob.steal(baker, "Coffee Cake");
    // baker.add("Chocolate Cake", 2);

    // PTSA Bothell = PTSAthing();
    // Bob.buyInFundraiser(baker, "Coffee Cake", Bothell);
    // baker.displayStock();
    // Bob.donate(Bothell, 300);
    // Bothell.displayGoal();
    // Bob.buyInFundraiserGoodQuality(baker, "Vanilla Cake", Bothell, 100);
    // Scanner s = new Scanner(System.in);
    // System.out.println("What can we do with a Scanner?");

    // System.out.println("IDK but I am done now");
    // s.close();
    Scanner s = new Scanner(System.in);
    Player player = new Player("Sigma");
    player.showOptions(s);
  }

  // Set up the fundraiser. It will be a fundraiser for a computer, sports maintenance, and some new
  // floors
  public static PTSA PTSAthing() {
    Map<String, Double> things = new HashMap<>();
    things.put("a computer", 100.00);
    things.put("some Sports maintenance", 10.000);
    things.put("some new floors", 50.00);
    PTSA BothellPTSA = new PTSA("Bothell", things, 10, 90);
    BothellPTSA.add("Chairs", 50);
    BothellPTSA.displayGoal();
    return BothellPTSA;
  }
}
