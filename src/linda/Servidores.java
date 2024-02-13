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
    }
	/**
	 * Pre: --- 
	 * Post: Este metodo entra en bucle infinito de escucha de clientes, 
	 * crea un hilo y aloja al cliente en ese hilo.
	 */
	private void startServ1() {
		try {
        	while(true) {
        		this.tuplas1 = new BaseDeDatos();
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
        	while(true) {
        		this.tuplas2 = new BaseDeDatos();
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
        	while(true) {
        		this.tuplas3 = new BaseDeDatos();
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
        if(tipo.equals("serv1")) startServ1();
        else if(tipo.equals("serv2")) startServ2();
        else startServ3();
    }
}
