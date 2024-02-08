package linda;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Esta clase contendra todos los datos de conexion entre el cliente y servidor.
 */
public class Conexion {
    private final int PUERTO = 1234;
    private final int PUERTOL = 4321;
    private final String HOSTLinda = "localhost";
    private final String HOST1 = "localhost";
    private final String HOST2 = "localhost";
    private final String HOST3 = "localhost";
    protected ServerSocket ss;
    protected Socket csl;
    protected Socket cs1; 
    protected Socket cs2; 
    protected Socket cs3; 
    /**
     * Pre: --- 
     * Post: Esta clase comprobara si el que hace la peticion es servidor o cliente 
     * y se ejecutara la conexion.
     */
    public Conexion(String tipo) throws IOException {
        if(tipo.equalsIgnoreCase("servidor")) {
            ss = new ServerSocket(PUERTO);
        }else if(tipo.equalsIgnoreCase("servidorLinda")) {
        	ss = new ServerSocket(PUERTOL);
        }
        else if(tipo.equalsIgnoreCase("cliente")){
            csl = new Socket(HOSTLinda, PUERTOL);
        }else if(tipo.equalsIgnoreCase("clienteLinda")) {
        	cs1 = new Socket(HOST1, PUERTO);
            cs2 = new Socket(HOST2, PUERTO);
            cs3 = new Socket(HOST3, PUERTO);
        }
    }
}
