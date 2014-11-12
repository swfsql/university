import java.lang.Thread;

public class Q51 {
  public static void main(String[] args) {
    Thread th1 = new Thread(new InnerThread("Thread1"));
    Thread th2 = new Thread(new InnerThread("Thread2"));
    Thread th3 = new Thread(new InnerThread("Thread3"));

    th1.start();
    th2.start();
    th3.start();
  }
}

class InnerThread implements Runnable {
  private String name;

  InnerThread(String name) {
    this.name = name;
  }

  public void run() {
    try {
      for (int i = 0; i < 10; i++) {
        System.out.println(name + " " + i + " times");
        Thread.sleep(1000);
      }
    } catch(Exception e) {

    }
  }
}


