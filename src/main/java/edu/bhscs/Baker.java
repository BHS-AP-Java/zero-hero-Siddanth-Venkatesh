package edu.bhscs;

public class Baker implements Creatable {
  // PROPERTIES AND FIELDS
  // placeOfWork should be a private field, but since I can't modify
  // that variable, bad practice will exist in the code.
  Player p;
  Flour f;
  Store placeOfWork;
  int cash;

  // Extra fields
  String name;
  double skill;

  // CONSTRUCTOR
  Baker(Player p) {
    this.p = p;
  }

  // Based of na,e
  Baker(String name) {
    this.name = name;
  }

  // METHODS
  void setBakerStore(Store store) {
    placeOfWork = store;
  }

  void takeOrder(int price, Customer c) {
    cash += c.pay(price);
    c.takeCake(bakeCake());
  }

  Cake bakeCake() {
    String answer = this.p.giveAnswer("what cake do you you want?");
    return new Cake(answer, this.f, this.skill);
  }

  void takeJob(Store bakery) {
    String doYouWantToWorkHere = this.p.giveAnswer("Do you want to work at " + bakery.getName());
    if (doYouWantToWorkHere.equals("y")) {
      this.placeOfWork = bakery;
      System.out.println(this.name + " now works at " + bakery.getName());
    }
  }

  void giveName(String name) {
    this.name = name;
  }

  void giveSkill(double skill) {
    this.skill = skill;
  }

  void setCash(int cash) {
    this.cash = cash;
  }

  Cake bakes(int age, String name) {
    return new Cake(age, name);
  }

  int getBalance() {
    return cash;
  }

  // This adds a cake to Stores inventory. If the cake added has the same name as another cake,
  // but different ingredients, it will still get added.
  public void add(Cake cake, int amount) {
    placeOfWork.inventory.put(cake, placeOfWork.inventory.get(cake) + amount);
    if (amount < 0) {
      System.out.println(
          name
              + " has sold "
              + -1 * amount
              + " cake(s) called "
              + cake.getName()
              + " from their store");
      return;
    }
    System.out.println(
        name
            + " has stocked "
            + amount
            + " extra cake(s) called "
            + cake.getName()
            + " to their store");
    skill += amount * 10;
    cake.setQuality(skill);
    System.out.println(
        "The Store's skill has increased to "
            + skill
            + " meaning it bakes cakes with better quality");
    System.out.println("-----------------------------");
  }

  // This adds a specific amount of cakes with a certain name into the Store's inventory.
  public void add(String cakeName, int amount) {
    Cake cake = placeOfWork.getCakeByName(cakeName);
    if (cake == null) {
      System.out.println(placeOfWork.getName() + " doesn't have cakes with that name ");
      return;
    }
    add(cake, amount);
  }

  public void addNewCake(Cake cake, int amount) {
    if (this.placeOfWork.getCakeAmount(cake.getName()) != 0) {
      System.out.println("This store already sells this cake");
      return;
    }
    this.placeOfWork.inventory.put(cake, amount);
  }

  // Creatable Methods

  // Returns the name of the type
  @Override
  public String getTypeName() {
    return "Baker";
  }

  // Returns the name of the type
  @Override
  public String toString() {
    return "Baker called " + name;
  }
}
