import java.util.*;

public class Q25 {
  public static void main(String args[]) {
    new Q25Main();
  }
}

class Q25Main {

  public Q25Main() {

    MyIterator iter = new MyIterator();

    for(int i = 0; i < 10; i++) {
      iter.add(i);
    }

    iter.remove();
    iter.add(30);

    iter = iter.iterator();
    while(iter.hasNext()) {
      System.out.println(iter.next());
    }

  }
}

// as linked list
class MyIterator implements Iterator {

  private LinkedList ll;
  private Node node;

  MyIterator() {
    ll = new LinkedList();
    node = ll.getHead();
  }
  MyIterator(MyIterator oll) {
    ll = oll.ll;
    node = ll.getHead();
  }

  public boolean hasNext() {
    return node != null && node.next != null;
  }

  public Object next() {
    if(node != null) {
      node = node.next;
      return node.value;
    }
    return null;
  }

  public void add(Object value) {
    ll.add(value);
  }

  public void remove() {
    ll.remove();
  }

  MyIterator iterator() {
    return new MyIterator(this);
  }

}



class LinkedList {

  private Node head; 

  LinkedList() {
    head = new Node(); // always empty
  }

  public void add(Object value) {
    Node node = head;
    while(node.next != null) {
      node = node.next;
    }
    node.next = new Node(value);
  }

  public void remove() {
    Node node = head;
    while(node.next != null && node.next.next != null) {
      node = node.next;
    }
    node.next = null;
  }

  public Node getHead() {
    return head;
  }

}

class Node {
  Node next; 
  Object value;

  Node(){
  }
  Node(Object value) {
    this.value = value;
  }

  public String toString() {
    return value.toString();
  }
}
