package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadCopiaServidores extends Thread{
	private final int PUERTO1 = 4321;
	private final int PUERTOReplica = 6587;
	private final String HOST1 = "localhost";
	private final String HOSTReplica = "localhost";
	
	public void run() {
		boolean serv1Activo = true;
		boolean replicaActivo = true;
		while(true) {
			try {
				try (Socket cs = new Socket(HOST1,PUERTO1)) {
					if(!(serv1Activo)) {
						try (Socket csReplica = new Socket(HOSTReplica,PUERTOReplica)) {
							DataOutputStream out = new DataOutputStream(csReplica.getOutputStream());
							out.writeUTF("bajar");
							ObjectInputStream inObj = new ObjectInputStream(csReplica.getInputStream());
							BaseDeDatos descarga = (BaseDeDatos) inObj.readObject();
							csReplica.close();
							DataOutputStream out1 = new DataOutputStream(cs.getOutputStream());
							out1.writeUTF("subir");
							ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
							outObj.writeObject(descarga);
							cs.close();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					cs.close();
				} catch (IOException e) {
					serv1Activo = false;
					e.printStackTrace();
				}
				try (Socket csReplica = new Socket(HOSTReplica,PUERTOReplica)) {
					if(!(replicaActivo)) {
						try (Socket cs = new Socket(HOSTReplica,PUERTOReplica)) {
							DataOutputStream out = new DataOutputStream(cs.getOutputStream());
							out.writeUTF("bajar");
							ObjectInputStream inObj = new ObjectInputStream(cs.getInputStream());
							BaseDeDatos descarga = (BaseDeDatos) inObj.readObject();
							cs.close();
							DataOutputStream out1 = new DataOutputStream(csReplica.getOutputStream());
							out1.writeUTF("subir");
							ObjectOutputStream outObj = new ObjectOutputStream(csReplica.getOutputStream());
							outObj.writeObject(descarga);
							csReplica.close();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					csReplica.close();
				} catch (IOException e) {
					replicaActivo = false;
					e.printStackTrace();
				}
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}