// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: Just a wrapper interface so that things can be offset
 * INPUT: Nothing
 * OUTPUT: Nothing
 * EDGE CASE: Will throw error if some class did not properly implement this
 */

package edu.bhscs;

// NO CONSTRUCTOR (it's an interface)
public interface Offsetable {
  int getWidth();

  // Default centering logic — always clamps to 0 so alignment never goes negative.
  default int getOffset(Offsetable below) {
    if (below == null) return 0;
    return Math.max(0, (below.getWidth() - this.getWidth()) / 2);
  }

  // Each Offsetable thing knows how to draw itself
  // relative to whatever is beneath it.
  public default void draw(Offsetable T) {
    setOffset(getOffset(T));
    T.setOffset(T.getOffset(this));
    this.draw();
    T.draw();
  }

  // Draws something once it already has an offset
  void draw();

  // Sets the offset
  void setOffset(int x);
}
