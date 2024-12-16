package com.ar.duxsoftware.futbol.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String liga;
    @Column(nullable = false)
    private String pais;

    public Equipo() {}

    public Equipo(final String nombre, final String liga, final String pais) {
        this.nombre = nombre;
        this.liga = liga;
        this.pais = pais;
    }

    public Equipo(final Integer id, final String nombre, final String liga, final String pais) {
        this.id = id;
        this.nombre = nombre;
        this.liga = liga;
        this.pais = pais;
    }

    public Integer getId() {
        return id;
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
