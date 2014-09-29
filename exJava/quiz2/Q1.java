import java.io.*;

public class Q1 {
  public static void main (String args[]) {
    new Q21Main(); 
  }
}


class Q21Main {

  public Q21Main () {
    AbstractBank bank = null;

    bank = new MizuhoBank();
    System.out.println(bank.getName());
    System.out.println(bank.getMaxDeposit());
    System.out.println(bank.getFee());
    System.out.println();

    bank = new UFJBank();
    System.out.println(bank.getName());
    System.out.println(bank.getMaxDeposit());
    System.out.println(bank.getFee());
    System.out.println();
  }
}

abstract class AbstractBank {
  public abstract String getName();
  public abstract int getMaxDeposit();
  public abstract int getFee();
}

class MizuhoBank extends AbstractBank {
  public String getName() {
    return "This is Mizuho Bank";
  }
  public int getMaxDeposit() {
    return 100000;
  }
  public int getFee() {
    return 210;
  }
}


class UFJBank extends AbstractBank {
  public String getName() {
    return "UFJ";
  }
  public int getMaxDeposit() {
    return 2000000;
  }
  public int getFee() {
    return 105;
  }
}








