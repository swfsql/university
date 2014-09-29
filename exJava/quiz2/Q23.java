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
  HashList hlist = new HashList();

  public void addList(Object str) {
    rlist.add(str);
  }

  public void addMap(Object key, Object value) {
    hlist.add(key, value);
  }

  public void showAll() {
    System.out.println("## List ##");
    showReverseList(rlist.head);

    System.out.println("## Map ##");
    for (int i = 0; i < hlist.L; i++) {
      showHashList(hlist.heads[i]);
    }
  }

  // recursive, prints objects from reverseList in FIFO order.
  public void showReverseList(Node n) {
    if (n == null) return;
    showReverseList(n.next);
    System.out.println(n);
  }

  // recursive, print objects value from hashList in FIFO hash order.
  public void showHashList(HashNode n) {
    if (n == null) return;
    System.out.println(n.key + " : " + n.value);
    showHashList(n.next);
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

class HashList {

  public static final int L = 10;
  HashNode[] heads;

  HashList() {
    heads = new HashNode[L];
  }

  void add(Object key, Object value){
    HashNode node = new HashNode(key, value);
    int hash = node.key.hashCode() % L;
    hash = hash < 0 ? -hash : hash;
    HashNode head = heads[hash];
    while(head != null) {
      if (head.key.equals(node.key)) {
        head.value = node.value;
        break;
      } 
      head = head.next;
    }
    if (head == null) {
      node.next = heads[hash];
      heads[hash] = node;
    }
  }

  Object get(Object key) {
    HashNode node = new HashNode(key, null);
    int hash = node.key.hashCode() % L;
    hash = hash < 0 ? -hash : hash;
    HashNode head = heads[hash];
    while(head != null) {
      if (head.key.equals(node.key)) {
        node.value = head.value;
        break;
      } 
      head = head.next;
    }
    return node.value;
  }

}

class HashNode {
  HashNode next;
  Object key, value;

  public HashNode(Object key, Object value) {
    next = null;
    this.key = key;
    this.value = value;
  }
}
