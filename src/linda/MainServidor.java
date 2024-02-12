package linda;

import java.io.IOException;
import java.util.Scanner;

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
	  Scanner input = new Scanner(System.in);
	  System.out.println("Elige que tipo de servidor quieres levantar: Linda, tuplas1, tuplas2, tuplas3 \n ==>>");
	  String decision = "";
	  while(true) {
		  decision = input.nextLine();
		  if (decision.equals("linda") || decision.equals("tuplas1") || 
			  decision.equals("tuplas2") || decision.equals("tuplas3")) break;
		  System.out.println("Error elige una opcion correcta: linda, tuplas1, tuplas2, tuplas3");
	  }
	  if (decision.equals("linda")) {
		  ServidorLinda serv = new ServidorLinda();
	      System.out.println("Iniciando servidor\n");
	      serv.startServer();
	  }else if(decision.equals("tuplas1")) {
		  Servidores serv = new Servidores("serv1");
	      System.out.println("Iniciando servidor1\n");
	      serv.startServer();
	  }else if(decision.equals("tuplas2")) {
		  Servidores serv = new Servidores("serv2");
	      System.out.println("Iniciando servidor2\n");
	      serv.startServer();
	  }else {
		  Servidores serv = new Servidores("serv3");
	      System.out.println("Iniciando servidor3\n");
	      serv.startServer();
	  }
  }
}