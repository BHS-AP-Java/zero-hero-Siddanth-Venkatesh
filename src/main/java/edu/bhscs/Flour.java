// Siddanth Venkatesh
// P: 2
// The cake project
// 9/19

/*
 * DESCRIPTION: Makes some flour that could be used in the cake
 * INPUT: Requires the name, weight, price, and quality of the flour
 * OUTPUT: This outputs information regarding the flour.
 * EDGE CASE: Flour can have negative quality. This doesn't represent anything.
 */

package edu.bhscs;

public class Flour {
  // Is the weight of the fliur
  double weight;
  // Is the name of the flour
  String name;
  // This is the price of of the flour
  double price;
  // This the quality of the flour
  int quality;

  // Constructer for the flour object.
  public Flour(String name, double weight, double price, int quality) {
    this.name = name;
    this.weight = weight;
    this.price = price;
    this.quality = quality;
  }

  // Returns the quality of the flour
  public int getQuality() {
    return quality;
  }

  // Makes the flour go bad
  public void goBad() {
    quality = 0;
  }
}
