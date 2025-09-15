// Siddanth Venkatesh
// P2
// Varriables: Replacement is the gateway to abstraction
// 9/12

/*
 * DESCRIPTION: Test to show types in Java
 * INPUT: No inputs required
 * OUTPUT: Shows Strings, ints, and other types
 * EDGE CASE: None
 */

package edu.bhscs;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
  // This is the example of the char constant
  // Because it is global, it is in capitial letters
  public static final char A = 'A';
  public static void main(String[] args) {
    int someNumber = 67;

    System.out.println("67!" + A + someNumber);
    System.out.println(A + "?" + A + someNumber);
    System.out.println(A + A + someNumber);
    System.out.println();
    System.out.println("this is neet");
    System.out.println();
    System.out.println();
    System.out.println("OK I am done");
    char[][] board = create_board();
    print_board(board);
  }
  public static void print_board(char[][] board){
    for(int i = 0; i < 3; i++){
      for(int j = 0; j < 3; j++){
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }
  public static char[][] create_board(){
    char[][] board = new char[3][3];
    Arrays.stream(board).forEach(row -> Arrays.fill(row, '-'));
    return board;
  }
  public static void turn(char[][] board, boolean player){
    System.out.println(player ? "Place an X at": "Place a Y at");
    // Scanner scanner = new Scanner(System.in);
    // do{
    //   String input = scanner.nextLine();
    //  } while (
    //   input.length() == 3,
    //   input.contains(',')

    //  );
  }
}
