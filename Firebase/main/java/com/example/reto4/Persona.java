package com.example.reto4;

public class Persona {
    private String idPersona;
    private String nombre;
    private String telefono;
    private String fecharegistro;
    private Long timestamp;

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(final String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = telefono;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(final String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
