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
	private Socket csGen;
	private final int PUERTO1 = 4321;
	private final int PUERTO2 = 5678;
	private final int PUERTO3 = 9101;
	private final int PUERTOReplica = 6587;
	private final String HOST1 = "172.30.100.145";
	private final String HOST2 = "172.30.100.145";
	private final String HOST3 = "172.30.100.145";
	private final String HOSTReplica = "172.30.100.145";
	public ThreadLinda(Socket csGen) {
		this.csGen = csGen;
	}
	private void copiaDatos(Socket cs, Socket csReplica, String tipo) {
		try {
			
			
		}catch(Exception e) {
			
		}
		
	}
	private String clienteRun(Socket cs, String tipo, ArrayList<String> tupla) {
		String devolucion = "";
		try {
			DataInputStream in = new DataInputStream(cs.getInputStream());
			DataOutputStream out = new DataOutputStream(cs.getOutputStream());
			ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
			out.writeUTF(tipo);
			System.out.println(in.readUTF());
			outObj.writeObject(tupla);
			devolucion = in.readUTF();
			System.out.println(devolucion);
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
					System.out.println(activo);
					cs = new Socket();
					cs.connect(new InetSocketAddress(HOST1,PUERTO1), 500);
					out.writeUTF(clienteRun(cs,accion,tupla));
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
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(csGen.getInputStream());
	        DataOutputStream out = new DataOutputStream(csGen.getOutputStream());
	        ObjectInputStream inObject = new ObjectInputStream(csGen.getInputStream());
	        out.writeUTF("BIENVENIDO AL SISTEMA LINDA");
	        while(true) {
				String accion = in.readUTF();
	            System.out.println(accion);
	            if(accion.equals("terminar")) break;
	            else gestionConexion(accion,in,out,inObject);
			}
	        csGen.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
