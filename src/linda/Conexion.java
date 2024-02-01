package linda;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Esta clase contendra todos los datos de conexion entre el cliente y servidor.
 */
public class Conexion {
    private final int PUERTO = 1234;
    private final String HOST = "localhost";
    protected ServerSocket ss;
    protected Socket cs; 
    /**
     * Pre: --- 
     * Post: Esta clase comprobara si el que hace la peticion es servidor o cliente 
     * y se ejecutara la conexion.
     */
    public Conexion(String tipo) throws IOException {
        if(tipo.equalsIgnoreCase("servidor")) {
            ss = new ServerSocket(PUERTO);
        } else {
            cs = new Socket(HOST, PUERTO);
        }
    }
}
