// Siddanth Venkatesh
// P2
// Varriables: Replacement is the gateway to abstraction
// 9/12

/*
 * DESCRIPTION: Test to show types in Java INPUT: No inputs required OUTPUT: Shows Strings, ints,
 * and other types EDGE CASE: None
 */

package edu.bhscs;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int[][] board = create_board();
    print_board(board);
    game(board, scanner);

    scanner.close();
  }

  public static void game(int[][] board, Scanner scanner) {
    boolean win = false;
    boolean player_number = true;
    int turns = 0;
    while (!win) {
      String player = player_number ? "X" : "O";
      win = turn(board, player, scanner);
      turns++;
      if (win) {
        System.out.println("YAY " + player + " Wins");
        break;
      }
      if (turns == 9) {
        System.out.println("HAHA TIE");
        break;
      }
      player_number = !player_number;
    }
  }

  public static boolean check_win(int[][] board, int[] place) {
    int a = 0;
    int x = place[0] - 1;
    int y = 3 - place[1];
    for (int i = 0; i < 3; i++) {
      a += board[i][x];
      if (a == 3) {
        return true;
      }
    }
    a = 0;
    for (int i = 0; i < 3; i++) {
      a += board[y][i];
    }
    if (Math.abs(a) == 3) {
      return true;
    }
    a = 0;
    for (int i = 0; i < 3; i++) {
      a += board[i][i];
    }
    if (Math.abs(a) == 3) {
      return true;
    }
    if (Math.abs(a) == 3) {
      return true;
    }
    a = 0;
    for (int i = 0; i < 3; i++) {
      a += board[i][2 - i];
    }
    if (Math.abs(a) == 3) {
      return true;
    }

    return false;
  }

  public static void print_board(int[][] board) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        char C = '-';
        if (board[i][j] != 0) {
          C = board[i][j] == 1 ? 'X' : 'O';
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

  public static boolean turn(int[][] board, String player, Scanner scanner) {
    System.out.println("Place an " + player + " at coordinates x, y");
    int x[] = get_cord(scanner);
    while (board[3 - x[1]][x[0] - 1] != 0) {
      System.out.println("Already has something");
      x = get_cord(scanner);
    }
    board[3 - x[1]][x[0] - 1] = player.charAt(0) == 'X' ? 1 : -1;
    print_board(board);
    return check_win(board, x);
  }

  public static int[] get_cord(Scanner scanner) {
    int[] numbers = new int[2];
    for (int i = 0; i < 2; ++i) {
      System.out.print((i == 1) ? "Y: " : "X: ");
      numbers[i] = scanner.nextInt();
      if (numbers[i] < 1 || numbers[i] > 3) {
        System.out.println("Has to be 1,2, or 3");
        System.out.print((i == 1) ? "Y: " : "X: ");
        numbers[i] = scanner.nextInt();
      }
    }

    System.out.println();
    return numbers;
  }
}
