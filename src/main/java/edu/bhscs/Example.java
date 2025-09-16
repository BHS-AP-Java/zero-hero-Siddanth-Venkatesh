package edu.bhscs;

import java.util.Scanner;

class Example {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int[] numbers = new int[4];
    for (int i = 0; i < 4; ++i) {
      numbers[i] = sc.nextInt();
    }
  }
}
