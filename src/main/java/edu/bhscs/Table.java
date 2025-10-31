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

public class Table implements Offsetable {
  int legs;
  int width;
  final int TABLEHEIGHT = 3;
  final int LEGHEIGHT = 5;

  String tableTopChars = "XY";
  String leg = "AB";

  public Table(int x, int y) {
    legs = x;
    width = y;
  }

  public void drawTop(int layers, int offset) {
    if (layers-- == 0) return;
    drawLine(offset, 1, " ");
    drawLine(width + leg.length(), tableTopChars.length(), tableTopChars);
    System.out.println();
    drawTop(layers, offset);
  }

  public void drawLine(int width, int len, String chars) {
    for (int i = 0; i < width; i++) System.out.print(chars.substring(i % len, i % len + 1));
  }

  public void drawLegs(int layers, int offset) {
    if (layers-- == 0) {
      return;
    }
    drawLine(offset, 1, " ");
    int legSpacing = (legs == 1) ? width + 10 : width / (legs - 1);
    String legWithSpace = leg;
    for (int i = leg.length(); i < legSpacing; i++) {
      legWithSpace += " ";
    }
    for (int i = 0; i <= width; i += legSpacing) {
      drawLine(legSpacing, legWithSpace.length(), legWithSpace);
    }
    System.out.println();
    drawLegs(layers, offset);
  }

  public void draw(int x) {
    width += (legs - 1) - width % (legs - 1);
    int offset = x - (width + leg.length()) / 2;
    drawTop(TABLEHEIGHT, offset);
    drawLegs(LEGHEIGHT, offset);
  }

  public int getLength() {
    return width;
  }
  public void setOffset(){
    return;
  }

  public static void main(String[] args) {
    new Table(7, 70).draw(50);
  }
}
