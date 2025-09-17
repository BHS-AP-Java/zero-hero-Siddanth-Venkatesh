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

    int[][] board = create_board();
    print_board(board);
    game(board,scanner);

    scanner.close();
  }

  public static void game(int[][] board, Scanner scanner){
    boolean win = false;
    boolean player_number = true;
    while (!win){
      String player = player_number? "X": "O";
      turn(board, player, scanner);
      player_number = !player_number;
    }
  }

  public boolean check_win(int[][] board){
    int[][] check = new int[3][3];
    for (int i = 0; i < 3; i++){
      
    }
    return false;
  }

  public static void print_board(int[][] board) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        char C = '-';
        if (board[i][j] != 0){
          C = board[i][j] == 1 ? 'X': 'O';
        }
        System.out.print(C + " ");
      }
      System.out.println();
    }
  }

  public static int[][] create_board() {
    int[][] board = new int[3][3];
    Arrays.stream(board).forEach(row -> Arrays.fill(row, 0));
    return board;
  }

  public static void turn(int[][] board, String player, Scanner scanner) {
    System.out.println("Place an " + player + " at coordinates x, y");
    int x[] = get_cord(scanner);
    while (board[3 - x[1]][x[0] - 1] != 0){
      System.out.println("Already has something");
      x = get_cord(scanner);
    }
    board[3 - x[1]][x[0] - 1] = player.charAt(0) == 'X' ? 1: -1;
    print_board(board);
  }

  public static int[] get_cord(Scanner scanner) {
    int[] numbers = new int[2];
    for (int i = 0; i < 2; ++i) {
      System.out.print((i == 1) ? "Y: ": "X: ");
      numbers[i] = scanner.nextInt();
      if (numbers[i] < 1 || numbers[i] > 3){
        System.out.println("Has to be 1,2, or 3");
        System.out.print((i == 1) ? "Y: " : "X: ");
        numbers[i] = scanner.nextInt();
      }
    }

    System.out.println();
    return numbers;
  }
}
