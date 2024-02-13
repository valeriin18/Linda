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
	
	public String remove(ArrayList<String> tupla) {
		if(!(isEmpty())) {
			int indice = 0;
			for(int i = 0; i < content.size(); i++) {
				if(tupla == content.get(i)) {
					indice = i;
					break;
				}
			}
			return("Tupla borrada correctamente!");
		}else return("No es posible borrar el dato puesto que la base de datos esta vacia.");
	}
	
	public void add(ArrayList<String> tupla) {
		content.add(tupla);
	}
}
