package linda;

import java.io.IOException;

/**
 * Esta clase inicia el servidor creando hilos para cada conexion.
 */
public class ServidorLinda extends Conexion { 
	
    public ServidorLinda() throws IOException {
    	super("servidor");
    }
    /**
	 * Pre: --- 
	 * Post: Este metodo entra en bucle infinito de escucha de clientes, 
	 * crea un hilo y aloja al cliente en ese hilo.
	 */
    public void startServer() {
        try {
        	while(true) {
        		System.out.println("Esperando...");
                csl = ss.accept(); 
                ThreadLinda hilo = new ThreadLinda(csl);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}