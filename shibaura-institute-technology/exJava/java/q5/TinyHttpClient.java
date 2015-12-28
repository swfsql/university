// based on Professor Fukuda's "HeeloClient.java"

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class TinyHttpClient {
	
	private final int PORT = 8888;
	private final String HOST = "localhost";
	
	private Socket socket;
	
	public static void main(String[] args) {
		try {
			new TinyHttpClient().process();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TinyHttpClient() {		
	}
	
	public void process() throws IOException {
		socket = new Socket(HOST, PORT);
		
		InputStream in = socket.getInputStream();
		
		//BufferedInputStream bin = new BufferedInputStream(in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String message = reader.readLine();
		
		in.close();
		
		System.out.println("message = " + message);
		
		
	}

}
