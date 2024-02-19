package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * esta clase comprueba el estado de los servidores y copia los datos si es necesario.
 */
public class ThreadCopiaServidores extends Thread{
	private final int PUERTO1 = 4321;
	private final int PUERTOReplica = 6587;
	private final String HOST1 = "localhost";
	private final String HOSTReplica = "localhost";
	boolean serv1Activo = true;
	boolean replicaActivo = true;
	
	/**
	 * Pre: --- 
	 * Post: Este metodo comprobara el servidor 1 y si se cae al levantarse de nuevo
	 * copiara los datos de replica a este..
	 */
	public void vidaCopiaServidor1() throws ClassNotFoundException {
		try {
			Socket cs = new Socket(HOST1,PUERTO1);
			if(serv1Activo == false) {
				Socket csReplica= new Socket(HOSTReplica,PUERTOReplica);
				ObjectOutputStream outObj = new ObjectOutputStream
						(csReplica.getOutputStream());
				outObj.writeObject("bajar");
				ObjectInputStream inObj = new ObjectInputStream(csReplica.getInputStream());
				ArrayList<ArrayList<String>> descarga = (ArrayList<ArrayList<String>>) 
						inObj.readObject();
				csReplica.close();
				ObjectOutputStream outObj1 = new ObjectOutputStream(cs.getOutputStream());
				outObj1.writeObject("subir");
				outObj1.writeObject(descarga);
				serv1Activo = true;
			}else {
				ObjectOutputStream outObj1 = new ObjectOutputStream(cs.getOutputStream());
				ObjectInputStream inObj = new ObjectInputStream(cs.getInputStream());
				outObj1.writeObject("vida");
				System.out.println("\n" +  inObj.readObject());
			}
			cs.close();
		}catch(IOException e) {
			serv1Activo = false;
			System.out.println("\nError servidor 1 caido.");
		} 
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo comprobara el servidor replica y si se cae al levantarse de nuevo
	 * copiara los datos de servidor 1 a este..
	 */
	public void vidaCopiaServidorReplica() throws ClassNotFoundException {
		try {
			Socket csReplica = new Socket(HOSTReplica,PUERTOReplica);
			if(replicaActivo == false) {
				Socket cs = new Socket(HOST1,PUERTO1);
				ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
				outObj.writeObject("bajar");
				ObjectInputStream inObj = new ObjectInputStream(cs.getInputStream());
				ArrayList<ArrayList<String>> descarga = (ArrayList<ArrayList<String>>) 
						inObj.readObject();
				cs.close();
				ObjectOutputStream outObj1 = new ObjectOutputStream(csReplica.getOutputStream());
				outObj1.writeObject("subir");
				outObj1.writeObject(descarga);
				replicaActivo = true;
			}else {
				ObjectOutputStream outObj1 = new ObjectOutputStream(csReplica.getOutputStream());
				ObjectInputStream inObj = new ObjectInputStream(csReplica.getInputStream());
				outObj1.writeObject("vida");
				System.out.println(inObj.readObject() + "\n");
			}
			csReplica.close();
		}catch(IOException e) {
			replicaActivo = false;
			System.out.println("\nError servidor replica caido.");
		} 
	}
	/**
	 * Pre: --- 
	 * Post: ESte metodo creara un bucle infinito que cada 30 segundos 
	 * comprobara la vida de los servidores y copiara si es necesario.
	 */
	public void run() {
		while(true) {
			try {
				Thread.sleep(30000);
				vidaCopiaServidor1();
				vidaCopiaServidorReplica();
			} catch (InterruptedException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}