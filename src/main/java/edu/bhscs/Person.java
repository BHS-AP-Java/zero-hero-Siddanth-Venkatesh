// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: Makes a person, who can buy and steal cakes or go to jail
 * INPUT: No inputs required
 * OUTPUT: No output
 * EDGE CASE: None
 */


package edu.bhscs;
import java.util.ArrayList;
import java.util.Random;

public class Person {
  ArrayList<Cake> cakesOwned = new ArrayList<>();
  public boolean fat = false;
  private double balance;
  private boolean jailed = false;
  private double happieness = 0.00;
  private String name;

  public void eat(Cake cake, double percent) {
    cake.weightPounds *= (100 - percent) / 100;
  }
  public void steal(Store store) {
    int random = (int) Math.floor(Math.random() * 2); // Generates 0 or 1
    if (random == 0){
      jailed = true;
      System.out.println(name + " got jailed for trying to steal a cake");
      return;
    }
  }

  public void rob(Store store) {
  }
}
