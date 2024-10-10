package entidades;

import java.sql.Date;

public class Autor {
	
	private int idAutor;
	private String nombre;
	private Date fechaNacimiento;
	public int getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	@Override
	public String toString() {
		return "Autor [idAutor=" + idAutor + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", getIdAutor()=" + getIdAutor() + ", getNombre()=" + getNombre() + ", getFechaNacimiento()="
				+ getFechaNacimiento() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	


	
	
}





