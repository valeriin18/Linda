package linda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Esta clase inicia el servidor creando hilos para cada conexion.
 */
public class ServidorLinda extends Conexion { 
	
    public ServidorLinda() throws IOException {
    	super("servidorLinda",0);
    }
    /**
	 * Pre: --- 
	 * Post: Este metodo entra en bucle infinito de escucha de clientes, 
	 * crea un hilo y aloja al cliente en ese hilo.
	 */
    public void startServer() {
        try {
        	while(true) {
        		//ThreadCopiaServers copia = new ThreadCopiaServers(); 
				//copia.start();
        		System.out.println("Esperando...");
                cs = ss.accept(); 
                System.out.println("Cliente en línea");
    	        DataInputStream in = new DataInputStream(cs.getInputStream());
    	        DataOutputStream out = new DataOutputStream(cs.getOutputStream());
    	        ObjectInputStream inObject = new ObjectInputStream(cs.getInputStream());
                ThreadLinda hilo = new ThreadLinda(cs,in,out,inObject);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}