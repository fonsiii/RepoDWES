package entidades;

import java.sql.Date;

public class SocioPenalizado {

    private int idSocio;
    private Date limitePenalizacion;

    // Constructor por defecto (sin parámetros)
    public SocioPenalizado() {
        // Puedes inicializar los campos con valores por defecto si lo deseas
    }

    // Constructor con parámetros
    public SocioPenalizado(int idSocio, Date limitePenalizacion) {
        this.idSocio = idSocio;
        this.limitePenalizacion = limitePenalizacion;
    }

    // Getter y Setter para idSocio
    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    // Getter y Setter para limitePenalizacion
    public Date getLimitePenalizacion() {
        return limitePenalizacion;
    }

    public void setLimitePenalizacion(Date limitePenalizacion) {
        this.limitePenalizacion = limitePenalizacion;
    }

    // Método toString simplificado
    @Override
    public String toString() {
        return "SocioPenalizado [idSocio=" + idSocio + ", limitePenalizacion=" + limitePenalizacion + "]";
    }
}
