package entidades;

public class Ejemplar {

	private int idEjemplar;
	private String isbn;
	private String baja;
	public int getIdEjemplar() {
		return idEjemplar;
	}
	public void setIdEjemplar(int idEjemplar) {
		this.idEjemplar = idEjemplar;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getBaja() {
		return baja;
	}
	public void setBaja(String baja) {
		this.baja = baja;
	}
	@Override
	public String toString() {
		return "Ejemplar [idEjemplar=" + idEjemplar + ", isbn=" + isbn + ", baja=" + baja + ", getIdEjemplar()="
				+ getIdEjemplar() + ", getIsbn()=" + getIsbn() + ", getBaja()=" + getBaja() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
