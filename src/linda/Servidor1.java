package linda;

import java.io.IOException;

public class Servidor1 extends Conexion{
	public Servidor1() throws IOException {
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
                cs1 = ss.accept(); 
                ThreadS hilo = new ThreadS(cs1);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
