package com.example.bucleinfinito.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

// BAD PRACTICE [S1104]: public fields without encapsulation
@MappedSuperclass
public class Person extends BaseEntity {

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "apellido")
    public String apellido;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
