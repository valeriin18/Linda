package linda;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Esta clase contendra todos los datos de conexion entre el cliente y servidor.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion {
    // Puertos para la conexión. Deben ser diferentes para evitar conflictos.
    private final int PUERTO = 1234; // Puerto para que los clientes se conecten a Linda
    private final int PUERTO1 = 4321;
	private final int PUERTO2 = 5678;
	private final int PUERTO3 = 9101;
    public static int puertoLindaReplica = 6587; // Puerto para que Linda se conecte al servidor
    private final String HOST = "localhost"; // Host para la conexió
    protected ServerSocket ss; // Socket del servidor
    protected ServerSocket ss1; // Socket del servidor
    protected ServerSocket ss2; // Socket del servidor
    protected ServerSocket ss3; // Socket del servidor
    protected ServerSocket ssReplica; // Socket del servidor
    protected Socket cs; // Socket del cliente

    public Conexion(String tipo, int numero) throws IOException { 
        if (tipo.equalsIgnoreCase("servidorLinda")) {
            ss = new ServerSocket(PUERTO);
        } else if (tipo.equalsIgnoreCase("servidor")) {
        	if(numero == 1) ss1 = new ServerSocket(PUERTO1);
        	else if(numero == 2) ss2 = new ServerSocket(PUERTO2);
        	else if(numero == 3) ss3 = new ServerSocket(PUERTO3);
        	else if(numero == 4) ssReplica = new ServerSocket(puertoLindaReplica);
        } else if (tipo.equalsIgnoreCase("cliente")) {
            cs = new Socket(HOST, PUERTO);
        }
    }
}
