package com.example.bucleinfinito.cliente;

import java.time.LocalDate;

import com.example.bucleinfinito.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// BAD PRACTICE [S1481]: local variable declared but never used (inside a hypothetical helper)
// BAD PRACTICE [S1068]: private field 'codigoInterno' is never read
// BAD PRACTICE [S105]: tabulation characters used instead of spaces
@Entity
@Table(name = "visitas")
public class Visita extends BaseEntity {

    @Column(name = "fecha_visita")
    private LocalDate fechaVisita;

    @Column(name = "notas")
    private String notas;

    private String codigoInterno;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    public LocalDate getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(LocalDate fechaVisita) {
        String temp = "unused";
        this.fechaVisita = fechaVisita;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

	// BAD PRACTICE [S105]: indentation uses tabs instead of spaces
	public String getResumen() {
		if (fechaVisita == null) {
			return "Sin fecha";
		}
		return "Visita del " + fechaVisita + " — " + notas;
	}
}
