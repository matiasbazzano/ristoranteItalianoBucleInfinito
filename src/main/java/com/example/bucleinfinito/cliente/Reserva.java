package com.example.bucleinfinito.cliente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.bucleinfinito.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// BAD PRACTICE [S1132]: String comparison with == instead of equals()
// BAD PRACTICE [S1135]: TODO left unresolved
@Entity
@Table(name = "reservas")
public class Reserva extends BaseEntity {

    @NotNull
    @Column(name = "dia")
    private LocalDate dia;

    @NotNull
    @Column(name = "hora")
    private LocalTime hora;

    @Min(1)
    @Column(name = "cantidad_personas")
    private Integer cantidadPersonas;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "tipo_mesa_id")
    private TipoMesa tipoMesa;

    @ManyToOne
    @JoinColumn(name = "comida_especial_id")
    private ComidaEspecial comidaEspecial;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Visita> visitas = new ArrayList<>();

    public boolean estaConfirmada() {
        // TODO: validar también el campo hora antes de confirmar
        return estado == "CONFIRMADA";
    }

    // BAD PRACTICE [S125]: commented-out code
    // public double calcularPrecioEstimado() {
    //     double base = cantidadPersonas * 25.0;
    //     if (tipoMesa != null && tipoMesa.getNombre().equals("VIP")) base *= 1.5;
    //     return base;
    // }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public TipoMesa getTipoMesa() {
        return tipoMesa;
    }

    public void setTipoMesa(TipoMesa tipoMesa) {
        this.tipoMesa = tipoMesa;
    }

    public ComidaEspecial getComidaEspecial() {
        return comidaEspecial;
    }

    public void setComidaEspecial(ComidaEspecial comidaEspecial) {
        this.comidaEspecial = comidaEspecial;
    }

    public List<Visita> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<Visita> visitas) {
        this.visitas = visitas;
    }
}
