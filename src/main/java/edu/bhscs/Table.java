// Siddanth Venkatesh
// P: 2
// The cake project
// 9/19

/*
 * DESCRIPTION: Makes a Table
 * INPUT: Needs Width and legs of the table
 * OUTPUT: Can draw the table with legs
 * EDGE CASE: If the tables width can't evenly put legs, the width of the table is made wider till it can.
 */







package edu.bhscs;

public class Table {
  int legs;
  int width;
  final int TABLEHEIGHT = 3;
  final int LEGHEIGHT = 5;

  String tableTopChars = "XY";
  String leg = "ABC";

  public Table(int x, int y) {
    legs = x;
    width = y;
  }

  public void drawTop(int layers) {
    if (layers == 0) {
      return;
    }
    int length = tableTopChars.length();
    drawLine(width + leg.length(), length, tableTopChars);
    System.out.println();
    drawTop(layers - 1);
  }

  public void drawLine(int width, int lengthOfChars, String chars) {
    for (int i = 0; i < width; i++) {
      int moddedI = i % lengthOfChars;
      String c = chars.substring(moddedI, moddedI + 1);
      System.out.print(c);
    }
  }

  public void drawLegs(int layers) {
    if (layers == 0) {
      return;
    }
    int legSpacing = legs == 1 ? width + 10 : width / (legs - 1);
    String legWithSpace = leg;
    for (int i = leg.length(); i < legSpacing; i++){
      legWithSpace += " ";
    }
    for (int i = 0; i <= width; i += legSpacing) {
      drawLine(legSpacing, legWithSpace.length(), legWithSpace);
    }
    System.out.println();
    drawLegs(layers - 1);
  }

  public void draw() {
    width += (legs - 1) - width % (legs - 1);
    drawTop(TABLEHEIGHT);
    drawLegs(LEGHEIGHT);
  }

  public static void main(String[] args) {
    new Table(7, 100).draw();
  }
}
