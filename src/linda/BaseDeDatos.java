package linda;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class BaseDeDatos {
	ArrayList<ArrayList<String>> content;
	private Semaphore s1;

	public BaseDeDatos() {
		this.content = new ArrayList<>();
		this.s1 = new Semaphore(2);
	}
	
	public String read(ArrayList<String> tupla) {
		try {
			s1.acquire(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			s1.release(1);
		}
		ArrayList<String> tuplaEncontrada = new ArrayList<>();
		tuplaEncontrada = search(tupla);
		if( tuplaEncontrada == null) {
			return("Error la tupla no se encuentra en la base de datos");
		}else {
			return(tuplaEncontrada.toString());
		}
	}
	public ArrayList<String> search(ArrayList<String> tupla){
		boolean seEncuentra = false;
		ArrayList<String> tuplaABorrar = new ArrayList<>();
		for( ArrayList<String> tuplita : content) {
			if(tuplita.size() == tupla.size()) {
				for(int i = 0; i < tupla.size();i++) {
					if(!(tupla.get(i).contains("?"))) {
						if(tupla.get(i).equals(tuplita.get(i))) {
							seEncuentra = true;
						}else seEncuentra = false;
					}
				}
				if (seEncuentra) {
					tuplaABorrar = tuplita;
					break;
				}
			}
		}
		if(seEncuentra == false) {
			return null;
		}else {
			return tuplaABorrar;
		}
	}
	
	public synchronized String remove(ArrayList<String> tupla) {
		try {
			s1.acquire(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			s1.release(2);
		}
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
		}else return("No es posible borrar el dato puesto que la base de datos esta vacia.");
	}
	
	public synchronized void add(ArrayList<String> tupla) {
		try {
			s1.acquire(1);
			content.add(tupla);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		s1.release(1);
	}
}
