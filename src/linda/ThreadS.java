package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ThreadS extends Thread {
	private Socket cs;
	private BaseDeDatos tuplas;
	public ThreadS(Socket cs,BaseDeDatos tuplas) {
		this.cs = cs;
		this.tuplas = tuplas;
	}
	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(cs.getInputStream());
	        DataOutputStream out = new DataOutputStream(cs.getOutputStream());
	        out.writeUTF("Petición recibida y aceptada.");
	        System.out.println(in.readUTF());
	        out.writeUTF("encantado de conocerte soy server1");
	        cs.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
