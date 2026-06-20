package com.example.bucleinfinito.cliente;

import java.util.ArrayList;
import java.util.List;

import com.example.bucleinfinito.model.Person;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

// BAD PRACTICE [S2384]: getReservas() returns the internal mutable list directly
// BAD PRACTICE [S1144]: private method calcularPuntos() is never called (dead code)
// BAD PRACTICE [S1192]: String literal "El Bucle Infinito" duplicated multiple times
@Entity
@Table(name = "clientes")
public class Cliente extends Person {

    @NotBlank
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "vip")
    private boolean vip;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reserva> reservas = new ArrayList<>();

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setCliente(this);
    }

    public String getRestaurante() {
        return "El Bucle Infinito";
    }

    public String getSaludo() {
        return "Bienvenido a El Bucle Infinito, " + this.getNombre();
    }

    public String getPie() {
        return "Gracias por elegir El Bucle Infinito";
    }

    private int calcularPuntos() {
        return reservas.size() * 10;
    }

    // BAD PRACTICE [S125]: commented-out code
    // public int getNivelFidelidad() {
    //     if (reservas.size() >= 10) return 3;
    //     if (reservas.size() >= 5) return 2;
    //     return 1;
    // }
}
