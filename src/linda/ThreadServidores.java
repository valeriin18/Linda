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
	
	public ArrayList<ArrayList<String>> getTuplas() {
		return tuplas.content;
	}

	public void setTuplas(ArrayList<ArrayList<String>> tuplas) {
		this.tuplas.content = tuplas;
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
	        ObjectOutputStream outObj = new ObjectOutputStream(cs.getOutputStream());
	        ObjectInputStream inObj = new ObjectInputStream(cs.getInputStream());
	        String mensaje = (String)inObj.readObject();
	        if(mensaje.equals("PostNote")) postNote(outObj,inObj);
	        else if(mensaje.equals("RemoveNote")) removeNote(outObj,inObj);
	        else if(mensaje.equals("bajar")) {
	        	ArrayList<ArrayList<String>> descarga = getTuplas();
	        	outObj.writeObject(descarga);
	        }
	        else if(mensaje.equals("subir")) setTuplas((ArrayList<ArrayList<String>>)inObj.readObject());
	        else if(mensaje.equals("vida")) outObj.writeObject("Esto es una linea de vida");
	        else readNote(outObj,inObj);
	        System.out.println(tuplas.content);
	        cs.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
