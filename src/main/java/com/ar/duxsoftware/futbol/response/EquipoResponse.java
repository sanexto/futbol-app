package com.ar.duxsoftware.futbol.response;

public class EquipoResponse {
    private Integer id;
    private String nombre;
    private String liga;
    private String pais;

    public EquipoResponse() {}

    public EquipoResponse(final Integer id, final String nombre, final String liga, final String pais) {
        this.id = id;
        this.nombre = nombre;
        this.liga = liga;
        this.pais = pais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(final String liga) {
        this.liga = liga;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(final String pais) {
        this.pais = pais;
    }
}
