package linda;

import java.io.IOException;

/**
 * Esta clase inicia el servidor creando hilos para cada conexion.
 */
public class Servidor extends Conexion { 
	
    public Servidor() throws IOException {
    	super("servidor");
    }
    /**
	 * Pre: --- 
	 * Post: Este metodo entra en bucle infinito de escucha de clientes, 
	 * crea un hilo y aloja al cliente en ese hilo.
	 */
    public void startServer() {
        try {
        	Avion avion1 = new Avion();
        	avion1.mostrarPlazas();
        	while(true) {
        		System.out.println("Esperando...");
                cs = ss.accept(); 
                ThreadFuncion hilo = new ThreadFuncion(cs,avion1);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}