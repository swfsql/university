import java.io.*;

public class Q3 {

  public static void main (String args[]) {
    System.out.print("\nPlease input your name -> "); 
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String line;
    try {
      line = reader.readLine();
      System.out.print("Hello "); 
      if (line.equals("")) {
        System.out.print("World"); 
      } else {
        System.out.print(line); 
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    
  }

}
