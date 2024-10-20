package entidades;

import java.sql.Date;

public class LibroMoroso {
	
	private String tituloLibro;
	private Date fechaPrestamo;
	private int diasDemora;
	public String getTituloLibro() {
		return tituloLibro;
	}
	public void setTituloLibro(String tituloLibro) {
		this.tituloLibro = tituloLibro;
	}
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public int getDiasDemora() {
		return diasDemora;
	}
	public void setDiasDemora(int diasDemora) {
		this.diasDemora = diasDemora;
	}
	@Override
	public String toString() {
		return "LibroMoroso [tituloLibro=" + tituloLibro + ", fechaPrestamo=" + fechaPrestamo + ", diasDemora="
				+ diasDemora + ", getTituloLibro()=" + getTituloLibro() + ", getFechaPrestamo()=" + getFechaPrestamo()
				+ ", getDiasDemora()=" + getDiasDemora() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
