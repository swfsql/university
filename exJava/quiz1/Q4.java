import java.io.*;

public class Q4 {

  public static void main (String args[]) {
    if (new StrCompare().compare()) {
      System.out.print("\njudge = true"); 
    } else {
      System.out.print("\njudge = false"); 

    }
  }
}

class StrCompare {

  public boolean compare() {

    System.out.print("\nInput First String -> "); 
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String line;
    char line1[], line2[];
    int l1, l2;
    try {
     
      line = reader.readLine();
      line1 = line.toCharArray();
      l1 = line.length();
      
    System.out.print("\nInput Second String -> "); 
      line = reader.readLine();
      line2 = line.toCharArray();
      l2 = line.length();

      int j = 0;
      for (int i = 0; i < l1 - l2 + 1; i++) {
        for (j = 0; j < l2; j++) {
          if (line1[i + j] != line2[j]) break;
        }
        if (j == l2) {
          return true;
        }
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return false;
  }
}
