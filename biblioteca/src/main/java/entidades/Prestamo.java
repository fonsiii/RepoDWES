package entidades;

import java.sql.Date;

public class Prestamo {

	private int idEjemplar;
	private int idSocio;
	private Date fechaPrestamo;
	private Date fechaLimiteDevolucion;
	public int getIdEjemplar() {
		return idEjemplar;
	}
	public void setIdEjemplar(int idEjemplar) {
		this.idEjemplar = idEjemplar;
	}
	public int getIdSocio() {
		return idSocio;
	}
	public void setIdSocio(int idSocio) {
		this.idSocio = idSocio;
	}
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public Date getFechaLimiteDevolucion() {
		return fechaLimiteDevolucion;
	}
	public void setFechaLimiteDevolucion(Date fechaLimiteDevolucion) {
		this.fechaLimiteDevolucion = fechaLimiteDevolucion;
	}
	@Override
	public String toString() {
		return "Prestamo [idEjemplar=" + idEjemplar + ", idSocio=" + idSocio + ", fechaPrestamo=" + fechaPrestamo
				+ ", fechaLimiteDevolucion=" + fechaLimiteDevolucion + ", getIdEjemplar()=" + getIdEjemplar()
				+ ", getIdSocio()=" + getIdSocio() + ", getFechaPrestamo()=" + getFechaPrestamo()
				+ ", getFechaLimiteDevolucion()=" + getFechaLimiteDevolucion() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	}
