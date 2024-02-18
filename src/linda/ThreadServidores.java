package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadServidores extends Thread {
	Socket cs;
	private BaseDeDatos tuplas;
	public ThreadServidores(Socket cs,BaseDeDatos tuplas) {
		this.cs = cs;
		this.tuplas = tuplas;
	}
	
	public BaseDeDatos getTuplas() {
		return tuplas;
	}

	public void setTuplas(BaseDeDatos tuplas) {
		this.tuplas = tuplas;
	}

	public void postNote(ObjectOutputStream out, ObjectInputStream inObj) {
		try {
			out.writeObject("Accion recibida todo correcto, Linda");
			ArrayList<String> tupla = (ArrayList<String>) inObj.readObject(); 
			tuplas.add(tupla);
			out.writeObject("Tupla añadida correctamente!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeNote(ObjectOutputStream out, ObjectInputStream inObj) {
		try {
			out.writeObject("Accion recibida todo correcto, Linda");
			ArrayList<String> tupla = (ArrayList<String>) inObj.readObject(); 
			ArrayList<String>tuplaAborrar = tuplas.search(tupla);
			if( tuplaAborrar == null) {
				out.writeObject("Error la tupla no se encuentra en la base de datos");
			}else {
				out.writeObject(tuplas.remove(tuplaAborrar));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readNote(ObjectOutputStream out, ObjectInputStream inObj) {
		try {
			out.writeObject("Accion recibida todo correcto, Linda");
			ArrayList<String> tupla = (ArrayList<String>) inObj.readObject(); 
			String StringtuplaEncontrada = tuplas.read(tupla);
			out.writeObject(StringtuplaEncontrada);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			System.out.println("Cliente en línea");
	        DataInputStream in = new DataInputStream(cs.getInputStream());
	        ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
	        ObjectInputStream inObj = new ObjectInputStream(cs.getInputStream());
	        
	        String mensaje = in.readUTF();
	        if(mensaje.equals("PostNote")) postNote(outObj,inObj);
	        else if(mensaje.equals("RemoveNote")) removeNote(outObj,inObj);
	        else if(mensaje.equals("bajar")) outObj.writeObject(getTuplas());
	        else if(mensaje.equals("subir")) setTuplas((BaseDeDatos)inObj.readObject());
	        else readNote(outObj,inObj);
	        System.out.println("El contenido: " + tuplas.content);
	        cs.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
