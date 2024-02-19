package linda;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Esta clase permite el simular una base de datos siendo un objeto que 
 * consiste en un array list de array list.
 */
public class BaseDeDatos {
	ArrayList<ArrayList<String>> content;
	private Semaphore s1;

	public BaseDeDatos() {
		this.content = new ArrayList<>();
		this.s1 = new Semaphore(2);
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo permite el leer la base de datos y comprobar
	 * si la tupla a buscar se encuentra en ella. hay otro metodo parecido
	 * pero este se a creado para poder gestionar con semaforos ya que el otro
	 * metodo lo usan mas metodos.
	 */
	public String read(ArrayList<String> tupla) {
		try {
			s1.acquire(1);
			ArrayList<String> tuplaEncontrada = new ArrayList<>();
			tuplaEncontrada = search(tupla);
			if( tuplaEncontrada == null) {
				return("Error la tupla no se encuentra en la base de datos");
			}else {
				return(tuplaEncontrada.toString());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			s1.release(1);
		}
		return null;
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo busca una tupla en la base de datos y la devuelve.
	 */
	public ArrayList<String> search(ArrayList<String> tupla){
		boolean seEncuentra = false;
		ArrayList<String> tuplaABorrar = new ArrayList<>();
		for( ArrayList<String> tuplita : content) {
			if(tuplita.size() == tupla.size()) {
				for(int i = 0; i < tupla.size();i++) {
					if(!(tupla.get(i).contains("?"))) {
						if(tupla.get(i).equals(tuplita.get(i))) seEncuentra = true;
						else seEncuentra = false;
					}
				}
				if (seEncuentra) {
					tuplaABorrar = tuplita;
					break;
				}
			}
		}
		if(seEncuentra == false) return null;
		else return tuplaABorrar;
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo permite el borrar una tupla de la base de datos.
	 */
	public synchronized String remove(ArrayList<String> tupla) {
		try {
			s1.acquire(2);
			if(!(content.isEmpty())) {
				int indice = 0;
				for(int i = 0; i < content.size(); i++) {
					if(tupla == content.get(i)) {
						indice = i;
						break;
					}
				}
				content.remove(indice);
				return("Tupla borrada correctamente!");
			}else return("No es posible borrar el dato puesto que la base "
					+ "de datos esta vacia.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			s1.release(2);
		}
		return null;
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo permite el añadir una tupla a la base de datos.
	 */
	public synchronized void add(ArrayList<String> tupla) {
		try {
			s1.acquire(1);
			content.add(tupla);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			s1.release(1);
		}
		
	}
}
