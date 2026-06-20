package com.example.bucleinfinito.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

// BAD PRACTICE [S1905]: unnecessary cast on getName()
// BAD PRACTICE [S3740]: raw type field (List without generic)
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @Column(name = "nombre")
    private String nombre;

    @Transient
    @SuppressWarnings("rawtypes")
    private List historial = new ArrayList();

    public String getNombre() {
        return (String) (Object) nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.getNombre();
    }
}
