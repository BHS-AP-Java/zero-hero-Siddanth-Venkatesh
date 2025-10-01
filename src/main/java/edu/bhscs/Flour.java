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
