import java.util.*;
import java.io.*;

public class Q33 {
  public static void main(String args[]) throws Exception {
    PairList<String, Integer> list1 = new PairList<String, Integer>();
    list1.add("key1", 10);
    list1.add("key2", 20);
    list1.add("key3", 30);

    Holder<String, Integer> holder = null;
    holder = list1.get(0);
    holder.showAll();

    holder = list1.get(1);
    holder.showAll();

    holder = list1.get(2);
    holder.showAll();
  }
}

class PairList<E1, E2> {
  List<E1> list1 = new ArrayList<E1>();
  List<E2> list2 = new ArrayList<E2>();

  public void add (E1 e1, E2 e2) {
    list1.add(e1);
    list2.add(e2);
  }

  public Holder<E1, E2> get (int index) {
    return new Holder(list1.get(index), list2.get(index));
  }
}

class Holder<V1, V2> {
  V1 v1;
  V2 v2;

  public Holder(V1 v1, V2 v2) {
    this.v1 = v1;
    this.v2 = v2;
  }

  public void showAll() {
    System.out.println("v1:v2 = " + v1 + ":" + v2);
  }
}

