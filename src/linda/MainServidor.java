package linda;

import java.io.IOException;

/** 
 * En esta clase se creara el Servidor, sera esta la que 
 * se ejecutara al iniciar un servidor.
 */
public class MainServidor {
	/**
	 * Pre: --- 
	 * Post: Este metodo creara y iniciara el servidor.
	 */
  public static void main(String[] args) throws IOException {
      Servidor serv = new Servidor();
      System.out.println("Iniciando servidor\n");
      serv.startServer();
  }
}