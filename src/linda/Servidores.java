package linda;

import java.io.IOException;

public class Servidores extends Conexion{
	int numero;
	BaseDeDatos tuplas1;
	BaseDeDatos tuplas2;
	BaseDeDatos tuplas3;
	public Servidores(int numero) throws IOException {
		super("servidor",numero);
		this.numero = numero;
		
    }
	/**
	 * Pre: --- 
	 * Post: Este metodo entra en bucle infinito de escucha de clientes, 
	 * crea un hilo y aloja al cliente en ese hilo.
	 */
	private void startServ1() {
		try {
			this.tuplas1 = new BaseDeDatos();
        	while(true) {
        		System.out.println("Esperando...");
        		cs = ss1.accept(); 
                ThreadServidores hilo = new ThreadServidores(cs,tuplas1);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
	
	private void startServ2() {
		try {
			this.tuplas2 = new BaseDeDatos();
        	while(true) {
        		System.out.println("Esperando...");
        		cs = ss2.accept(); 
                ThreadServidores hilo = new ThreadServidores(cs,tuplas2);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
	
	private void startServ3() {
		try {
			this.tuplas3 = new BaseDeDatos();
        	while(true) {
        		System.out.println("Esperando...");
        		cs = ss3.accept(); 
                ThreadServidores hilo = new ThreadServidores(cs,tuplas3);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
    public void startServer() {
        if(numero == 1) startServ1();
        else if(numero == 2) startServ2();
        else startServ3();
    }
}
