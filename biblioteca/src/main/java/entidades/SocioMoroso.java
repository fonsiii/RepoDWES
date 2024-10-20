package entidades;

public class SocioMoroso {
	private int id;
	private String nombre;
	private int diasMoroso;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getDiasMoroso() {
		return diasMoroso;
	}
	public void setDiasMoroso(int diasMoroso) {
		this.diasMoroso = diasMoroso;
	}
	@Override
	public String toString() {
		return "SocioMoroso [id=" + id + ", nombre=" + nombre + ", diasMoroso=" + diasMoroso + ", getId()=" + getId()
				+ ", getNombre()=" + getNombre() + ", getDiasMoroso()=" + getDiasMoroso() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
