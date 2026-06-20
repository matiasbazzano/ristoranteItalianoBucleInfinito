package com.example.bucleinfinito.cliente;

import com.example.bucleinfinito.model.BaseEntity;
import jakarta.persistence.*;

// BAD PRACTICE [S1068]: private field 'seccion' is declared but never read
@Entity
@Table(name = "mesas")
public class Mesa extends BaseEntity {

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "activa")
    private boolean activa;

    private String seccion;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
