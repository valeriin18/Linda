package linda;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Esta clase es el hilo del servidor delegado a un cliente.
 */
public class ThreadFuncion extends Thread{
	private Socket cs;
	private Avion avion1;
	
	public ThreadFuncion(Socket cs, Avion avion) {
		this.cs = cs;
		this.avion1 = avion;
	} 
	/**
	 * Pre: --- 
	 * Post: Este metodo se comunica con el cliente dandole acceso 
	 * a el avion y respondiendo a sus peticiones de compra hasta 
	 * que el avion esta lleno.
	 */
	public void run() {
		try {
			System.out.println("Cliente en línea");
			ArrayList<String> cartera = new ArrayList<String>();
	        DataInputStream in = new DataInputStream(cs.getInputStream());
	        DataOutputStream out = new DataOutputStream(cs.getOutputStream());
	        out.writeUTF("Petición recibida y aceptada.");
	        out.writeUTF(avion1.mostrarPlazas());
	        String nombre = in.readUTF();
	        System.out.println("\nBIENVENIDO " + nombre + "!\n");
	        while (true) {
	        	System.out.println("Esperando...");
	            String mensaje = in.readUTF();
	            System.out.println(mensaje);
	            if (mensaje.equalsIgnoreCase("END OF SERVICE")) break;
	            System.out.println("El cliente \"" + nombre + "\" desea comprar el asiento -> " + mensaje);
	            if (avion1.buscarPlaza(mensaje)) {
	                System.out.println("Asiento " + mensaje + " ha sido comprado por el cliente " + nombre + " con éxito.");
	                avion1.vaciarPlaza(mensaje);
	                out.writeUTF("\nPlaza de avión comprada con éxito. \n" + avion1.mostrarPlazas());
	                cartera.add(mensaje);
	            } else if (avion1.noHayPlazas()) {
	                System.out.println("VUELO COMPLETO");
	                out.writeUTF("VUELO COMPLETO");
	                out.writeUTF("Hola " + nombre + ", le informamos de que ha realizado la \n"
	                		   + "compra de un total de " + cartera.size() + " asientos que son: \n" + cartera);
	                break;
	            } else {
	                out.writeUTF("\nError: ¡esa plaza ya ha sido comprada por otro cliente! o no Existe!\n" + avion1.mostrarPlazas());
	            }

	        }
	        cs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
	    }
	}
}
