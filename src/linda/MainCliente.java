package linda;

import java.io.IOException;
import java.util.Scanner;
/**
 * En esta clase se crearan todos los clientes, sera esta la que 
 * se ejecutara al iniciar un cliente.
 */
public class MainCliente {
	/**
	 * Pre: --- 
	 * Post: Este metodo creara el cliente y  lo iniciara.
	 */
  public static void main(String[] args) throws IOException {
      Cliente cli = new Cliente(); 
      System.out.println("Iniciando cliente\n");
      cli.startClient(); 
  }
}