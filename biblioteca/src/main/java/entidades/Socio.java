package entidades;

public class Socio {

	private int idSocio;
	private String email;
	private String nombre;
	private String direccion;
	private int version;
	public int getIdSocio() {
		return idSocio;
	}
	public void setIdSocio(int idSocio) {
		this.idSocio = idSocio;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "Socio [idSocio=" + idSocio + ", email=" + email + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", version=" + version + ", getIdSocio()=" + getIdSocio() + ", getEmail()=" + getEmail()
				+ ", getNombre()=" + getNombre() + ", getDireccion()=" + getDireccion() + ", getVersion()="
				+ getVersion() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
	
	
	
}
