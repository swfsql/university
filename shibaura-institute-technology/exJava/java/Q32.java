import java.util.*;
import java.lang.reflect.*;


public class Q32 {
  public static void main(String args[]) throws Exception {
  
  List<Phone> plist = new ArrayList<Phone>();
  plist.add(new IPhone());
  plist.add(new Xperia());
  
  List<Book> blist = new ArrayList<Book>();
  blist.add(new Dictionary());
  blist.add(new Novel());
  
  Shop shop = new Shop();
  shop.bookDisplay(blist);
  System.out.println();
  shop.phoneDisplay(plist);

  System.out.println();
  shop.display(blist);
  System.out.println();
  shop.display(plist);
  }
}


class Shop {

  void bookDisplay(List<Book> blist) {
    for(int i = 0; i < blist.size(); i++) {
      Book bk = blist.get(i);
      System.out.println("Index");
      System.out.println("\t" + bk.getIndex());
      System.out.print("\tTotal page = ");
      System.out.println(bk.getPageNum());
      System.out.print("\tPrice = ");
      System.out.println(bk.getPrice());
    }
  }

  void phoneDisplay(List<Phone> plist) {
    for(int i = 0; i < plist.size(); i++) {
      Phone ph = plist.get(i);
      System.out.print("Name = ");
      System.out.println(ph.getName());
      System.out.print("\tApp Num = ");
      System.out.println(ph.getNumOfApp());
      System.out.print("\tPrice = ");
      System.out.println(ph.getPrice());
    }
  }

   void display(List<?> list) throws Exception {

    for(int i = 0; i < list.size(); i++) {
      Object o = list.get(i);
      if (o instanceof Phone) {
        System.out.print("Kind: phone\n  Name = ");
      } else if (o instanceof Book) {
        System.out.print("Kind: book\n  Index = ");
      }

      String nameOrIndex = "", price = "";
      Method[] methods = o.getClass().getMethods();
      for(int j = 0; j < methods.length; j++) {
        String name = methods[j].getName();
        if (name.matches("getIndex|getName")) {
          nameOrIndex = methods[j].invoke(o).toString();
        } else if (name.matches("getPrice")) {
          price = methods[j].invoke(o).toString();
        }
      }

      System.out.println(nameOrIndex);
      System.out.println("  Price = " + price);
    }
  }
}

class Phone {

  protected String name;
  protected int numOfApp;
  protected int price;

  public String getName() {
    return name;
  }

  public int getNumOfApp() {
    return numOfApp;
  }

  public int getPrice() {
    return price;
  }
}

class IPhone extends Phone {
  IPhone() {
    name = "IPhone";
    price = 50000;
    numOfApp = 5000;
  }
}

class Xperia extends Phone {
  Xperia () {
    name = "Xperia";
    price = 45000;
    numOfApp = 300;
  }
}

class Book {

  protected String index;
  protected int pageNum;
  protected int price;

  public String getIndex() {
    return index;
  }

  public int getPageNum() {
    return pageNum;
  }

  public int getPrice() {
    return price;
  }
}

class Dictionary extends Book {
  Dictionary () {
    index = "a, b, c, d, e";
    pageNum = 360;
    price = 4000;
  }
}

class Novel extends Book {
  Novel () {
    index = "1. Intro, 2. It's Happening";
    pageNum = 280;
    price = 760;
  }
}

