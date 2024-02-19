package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Esta clase contendra el codigo de uso de los clientes.
 */
public class Cliente extends Conexion {
    public Cliente() throws IOException {
    	super("cliente",0);
    } 
    
    public ArrayList<String> creacionTupla(Scanner entrada, String eleccionP){
    	ArrayList<String> tupla = new ArrayList<>();
    	while(true) {
    		System.out.println("\nIntroduce los strings que constituyen la tupla, \n"
    						 + "si se introduce \"EXIT\" se terminara la introduccion.");
    		String eleccion = entrada.nextLine();
    		if(eleccion.contains("?") && eleccionP.equals("1"))System.out.println("Error al añadir no puedes ingresar ?");
    		else if(eleccion.equals("EXIT")) break;
    		else if(eleccion.equals("")) System.out.println("\nError debe introducir algun caracter.");
    		else tupla.add(eleccion);
    	}
    	return tupla;
    }
    
	/**
	 * Pre: --- 
	 * Post: En este metodo se solicitara al cliente su nombre de usuario 
	 * y luego entrara en bucle de compra de billetes hasta que el avion 
	 * este vacio.
	 */
    public void startClient() {
    	try {
			DataInputStream in = new DataInputStream(cs.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(cs.getOutputStream());
            String mensaje = in.readUTF();
            System.out.println(mensaje);
            Scanner entrada = new Scanner(System.in);
            while(true) {
            	System.out.print("\nElija una de las siguientes opciones: \n"
            			+ " 1.PostNote. \n 2.RemoveNote. \n 3.ReadNote. \n"
            			+ " 4.Salir. \n==>>");
            	String eleccion = entrada.nextLine();
            	if (eleccion.equals("1")) {
            		out.writeObject("PostNote");
            		System.out.println(in.readUTF());
            		System.out.println(in.readUTF());
            		ArrayList<String> tupla = creacionTupla(entrada,eleccion);
            		out.writeObject(tupla);
            		System.out.println(in.readUTF());
            		
            		
            	}else if(eleccion.equals("2")){
            		out.writeObject("RemoveNote");
            		System.out.println(in.readUTF());
            		System.out.println(in.readUTF());
            		ArrayList<String> tupla = creacionTupla(entrada,eleccion);
            		out.writeObject(tupla);
            		System.out.println(in.readUTF());
            		
            	}else if(eleccion.equals("3")) {
            		out.writeObject("ReadNote");
            		System.out.println(in.readUTF());
            		System.out.println(in.readUTF());
            		ArrayList<String> tupla = creacionTupla(entrada,eleccion);
            		out.writeObject(tupla);
            		System.out.println(in.readUTF());

            	}else if(eleccion.equals("4")) {
            		out.writeObject("terminar");
            		System.out.println(in.readUTF());
            		System.out.println("\nSaliendo del Sistema... Hasta Pronto!");
            		break;
            	}
            	else System.out.println("\nError elija una opcion correcta...");
            }
            cs.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }
}