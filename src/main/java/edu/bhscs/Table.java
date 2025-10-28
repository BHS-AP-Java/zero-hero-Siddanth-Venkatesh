package edu.bhscs;

public class Table {
  int legs;
  int width;
  final int tableHeight = 3;
  final int legHeight = 5;

  String tableTopChars = "XY";
  String leg = "AB";

  public Table(int x, int y) {
    legs = x;
    width = y;
  }

  public void drawTop(int layers) {
    if (layers == 0){
      return;
    }
    int length = tableTopChars.length();
    drawLine(width, length, tableTopChars);
    System.out.println();
    drawTop(layers - 1);
  }

  public void drawLine(int width, int lengthOfChars, String chars){
    for (int i = 0; i < width; i++) {
      int moddedI = i % lengthOfChars;
      String c = chars.substring(moddedI, moddedI + 1);
      System.out.print(c);
    }
  }
  public void drawLegs(int layers){
    if (layers == 0){
      return;
    }
    for (int i = 0; i < width; i++){

    }
  }


  public void draw(){
    drawTop(tableHeight);
  }

  public static void main(String[] args){
    new Table(1 ,100 ).draw();
  }
}
