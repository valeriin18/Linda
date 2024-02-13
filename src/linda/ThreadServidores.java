package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadServidores extends Thread {
	private Socket cs;
	private BaseDeDatos tuplas;
	public ThreadServidores(Socket cs,BaseDeDatos tuplas) {
		this.cs = cs;
		this.tuplas = tuplas;
	}
	
	public void postNote(DataInputStream in, DataOutputStream out, ObjectInputStream inObj) {
		try {
			out.writeUTF("Accion recibida todo correcto, Linda");
			ArrayList<String> tupla = (ArrayList<String>) inObj.readObject(); 
			tuplas.add(tupla);
			out.writeUTF("Tupla añadida correctamente!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeNote(DataInputStream in, DataOutputStream out, ObjectInputStream inObj) {
		try {
			out.writeUTF("Accion recibida todo correcto, Linda");
			ArrayList<String> tupla = (ArrayList<String>) inObj.readObject(); 
			ArrayList<String>tuplaAborrar = tuplas.search(tupla);
			if( tuplaAborrar == null) {
				out.writeUTF("Error la tupla no se encuentra en la base de datos");
			}else {
				out.writeUTF(tuplas.remove(tuplaAborrar));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readNote(DataInputStream in, DataOutputStream out, ObjectInputStream inObj) {
		try {
			out.writeUTF("Accion recibida todo correcto, Linda");
			ArrayList<String> tupla = (ArrayList<String>) inObj.readObject(); 
			ArrayList<String>tuplaEncontrada = tuplas.search(tupla);
			if( tuplaEncontrada == null) {
				out.writeUTF("Error la tupla no se encuentra en la base de datos");
			}else {
				String tuplaAbierta = "";
				for(String valor : tuplaEncontrada) {
					tuplaAbierta += valor + " ";
				}
				out.writeUTF(tuplaAbierta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(cs.getInputStream());
	        DataOutputStream out = new DataOutputStream(cs.getOutputStream());
	        ObjectInputStream inObj = new ObjectInputStream(cs.getInputStream());
	        String mensaje = in.readUTF();
	        if(mensaje.equals("PostNote")) postNote(in,out,inObj);
	        else if(mensaje.equals("RemoveNote")) removeNote(in,out,inObj);
	        else readNote(in,out,inObj);
	        cs.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
