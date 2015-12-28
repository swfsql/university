import java.util.*;

public class Q22 {
  public static void main(String args[]) {
    new Q22Main();   
  }
}

class Q22Main {

  public Q22Main() {
    ReverseList rlist = new ReverseList();

    rlist.add("test1");
    rlist.add("test2");
    rlist.add("test3");
    rlist.add("test4");
    rlist.add("test5");

    String str = null;
    while((str = rlist.get()) != null) {
      System.out.println(str);
    }
  }
}

class ReverseList {
  Node head;

  void add(String str){
    Node node = new Node(str);
    node.next = head;
    head = node;
  }

  String get() {
    if (head == null) return null;
    String str = head.toString();
    head = head.next;
    return str;
  }
}

class Node {
  Node next;
  String str;

  public Node(String str) {
    next = null;
    this.str = str;
  }

  public String toString() {
    return str;
  }
}





