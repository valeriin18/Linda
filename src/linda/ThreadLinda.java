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
	private Socket cs;
	public ThreadLinda(Socket cs) {
		this.cs = cs;
	}
	
	private String clienteRun(OutputStream outLin, InputStream inLin, String tipo, ArrayList<String> tupla) {
		String devolucion = "";
		try {
			DataInputStream in = new DataInputStream(inLin);
			DataOutputStream out = new DataOutputStream(outLin);
			ObjectOutputStream outObj = new ObjectOutputStream(outLin);
			out.writeUTF(tipo);
			System.out.println(in.readUTF());
			outObj.writeObject(tupla);
			devolucion = in.readUTF();
			System.out.println(devolucion);
			///////////////////////////////////////////////////pasamos al serv tipo y tupla y ya valdria esto.
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devolucion;
	}
	
	private void postNote(String accion, DataInputStream in, DataOutputStream out, ObjectInputStream inObject) {
		try {
			if(accion.equals("PostNote")) {
				out.writeUTF("Accion recibida, has elegido PostNote.");
				out.writeUTF("Devuelve que tupla deseas añadir.");
			}else if(accion.equals("ReadNote")) {
				out.writeUTF("Accion recibida, has elegido ReadNote.");
				out.writeUTF("Devuelve que tupla deseas buscar.");
			}else {
				out.writeUTF("Accion recibida, has elegido RemoveNote.");
				out.writeUTF("Devuelve que tupla deseas borrar.");
			}
			ArrayList<String> tupla = (ArrayList<String>) inObject.readObject();
			Conexion conexionLinda = new Conexion("clienteLinda");
			System.out.println(tupla);
			if(tupla.size() <= 3) {
				out.writeUTF(clienteRun(conexionLinda.cs.getOutputStream(), conexionLinda.cs.getInputStream(),accion,tupla));
			}else if(tupla.size() > 3 && tupla.size() <= 5) {
				out.writeUTF(clienteRun(conexionLinda.cs.getOutputStream(), conexionLinda.cs.getInputStream(),accion,tupla));
			}else out.writeUTF(clienteRun(conexionLinda.cs.getOutputStream(), conexionLinda.cs.getInputStream(),accion,tupla));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(cs.getInputStream());
	        DataOutputStream out = new DataOutputStream(cs.getOutputStream());
	        ObjectInputStream inObject = new ObjectInputStream(cs.getInputStream());
	        out.writeUTF("BIENVENIDO AL SISTEMA LINDA");
	        while(true) {
				String accion = in.readUTF();
	            System.out.println(accion);
	            if(accion.equals("terminar")) break;
	            else postNote(accion,in,out,inObject);
			}
	        cs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
