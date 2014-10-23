// based on Professor Fukuda's "HelloServer.java"

import java.lang.Thread;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


// Helloと返すだけのサーバ
public class TinyHttpServer {

	// ポート番号
	private final int PORT = 8888;
	// サーバソケット
	private ServerSocket serverSocket;
	
	
	public static void main(String[] args) {
		new TinyHttpServer().process();
	}
	
	public TinyHttpServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			serverSocket.setReuseAddress(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void process() {		
		Socket connection = null;
		System.out.println("Server Start");
		
		// コネクションを無限に待つ
		while(true) {
			try {
				// コネクションを受けつける
				connection = serverSocket.accept();
				System.out.println("Accept");
				// メッセージを送る
        new Thread(new SendMessage(connection)).start();
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}			
	}
	

}

class SendMessage implements Runnable {
  Socket connection;

  SendMessage(Socket connection) throws IOException {
    this.connection = connection;
  }

  public void run() {
    try {
		  String msg = "Hello TinyHttpServer\n";
		  byte[] data = msg.getBytes();
		  OutputStream out = connection.getOutputStream();
		  out.write(data, 0, data.length);
    } catch(Exception e) {

    }

  }

}


