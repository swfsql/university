import java.util.*;
import java.io.*;

public class Q31 {
  public static void main(String args[]) {
    new Q31Main();
  }
}

class Q31Main {

  public Q31Main() {
    MultiCollection multiCol = new MultiCollection();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String line = reader.readLine();
    while(!line.equals("end")) {
      
      line = reader.readLine();
    }


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
    for(int i = 0; i < list.length(); i++) {
      System.out.println(list.get(i));
    }

    System.out.println("## Map ##");
    for (String key : map.keySet()) {
      System.out.println(key + " : " + map.get(key));
    }
  }
}

