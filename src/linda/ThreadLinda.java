package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadLinda extends Thread{
	private Socket csl;
	private Conexion conexionLinda;
	public ThreadLinda(Socket csl) {
		this.csl = csl;
		try {
			this.conexionLinda = new Conexion("clienteLinda");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void clienteRun() {
		try {
			DataInputStream in = new DataInputStream(conexionLinda.cs1.getInputStream());
			DataOutputStream out = new DataOutputStream(conexionLinda.cs1.getOutputStream());
            System.out.println(in.readUTF());
            out.writeUTF("Hola soy cliente linda");
            System.out.println(in.readUTF());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(csl.getInputStream());
	        DataOutputStream out = new DataOutputStream(csl.getOutputStream());
	        out.writeUTF("Petición recibida y aceptada.");
	        System.out.println(in.readUTF());
	        out.writeUTF("Que tal todo");
	        clienteRun();
	        csl.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
