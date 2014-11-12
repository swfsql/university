import java.io.*;

public class Q5 {
  public static void main (String args[]) {
    new Feature();
  }
}

class Feature {
  Animal animal;

  public Feature() {
    String line;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.print("\ninput: "); 
      line = reader.readLine();
      animal = new Creator().create(line);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    check();
  }

  public void check() {
    if (animal == null) {
      System.out.println("Error"); 
    } else {
      System.out.print("Kind: "); 
      System.out.println(animal.getKind()); 
      System.out.print("Move: "); 
      System.out.println(animal.getMove()); 
      System.out.print("Child: "); 
      System.out.println(animal.getHowChild()); 
      System.out.print("Die: "); 
      System.out.println(animal.willDie()); 
    }
  }
}

class Creator {
  public Animal create(String line) {
    if (line.equals("inu")) {
      return new Dog();
    } else if (line.equals("bird")) {
      return new Bird();
    } else if (line.equals("human")) {
      return new Human();
    } else {
      return null; 
    }
  } 
}

abstract class Animal {
 public abstract String getKind();
 public abstract String getMove();
 public abstract Boolean willDie();
 public abstract String getHowChild();
}

class Mammal extends Animal {
  public String getKind() {
    return "Animal";
  }
  public String getMove() {
    return "Move on Foot";
  }
  public Boolean willDie() {
    return true;
  }
  public String getHowChild() {
    return "as Child";
  }
}

class Human extends Mammal {
  public String getKind() {
    return "Human";
  }
  public String getMove() {
    return "Two legs walking";
  }
}

class Dog extends Mammal {
  public String getKind() {
    return "Dog";
  }
  public String getMove() {
    return "Four legs walking";
  }
}

class Bird extends Animal {
  public String getKind() {
    return "Bird";
  }
  public String getMove() {
    return "Fly";
  }
  public Boolean willDie() {
    return true;
  }
  public String getHowChild() {
    return "as Egg";
  }
}
