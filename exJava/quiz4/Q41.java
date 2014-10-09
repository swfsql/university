import java.nio.*;
import java.nio.channels.*;
import java.io.*;

public class Q41 {

  public static void main(String args[]) throws Exception {

    FileOutputStream fos = new FileOutputStream(args[args.length - 1]);
    FileChannel fc = fos.getChannel();
    ReadableByteChannel rbc;

    for(int i = 0; i < args.length - 1; i++) {
      rbc = Channels.newChannel(new FileInputStream(new File(args[i])));
      fc.transferFrom(rbc, fc.size(), Long.MAX_VALUE);
    }
    fos.close();
  }
}

