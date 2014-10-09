import java.nio.*;
import java.nio.channels.*;
import java.io.*;

public class Q42 {

  public static void main(String args[]) throws Exception {
    BinaryInput bi = new BinaryInput("test.dat");
    
    for(int i = 0; i < 26; i++) {
      System.out.println(bi.readChar());
    }
    bi.reset();
    
    for(int i = 0; i < 13; i++) {
      System.out.println(bi.readShort());
    }
    bi.reset();
    
    for(int i = 0; i < 6; i++) {
      System.out.println(bi.readInt());
    }
    bi.reset();
  }
}



class BinaryInput {

  byte[] b = new byte[4];

  FileInputStream fis;

  BinaryInput(String pathf) throws Exception {
    fis = new FileInputStream(new File(pathf));
  }

  public char readChar() {
    fis.read(b, 0, 1);

    return (char) b[0];
  }

  public short readShort() {
    fis.read(b, 0, 2);
    return 0;
  }

  public int readInt() {
    fis.read(b, 0, 4);
    return 0;
  }

  public void reset() {
    fis.reset();
  }


}

