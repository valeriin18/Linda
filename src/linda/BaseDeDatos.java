package linda;

import java.util.ArrayList;

public class BaseDeDatos {
	ArrayList<ArrayList<String>> content;

	public BaseDeDatos() {
		this.content = new ArrayList<>();
	}

	public boolean isEmpty() {
		if(content.size() == 0) {
			return true;
		}
		return false;
	}
	public ArrayList<ArrayList<String>> getContent() {
		return content;
	}
	
	public void remove(int posicion) {
		if(!(isEmpty())) content.remove(posicion);
		else System.out.println("No es posible borrar el dato puesto que la base de datos esta vacia.");
	}
	
	public void add(ArrayList<String> tupla) {
		content.add(tupla);
	}
}
