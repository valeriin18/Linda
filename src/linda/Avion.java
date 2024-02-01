package linda;

import java.util.concurrent.Semaphore;
/**
 * En esta clase crearemos un avion que contendra un semaforo y
 * una tabla con las plazas.
 */
public class Avion {
	private String[][] plazasAvion;
	private Semaphore s1;
	
	public Avion() {
		this.plazasAvion = new String[][]{{"1A","1B","1C","1D"},{"2A","2B","2C","2D"},
						   {"3A","3B","3C","3D"},{"4A","4B","4C","4D"}};
		this.s1 = new Semaphore(1);
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo comprobara la existencia de plazas libres, si no hay
	 * devolvera true y si hay devolvera false.
	 */
	public boolean noHayPlazas() {
		String vacio = "XX";
		for(int i = 0; i < this.plazasAvion.length; i++) {
			for(int j = 0; j < this.plazasAvion[i].length; j++) {
				if(!(vacio.equals(this.plazasAvion[i][j]))) {
					return false;
				}
			}
		}
		return true;	
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo devuelve un String con las plazas mostrando las 
	 * libres y ocupadas.
	 */
	public String mostrarPlazas() {
		String plazas = "Los asientos libres son: ";
		for(int i = 0; i < this.plazasAvion.length; i++) {
			for(int j = 0; j < this.plazasAvion[i].length; j++) {
				plazas += this.plazasAvion[i][j] + " ";
			}
			plazas += "| ";
		}
		return plazas;
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo recibe un string con la plaza que se desea buscar 
	 * y si se encuentra libre devuelve true y si esta ocupada o no existe 
	 * devuelve false.
	 */
	public boolean buscarPlaza(String plaza) {
			try {
				this.s1.acquire();
				for(int i = 0; i < this.plazasAvion.length; i++) {
					for(int j = 0; j < this.plazasAvion[i].length; j++) {
						if(plaza.equals(this.plazasAvion[i][j])) {
							return true;
						}
					}
				}
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			s1.release();
			return false;
	}
	/**
	 * Pre: --- 
	 * Post: Este metodo busca la plaza que se le pasa como parametro y la 
	 * vacia igualando el nombre de la plaza a XX.
	 */
	public void vaciarPlaza(String plaza) {
		for(int i = 0; i < this.plazasAvion.length; i++) {
			for(int j = 0; j < this.plazasAvion[i].length; j++) {
				if(plaza.equals(this.plazasAvion[i][j])) {
					this.plazasAvion[i][j] = "XX";
					break;
				}
			}
		}
		s1.release();
	}
}
