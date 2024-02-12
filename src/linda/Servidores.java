package linda;

import java.io.IOException;

public class Servidores extends Conexion{
	String tipo;
	BaseDeDatos tuplas1;
	BaseDeDatos tuplas2;
	BaseDeDatos tuplas3;
	public Servidores(String tipo) throws IOException {
		super("servidor");
		this.tipo = tipo;
		this.tuplas1 = new BaseDeDatos();
		this.tuplas2 = new BaseDeDatos();
		this.tuplas3 = new BaseDeDatos();
    	
    }
	/**
	 * Pre: --- 
	 * Post: Este metodo entra en bucle infinito de escucha de clientes, 
	 * crea un hilo y aloja al cliente en ese hilo.
	 */
	private void startServ1() {
		try {
        	while(true) {
        		System.out.println("Esperando...");
        		cs1 = ss.accept(); 
                ThreadS hilo = new ThreadS(cs1,tuplas1);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
	
	private void startServ2() {
		try {
        	while(true) {
        		System.out.println("Esperando...");
        		cs2 = ss.accept(); 
                ThreadS hilo = new ThreadS(cs2,tuplas2);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
	
	private void startServ3() {
		try {
        	while(true) {
        		System.out.println("Esperando...");
        		cs3 = ss.accept(); 
                ThreadS hilo = new ThreadS(cs3,tuplas3);
                hilo.start();
        	}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
    public void startServer() {
        if(tipo.equals("serv1")) startServ1();
        else if(tipo.equals("serv2")) startServ2();
        else startServ3();
    }
}
