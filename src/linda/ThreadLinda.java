package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadLinda extends Thread{
	private Socket cs;
	DataInputStream in;
	DataOutputStream out;
	ObjectInputStream inObj;
	private final int PUERTO1 = 4321;
	private final int PUERTO2 = 5678;
	private final int PUERTO3 = 9101;
	private final int PUERTOReplica = 6587;
	private final String HOST1 = "localhost";
	private final String HOST2 = "localhost";
	private final String HOST3 = "localhost";
	private final String HOSTReplica = "localhost";
	
	public ThreadLinda(Socket cs,DataInputStream in,DataOutputStream out,ObjectInputStream inObj) {
		this.cs = cs;
		this.in = in;
		this.out = out;
		this.inObj = inObj;
	}
	
	private String clienteRun(Socket cs, String accion, ArrayList<String> tupla) {
		String devolucion = "";
		try {
			System.out.println("3");
			DataInputStream in = new DataInputStream(cs.getInputStream());
			DataOutputStream out = new DataOutputStream(cs.getOutputStream());
			ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
			System.out.println("4");
			out.writeUTF(accion);
			System.out.println("5");
			System.out.println(in.readUTF());
			System.out.println("6");
			outObj.writeObject(tupla);
			System.out.println("7");
			devolucion = in.readUTF();
			System.out.println("8");
			System.out.println(devolucion);
			System.out.println("9");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devolucion;
	}
	
	private void gestionConexion(String accion, DataInputStream in, DataOutputStream out, ObjectInputStream inObject) {
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
			System.out.println(tupla);
			if(tupla.size() <= 3) {
				Socket cs = null;
				Socket csReplica = null;
				Boolean activo = true;
				try {
					System.out.println("1");
					cs = new Socket();
					cs.connect(new InetSocketAddress(HOST1,PUERTO1), 500);
					System.out.println("2");
					out.writeUTF(clienteRun(cs,accion,tupla));
					System.out.println("10");
					cs.close();
				}catch(Exception e) {
					activo = false;
					System.out.println(activo);
				}try {
					csReplica = new Socket();
					csReplica.connect(new InetSocketAddress(HOSTReplica,PUERTOReplica), 500);
					if(activo == false) {
						out.writeUTF(clienteRun(csReplica,accion,tupla));
						csReplica.close();
					}else {
						clienteRun(csReplica,accion,tupla);
						csReplica.close();
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
			}else if(tupla.size() > 3 && tupla.size() <= 5) {
				Socket cs = new Socket(HOST2,PUERTO2);
				out.writeUTF(clienteRun(cs,accion,tupla));
				cs.close();
			}else {
				Socket cs = new Socket(HOST3,PUERTO3);
				out.writeUTF(clienteRun(cs,accion,tupla));
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void run() {
		try {
	        out.writeUTF("BIENVENIDO AL SISTEMA LINDA");
	        while(true) {
				String accion = in.readUTF();
	            System.out.println(accion);
	            if(accion.equals("terminar")) break;
	            else gestionConexion(accion,in,out,inObj);
			}
	        cs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
