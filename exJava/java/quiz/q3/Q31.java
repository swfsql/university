import java.util.*;
import java.io.*;

public class Q31 {
  public static void main(String args[]) throws Exception {
    MultiCollection multiCol = new MultiCollection();

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("input: ");
    String line = reader.readLine(),
      END = "end",
      MAP = "^(.+?),(.+)$";
    while(line != null) {
      if (line.matches(END)) {
        break;
      }
      if (line.matches(MAP)) {
        String[] strs = line.split(",");
        multiCol.addMap(strs[0], strs[1]);  
      } else {
        multiCol.addList(line);
      }
      
      System.out.print("input: ");
      line = reader.readLine();
    }

    multiCol.showAll();
  }
}

class MultiCollection {

  List<String> list = new ArrayList<String>();
  Map<String, String> map = new HashMap<String, String>();

  public void addList(String str) {
    list.add(str);
  }

  public void addMap(String key, String value) {
    map.put(key, value);
  }

  public void showAll() {
    System.out.println("## List ##");
    for(int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }

    System.out.println("## Map ##");
    for (String key : map.keySet()) {
      System.out.println(key + " : " + map.get(key));
    }
  }
}

