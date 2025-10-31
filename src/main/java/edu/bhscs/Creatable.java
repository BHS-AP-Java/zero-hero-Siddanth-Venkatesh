// Siddanth Venkatesh
// P2
// Cake
// 9/19

/*
 * DESCRIPTION: Just a wrapper class so that all creatable objects can be refered to.
 * INPUT: Nothing
 * OUTPUT: Nothing
 * EDGE CASE: Will throw error of some class did not properly implement this
 */

package edu.bhscs;

// CONSTRUCTOR (NA, it's an interface)
public interface Creatable {
  String getTypeName(); // The type of the object
}
