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
  double weight;
  String name;
  int price;
  int quality;

  public Flour(String name, double weight, int price, int quality) {
    this.name = name;
    this.weight = weight;
    this.price = price;
    this.quality = quality;
  }

  public int getQuality() {
    return quality;
  }

  public void goBad() {
    quality = 0;
  }
}
