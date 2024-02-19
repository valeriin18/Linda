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
	 * Post: Este metodo elegiras que servidor deseas levantar y lo iniciara.
	 */
  public static void main(String[] args) throws IOException {
	  Scanner input = new Scanner(System.in);
	  System.out.println("\nElige que tipo de servidor quieres levantar: Linda, "
	  		+ "tuplas1, replica, tuplas2, tuplas3 \n ==>>");
	  String decision = "";
	  while(true) {
		  decision = input.nextLine();
		  if (decision.equals("linda") || decision.equals("tuplas1") || 
			  decision.equals("tuplas2") || decision.equals("tuplas3")
			  || decision.equals("replica")) break;
		  System.out.println("\nError elige una opcion correcta: linda, tuplas1, "
		  		+ "replica, tuplas2, tuplas3");
	  }
	  if (decision.equals("linda")) {
		  ServidorLinda serv = new ServidorLinda();
	      System.out.println("Iniciando servidor\n");
	      serv.startServer();
	  }else if(decision.equals("tuplas1")) {
		  Servidores serv = new Servidores(1);
	      System.out.println("Iniciando servidor1\n");
	      serv.startServer();
	  }else if(decision.equals("replica")) {
		  Servidores serv = new Servidores(4);
	      System.out.println("Iniciando servidor replica\n");
	      serv.startServer();
	  }else if(decision.equals("tuplas2")) {
		  Servidores serv = new Servidores(2);
	      System.out.println("Iniciando servidor2\n");
	      serv.startServer();
	  }else {
		  Servidores serv = new Servidores(3);
	      System.out.println("Iniciando servidor3\n");
	      serv.startServer();
	  }
  }
}