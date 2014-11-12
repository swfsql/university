import java.nio.*;
import java.nio.channels.*;
import java.io.*;

public class Q43 {

  public static void main(String args[]) throws Exception {

    FileOutputStream out = new FileOutputStream("output.dat");
    TinyDataOutputStream tout = new TinyDataOutputStream(out);
    tout.writeChar('a');
    tout.writeInt(10);
    tout.writeChar('b');
    tout.writeInt(20);
    tout.writeChar('c');
    tout.writeInt(30);
    tout.close();

    FileInputStream in = new FileInputStream("output.dat");
    TinyDataInputStream tin = new TinyDataInputStream(in);
    System.out.println(tin.readChar());
    System.out.println(tin.readInt());
    System.out.println(tin.readChar());
    System.out.println(tin.readInt());
    System.out.println(tin.readChar());
    System.out.println(tin.readInt());
    tin.close();
  }
}

class TinyDataOutputStream {
  FileOutputStream fos;
  byte[] b = new byte[4];

  TinyDataOutputStream (FileOutputStream fos) {
    this.fos = fos;
  }

  public void writeInt(int i) throws Exception {
    for(int j = 0; j < 4; j++) {
      b[j] = (byte)(i & (0xFF << (j * 8)));
    }
    fos.write(b, 0, 4);
  }

  public void writeChar(char c) throws Exception {
    for(int j = 0; j < 2; j++) {
      b[j] = (byte)(c & (0xFF << (j * 8)));
    }
    fos.write(b, 0, 2);
  }

  public void close() throws Exception {
    fos.close();
  }
}

class TinyDataInputStream {
  FileInputStream fis;
  byte[] b = new byte[4];

  TinyDataInputStream (FileInputStream fis) {
    this.fis = fis;
  }

  public int readInt() throws Exception {
    fis.read(b, 0, 4);
    return (int)((b[3] << 24) | (b[2] << 16) | (b[1] << 8) | (b[0] << 0));
  }

  public char readChar() throws Exception {
    fis.read(b, 0, 2);
    return (char)((b[1] << 8) | (b[0] << 0));
  }

  public void close() throws Exception {
    fis.close();
  }
}

