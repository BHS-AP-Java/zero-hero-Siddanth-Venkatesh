// Siddanth Venkatesh
// P: 2
// The cake project
// 9/19

/*
 * DESCRIPTION: Allows you to make people and a baker where people can buy or steal from the baker. Additionally,
 * there is a fundraiser going on by a PTSA that is looking to make money through donations and by people buying cakes for the fundraiser.
 * All classes will be controlled through the Player class, which is a wrapper for a scanner.
 * INPUT: Requires the people, the store, and the PTSA to all be made in the main function. Then actions can be written in main
 * such as buying, selling, stealing, and donating.
 * OUTPUT: Infomration regarding the updated status of the PTSA, or Bakers, or Customers.
 * EDGE CASE: Don't name two different cakes the same thing. Don't give two cakes the same two ingredients. Don't make
 * the PTSA have to buy two of the sames things. All of these will result in doubled up behavior, where the actual thing that gets chosen
 * would be random. For example, if two cakes were named "Vanilla", then buying a cake named "Vanilla" would randomly pick one of them
 */

package edu.bhscs;

public class Main {

  public static void main(String[] args) {
    // Player player = new Player("Sigma");
    // player.setOptions("Sigma");
    // player.showOptions();
    // String age = player.giveAnswer("Age? ");
    // String name = player.giveAnswer("Name? ");

    Cake cake = new Cake();
    // cake.draw(name, age);
    date10_27();
  }

  public static void date10_27() {
    Baker bob = new Baker("Bob");
    Table t = new Table(3, 15);
    Cake bDay = bob.bakes(5, "Suzzie");
    bDay.draw(t);
  }

  // Set for debugging PTSA class
  // // Set up the fundraiser. It will be a fundraiser for a computer, sports maintenance, and some
  // new
  // // floors
  // public static PTSA PTSAthing() {
  //   Map<String, Double> things = new HashMap<>();
  //   things.put("a computer", 100.00);
  //   things.put("some Sports maintenance", 10.000);
  //   things.put("some new floors", 50.00);
  //   PTSA BothellPTSA = new PTSA("Bothell", things, 10, 90);
  //   BothellPTSA.add("Chairs", 50);
  //   BothellPTSA.displayGoal();
  //   return BothellPTSA;
  // }
}
