package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ThreadS extends Thread {
	private Socket cs1;
	public ThreadS(Socket cs1) {
		this.cs1 = cs1;
	} 
	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(cs1.getInputStream());
	        DataOutputStream out = new DataOutputStream(cs1.getOutputStream());
	        out.writeUTF("Petición recibida y aceptada.");
	        System.out.println(in.readUTF());
	        out.writeUTF("encantado de conocerte soy server1");
	        cs1.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
