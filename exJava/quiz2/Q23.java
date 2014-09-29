import java.util.*;

public class Q23 {
  public static void main(String args[]) {
    new Q23Main();
  }
}

class Q23Main {

  public Q23Main() {
    MultiCollection multiCol = new MultiCollection();

    multiCol.addList("AAA");
    multiCol.addList("BBB");
    multiCol.addList("CCC");

    multiCol.addMap("X", 100);
    multiCol.addMap("Y", 200);
    multiCol.addMap("Z", 300);
    
    multiCol.showAll();
  }
}


class MultiCollection {

  ReverseList rlist = new ReverseList();

  public void addList(Object str) {
    rlist.add(str);
  }

  public void addMap(Object key, Object value) {


  }

  public void showAll() {
    System.out.println("## List ##");
    showList(rlist.head);

    System.out.println("## Map ##");

  }

  public void showList(Node n) {
    if (n == null) return;
    showList(n.next);
    System.out.println(n);
  }
}


class ReverseList {
  Node head;

  void add(Object obj){
    Node node = new Node(obj);
    node.next = head;
    head = node;
  }

  Object get() {
    if (head == null) return null;
    Object obj = head;
    head = head.next;
    return obj;
  }
}

class Node {
  Node next;
  Object obj;

  public Node(Object obj) {
    next = null;
    this.obj = obj;
  }

  public String toString() {
    return String.valueOf(obj);
  }
}

