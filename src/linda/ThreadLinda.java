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

/**
 * Esta clase es el hilo de linda y se encarga de llevar la conversacion
 * con el cliente.
 */
public class ThreadLinda extends Thread{
	private Socket cs;
	private final int PUERTO1 = 4321;
	private final int PUERTO2 = 5678;
	private final int PUERTO3 = 9101;
	private final int PUERTOReplica = 6587;
	private final String HOST1 = "172.30.100.145";
	private final String HOST2 = "172.30.100.145";
	private final String HOST3 = "172.30.100.145";
	private final String HOSTReplica = "172.30.100.145";
	public ThreadLinda(Socket cs) {
		this.cs = cs;
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo creara y iniciara un cliente Linda conectado al servidor que 
	 * sea necesario para obtener sus datos y mas tarde devolverselos al cliente.
	 */
	private String clienteRun(Socket cs, String tipo, ArrayList<String> tupla) {
		String devolucion = "";
		try {
			ObjectInputStream in = new ObjectInputStream(cs.getInputStream());
			ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
			outObj.writeObject(tipo);
			System.out.println(in.readObject());
			outObj.writeObject(tupla);
			devolucion = (String) in.readObject();
			System.out.println(devolucion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return devolucion;
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo comprobara la tupla recivida y la accion a realizar para
	 * mandar que el cliente linda se conecte al servidor que sea necesario en funcion
	 * de su longitud.
	 * @throws ClassNotFoundException 
	 */
	private void gestionConexion(String accion, DataOutputStream out, 
			ObjectInputStream inObject) throws ClassNotFoundException {
		try {
			if(accion.equals("PostNote")) {
				out.writeUTF("\nAccion recibida, has elegido PostNote.");
				out.writeUTF("Devuelve que tupla deseas añadir.\n");
			}else if(accion.equals("ReadNote")) {
				out.writeUTF("\nAccion recibida, has elegido ReadNote.");
				out.writeUTF("Devuelve que tupla deseas buscar.\n");
			}else {
				out.writeUTF("\nAccion recibida, has elegido RemoveNote.");
				out.writeUTF("Devuelve que tupla deseas borrar.\n");
			}
			ArrayList<String> tupla = (ArrayList<String>) inObject.readObject();
			System.out.println(tupla);
			if(tupla.size() <= 3) {
				Socket cs = null;
				Socket csReplica = null;
				Boolean activo = true;
				try {
					cs = new Socket(HOST1,PUERTO1);
					out.writeUTF(clienteRun(cs,accion,tupla));
					cs.close();
				}catch(IOException e) {
					activo = false;
					System.out.println("\nError el servidor 1 esta caido");
				}try {
					csReplica = new Socket(HOSTReplica,PUERTOReplica);
					if(activo == false) {
						out.writeUTF(clienteRun(csReplica,accion,tupla));
						csReplica.close();
					}else {
						clienteRun(csReplica,accion,tupla);
						csReplica.close();
					}
				}catch(IOException e) {
					System.out.println("\nError el servidor replica esta caido");
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
		} catch (IOException e) {
			System.out.println("\nError el servidor 2 o 3 esta caido");
		}
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo sera el inicio de la conversacion y desde aqui se lanzara
	 * todo.
	 */
	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataOutputStream out = new DataOutputStream(cs.getOutputStream());
	        ObjectInputStream in = new ObjectInputStream(cs.getInputStream());
	        out.writeUTF("BIENVENIDO AL SISTEMA LINDA");
	        while(true) {
				String accion = (String) in.readObject();
	            System.out.println(accion);
	            if(accion.equals("terminar")) break;
	            else gestionConexion(accion,out,in);
			}
	        cs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
