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
  int getLength(); // The length of the object
  void setOffset();
}
