package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadLinda extends Thread{
	private Socket csl;
	public ThreadLinda(Socket csl) {
		this.csl = csl;
	}
	
	private void clienteRun(OutputStream outLin, InputStream inLin, String tipo, ArrayList<String> tupla) {
		try {
			DataInputStream in = new DataInputStream(inLin);
			DataOutputStream out = new DataOutputStream(outLin);
			
			///////////////////////////////////////////////////pasamos al serv tipo y tupla y ya valdria esto.
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	private void postNote(DataInputStream in, DataOutputStream out, ObjectInputStream inObject) {
		try {
			String tipo = "pn";
			out.writeUTF("Accion recibida, has elegido PostNote.");
			out.writeUTF("Devuelve que tupla deseas añadir.");
			ArrayList<String> tupla = (ArrayList<String>) inObject.readObject();
			Conexion conexionLinda = new Conexion("clienteLinda");
			System.out.println(tupla);
			if(tupla.size() <= 3) {
				clienteRun(conexionLinda.cs1.getOutputStream(), conexionLinda.cs1.getInputStream(),tipo,tupla);
			}else if(tupla.size() > 3 && tupla.size() <= 5) {
				clienteRun(conexionLinda.cs2.getOutputStream(), conexionLinda.cs2.getInputStream(),tipo,tupla);
			}else clienteRun(conexionLinda.cs3.getOutputStream(), conexionLinda.cs3.getInputStream(),tipo,tupla);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void removeNote(DataInputStream in, DataOutputStream out, ObjectInputStream inObject) {
		try {
			out.writeUTF("Accion recibida, has elegido RemoveNote.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readNote(DataInputStream in, DataOutputStream out) {
		try {
			out.writeUTF("Accion recibida, has elegido ReadNote.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(csl.getInputStream());
	        DataOutputStream out = new DataOutputStream(csl.getOutputStream());
	        ObjectInputStream inObject = new ObjectInputStream(csl.getInputStream());
	        out.writeUTF("BIENVENIDO AL SISTEMA LINDA");
	        while(true) {
				String accion = in.readUTF();
	            System.out.println(accion);
	            if(accion.equals("PostNote")) postNote(in,out,inObject);
	            else if(accion.equals("RemoveNote")) removeNote(in,out,inObject);
	            else if(accion.equals("terminar")) break;
	            else readNote(in, out);
			}
	        csl.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
