package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadLinda extends Thread{
	private Socket csl;
	public ThreadLinda(Socket csl) {
		this.csl = csl;
	} 
	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(csl.getInputStream());
	        DataOutputStream out = new DataOutputStream(csl.getOutputStream());
	        out.writeUTF("Petición recibida y aceptada.");
	        System.out.println(in.readUTF());
	        out.writeUTF("Que tal todo");
	        csl.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
