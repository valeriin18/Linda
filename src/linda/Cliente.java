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
    public void startClient() {
    	try {
			DataInputStream in = new DataInputStream(csl.getInputStream());
            DataOutputStream out = new DataOutputStream(csl.getOutputStream());
            String mensaje = in.readUTF();
            System.out.println(mensaje);
            Scanner entrada = new Scanner(System.in);
            System.out.print("Introduzca su nombre, por favor: ");
            String nombre = entrada.nextLine();
            out.writeUTF(nombre);
            while (true) {
                String respuesta = in.readUTF();
                System.out.println(respuesta);
                if (respuesta.equals("VUELO COMPLETO")) break;
                System.out.print("\nEscriba la plaza que desea comprar: ");
                String cadena = entrada.nextLine();
                out.writeUTF(cadena);
                
            }
            csl.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }
}