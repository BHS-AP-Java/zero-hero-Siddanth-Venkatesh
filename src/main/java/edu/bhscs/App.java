package edu.bhscs;

public class App {
  public static void main(String[] args) {

    Player user = new Player("Usario P. Player");

    String name = user.giveAnswer("What is your name?");
    String age = user.giveAnswer("How old are you?");

    new Cake().draw(name, Integer.parseInt(age));
  }

  public static void date_01() {
    System.out.println("today's work");
  }
}
