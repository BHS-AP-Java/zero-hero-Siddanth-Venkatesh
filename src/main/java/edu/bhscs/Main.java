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
    Scanner scanner = new Scanner(System.in);

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
    turn(board, "x", scanner);

    scanner.close();

  }

  public static void print_board(char[][] board) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static char[][] create_board() {
    char[][] board = new char[3][3];
    Arrays.stream(board).forEach(row -> Arrays.fill(row, '-'));
    return board;
  }

  public static void turn(char[][] board, String player, Scanner scanner) {
    System.out.println("Place an " + player + " at x y" );
    int x = get_cord("x", scanner);
    int y = get_cord("y", scanner);
    board[3 - y][x-1] = player.charAt(0);
    print_board(board);

  }
  public static int get_cord(String x, Scanner scanner){
    System.out.print(x + " cordinate (1, 2, 3): ");
    int input = scanner.nextInt();
    int[] numbers = new int[3];
    for (int i = 0; i < 3; ++i) {
      numbers[i] = scanner.nextInt();
    }
    // scanner.close();
    return input;
  }
}
