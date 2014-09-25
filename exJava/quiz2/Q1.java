import java.io.*;

public class Q1 {


  public static void main (String args[]) {
    AbstractBank bank = null;

    bank = new MizuhoBank();
    System.out.println(bank.getName());
    System.out.println(bank.getMaxDeposit());
    System.out.println(bank.getFee());
    System.out.println();

    bank = new MizuhoBank();
    System.out.println(bank.getName());
    System.out.println(bank.getMaxDeposit());
    System.out.println(bank.getFee());
    System.out.println();


  }
}



public abstract class AbstractBank {
  public abstract String getName();
  public abstract int getMaxDeposit();
  public abstract int getFee();
}












