import java.util.*;
import java.io.*;

public class Q35 {
  public static void main(String args[]) throws Exception {

    TinyList<String> list = new TinyList<String>();

    list.add("+1");
    list.add("+2");
    list.add("+3");
    list.add("+4");
    list.add("+5");
    list.add("+6");
    list.add("+7");
    list.add("+8");
    list.add("+9");
    list.add("+10");

    System.out.println(list.get(-1)); // null

    list.add("+11");
    System.out.println(list.get(9)); // +10
    System.out.println(list.get(10)); // null
    
    System.out.println(list.remove()); // +10
    System.out.println(list.get(9)); // null
    list.add("+12");
    System.out.println(list.get(9)); // +12
  }
}

class TinyList<E> {

  Object os[] = new Object[10];
  int last = -1;

  public E add (E e) {
    if (last >= 10 - 1) { // when full
      return null;
    }

    last++;
    os[last]= e;
    return e;
  }
  
  public E get (int index) {
    return index < 0 || index > 9 || index > last ? null : (E) os[index];
  }

  public E remove() {
    if (last < 0) {
      return null;
    }

    E e = (E) os[last];
    os[last] = null;
    last--;
    return e;
  }
}

