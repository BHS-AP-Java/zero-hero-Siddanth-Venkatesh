// Siddanth Venkatesh
// P: 2
// The cake project
// 9/19

/*
 * DESCRIPTION: Makes a Table
 * INPUT: Needs Width and legs of the table
 * OUTPUT: Can draw the table with legs. Will know to center itself
 * EDGE CASE: If the tables width can't evenly put legs, the width of the table is made wider till it can.
 */

package edu.bhscs;

public class Table implements Offsetable {
  int legs;
  int width;
  final int TABLEHEIGHT = 3;
  final int LEGHEIGHT = 5;

  String tableTopChars = "----";
  String leg = "AB";
  int offset;

  public Table(int x, int y) {
    legs = x;
    width = y;
  }

  public void drawTop(int layers, int offset) {
    if (layers-- == 0) return;
    drawLine(offset, 1, " ");
    drawLine(width, tableTopChars.length(), tableTopChars);
    System.out.println();
    drawTop(layers, offset);
  }

  // Draws a line made of String chars, where width is how long it is, and len is the lenght of the
  // chars
  public void drawLine(int width, int lengthOfChars, String chars) {
    for (int i = 0; i < width; i++) {
      System.out.print(chars.substring(i % lengthOfChars, i % lengthOfChars + 1));
    }
  }

  public void drawLegs(int layers, int offset) {
    if (layers-- == 0) {
      return;
    }
    drawLine(offset, 1, " ");
    int legSpacing = (legs == 1) ? width + 10 : (width - leg.length()) / (legs - 1);
    String legWithSpace = leg;
    for (int i = leg.length(); i < legSpacing; i++) {
      legWithSpace += " ";
    }
    for (int i = 0; i <= width; i += legSpacing) {
      if (i == width) {
        drawLine(leg.length(), leg.length(), leg);
        break;
      }
      drawLine(legSpacing, legWithSpace.length(), legWithSpace);
    }
    System.out.println();
    drawLegs(layers, offset);
  }

  // draws centered around x
  public void draw(int x) {
    width = getWidth();
    setOffset(x);
    drawTop(TABLEHEIGHT, offset);
    drawLegs(LEGHEIGHT, offset);
  }

  // Draws with whatever cenntering their is
  public void draw() {
    draw(offset);
  }

  public void draw(Offsetable thing) {
    draw(0);
  }

  public int getWidth() {
    if (legs == 1) {
      return width;
    }
    return width + ((legs - 1) - width % (legs - 1)) + leg.length() + 10;
  }

  public void setOffset(int x) {
    offset = x;
  }

  public void setLeg(String leg) {
    this.leg = leg;
  }

  public static void main(String[] args) {
    new Table(3, 5).draw(0);
  }
}
