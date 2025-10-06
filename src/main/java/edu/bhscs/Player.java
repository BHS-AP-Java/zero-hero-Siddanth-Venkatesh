package edu.bhscs;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Player {
  // Properties
  // String name
  private String name;
  // Scanner s scans user input
  private Scanner s;
  // Array List of options Player can do
  private final List<Option> options = new ArrayList<>();

  // Options is the set of things the Player can do

  public Player(String name) {
    this.name = name;
    this.s = new Scanner(System.in);

    // This options the Player has
    options.add(new Option("Make a Customer", (String name, String weight, double wealth, String race) -> new Customer(name, weight, wealth, race)) );
  }

  public String askQuestion(String question) {
    System.out.println(question);
    System.out.print("> ");
    return s.nextLine().trim();
  }

  public void Options() {}

  private record Option(String label, Runnable action) {}
}
