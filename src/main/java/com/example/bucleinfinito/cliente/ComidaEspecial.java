package com.example.bucleinfinito.cliente;

import com.example.bucleinfinito.model.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "comidas_especiales")
public class ComidaEspecial extends NamedEntity {
}
