package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadCopiaServers extends Thread {
	private final int PUERTO1 = 4321;
	private final int PUERTOReplica = 6587;
	private final String HOST1 = "localhost";
	private final String HOSTReplica = "localhost";
	private Socket cs = null;
	private Socket replica = null;
	
	public ThreadCopiaServers() {
	}
	private void comprobarVida(boolean serv1, boolean servReplica) {
		try {
			 cs.connect(new InetSocketAddress(HOST1,PUERTO1), 500);
			 DataInputStream in = new DataInputStream(cs.getInputStream());
			 DataOutputStream out = new DataOutputStream(cs.getOutputStream());
			 ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
			 DataInputStream inR = new DataInputStream(replica.getInputStream());
			 DataOutputStream outR = new DataOutputStream(replica.getOutputStream());
			 ObjectOutputStream outObjR = new ObjectOutputStream(replica.getOutputStream());
			 ObjectInputStream inObjR = new ObjectInputStream(replica.getInputStream());
			 if(serv1 == false) {
				 outR.writeUTF("descarga");
				 BaseDeDatos recivo = (BaseDeDatos) inObjR.readObject();
				 out.writeUTF("subida");
				 outObj.writeObject(recivo);
				 serv1 = true;
			 }
		 }catch(Exception e) {
			 serv1 = false;
		 }try {
			 replica.connect(new InetSocketAddress(HOSTReplica,PUERTOReplica), 500);
			 DataInputStream in = new DataInputStream(cs.getInputStream());
			 DataOutputStream out = new DataOutputStream(cs.getOutputStream());
			 ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
			 ObjectInputStream inObj = new ObjectInputStream(replica.getInputStream());
			 DataInputStream inR = new DataInputStream(replica.getInputStream());
			 DataOutputStream outR = new DataOutputStream(replica.getOutputStream());
			 ObjectOutputStream outObjR = new ObjectOutputStream(replica.getOutputStream());
			 if(servReplica == false) {
				 out.writeUTF("descarga");
				 BaseDeDatos recivo = (BaseDeDatos) inObj.readObject();
				 outR.writeUTF("subida");
				 outObjR.writeObject(recivo);
				 servReplica = true;
			 }
		 }catch(Exception e) {
			 servReplica = false;
		 }
	}
	public void run() {
		boolean serv1 = true;
		boolean servReplica = true;
		 while(true) {
			 try {
				 comprobarVida(serv1,servReplica);
				 Thread.sleep(60000);
			} catch (InterruptedException e) {
			}
		 }
	}
}
