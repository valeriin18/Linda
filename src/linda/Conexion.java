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

/**
 * esta realiza las conexiones pertinentes en funcion de lo que se requiere.
 */
public class Conexion {
    private final int PUERTO = 1234; 
    public static int PUERTOLinda1= 4321; 
    public static int PUERTOLinda2 = 5678; 
    public static int PUERTOLinda3 = 9101;
    public static int puertoLindaReplica = 6587; 
    private final String HOST = "localhost";
    protected ServerSocket ss; 
    protected ServerSocket ss1; 
    protected ServerSocket ss2;
    protected ServerSocket ss3; 
    protected ServerSocket ssReplica;
    protected Socket cs;

    public Conexion(String tipo, int numero) throws IOException { 
        if (tipo.equalsIgnoreCase("servidorLinda")) {
            ss = new ServerSocket(PUERTO);
        } else if (tipo.equalsIgnoreCase("servidor")) {
        	if(numero == 1) ss1 = new ServerSocket(PUERTOLinda1);
        	else if(numero == 2) ss2 = new ServerSocket(PUERTOLinda2);
        	else if(numero == 3) ss3 = new ServerSocket(PUERTOLinda3);
        	else ssReplica = new ServerSocket(puertoLindaReplica);
        } else if (tipo.equalsIgnoreCase("cliente")) {
            cs = new Socket(HOST, PUERTO);
        }
    }
}
