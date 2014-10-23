import java.nio.*;
import java.nio.channels.*;
import java.io.*;

public class Q42 {

  public static void main(String args[]) throws Exception {
    BinaryInput bi = new BinaryInput("test.dat");
    int test = 0;

    switch (test) {
      case 0:
        for(int i = 0; i < 26; i++) {
          System.out.println(bi.readChar());
        }
        break;

      case 1:
        for(int i = 0; i < 13; i++) {
          System.out.println(bi.readShort());
        }
        break;

      case 2:
        for(int i = 0; i < 6; i++) {
          System.out.println(bi.readInt());
        }
    }
  }
}

class BinaryInput {
  byte[] b = new byte[4];
  FileInputStream fis;

  BinaryInput(String pathf) throws Exception {
    fis = new FileInputStream(new File(pathf));
  }

  public char readChar() throws Exception {
    fis.read(b, 0, 1);
    return (char) (b[0] << 0);
  }

  public short readShort() throws Exception {
    fis.read(b, 0, 2);
    return (short)((b[0] << 8) | (b[1] << 0));
  }

  public int readInt() throws Exception {
    fis.read(b, 0, 4);
    return (int)((b[0] << 24) | (b[1] << 16) | (b[2] << 8) | (b[3] << 0));
  }

  public void reset() throws Exception {
    fis.reset();
  }
}

