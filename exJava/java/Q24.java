import java.util.*;

public class Q24 {
  public static void main(String args[]) {
    new Q24Main();
  }
}

class Q24Main {

  public Q24Main() {
    OrderedHashMap omap = new OrderedHashMap();
    omap.put("AAA", 100);
    omap.put("BBB", 200);
    omap.put("CCC", 300);
    omap.put("DDD", 400);
    omap.put("EEE", 500);
    omap.put("DDD", 1000);
    omap.put("DDD", 2000);
    omap.put("XYX", 10000);

    List klist = omap.getKeyList();
    for (int i = 0; i < klist.size(); i++) {
      Object key = klist.get(i);
      System.out.println(key + " = " + omap.get(key));
    }
  }
}


class OrderedHashMap {

  public static final int L = 10;
  HashNode[] heads;
  HashNode ohead, otail; // ordered head and tail

  OrderedHashMap () {
    heads = new HashNode[L];
    ohead = null;
  }

  void put(Object key, Object value){
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
    if (head == null) { // new node 
      node.next = heads[hash];
      heads[hash] = node;
      if (ohead == null) {
        ohead = otail = node;
      } else {
        otail.onext = node;
        otail = node;
      }
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

  List getKeyList() {
    HashNode hn = ohead;
    List<String> klist = new ArrayList<String>();
    while(hn != null) {
      klist.add(hn.key.toString());
      hn = hn.onext;
    }
    return klist;
  }
}

class HashNode {
  HashNode next, 
    onext; // ordered next
  Object key, value;

  public HashNode(Object key, Object value) {
    next = null;
    onext = null;
    this.key = key;
    this.value = value;
  }
}
