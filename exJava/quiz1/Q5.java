import java.io.*;

public class Q5 {
  public static void main (String args[]) {
  }
}

class Feature {
  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  String line;
  try {
    System.out.print("input: "); 
    line = reader.readLine()
      if (line.equals("inu")) {
// todo        
      } else 
      if (line.equals("bird")) {

      } else 
      if (line.equals()) {

      } else 
      if (line.equals()) {

      } else 
      
  } catch (IOException e) {
    System.err.println(e.getMessage());
  }
}

class Creator {
// todo

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
