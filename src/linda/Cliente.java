package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
/**
 * Esta clase contendra el codigo de uso de los clientes.
 */
public class Cliente extends Conexion {
    public Cliente() throws IOException {
    	super("cliente");
    } 
    /**
	 * Pre: --- 
	 * Post: En este metodo se solicitara al cliente su nombre de usuario 
	 * y luego entrara en bucle de compra de billetes hasta que el avion 
	 * este vacio. 
	 */
    private void postNote(DataInputStream in, DataOutputStream out) {
    	try {
    		out.writeUTF("Holaa linda");
    		System.out.println(in.readUTF());
    	}catch(Exception e) {
    		
    	}
    }
    /**
	 * Pre: --- 
	 * Post: En este metodo se solicitara al cliente su nombre de usuario 
	 * y luego entrara en bucle de compra de billetes hasta que el avion 
	 * este vacio.
	 */
    private void removeNote() {
    	try {
    		
    	}catch(Exception e) {
    		
    	}
    }
    /**
	 * Pre: --- 
	 * Post: En este metodo se solicitara al cliente su nombre de usuario 
	 * y luego entrara en bucle de compra de billetes hasta que el avion 
	 * este vacio.
	 */
    private void readNote() {
    	try {
    		
    	}catch(Exception e) {
    		
    	}
    }
	/**
	 * Pre: --- 
	 * Post: En este metodo se solicitara al cliente su nombre de usuario 
	 * y luego entrara en bucle de compra de billetes hasta que el avion 
	 * este vacio.
	 */
    public void startClient() {
    	try {
			DataInputStream in = new DataInputStream(csl.getInputStream());
            DataOutputStream out = new DataOutputStream(csl.getOutputStream());
            String mensaje = in.readUTF();
            System.out.println(mensaje);
            System.out.println("BIENVENIDO AL SISTEMA LINDA");
            Scanner entrada = new Scanner(System.in);
            while(true) {
            	System.out.print("Elija una de las siguientes opciones: \n"
            			+ " 1.PostNote. \n 2.RemoveNote. \n 3.ReadNote. \n"
            			+ " 4.Salir. \n==>>");
            	int eleccion = entrada.nextInt();
            	if (eleccion == 1) {
            		postNote(in,out);
            	}else if (eleccion == 2) {
            		removeNote();
            	}else if (eleccion == 3) {
            		readNote();
            	}else if(eleccion == 4){
            		System.out.println("Saliendo del sistema...");
            		break;
            	}else {
            		System.out.println("Error elija una opcion correcta...");
            	}
            }
            csl.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }
}