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
    public static int PUERTOLinda1= 4321; // Puerto para que Linda se conecte al servidor
    public static int PUERTOLinda2 = 5678; // Puerto para que Linda se conecte al servidor
    public static int PUERTOLinda3 = 9101; // Puerto para que Linda se conecte al servidor
    private final String HOST = "localhost"; // Host para la conexión
    protected ServerSocket ss; // Socket del servidor
    protected ServerSocket ss1; // Socket del servidor
    protected ServerSocket ss2; // Socket del servidor
    protected ServerSocket ss3; // Socket del servidor
    protected Socket cs; // Socket del cliente

    public Conexion(String tipo) throws IOException { 
        if (tipo.equalsIgnoreCase("servidorLinda")) {
            ss = new ServerSocket(PUERTO);
        } else if (tipo.equalsIgnoreCase("servidor")) {
            ss1 = new ServerSocket(PUERTOLinda1);
            ss2 = new ServerSocket(PUERTOLinda2);
            ss3 = new ServerSocket(PUERTOLinda3);
        } else if (tipo.equalsIgnoreCase("cliente")) {
            cs = new Socket(HOST, PUERTO);
        }
    }
}
